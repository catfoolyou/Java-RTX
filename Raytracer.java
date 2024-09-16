
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

        Material ground_material = new Lambertian(new ImageTexture("de_grid.jpg"));
        world.add(new Sphere(new Vector3(0,-1000,0), 1000, ground_material));

        Material material1 = new Dielectric(1.5);
        world.add(new Sphere(new Vector3(0, 1, 0), 1.0, material1));

        Material material2 = new Lambertian(new Vector3(0.4, 0.2, 0.1));
        world.add(new Sphere(new Vector3(-4, 1, 0), 1.0, material2));

        Material material3 = new Metal(new Vector3(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Vector3(4, 1, 0), 1.0, material3));

        Camera cam = new Camera();

        cam.aspect_ratio = 16.0 / 9.0;
        cam.image_width = 400;
        cam.samples_per_pixel = 100;
        cam.max_depth = 50;

        cam.vfov = 20;
        cam.lookfrom = new Vector3(13,2,3);
        cam.lookat = new Vector3(0,0,0);
        cam.up = new Vector3(0,1,0);

        cam.defocus_angle = 0.6;
        cam.focus_dist = 10.0;

        cam.render(world);
    }

    public static void main(String[] args) throws IOException {
        Raytracer rtx = new Raytracer();
    }
}