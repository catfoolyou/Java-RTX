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

    public static void main(String[] args) throws IOException {
        CornellBox();
    }
}