
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

    File image = new File("result.ppm");
    
    public Raytracer() throws IOException{
        
        HittableList world = new HittableList();

        Material material_ground = new Lambertian(new Vector3(0.8, 0.8, 0.0));
        Material material_center = new Lambertian(new Vector3(0.1, 0.2, 0.5));
        Material material_left = new Dielectric(1.50);
        Material material_bubble = new Dielectric(1.00 / 1.50);
        Material material_right = new Metal(new Vector3(0.8, 0.6, 0.2), 0.01);

        world.add(new Sphere(new Vector3( 0.0, -100.5, -1.0), 100.0, material_ground));
        world.add(new Sphere(new Vector3( 0.0, 0.0, -1.2), 0.5, material_center));
        world.add(new Sphere(new Vector3(-1.0, 0.0, -1.0), 0.5, material_left));
        world.add(new Sphere(new Vector3(-1.0, 0.0, -1.0), 0.4, material_bubble));
        world.add(new Sphere(new Vector3( 1.0,0.0, -1.0), 0.5, material_right));

        Camera cam = new Camera();

        cam.aspect_ratio = 16.0 / 9.0;
        cam.image_width = 800;
        cam.samples_per_pixel = 50;
        cam.max_depth = 50;

        cam.vfov = 20;
        cam.lookfrom = new Vector3(-2,2,1);
        cam.lookat = new Vector3(0,0,-1);
        cam.up = new Vector3(0,1,0);

        //cam.defocus_angle = 10.0;
        //cam.focus_dist = 3.4;

        cam.render(world);
    }

    public static void main(String[] args) throws IOException {
        Raytracer rtx = new Raytracer();
    }
}