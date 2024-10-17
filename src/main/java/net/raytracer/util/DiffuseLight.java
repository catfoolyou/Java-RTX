package net.raytracer.util;

import net.raytracer.math.Vector3;

public class DiffuseLight extends Material{
    private Texture texture;

    public DiffuseLight(Vector3 emissive){
        this.texture = new SolidColor(emissive);
    }

    public DiffuseLight(Texture texture){
        this.texture = texture;
    }

    public Vector3 emitted(double u, double v, Vector3 p){
        return texture.value(u, v, p);
    }
}
