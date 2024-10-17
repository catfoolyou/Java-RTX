package net.raytracer.util;

import net.raytracer.math.*;
import net.raytracer.object.*;

public class Material {
    public Vector3 albedo;
    public Ray scattered;

    public boolean scatter(Ray r_in, HitRecord rec){
        return false;
    }

    public Vector3 emitted(double u, double v, Vector3 p){
        return new Vector3(0,0,0);
    }
}

