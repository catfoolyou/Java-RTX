
import java.util.ArrayList;

public class HittableList extends Hittable {

    ArrayList<Hittable> hittable_list = new ArrayList<Hittable>(); 

    public void clear(){
        hittable_list.clear();
    }

    public void add(Hittable h){
        hittable_list.add(h);
    }

    public boolean hit(Ray r, double ray_tmin, double ray_tmax, hit_record rec){
        hit_record temp_rec = rec;
        boolean hit_anything = false;
        double closest_so_far = ray_tmax;

        for(Hittable h : hittable_list) {
            if (h.hit(r, ray_tmin, closest_so_far, temp_rec)) {
                hit_anything = true;
                closest_so_far = temp_rec.t;
                rec = temp_rec;
            }
        }

        return hit_anything;
    }    
}
 