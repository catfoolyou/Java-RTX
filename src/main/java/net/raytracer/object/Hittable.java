package net.raytracer.object;

import net.raytracer.math.*;
import net.raytracer.util.AABB;

public class Hittable{
    public AABB bounding_box;

    public boolean hit(Ray r, Interval ray_t, HitRecord rec){
        return false;
    }
}

