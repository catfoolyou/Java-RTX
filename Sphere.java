public class Sphere extends Hittable {
    private Vector3 center;
    private double radius;
    
    public Sphere(Vector3 center, double radius){
        this.center = center;
        this.radius = radius;
    }

    public boolean hit(Ray r, double ray_tmin, double ray_tmax, hit_record rec){
        Vector3 oc = center.subtract(r.origin);
        float a = r.direction.length();
        float h = Vector3.dot(r.direction, oc);
        float c = (float) (oc.length_squared() - radius * radius);
        float discriminant = h * h - a * c;

        if (discriminant < 0) {
            return false;
        }
        
        float sqrtd = (float) Math.sqrt(discriminant);

        float root = (h - sqrtd) / a;
        if (root <= ray_tmin || ray_tmax <= root) {
            root = (h + sqrtd) / a;
            if (root <= ray_tmin || ray_tmax <= root)
                return false;
        }

        rec.t = root;
        rec.p = r.at(rec.t);
        Vector3 outward_normal = (rec.p.subtract(center)).divide(new Vector3(radius));
        rec.set_face_normal(r, outward_normal);

        return true;
    }
}
