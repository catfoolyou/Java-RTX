package net.raytracer.util;

import net.raytracer.math.Vector3;

public class SolidColor extends Texture{
    public Vector3 albedo;

    public SolidColor(Vector3 albedo){
        this.albedo = albedo;
    }

    public SolidColor(double r, double g, double b){
        this.albedo = new Vector3(r, g, b);
    }

    public Vector3 value(double u, double v, Vector3 p){
        return this.albedo;
    }
}
