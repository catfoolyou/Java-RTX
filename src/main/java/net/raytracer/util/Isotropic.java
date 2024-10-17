package net.raytracer.util;

import net.raytracer.math.Ray;
import net.raytracer.math.Vector3;
import net.raytracer.object.HitRecord;

public class Isotropic extends Material{
    private Texture texture;

    public Isotropic(Vector3 albdeo){
        this.texture = new SolidColor(albdeo);
    }

    public Isotropic(Texture tex){
        this.texture = tex;
    }

    public boolean scatter(Ray r_in, HitRecord rec, Vector3 attenuation, Ray scattered){
        scattered = new Ray(rec.p, Vector3.random_unit_vector(), r_in.time);
        attenuation = this.texture.value(rec.u, rec.v, rec.p);

        rec.setMaterial(attenuation, scattered);
        return true;
    }
}
