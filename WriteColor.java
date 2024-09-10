import java.io.BufferedWriter;
import java.io.IOException;

public class WriteColor {
    public static void write_color(BufferedWriter writerin, float r2, float g2, float b2) throws IOException{        
        float r = r2;
        float g = g2;
        float b = b2;

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

        Interval intensity = new Interval(0.000, 0.999);

        int ir = (int) (256 * intensity.clamp(r));
        int ig = (int) (256 * intensity.clamp(g));
        int ib = (int) (256 * intensity.clamp(b));

        String output = (ir + " " + ig + " " + ib + "\n");                
        writerin.write(output);

    }
}
