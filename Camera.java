
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Camera {

    final double infinity = Double.POSITIVE_INFINITY;
    final double pi = 3.1415926535897932385;

    double aspect_ratio = 16.0 / 9.0;  // Ratio of image width over height
    int image_width  = 400;  // Rendered image width in pixel count
    int samples_per_pixel = 10;
    int max_depth = 10;

    int image_height;   // Rendered image height
    Vector3 center;         // Camera center
    Vector3 pixel00_loc;    // Location of pixel 0, 0
    Vector3 pixel_delta_u;  // Offset to pixel to the right
    Vector3 pixel_delta_v;  // Offset to pixel below
    double pixel_samples_scale;

    File image = new File("result.ppm");

    public double random_double() {
        return Math.random();
    }
    
    public double random_double(double min, double max) {
        return min + (max-min) * random_double();
    }

    public void initialize(){
        image_height = (int) (image_width / aspect_ratio);
        image_height = (image_height < 1) ? 1 : image_height;
        pixel_samples_scale = 1.0 / samples_per_pixel;

        center = new Vector3(0, 0, 0);

        double focal_length = 1.0;
        double viewport_height = 2.0;
        double viewport_width = viewport_height * image_width/image_height;
        Vector3 camera_center = new Vector3(0, 0, 0);

        Vector3 viewport_u = new Vector3(viewport_width, 0, 0);
        Vector3 viewport_v = new Vector3(0, -viewport_height, 0);

        pixel_delta_u = viewport_u.divide(new Vector3(image_width));
        pixel_delta_v = viewport_v.divide(new Vector3(image_height));

        Vector3 viewport_upper_left = camera_center.subtract(new Vector3(0, 0, focal_length)).subtract(viewport_u.divide(new Vector3(2))).subtract(viewport_v.divide(new Vector3(2)));
        pixel00_loc = viewport_upper_left.add((new Vector3(0.5)).multiply(pixel_delta_u.add(pixel_delta_v)));
    }

    public void render(Hittable world) throws IOException {
        initialize();

        image.delete();
        BufferedWriter writer = new BufferedWriter(new FileWriter(image.getAbsoluteFile()));

        long startTime = System.currentTimeMillis();
        
        writer.write("P3\n" + image_width + " " + image_height + "\n" + "255\n");

        for (int j = 0; j < image_height; j++) {
            System.out.println("Scanlines remaining: " + (image_height - j));
            for (int i = 0; i < image_width; i++) {

                Vector3 pixel_color = new Vector3(0,0,0);
                
                for (int sample = 0; sample < samples_per_pixel; sample++) {
                    Ray r = get_ray(i, j);
                    pixel_color = pixel_color.add(ray_color(r, max_depth, world));
                }

                WriteColor.write_color(writer, pixel_color.multiply(pixel_samples_scale));
            }
        }
        writer.close();

        long endTime = System.currentTimeMillis();
        System.out.println("Done! (" + (endTime - startTime) / 1000.0 + " s)");
    }

    public Ray get_ray(int i, int j){
        // Construct a camera ray originating from the origin and directed at randomly sampled
        // point around the pixel location i, j.

        Vector3 offset = sample_square();
        Vector3 pixel_sample = pixel00_loc.add(pixel_delta_u.multiply(i + offset.x)).add(pixel_delta_v.multiply(j + offset.y));

        Vector3 ray_origin = center;
        Vector3 ray_direction = pixel_sample.subtract(ray_origin);

        return new Ray(ray_origin, ray_direction);
    }

    public Vector3 sample_square(){
        // Returns the vector to a random point in the [-.5,-.5]-[+.5,+.5] unit square.
        return new Vector3(random_double() - 0.5, random_double() - 0.5, 0);
    }

    Vector3 ray_color(Ray r, int depth, Hittable world) {
        if (depth <= 0)
            return new Vector3(0,0,0);

        hit_record rec = new hit_record();

        if (world.hit(r, new Interval(0.001, infinity), rec)) {
            
            Vector3 attenuation = rec.normal.add(Vector3.random_unit_vector());
            Ray scattered = new Ray(rec.p, attenuation);
            
            if (rec.material.scatter(r, rec)){

                attenuation = rec.material.albedo = new Vector3(0.5, 0.4, 0.9);
                //scattered = rec.material.scattered;
                
                return attenuation.multiply(ray_color(scattered, depth-1, world));
                //return ray_color(scattered, depth-1, world).multiply(0.5);
            }
            return new Vector3(0,0,0);
        }

        Vector3 unit_direction = (r.direction);
        float a = (float) (0.5 * (unit_direction.y + 1.0));
        return new Vector3(1).multiply((float) 1.0 - a).add(new Vector3(0.5, 0.7, 1.0).multiply(a));
    }
}
