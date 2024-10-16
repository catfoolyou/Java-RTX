package main.java.net.raytracer;

public class AABB {

    public Interval x, y, z;
    public AABB(){}

    public AABB(Interval x, Interval y, Interval z){
        this.x = x;
        this.y = y;
        this.z = z;

        pad_to_minimums();
    }

    public AABB(Vector3 a, Vector3 b){
        this.x = (a.x <= b.x) ? new Interval(a.x, b.x) : new Interval(b.x, a.x);
        this.y = (a.y <= b.y) ? new Interval(a.y, b.y) : new Interval(b.y, a.y);
        this.z = (a.z <= b.z) ? new Interval(a.z, b.z) : new Interval(b.z, a.z);

        pad_to_minimums();
    }

    public AABB(AABB box0, AABB box1){
        this.x = new Interval(box0.x, box1.x);
        this.y = new Interval(box0.y, box1.y);
        this.z = new Interval(box0.z, box1.z);
    }

    public AABB add(Vector3 vec){
        Interval xx = new Interval(this.x.min + vec.x, this.x.max + vec.x);
        Interval yy = new Interval(this.y.min + vec.y, this.y.max + vec.y);
        Interval zz = new Interval(this.z.min + vec.z, this.z.max + vec.z);
        return new AABB(xx, yy, zz);
    }

    private void pad_to_minimums() {
        // Adjust the AABB so that no side is narrower than some delta, padding if necessary.
        double delta = 0.0001;
        if (x.size() < delta) x = x.expand(delta);
        if (y.size() < delta) y = y.expand(delta);
        if (z.size() < delta) z = z.expand(delta);
    }

    public Interval axis_interval(int n){ 
        if (n == 1) return y;
        if (n == 2) return z;
        return x;
    }

    boolean hit(Ray r, Interval ray_t){
        Vector3 ray_orig = r.origin;
        Vector3 ray_dir = r.direction;

        for (int axis = 0; axis < 3; axis++) {
            double rayDirCoord = ray_dir.x;
            double rayOrigCoord = ray_orig.x;

            switch (axis) {
                case 1:
                    rayDirCoord = ray_dir.x;
                    rayOrigCoord = ray_orig.x;
                    break;
                case 2:
                    rayDirCoord = ray_dir.y;
                    rayOrigCoord = ray_orig.y;
                    break;
                case 3: 
                    rayDirCoord = ray_dir.z;
                    rayOrigCoord = ray_orig.z;
                    break;                   
            }

            Interval ax = axis_interval(axis);
            double adinv = 1.0 / rayDirCoord;

            double t0 = (ax.min - rayOrigCoord) * adinv;
            double t1 = (ax.max - rayOrigCoord) * adinv;

            if (t0 < t1) {
                if (t0 > ray_t.min) ray_t.min = t0;
                if (t1 < ray_t.max) ray_t.max = t1;
            } else {
                if (t1 > ray_t.min) ray_t.min = t1;
                if (t0 < ray_t.max) ray_t.max = t0;
            }

            if (ray_t.max <= ray_t.min)
                return false;
        }
        return true;
    }

    public int longest_axis(){
        // Returns the index of the longest axis of the bounding box.
        if (x.size() > y.size())
            return x.size() > z.size() ? 0 : 2;
        else
            return y.size() > z.size() ? 1 : 2;
    }

    //public final AABB empty = new AABB(Interval.empty, Interval.empty, Interval.empty);
    //public final AABB universe = new AABB(Interval.universe, Interval.universe, Interval.universe);
}
