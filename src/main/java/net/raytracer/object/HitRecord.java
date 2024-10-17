package net.raytracer.object;

import net.raytracer.math.Ray;
import net.raytracer.math.Vector3;
import net.raytracer.util.Material;

public class HitRecord {
    public Vector3 p;
    public Vector3 normal;
    public Material material;
    public double t;
    public boolean front_face;
    public double u;
    public double v;

    public void setMaterial(Vector3 albedo, Ray scattered){
        this.material.albedo = albedo;
        this.material.scattered = scattered;
    }

    void set_face_normal(Ray r, Vector3 outward_normal) {
        front_face = Vector3.dot(r.direction, outward_normal) < 0;
        normal = front_face ? outward_normal : new Vector3(0).subtract(outward_normal);
    }

}
