import java.util.ArrayList;

public class HittableList extends Hittable {
    public AABB bounding_box = new AABB();

    ArrayList<Hittable> objects = new ArrayList<Hittable>(); 

    public void clear(){
        objects.clear();
    }

    public void add(Hittable object){
        objects.add(object);
        //this.bounding_box = new AABB(this.bounding_box, object.bounding_box);
    }

    public boolean hit(Ray r, Interval ray_t, hit_record rec){
        hit_record temp_rec = rec;
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
 