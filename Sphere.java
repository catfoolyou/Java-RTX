public class Sphere extends Hittable {
    private Ray center;
    private double radius;
    public Material material; 
    
    public Sphere(Vector3 static_center, double radius, Material mat){
        this.center = new Ray(static_center, new Vector3(0));
        this.radius = radius;
        this.material = mat;
    }

    public Sphere(Vector3 center1, Vector3 center2, double radius, Material mat){
        this.center = new Ray(center1, center2.subtract(center1));
        this.radius = radius;
        this.material = mat;
    }

    public boolean hit(Ray r, Interval ray_t, hit_record rec){
        if((this.center.direction.x + this.center.direction.y + this.center.direction.z) != 1.0){
            this.center.direction = new Vector3(0);
        }

        Vector3 current_center = this.center.at(r.time);
        Vector3 oc = current_center.subtract(r.origin);
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
        Vector3 outward_normal = (rec.p.subtract(current_center)).divide(new Vector3(radius));
        rec.set_face_normal(r, outward_normal);
        rec.material = this.material;

        return true;
    }
}
