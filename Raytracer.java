
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

        Material red = new Lambertian(new Vector3(.65, .05, .05));
        Material white = new Lambertian(new Vector3(.73, .73, .73));
        Material green = new Lambertian(new Vector3(.12, .45, .15));
        Material light = new DiffuseLight(new Vector3(15, 15, 15));

        world.add(new Quad(new Vector3(555,0,0), new Vector3(0,555,0), new Vector3(0,0,555), green));
        world.add(new Quad(new Vector3(0,0,0), new Vector3(0,555,0), new Vector3(0,0,555), red));
        world.add(new Quad(new Vector3(343, 554, 332), new Vector3(-130,0,0), new Vector3(0,0,-105), light));
        world.add(new Quad(new Vector3(0,0,0), new Vector3(555,0,0), new Vector3(0,0,555), white));
        world.add(new Quad(new Vector3(555,555,555), new Vector3(-555,0,0), new Vector3(0,0,-555), white));
        world.add(new Quad(new Vector3(0,0,555), new Vector3(555,0,0), new Vector3(0,555,0), white));

        world.add(Quad.box(new Vector3(130, 0, 65), new Vector3(295, 165, 230), white));
        world.add(Quad.box(new Vector3(265, 0, 295), new Vector3(430, 330, 460), white));

        Camera cam = new Camera();

        cam.aspect_ratio = 1.0;
        cam.image_width = 400;
        cam.samples_per_pixel = 50;
        cam.max_depth = 50;
        cam.background = new Vector3(0,0,0);

        cam.vfov = 40;
        cam.lookfrom = new Vector3(278, 278, -800);
        cam.lookat = new Vector3(278, 278, 0);
        cam.up = new Vector3(0,1,0);

        cam.defocus_angle = 0;

        cam.render(world);
    }

    public static void main(String[] args) throws IOException {
        Raytracer rtx = new Raytracer();
    }
}