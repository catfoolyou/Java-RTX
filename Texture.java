
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

class ImageTexture extends Texture{
    public BufferedImage bufImage;
    private File image;

    public ImageTexture(String filename) throws IOException{
        this.image = new File(filename);
        this.bufImage = ImageIO.read(image);
    }

    public Vector3 value(double u, double v, Vector3 p){
        // If we have no texture data, then return solid cyan as a debugging aid.
        if (bufImage.getHeight() <= 0) return new Vector3(0,1,1);

        // Clamp input texture coordinates to [0,1] x [1,0]
        u = new Interval(0,1).clamp(u);
        v = 1.0 - new Interval(0,1).clamp(v);  // Flip V to image coordinates

        int i = (int) u * bufImage.getWidth();
        int j = (int) v * bufImage.getHeight();

        int clr = bufImage.getRGB(i, j);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;

        double color_scale = 1.0 / 255.0;
        return new Vector3(color_scale * red, color_scale * green, color_scale * blue);
    }
}
