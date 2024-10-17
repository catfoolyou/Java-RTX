package net.raytracer.util;

import net.raytracer.math.Ray;
import net.raytracer.math.Vector3;
import net.raytracer.object.HitRecord;

public class Lambertian extends Material{
    public Texture texture;
    public Ray scattered;

    public Lambertian(Vector3 al){
        this.texture = new SolidColor(al);
    }

    public Lambertian(Texture tex){
        this.texture = tex;
    }

    public boolean scatter(Ray r_in, HitRecord rec){
        Vector3 scatter_direction = rec.normal.add(Vector3.random_unit_vector());

        if (scatter_direction.near_zero())
            scatter_direction = rec.normal;

        this.scattered = new Ray(rec.p, scatter_direction, r_in.time);
        rec.setMaterial(this.texture.value(rec.u, rec.v, rec.p), this.scattered);
        
        return true;
    }
}
