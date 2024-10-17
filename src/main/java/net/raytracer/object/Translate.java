package net.raytracer.object;

import net.raytracer.math.Interval;
import net.raytracer.math.Ray;
import net.raytracer.math.Vector3;
import net.raytracer.util.AABB;

public class Translate extends Hittable{
    private Hittable object;
    private Vector3 offset;
    public AABB bounding_box;

    public Translate(Hittable object, Vector3 offset){
        this.object = object;
        this.offset = offset;
        this.bounding_box = new AABB();
    }


    public boolean hit(Ray r, Interval ray_t, HitRecord rec){
        // Move the ray backwards by the offset
        Ray offset_r = new Ray(r.origin.subtract(offset), r.direction, r.time);

        // Determine whether an intersection exists along the offset ray (and if so, where)
        if (!object.hit(offset_r, ray_t, rec))
            return false;

        // Move the intersection point forwards by the offset
        rec.p = rec.p.add(offset);

        return true;
    }
}
