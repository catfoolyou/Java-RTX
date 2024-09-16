
import java.util.ArrayList;

public class HittableList extends Hittable {
    private AABB bounding_box;

    ArrayList<Hittable> hittable_list = new ArrayList<Hittable>(); 

    public void clear(){
        hittable_list.clear();
    }

    public void add(Hittable h){
        hittable_list.add(h);
        //bounding_box = new AABB(bounding_box, boundingBox());
    }

    public boolean hit(Ray r, Interval ray_t, hit_record rec){
        hit_record temp_rec = rec;
        boolean hit_anything = false;
        double closest_so_far = ray_t.max;

        for(Hittable h : hittable_list) {
            if (h.hit(r, new Interval(ray_t.min, closest_so_far), temp_rec)) {
                hit_anything = true;
                closest_so_far = temp_rec.t;
                rec = temp_rec;
            }
        }

        return hit_anything;
    }
    
    public AABB boundingBox(){
        return this.bounding_box;
    }
}
 