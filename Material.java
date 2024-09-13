public class Material {
    public Vector3 albedo;
    public Ray scattered;

    boolean scatter(Ray r_in, hit_record rec){
        return false;
    }
}

class Lambertian extends Material{
    public Vector3 albedo;
    public Ray scattered;

    public Lambertian(Vector3 albedo){
        this.albedo = albedo;
    }

    public boolean scatter(Ray r_in, hit_record rec){
        Vector3 scatter_direction = rec.normal.add(Vector3.random_unit_vector());

        if (scatter_direction.near_zero())
            scatter_direction = rec.normal;

        this.scattered = new Ray(rec.p, scatter_direction);
        return true;
    }
}

class Metal extends Material{
    public Vector3 albedo;
    public Ray scattered;

    public Metal(Vector3 albedo){
        this.albedo = albedo;
    }

    public boolean scatter(Ray r_in, hit_record rec){
        Vector3 reflected = Vector3.reflect(r_in.direction, rec.normal);
        this.scattered = new Ray(rec.p, reflected);
        return true;
    }
}

