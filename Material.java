public class Material {
    boolean scatter(Ray r_in, hit_record rec, Vector3 attenuation, Ray scattered){
        return false;
    }
}

class Lambertian extends Material{
    private Vector3 albedo;

    public Lambertian(Vector3 albedo){
        this.albedo = albedo;
    }

    public boolean scatter(Ray r_in, hit_record rec, Vector3 attenuation, Ray scattered){
        Vector3 scatter_direction = rec.normal.add(Vector3.random_unit_vector());

        if (scatter_direction.near_zero())
            scatter_direction = rec.normal;

        scattered = new Ray(rec.p, scatter_direction);
        attenuation = albedo;
        return true;
    }
}
