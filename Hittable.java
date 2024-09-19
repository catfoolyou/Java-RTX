public class Hittable{
    public AABB bounding_box;

    public boolean hit(Ray r, Interval ray_t, hit_record rec){
        return false;
    }
}

class Translate extends Hittable{
    private Hittable object;
    private Vector3 offset;
    public AABB bounding_box;

    public Translate(Hittable object, Vector3 offset){
        this.object = object;
        this.offset = offset;
        this.bounding_box = new AABB();
    }


    public boolean hit(Ray r, Interval ray_t, hit_record rec){
        // Move the ray backwards by the offset
        Ray offset_r = new Ray(r.origin.subtract(offset), r.direction, r.time);

        // Determine whether an intersection exists along the offset ray (and if so, where)
        if (!object.hit(offset_r, ray_t, rec))
            return false;

        // Move the intersection point forwards by the offset
        rec.p = rec.p.add(offset);

        return true;
    }
}

class RotateY extends Hittable{
    private Hittable object;
    public AABB bounding_box;
    double sin_theta;
    double cos_theta;

    final double infinity = Double.POSITIVE_INFINITY;

    public RotateY(Hittable object, double angle){
        this.object = object;
        double radians = Vector3.degrees_to_radians(angle);
        this.sin_theta = Math.sin(radians);
        this.cos_theta = Math.cos(radians);
        this.bounding_box = new AABB(new Interval(0, 1), new Interval(0, 1), new Interval(0, 1));

        Vector3 min = new Vector3( infinity,  infinity,  infinity);
        Vector3 max = new Vector3(-infinity, -infinity, -infinity);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    double x = i*bounding_box.x.max + (1-i)*bounding_box.x.min;
                    double y = j*bounding_box.y.max + (1-j)*bounding_box.y.min;
                    double z = k*bounding_box.z.max + (1-k)*bounding_box.z.min;

                    double newx =  cos_theta*x + sin_theta*z;
                    double newz = -sin_theta*x + cos_theta*z;

                    Vector3 tester = new Vector3(newx, y, newz);

                    for (int c = 0; c < 3; c++) {
                        if(c == 0)
                            min.x = Math.min(min.x, tester.x);
                            max.x = Math.max(max.x, tester.x);
                        if(c == 1)
                            min.y = Math.min(min.y, tester.y);
                            max.y = Math.max(max.y, tester.y);
                        if(c == 2)
                            min.z = Math.min(min.z, tester.z);
                            max.z = Math.max(max.z, tester.z);
                    }
                }
            }
        }

        this.bounding_box = new AABB(min, max);
    }

    public boolean hit(Ray r, Interval ray_t, hit_record rec){

        // Transform the ray from world space to object space.

        Vector3 origin = new Vector3(
            (cos_theta * r.origin.x) - (sin_theta * r.origin.z),
            r.origin.y,
            (sin_theta * r.origin.x) + (cos_theta * r.origin.z)
        );

        Vector3 direction = new Vector3(
            (cos_theta * r.direction.x) - (sin_theta * r.direction.z),
            r.direction.y,
            (sin_theta * r.direction.x) + (cos_theta * r.direction.z)
        );

        Ray rotated_r = new Ray(origin, direction, r.time);

        // Determine whether an intersection exists in object space (and if so, where).

        if (!object.hit(rotated_r, ray_t, rec))
            return false;

        // Transform the intersection from object space back to world space.

        rec.p = new Vector3(
            (cos_theta * rec.p.x) + (sin_theta * rec.p.z),
            rec.p.y,
            (-sin_theta * rec.p.x) + (cos_theta * rec.p.z)
        );

        rec.normal = new Vector3(
            (cos_theta * rec.normal.x) + (sin_theta * rec.normal.z),
            rec.normal.y,
            (-sin_theta * rec.normal.x) + (cos_theta * rec.normal.z)
        );

        return true;
    }
}

class hit_record {
    Vector3 p;
    Vector3 normal;
    Material material; 
    double t;
    boolean front_face;
    double u, v;

    public void setMaterial(Vector3 albedo, Ray scattered){
        this.material.albedo = albedo;
        this.material.scattered = scattered;
    }

    void set_face_normal(Ray r, Vector3 outward_normal) {
        front_face = Vector3.dot(r.direction, outward_normal) < 0;
        normal = front_face ? outward_normal : new Vector3(0).subtract(outward_normal);
    }

}
