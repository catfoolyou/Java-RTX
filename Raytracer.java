
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

        Texture checker = new Checker(3.2, new Vector3(0.2, 0.3, 0.1), new Vector3(0.9, 0.9, 0.9));

        Material ground_material = new Lambertian(checker);
        world.add(new Sphere(new Vector3(0,-1000,0), 1000, ground_material));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double choose_mat = random_double();
                Vector3 center = new Vector3(a + 0.9 * random_double(), 0.2, b + 0.9 * random_double());

                if ((center.subtract(new Vector3(4, 0.2, 0)).length()) > 0.9) {
                    Material sphere_material;

                    if (choose_mat < 0.8) {
                        // diffuse
                        Vector3 albedo = new Vector3(Math.random(), Math.random(), Math.random());
                        sphere_material = new Lambertian(albedo);
                        //Vector3 center2 = center.add(new Vector3(0, random_double(0, 0.5), 0));
                        world.add(new Sphere(center, 0.2, sphere_material));
                    } else if (choose_mat < 0.95) {
                        // metal
                        Vector3 albedo = new Vector3(Math.random(), Math.random(), Math.random());
                        double fuzz = random_double(0, 0.5);
                        sphere_material = new Metal(albedo, fuzz);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    } else {
                        // glass
                        sphere_material = new Dielectric(1.5);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    }
                }
            }
        }

        Material material1 = new Dielectric(1.5);
        world.add(new Sphere(new Vector3(0, 1, 0), 1.0, material1));

        Material material2 = new Lambertian(new Vector3(0.4, 0.2, 0.1));
        world.add(new Sphere(new Vector3(-4, 1, 0), 1.0, material2));

        Material material3 = new Metal(new Vector3(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Vector3(4, 1, 0), 1.0, material3));

        Camera cam = new Camera();

        cam.aspect_ratio = 16.0 / 9.0;
        cam.image_width = 400;
        cam.samples_per_pixel = 10;
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