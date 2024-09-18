public class Vector3 {
    public float x;
    public float y;
    public float z;

    final static double pi = 3.1415926535897932385;

     /*
     * Funny vector math library that I stole from carlvbn
     * Also has a lot of shit that I had to add myself 
     * Source can be found here https://github.com/carl-vbn/pure-java-raytracer/blob/master/src/carlvbn/raytracing/math/Vector3.java
     */

    public Vector3(){
        new Vector3(0, 0, 0);
    }

    public Vector3(float xIn, float yIn, float zIn){
        x = xIn;
        y = yIn;
        z = zIn;
    }

    public Vector3(double xIn, double yIn, double zIn){
        x = (float) xIn;
        y = (float) yIn;
        z = (float) zIn;
    }

    public Vector3(float oneCoord){
        x = oneCoord;
        y = oneCoord;
        z = oneCoord;
    }

    public Vector3(double  oneCoord){
        x = (float) oneCoord;
        y = (float) oneCoord;
        z = (float) oneCoord;
    }

    static double degrees_to_radians(double degrees) {
        return degrees * pi / 180.0;
    }

    public static double random_double() {
        return Math.random();
    }
    
    public static double random_double(double min, double max) {
        return min + (max-min) * random_double();
    }

    // Operator overloading doesnt exist.
    // Also vscode is a piece of shit and doesnt even support manifold

    public Vector3 add(Vector3 vec) {
        return new Vector3(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }

    public Vector3 subtract(Vector3 vec) {
        return new Vector3(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }

    public Vector3 multiply(float scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3 multiply(double scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3 multiply(Vector3 vec) {
        return new Vector3(this.x * vec.x, this.y * vec.y, this.z * vec.z);
    }

    public Vector3 divide(Vector3 vec) {
        return new Vector3(this.x / vec.x, this.y / vec.y, this.z / vec.z);
    }

    public float length() {
        return (float) Math.sqrt(length_squared());
    }

    public double length_squared() {
        return ((x*x)+(y*y)+(z*z));
    }

    public boolean near_zero(){
        // Return true if the vector is close to zero in all dimensions.
        double s = 1e-8;
        return (Math.abs(x) < s) && (Math.abs(y) < s) && (Math.abs(z) < s);
    }

    public Vector3 normalize() {
        float length = length();
        return new Vector3(this.x / length, this.y / length, this.z / length);
    }

    public static float distance(Vector3 a, Vector3 b) {
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2)+Math.pow(a.y - b.y, 2)+Math.pow(a.z - b.z, 2));
    }

    public static float dot(Vector3 a, Vector3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static Vector3 cross(Vector3 a, Vector3 b){
        return new Vector3(((a.y * b.z) - (b.y * a.z)), ((a.z * b.x) - (b.z * a.x)), ((a.x * b.y) - (b.x * a.y)));
    }

    public static Vector3 lerp(Vector3 a, Vector3 b, float t) {
        return a.add(b.subtract(a).multiply(t));
    }

    public Vector3 random() {
        return new Vector3(random_double(), random_double(), random_double());
    }

    public static Vector3 random(double min, double max) {
        return new Vector3(random_double(min,max), random_double(min,max), random_double(min,max));
    }

    public static Vector3 unit_vector(Vector3 v) {
        return v.divide(new Vector3(v.length()));
    }
    
    public static Vector3 random_unit_vector() {
        while (true) {
            Vector3 p = Vector3.random(-1, 1);
            double lensq = p.length_squared();
            if (1e-160 < lensq && lensq <= 1)
                return p.divide(new Vector3(Math.sqrt(lensq)));
        }
    }

    public static Vector3 random_on_hemisphere(Vector3 normal) {
        Vector3 on_unit_sphere = random_unit_vector();
        if (dot(on_unit_sphere, normal) > 0.0) // In the same hemisphere as the normal
            return on_unit_sphere;
        else
            return new Vector3(0).subtract(on_unit_sphere);
    }

    public static Vector3 random_in_unit_disk() {
        while (true) {
            Vector3 p = new Vector3(random_double(-1,1), random_double(-1,1), 0);
            if (p.length_squared() < 1)
                return p;
        }
    }

    public static Vector3 reflect(Vector3 v, Vector3 n) {
        return v.subtract(new Vector3(2)).multiply(new Vector3(dot(v,n))).multiply(n);
    }

    public static Vector3 refract(Vector3 uv, Vector3 n, double etai_over_etat) {
        double cos_theta = Math.min(dot(new Vector3().subtract(uv), n), 1.0);
        Vector3 r_out_perp = (uv.add(n.multiply(cos_theta))).multiply(etai_over_etat);
        Vector3 r_out_parallel = new Vector3(-Math.sqrt(Math.abs(1.0 - r_out_perp.length_squared()))).multiply(n);

        return r_out_perp.add(r_out_parallel);
    }

    @Override
    public Vector3 clone() {
        return new Vector3(x, y, z);
    }
}

class point3 extends Vector3{

}
