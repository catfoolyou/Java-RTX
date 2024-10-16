package main.java.net.raytracer;

public class Quad extends Hittable {
    private Vector3 q;
    private Vector3 u, v;
    private Vector3 w;
    private Vector3 normal;
    public double d;
    public Material material;
    public AABB bounding_box;

    public Quad(Vector3 q, Vector3 u, Vector3 v, Material mat){
        this.q = q;
        this.u = u;
        this.v = v;
        this.material = mat;

        Vector3 n = Vector3.cross(u, v);
        this.normal = Vector3.unit_vector(n);
        this.d = Vector3.dot(normal, q);
        this.w = n.divide(new Vector3(Vector3.dot(n, n)));

        AABB bbox_diagonal1 = new AABB(q, q.add(u).add(v));
        AABB bbox_diagonal2 = new AABB(q.add(u), q.add(v));
        this.bounding_box = new AABB(bbox_diagonal1, bbox_diagonal2);
    }

    public boolean hit(Ray r, Interval ray_t, hit_record rec){
        float denom = Vector3.dot(normal, r.direction);

        // No hit if the ray is parallel to the plane.
        if (Math.abs(denom) < 1e-8)
            return false;

        // Return false if the hit point parameter t is outside the ray interval.
        double t = (d - Vector3.dot(normal, r.origin)) / denom;
        if (!ray_t.contains(t))
            return false;

        Vector3 intersection = r.at(t);
        Vector3 planar_hitpt_vector = intersection.subtract(q);
        float alpha = Vector3.dot(w, Vector3.cross(planar_hitpt_vector, v));
        float beta = Vector3.dot(w, Vector3.cross(u, planar_hitpt_vector));

        if (!is_interior(alpha, beta, rec))
            return false;

        rec.t = t;
        rec.p = intersection;
        rec.material = this.material;
        rec.set_face_normal(r, normal);

        return true;
    }

    private boolean is_interior(double a, double b, hit_record rec){
        Interval unit_interval = new Interval(0, 1);
        // Given the hit point in plane coordinates, return false if it is outside the
        // primitive, otherwise set the hit record UV coordinates and return true.

        if (!unit_interval.contains(a) || !unit_interval.contains(b))
            return false;

        rec.u = a;
        rec.v = b;
        return true;
    }

    public static HittableList box(Vector3 a, Vector3 b, Material mat){
        HittableList sides = new HittableList();

        Vector3 min = new Vector3(Math.min(a.x,b.x), Math.min(a.y,b.y), Math.min(a.z,b.z));
        Vector3 max = new Vector3(Math.max(a.x,b.x), Math.max(a.y,b.y), Math.max(a.z,b.z));

        Vector3 dx = new Vector3(max.x - min.x, 0, 0);
        Vector3 dy = new Vector3(0, max.y - min.y, 0);
        Vector3 dz = new Vector3(0, 0, max.z - min.z);

        sides.add(new Quad(new Vector3(min.x, min.y, max.z),  dx,  dy, mat)); // front
        sides.add(new Quad(new Vector3(max.x, min.y, max.z), new Vector3(0).subtract(dz),  dy, mat)); // right
        sides.add(new Quad(new Vector3(max.x, min.y, min.z), new Vector3(0).subtract(dx),  dy, mat)); // back
        sides.add(new Quad(new Vector3(min.x, min.y, min.z),  dz,  dy, mat)); // left
        sides.add(new Quad(new Vector3(min.x, max.y, max.z),  dx, new Vector3(0).subtract(dz), mat)); // top
        sides.add(new Quad(new Vector3(min.x, min.y, min.z),  dx,  dz, mat)); // bottom

        return sides;
    }
    
}
