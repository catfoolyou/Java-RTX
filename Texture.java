public class Texture {
    public Vector3 albedo;

    public Vector3 value(double u, double v, Vector3 p){
        return null;
    }
}

class SolidColor extends Texture{
    public Vector3 albedo;

    public SolidColor(Vector3 albedo){
        this.albedo = albedo;
    }

    public SolidColor(double r, double g, double b){
        this.albedo = new Vector3(r, g, b);
    }

    public Vector3 value(double u, double v, Vector3 p){
        return this.albedo;
    }
}

class Checker extends Texture{
    double inv_scale;
    Texture even;
    Texture odd;

    public Checker(double scale, Texture even, Texture odd){
        this.inv_scale = scale;
        this.even = even;
        this.odd = odd;
    }

    public Checker(double scale, Vector3 c1, Vector3 c2){
        this.inv_scale = scale;
        this.even = new SolidColor(c1);
        this.odd = new SolidColor(c2);
    }

    public Vector3 value(double u, double v, Vector3 p){
        int xInteger = (int) Math.floor(inv_scale * p.x);
        int yInteger = (int) Math.floor(inv_scale * p.y);
        int zInteger = (int) Math.floor(inv_scale * p.z);

        boolean isEven = (xInteger + yInteger + zInteger) % 2 == 0;

        return isEven ? even.value(u, v, p) : odd.value(u, v, p);
    }
}
