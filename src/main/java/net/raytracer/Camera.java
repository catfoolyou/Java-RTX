package main.java.net.raytracer;

import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import javax.imageio.ImageIO;
import static javax.swing.WindowConstants.*;

public class Camera {

    double degrees_to_radians(double degrees) {
        return degrees * pi / 180.0;
    }

    final double infinity = Double.POSITIVE_INFINITY;
    final double pi = 3.1415926535897932385;

    double aspect_ratio = 16.0 / 9.0;  // Ratio of image width over height
    int image_width  = 400;  // Rendered image width in pixel count
    int samples_per_pixel = 10;
    int max_depth = 10;
    Vector3 background;

    int image_height;   // Rendered image height
    Vector3 center;         // Camera center
    Vector3 pixel00_loc;    // Location of pixel 0, 0
    Vector3 pixel_delta_u;  // Offset to pixel to the right
    Vector3 pixel_delta_v;  // Offset to pixel below
    double pixel_samples_scale;
    
    double vfov = 90;

    Vector3 lookfrom = new Vector3(0,0,0);   // Point camera is looking from
    Vector3 lookat = new Vector3(0,0,-1);  // Point camera is looking at
    Vector3 up = new Vector3(0,1,0); // Camera-relative "up" direction

    double defocus_angle = 0;  // Variation angle of rays through each pixel
    double focus_dist = 10; // Distance from camera lookfrom point to plane of perfect focus

    private Vector3 u, v, w;
    private Vector3 defocus_disk_u;       // Defocus disk horizontal radius
    private Vector3 defocus_disk_v;       // Defocus disk vertical radius

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

        center = lookfrom;

        double theta = degrees_to_radians(vfov);
        double h = Math.tan(theta / 2);
        double viewport_height = 2 * h * focus_dist;
        double viewport_width = viewport_height * image_width/image_height;

        w = Vector3.unit_vector(lookfrom.subtract(lookat));
        u = Vector3.unit_vector(Vector3.cross(up, w));
        v = Vector3.cross(w, u);

        Vector3 viewport_u = u.multiply(viewport_width);
        Vector3 viewport_v = new Vector3().subtract(v).multiply(viewport_height);

        pixel_delta_u = viewport_u.divide(new Vector3(image_width));
        pixel_delta_v = viewport_v.divide(new Vector3(image_height));

        Vector3 viewport_upper_left = center.subtract(w.multiply(focus_dist)).subtract(viewport_u.divide(new Vector3(2))).subtract(viewport_v.divide(new Vector3(2)));
        pixel00_loc = viewport_upper_left.add((new Vector3(0.5)).multiply(pixel_delta_u.add(pixel_delta_v)));

        double defocus_radius = focus_dist * Math.tan(degrees_to_radians(defocus_angle / 2));
        defocus_disk_u = u.multiply(defocus_radius);
        defocus_disk_v = v.multiply(defocus_radius);
    }

    public void render(Hittable world) throws IOException {
        initialize();

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(this.image_width, this.image_height));
        frame.setTitle("Raytracer");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        BufferedImage result = new BufferedImage(this.image_width, this.image_height, TYPE_INT_RGB);

        JLabel img = new JLabel(new ImageIcon(result));
        frame.add(img);
        frame.pack();

        long startTime = System.currentTimeMillis();

        for (int j = 0; j < image_height; j++) {
            System.out.println("Scanlines remaining: " + (image_height - j));
            for (int i = 0; i < image_width; i++) {

                Vector3 pixel_color = new Vector3(0,0,0);
                
                for (int sample = 0; sample < samples_per_pixel; sample++) {
                    Ray r = get_ray(i, j);
                    pixel_color = pixel_color.add(ray_color(r, max_depth, world));
                }

                Vector3 color = WriteColor.write_color(pixel_color.multiply(pixel_samples_scale));
                int rgb = (int) (65536 * color.x + 256 * color.y + color.z);
                result.setRGB(i, j, rgb);
                img.repaint();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Done! (" + (endTime - startTime) / 1000.0 + " s)");

        File outputfile = new File("result.jpg");

        int option = JOptionPane.showConfirmDialog(frame, "Save to file?", "Render completed", JOptionPane.YES_NO_OPTION);
        if(option == JOptionPane.YES_OPTION)
            ImageIO.write(result, "jpg", outputfile); 
        
    }



    public Ray get_ray(int i, int j){
        // Construct a camera ray originating from the origin and directed at randomly sampled
        // point around the pixel location i, j.

        Vector3 offset = sample_square();
        Vector3 pixel_sample = pixel00_loc.add(pixel_delta_u.multiply(i + offset.x)).add(pixel_delta_v.multiply(j + offset.y));

        Vector3 ray_origin = (defocus_angle <= 0) ? center : defocus_disk_sample();
        Vector3 ray_direction = pixel_sample.subtract(ray_origin);
        double ray_time = random_double(0, 0.25);

        return new Ray(ray_origin, ray_direction, ray_time);
    }

    public Vector3 sample_square(){
        // Returns the vector to a random point in the [-.5,-.5]-[+.5,+.5] unit square.
        return new Vector3(random_double() - 0.5, random_double() - 0.5, 0);
    }

    public Vector3 defocus_disk_sample(){
        // Returns a random point in the camera defocus disk.
        Vector3 p = Vector3.random_in_unit_disk();
        return center.add(defocus_disk_u.multiply(p.x)).add(defocus_disk_v.multiply(p.y));
    }

    Vector3 ray_color(Ray r, int depth, Hittable world) {
        if (depth <= 0)
            return new Vector3(0,0,0);

        hit_record rec = new hit_record();

        if (!world.hit(r, new Interval(0.001, infinity), rec))
            return background;

        if (world.hit(r, new Interval(0.001, infinity), rec)) {
            
            Vector3 attenuation = rec.normal.add(Vector3.random_unit_vector());
            Ray scattered = new Ray(rec.p, attenuation);
            Vector3 color_from_emission = rec.material.emitted(rec.u, rec.v, rec.p);
            
            if (rec.material.scatter(r, rec)){
                attenuation = rec.material.albedo;
                scattered = rec.material.scattered;
                
                return attenuation.multiply(ray_color(scattered, depth-1, world)).add(color_from_emission);
            }

            if (!rec.material.scatter(r, rec))
                return color_from_emission;

            return new Vector3(0,0,0);
        }

        Vector3 unit_direction = (r.direction);
        float a = (float) (0.5 * (unit_direction.y + 1.0));
        return new Vector3(1).multiply((float) 1.0 - a).add(background.multiply(a));
    }
}
