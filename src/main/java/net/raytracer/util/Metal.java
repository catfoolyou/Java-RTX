package net.raytracer.util;

import net.raytracer.math.Ray;
import net.raytracer.math.Vector3;
import net.raytracer.object.HitRecord;

public class Metal extends Material{
    public Texture texture;
    public Ray scattered;
    private double fuzz;

    public Metal(Vector3 albedo, double fuzz){
        this.texture = new SolidColor(albedo);
        this.fuzz = fuzz;
    }

    public Metal(Texture tex, double fuzz){
        this.texture = tex;
        this.fuzz = fuzz;
    }

    public boolean scatter(Ray r_in, HitRecord rec){
        Vector3 reflected = Vector3.reflect(r_in.direction, rec.normal);
        reflected = Vector3.unit_vector(reflected).add(Vector3.random_unit_vector().multiply(fuzz));

        this.scattered = new Ray(rec.p, reflected, r_in.time);

        rec.setMaterial(this.texture.value(rec.u, rec.v, rec.p), this.scattered);
        return Vector3.dot(scattered.direction, rec.normal) > 0;
    }
}
