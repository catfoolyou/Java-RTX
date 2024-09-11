import java.io.BufferedWriter;
import java.io.IOException;

public class WriteColor {

    static double linear_to_gamma(double linear_component){
        if (linear_component > 0)
            return Math.sqrt(linear_component);
        return 0;
    }


    public static void write_color(BufferedWriter writerin, float r2, float g2, float b2) throws IOException{        
        float r = r2;
        float g = g2;
        float b = b2;

        r = (float) linear_to_gamma(r);
        g = (float) linear_to_gamma(g);
        b = (float) linear_to_gamma(b);

        Interval intensity = new Interval(0.000, 0.999);

        int ir = (int) (256 * intensity.clamp(r));
        int ig = (int) (256 * intensity.clamp(g));
        int ib = (int) (256 * intensity.clamp(b));

        String output = (ir + " " + ig + " " + ib + "\n");                
        writerin.write(output);

    }

    public static void write_color(BufferedWriter writerin, Vector3 vec) throws IOException{        
        float r = vec.x;
        float g = vec.y;
        float b = vec.z;

        r = (float) linear_to_gamma(r);
        g = (float) linear_to_gamma(g);
        b = (float) linear_to_gamma(b);

        Interval intensity = new Interval(0.000, 0.999);

        int ir = (int) (256 * intensity.clamp(r));
        int ig = (int) (256 * intensity.clamp(g));
        int ib = (int) (256 * intensity.clamp(b));

        String output = (ir + " " + ig + " " + ib + "\n");                
        writerin.write(output);

    }
}
