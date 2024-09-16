import java.util.ArrayList;

public class BVHNode extends Hittable {
    public AABB bounding_box;
    public Hittable left;
    public Hittable right;

    public BVHNode(HittableList list){
        new BVHNode(list.hittable_list, 0, list.hittable_list.size());
    }

    public BVHNode(ArrayList<Hittable> hittable_list, int start, int end){
        int axis = random_int(0,2);

        //boolean comparator = (axis == 0) ? box_x_compare : (axis == 1) ? box_y_compare : box_z_compare;

        double object_span = end - start;

        if (object_span == 1) {
            left = right = hittable_list.get(start);
        } else if (object_span == 2) {
            left = hittable_list.get(start);
            right = hittable_list.get(start + 1);
        } else {
            //sort((hittable_list) + start, (hittable_list) + end, comparator);

            int mid = (int) (start + object_span / 2);
            left = new BVHNode(hittable_list, start, mid);
            right = new BVHNode(hittable_list, mid, end);
        }

        bounding_box = new AABB(left.bounding_box, right.bounding_box);
    }

    static boolean box_compare(Hittable a, Hittable b, int axis_index) {
        Interval a_axis_interval = a.bounding_box.axis_interval(axis_index);
        Interval b_axis_interval = b.bounding_box.axis_interval(axis_index);
        return a_axis_interval.min < b_axis_interval.min;
    }

    static boolean box_x_compare (Hittable a, Hittable b) {
        return box_compare(a, b, 0);
    }

    static boolean box_y_compare (Hittable a, Hittable b) {
        return box_compare(a, b, 1);
    }

    static boolean box_z_compare (Hittable a, Hittable b) {
        return box_compare(a, b, 2);
    }

    public int random_int(int min, int max) {
        return (int) random_double(min, max+1);
    }

    public double random_double() {
        return Math.random();
    }
    
    public double random_double(double min, double max) {
        return min + (max-min) * random_double();
    }

    public boolean hit(Ray r, Interval ray_t, hit_record rec){
        if (!bounding_box.hit(r, ray_t))
            return false;

        boolean hit_left = left.hit(r, ray_t, rec);
        boolean hit_right = right.hit(r, new Interval(ray_t.min, hit_left ? rec.t : ray_t.max), rec);

        return hit_left || hit_right;
    }

}
