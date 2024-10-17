package net.raytracer;

public class ConstantMedium extends Hittable{
    private Hittable boundary;
    private double neg_inv_density;
    private Material phase_function;
    
    public ConstantMedium(Hittable boundary, double density, Texture tex){
        this.boundary = boundary;
        this.neg_inv_density = -1/density;
        this.phase_function = new Lambertian(tex);
    }

    public ConstantMedium(Hittable boundary, double density, Vector3 albedo){
        this.boundary = boundary;
        this.neg_inv_density = -1/density;
        this.phase_function = new Lambertian(albedo);
    }

    public boolean hit(Ray r, Interval ray_t, hit_record rec){
        hit_record rec1 = new hit_record();
        hit_record rec2 = new hit_record();

        if (!boundary.hit(r, Interval.universe, rec1))
            return false;

        if (!boundary.hit(r, new Interval(rec1.t+0.0001, Double.POSITIVE_INFINITY), rec2))
            return false;

        if (rec1.t < ray_t.min) rec1.t = ray_t.min;
        if (rec2.t > ray_t.max) rec2.t = ray_t.max;

        if (rec1.t >= rec2.t)
            return false;

        if (rec1.t < 0)
            rec1.t = 0;

        double ray_length = r.direction.length();
        double distance_inside_boundary = (rec2.t - rec1.t) * ray_length;
        double hit_distance = neg_inv_density * Math.log(Vector3.random_double());

        if (hit_distance > distance_inside_boundary)
            return false;

        rec.t = rec1.t + hit_distance / ray_length;
        rec.p = r.at(rec.t);

        rec.normal = new Vector3(1,0,0);  // arbitrary
        rec.front_face = true;     // also arbitrary
        rec.material = this.phase_function;

        return true;
    }

    public AABB bounding_box(){
         return this.boundary.bounding_box; 
    }

}
