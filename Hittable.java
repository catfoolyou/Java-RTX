public class Hittable {
    public Vector3 p;
    public Vector3 normal;
    public double t;

    public Hittable(){

    }

    boolean hittable = true;

    public boolean hit(Ray r, double ray_tmin, double ray_tmax, Hittable hit_record){
        return true;
    }
}
