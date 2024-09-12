public class Sphere extends Hittable {
    private Vector3 center;
    private double radius;
    public Material material; 
    
    public Sphere(Vector3 center, double radius, Material mat){
        this.center = center;
        this.radius = radius;
        this.material = mat;
    }

    public boolean hit(Ray r, Interval ray_t, hit_record rec){
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
        if (!ray_t.surrounds(root)) {
            root = (h + sqrtd) / a;
            if (!ray_t.surrounds(root))
                return false;
        }

        rec.t = root;
        rec.p = r.at(rec.t);
        Vector3 outward_normal = (rec.p.subtract(center)).divide(new Vector3(radius));
        rec.set_face_normal(r, outward_normal);
        rec.material = this.material;

        return true;
    }
}
