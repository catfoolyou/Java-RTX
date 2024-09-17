
import java.io.File;
import java.io.IOException;

public class Raytracer{

    final double infinity = Double.POSITIVE_INFINITY;
    final double pi = 3.1415926535897932385;

    public double random_double() {
        return Math.random();
    }
    
    public double random_double(double min, double max) {
        return min + (max-min) * random_double();
    }
    
    public Raytracer() throws IOException{
        
        HittableList world = new HittableList();

        Material left_red = new Lambertian(new Vector3(1.0, 0.2, 0.2));
        Material back_green = new Lambertian(new Vector3(0.2, 1.0, 0.2));
        Material right_blue = new Lambertian(new Vector3(0.2, 0.2, 1.0));
        Material upper_orange = new Lambertian(new Vector3(1.0, 0.5, 0.0));
        Material lower_teal = new Lambertian(new Vector3(0.2, 0.8, 0.8));

        // Quads
        world.add(new Quad(new Vector3(-3,-2, 5), new Vector3(0, 0,-4), new Vector3(0, 4, 0), left_red));
        world.add(new Quad(new Vector3(-2,-2, 0), new Vector3(4, 0, 0), new Vector3(0, 4, 0), back_green));
        world.add(new Quad(new Vector3( 3,-2, 1), new Vector3(0, 0, 4), new Vector3(0, 4, 0), right_blue));
        world.add(new Quad(new Vector3(-2, 3, 1), new Vector3(4, 0, 0), new Vector3(0, 0, 4), upper_orange));
        world.add(new Quad(new Vector3(-2,-3, 5), new Vector3(4, 0, 0), new Vector3(0, 0,-4), lower_teal));

        Camera cam = new Camera();

        cam.aspect_ratio = 1.0;
        cam.image_width = 400;
        cam.samples_per_pixel = 100;
        cam.max_depth = 50;

        cam.vfov = 80;
        cam.lookfrom = new Vector3(0,0,9);
        cam.lookat = new Vector3(0,0,0);
        cam.up = new Vector3(0,1,0);

        cam.defocus_angle = 0;

        cam.render(world);
    }

    public static void main(String[] args) throws IOException {
        Raytracer rtx = new Raytracer();
    }
}