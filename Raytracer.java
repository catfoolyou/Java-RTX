import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Raytracer{

    double hit_sphere(Vector3 center, double radius, Ray r) {
        Vector3 oc = center.subtract(r.origin);
        float a = r.direction.length();
        float h = Vector3.dot(r.direction, oc);
        float c = (float) (oc.length() - radius * radius);
        float discriminant = h * h - a * c;

        if (discriminant < 0) {
            return -1.0;
        } else {
            return (h - Math.sqrt(discriminant) ) / a;
        }
    }

    Vector3 ray_color(Ray r) {
        double t = hit_sphere(new Vector3(0,0,-1), 0.5, r);
        if (t > 0.0) {
            Vector3 N = (r.at((float) t).subtract(new Vector3(0,0,-1)));
            return new Vector3(N.x+1, N.y+1, N.z+1).multiply((float) 0.6);
        }

        Vector3 unit_direction = (r.direction);
        float a = (float) (0.5 * (unit_direction.y + 1.0));
        return new Vector3(1.0, 1.0, 1.0).multiply((float) 1.0 - a).add(new Vector3(0.5, 0.7, 1.0).multiply(a));
    }

    int image_width = 256;
    int image_height = 256;

    File image = new File("result.ppm");
    
    public Raytracer() throws IOException{
        double aspect_ratio = 16.0 / 9.0;
        image_width = 400;

        image_height = (int) (image_width / aspect_ratio);
        image_height = (image_height < 1) ? 1 : image_height;

        double focal_length = 1.0;
        double viewport_height = 2.0;
        double viewport_width = viewport_height * image_width/image_height;
        Vector3 camera_center = new Vector3(0, 0, 0);

        Vector3 viewport_u = new Vector3(viewport_width, 0, 0);
        Vector3 viewport_v = new Vector3(0, -viewport_height, 0);

        Vector3 pixel_delta_u = viewport_u.divide(new Vector3(image_width));
        Vector3 pixel_delta_v = viewport_v.divide(new Vector3(image_height));

        Vector3 viewport_upper_left = camera_center.subtract(new Vector3(0, 0, focal_length)).subtract(viewport_u.divide(new Vector3(2))).subtract(viewport_v.divide(new Vector3(2)));
        Vector3 pixel00_loc = viewport_upper_left.add((new Vector3(0.5)).multiply(pixel_delta_u.add(pixel_delta_v)));

        image.delete();
        BufferedWriter writer = new BufferedWriter(new FileWriter(image.getAbsoluteFile()));
        
        writer.write("P3\n" + image_width + " " + image_height + "\n" + "255\n");

        for (int j = 0; j < image_height; j++) {
            for (int i = 0; i < image_width; i++) {

                Vector3 pixel_center = pixel00_loc.add(pixel_delta_u.multiply(new Vector3(i))).add(pixel_delta_v.multiply(new Vector3(j)));
                Vector3 ray_direction = pixel_center.subtract(camera_center);
                Ray r = new Ray(camera_center, ray_direction);

                Vector3 pixel_color = ray_color(r);

                WriteColor.write_color(writer, pixel_color);
            }
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        Raytracer rtx = new Raytracer();
    }
}
