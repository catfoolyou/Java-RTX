public class Sphere extends Hittable {
    private Ray center;
    private double radius;
    public AABB bounding_box;
    public Material material;
    
    final static double pi = 3.1415926535897932385;
    
    public Sphere(Vector3 static_center, double radius, Material mat){
        this.center = new Ray(static_center, new Vector3(0));
        this.radius = radius;
        this.material = mat;

        Vector3 rvec = new Vector3(this.radius);
        bounding_box = new AABB(static_center.subtract(rvec), static_center.add(rvec));
    }

    public Sphere(Vector3 center1, Vector3 center2, double radius, Material mat){
        this.center = new Ray(center1, center2.subtract(center1));
        this.radius = radius;
        this.material = mat;

        Vector3 rvec = new Vector3(this.radius);
        AABB box1 = new AABB(center.at(0).subtract(rvec), center.at(0).add(rvec));
        AABB box2 = new AABB(center.at(1).subtract(rvec), center.at(1).add(rvec));
        bounding_box = new AABB(box1, box2);
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

        double[] uv = get_sphere_uv(outward_normal, rec.u, rec.v);
        rec.u = uv[0];
        rec.v = uv[1];

        rec.material = this.material;

        return true;
    }

    public static double[] get_sphere_uv(Vector3 p, double u, double v) {
        // p: a given point on the sphere of radius one, centered at the origin.
        // u: returned value [0,1] of angle around the Y axis from X=-1.
        // v: returned value [0,1] of angle from Y=-1 to Y=+1.
        //     <1 0 0> yields <0.50 0.50>       <-1  0  0> yields <0.00 0.50>
        //     <0 1 0> yields <0.50 1.00>       < 0 -1  0> yields <0.50 0.00>
        //     <0 0 1> yields <0.25 0.50>       < 0  0 -1> yields <0.75 0.50>

        double theta = Math.acos(-p.y);
        double phi = Math.atan2(-p.z, p.x) + pi;

        double[] array = new double[2];

        array[0] = phi / (2 * pi);
        array[1] = theta / pi;

        return array;
    }
}
