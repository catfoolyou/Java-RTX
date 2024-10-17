package net.raytracer.object;

import java.util.ArrayList;
import net.raytracer.math.*;
import net.raytracer.util.*;

public class HittableList extends Hittable {
    public AABB bounding_box = new AABB(new Interval(0, 1), new Interval(0, 1), new Interval(0, 1));

    public ArrayList<Hittable> objects = new ArrayList<Hittable>();

    public void clear(){
        objects.clear();
    }

    public void add(Hittable object){
        objects.add(object);
    }

    public boolean hit(Ray r, Interval ray_t, HitRecord rec){
        HitRecord temp_rec = rec;
        boolean hit_anything = false;
        double closest_so_far = ray_t.max;

        for(Hittable h : objects) {
            if (h.hit(r, new Interval(ray_t.min, closest_so_far), temp_rec)) {
                hit_anything = true;
                closest_so_far = temp_rec.t;
                rec = temp_rec;
            }
        }

        return hit_anything;
    }
}
 