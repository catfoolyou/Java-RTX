public class Hittable{

    public boolean hit(Ray r, Interval ray_t, hit_record rec){
        return false;
    }

    public AABB bounding_box;
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
