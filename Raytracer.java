
import java.io.File;
import java.io.IOException;

public class Raytracer{

    final double infinity = Double.POSITIVE_INFINITY;
    final double pi = 3.1415926535897932385;

    double degrees_to_radians(double degrees) {
        return degrees * pi / 180.0;
    }

    public double random_double() {
        return Math.random();
    }
    
    public double random_double(double min, double max) {
        return min + (max-min) * random_double();
    }

    File image = new File("result.ppm");
    
    public Raytracer() throws IOException{
        
        HittableList world = new HittableList();

        world.add(new Sphere(new Vector3(0,0,-1), 0.5));
        world.add(new Sphere(new Vector3(0,-100.5,-1), 100));

        Camera cam = new Camera();

        cam.aspect_ratio = 16.0 / 9.0;
        cam.image_width = 800;
        cam.samples_per_pixel = 100;
        cam.max_depth = 50;

        cam.render(world);
    }

    public static void main(String[] args) throws IOException {
        Raytracer rtx = new Raytracer();
    }
}