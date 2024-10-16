package main.java.net.raytracer;
import java.io.IOException;

public class DefaultScenes {
    public static double random_double() {
        return Math.random();
    }
    
    public static double random_double(double min, double max) {
        return min + (max-min) * random_double();
    }

    public static void Spheres() throws IOException{
        HittableList world = new HittableList();

        Material ground_material = new Lambertian(new Vector3(0.5, 0.5, 0.5));
        world.add(new Sphere(new Vector3(0,-1000,0), 1000, ground_material));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double choose_mat = random_double();
                Vector3 center = new Vector3(a + 0.9*random_double(), 0.2, b + 0.9*random_double());

                if ((center.subtract(new Vector3(4, 0.2, 0)).length()) > 0.9) {
                    Material sphere_material;

                    if (choose_mat < 0.8) {
                        // diffuse
                        Vector3 albedo = new Vector3(Math.random(), Math.random(), Math.random());
                        sphere_material = new Lambertian(albedo);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    } else if (choose_mat < 0.95) {
                        // metal
                        Vector3 albedo = new Vector3(random_double(), 0.5, 1);
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
        cam.image_width = 800;
        cam.samples_per_pixel = 50;
        cam.max_depth = 50;
        cam.background = new Vector3(0.5, 0.7, 1);

        cam.vfov = 20;
        cam.lookfrom = new Vector3(13,2,3);
        cam.lookat = new Vector3(0,0,0);
        cam.up = new Vector3(0,1,0);

        cam.defocus_angle = 0.6;
        cam.focus_dist = 10.0;

        cam.render(world);
    }

    public static void SpheresChecker() throws IOException{
        HittableList world = new HittableList();

        Material ground_material = new Lambertian(new Checker(0.32, new Vector3(0.2, 0.3, 0.1), new Vector3(0.9)));
        world.add(new Sphere(new Vector3(0,-1000,0), 1000, ground_material));

        Material material1 = new Dielectric(1.5);
        world.add(new Sphere(new Vector3(0, 1, 0), 1.0, material1));

        Material material2 = new Lambertian(new Vector3(0.4, 0.2, 0.1));
        world.add(new Sphere(new Vector3(-4, 1, 0), 1.0, material2));

        Material material3 = new Metal(new Vector3(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Vector3(4, 1, 0), 1.0, material3));

        Camera cam = new Camera();

        cam.aspect_ratio = 16.0 / 9.0;
        cam.image_width = 800;
        cam.samples_per_pixel = 50;
        cam.max_depth = 50;
        cam.background = new Vector3(0.5, 0.7, 1);

        cam.vfov = 20;
        cam.lookfrom = new Vector3(13,2,3);
        cam.lookat = new Vector3(0,0,0);
        cam.up = new Vector3(0,1,0);

        cam.defocus_angle = 0.6;
        cam.focus_dist = 10.0;

        cam.render(world);
    }

    public static void Quads() throws IOException {
        HittableList world = new HittableList();
    
        // Materials
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
        cam.image_width = 600;
        cam.samples_per_pixel = 100;
        cam.max_depth = 50;
        cam.background = new Vector3(0.5, 0.7, 1);
    
        cam.vfov = 80;
        cam.lookfrom = new Vector3(0,0,9);
        cam.lookat = new Vector3(0,0,0);
        cam.up = new Vector3(0,1,0);
    
        cam.defocus_angle = 0;
    
        cam.render(world);
    }
    

    public static void SimpleLight() throws IOException {
        HittableList world = new HittableList();
    
        SolidColor pertext = new SolidColor(new Vector3(0.5));
        world.add(new Sphere(new Vector3(0,-1000,0), 1000, new Lambertian(pertext)));
        world.add(new Sphere(new Vector3(0,2,0), 2, new Lambertian(pertext)));
    
        Material difflight = new DiffuseLight(new Vector3(4,4,4));
        world.add(new Sphere(new Vector3(0,7,0), 2, difflight));
        world.add(new Quad(new Vector3(3,1,-2), new Vector3(2,0,0), new Vector3(0,2,0), difflight));
    
        Camera cam = new Camera();
    
        cam.aspect_ratio = 16.0 / 9.0;
        cam.image_width = 800;
        cam.samples_per_pixel = 200;
        cam.max_depth = 50;
        cam.background = new Vector3(0,0,0);
    
        cam.vfov = 20;
        cam.lookfrom = new Vector3(26,3,6);
        cam.lookat = new Vector3(0,2,0);
        cam.up = new Vector3(0,1,0);
    
        cam.defocus_angle = 0;
    
        cam.render(world);
    }
    
    public static void CornellBox() throws IOException{
        
        HittableList world = new HittableList();

        Material red = new Lambertian(new Vector3(0.65, 0.05, 0.05));
        Material white = new Lambertian(new Vector3(0.73));
        Material green = new Lambertian(new Vector3(0.12, 0.45, 0.15));
        Material metal = new Metal(new Vector3(0.7), 0.01);
        Material light = new DiffuseLight(new Vector3(15));

        world.add(new Quad(new Vector3(555,0,0), new Vector3(0,555,0), new Vector3(0,0,555), green));
        world.add(new Quad(new Vector3(0,0,0), new Vector3(0,555,0), new Vector3(0,0,555), red));
        world.add(new Quad(new Vector3(343, 554, 332), new Vector3(-130,0,0), new Vector3(0,0,-105), light));
        world.add(new Quad(new Vector3(0,0,0), new Vector3(555,0,0), new Vector3(0,0,555), white));
        world.add(new Quad(new Vector3(555,555,555), new Vector3(-555,0,0), new Vector3(0,0,-555), white));
        world.add(new Quad(new Vector3(0,0,555), new Vector3(555,0,0), new Vector3(0,555,0), white));

        Hittable box1 = Quad.box(new Vector3(0,0,0), new Vector3(165,330,165), metal);
        box1 = new RotateY(box1, 15);
        box1 = new Translate(box1, new Vector3(265,0,295));
        world.add(box1);
    
        Hittable box2 = Quad.box(new Vector3(0,0,0), new Vector3(165,165,165), white);
        box2 = new RotateY(box2, -18);
        box2 = new Translate(box2, new Vector3(130,0,65));
        world.add(box2);

        Camera cam = new Camera();

        cam.aspect_ratio = 1.0;
        cam.image_width = 600; // use 640 x 400 px
        cam.samples_per_pixel = 200; // use 512
        cam.max_depth = 50; // use 50
        cam.background = new Vector3(0,0,0);

        cam.vfov = 40;
        cam.lookfrom = new Vector3(278, 278, -800);
        cam.lookat = new Vector3(278, 278, 0);
        cam.up = new Vector3(0,1,0);

        cam.defocus_angle = 0;

        cam.render(world);
    }

    public static void CornellSmoke() throws IOException{
        
        HittableList world = new HittableList();

        Material red = new Lambertian(new Vector3(0.65, 0.05, 0.05));
        Material white = new Lambertian(new Vector3(0.73));
        Material green = new Lambertian(new Vector3(0.12, 0.45, 0.15));
        Material light = new DiffuseLight(new Vector3(7));

        world.add(new Quad(new Vector3(555,0,0), new Vector3(0,555,0), new Vector3(0,0,555), green));
        world.add(new Quad(new Vector3(0,0,0), new Vector3(0,555,0), new Vector3(0,0,555), red));
        world.add(new Quad(new Vector3(113,554,127), new Vector3(330,0,0), new Vector3(0,0,305), light));
        world.add(new Quad(new Vector3(0,0,0), new Vector3(555,0,0), new Vector3(0,0,555), white));
        world.add(new Quad(new Vector3(555,555,555), new Vector3(-555,0,0), new Vector3(0,0,-555), white));
        world.add(new Quad(new Vector3(0,0,555), new Vector3(555,0,0), new Vector3(0,555,0), white));

        Hittable box1 = Quad.box(new Vector3(0,0,0), new Vector3(165,330,165), white);
        box1 = new RotateY(box1, 15);
        box1 = new Translate(box1, new Vector3(265,0,295));

        Hittable box2 = Quad.box(new Vector3(0,0,0), new Vector3(165,165,165), white);
        box2 = new RotateY(box2, -18);
        box2 = new Translate(box2, new Vector3(130,0,65));

        world.add(new ConstantMedium(box1, 0.01, new Vector3(0,0,0)));
        world.add(new ConstantMedium(box2, 0.01, new Vector3(1,1,1)));

        Camera cam = new Camera();

        cam.aspect_ratio = 1.0;
        cam.image_width = 600; // use 640 x 400 px
        cam.samples_per_pixel = 200; // use 512
        cam.max_depth = 50; // use 50
        cam.background = new Vector3(0,0,0);

        cam.vfov = 40;
        cam.lookfrom = new Vector3(278, 278, -800);
        cam.lookat = new Vector3(278, 278, 0);
        cam.up = new Vector3(0,1,0);

        cam.defocus_angle = 0;

        cam.render(world);
    }
}
