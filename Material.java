
import javax.crypto.spec.RC2ParameterSpec;

public class Material {
    public Vector3 albedo;
    public Ray scattered;

    boolean scatter(Ray r_in, hit_record rec){
        return false;
    }
}

class Lambertian extends Material{
    public Texture texture;
    public Ray scattered;

    public Lambertian(Vector3 al){
        this.texture = new SolidColor(al);
    }

    public Lambertian(Texture tex){
        this.texture = tex;
    }

    public boolean scatter(Ray r_in, hit_record rec){
        Vector3 scatter_direction = rec.normal.add(Vector3.random_unit_vector());

        if (scatter_direction.near_zero())
            scatter_direction = rec.normal;

        this.scattered = new Ray(rec.p, scatter_direction, r_in.time);
        rec.setMaterial(this.texture.value(rec.u, rec.v, rec.p), this.scattered);
        
        return true;
    }
}

class Metal extends Material{
    public Texture texture;
    public Ray scattered;
    private double fuzz;

    public Metal(Vector3 albedo, double fuzz){
        this.texture = new SolidColor(albedo);
        this.fuzz = fuzz;
    }

    public Metal(Texture tex, double fuzz){
        this.texture = tex;
        this.fuzz = fuzz;
    }

    public boolean scatter(Ray r_in, hit_record rec){
        Vector3 reflected = Vector3.reflect(r_in.direction, rec.normal);
        reflected = Vector3.unit_vector(reflected).add(Vector3.random_unit_vector().multiply(fuzz));

        this.scattered = new Ray(rec.p, reflected, r_in.time);

        rec.setMaterial(this.texture.value(rec.u, rec.v, rec.p), this.scattered);
        return Vector3.dot(scattered.direction, rec.normal) > 0;
    }
}

class Dielectric extends Material{
    public double refraction_index;

    public Dielectric(double refraction_index){
        this.refraction_index = refraction_index;
    }

    public boolean scatter(Ray r_in, hit_record rec){
        Vector3 attenuation = new Vector3(1.0, 1.0, 1.0);
        double ri = rec.front_face ? (1.0 / refraction_index) : refraction_index;

        Vector3 unit_direction = Vector3.unit_vector(r_in.direction);
        //Vector3 refracted = Vector3.refract(unit_direction, rec.normal, ri);
        double cos_theta = Math.min(Vector3.dot(new Vector3().subtract(unit_direction), rec.normal), 1.0);
        double sin_theta = Math.sqrt(1.0 - cos_theta * cos_theta);

        boolean cannot_refract = ri * sin_theta > 1.0;
        Vector3 direction;

        if (cannot_refract || reflectance(cos_theta, ri) > Vector3.random_double())
            direction = Vector3.reflect(unit_direction, rec.normal);
        else
            direction = Vector3.refract(unit_direction, rec.normal, ri);

        this.scattered = new Ray(rec.p, direction, r_in.time);
        rec.setMaterial(attenuation, this.scattered);
        return true;
        
    }

    static double reflectance(double cosine, double refraction_index) {
        // Use Schlick's approximation for reflectance.
        double r0 = (1 - refraction_index) / (1 + refraction_index);
        r0 = r0 * r0;
        return r0 + (1 - r0) * Math.pow((1 - cosine), 5);
    }
}

