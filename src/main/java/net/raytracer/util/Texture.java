package net.raytracer.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import net.raytracer.math.*;
import javax.imageio.ImageIO;

public class Texture {
    public Vector3 albedo;

    public Vector3 value(double u, double v, Vector3 p){
        return null;
    }
}

class ImageTexture extends Texture{
    public BufferedImage image;

    public ImageTexture(String filename) throws IOException{
        this.image = ImageIO.read(new File(filename));
    }

    public Vector3 value(double u, double v, Vector3 p){
        // If we have no texture data, then return solid cyan as a debugging aid.
        if (image.getHeight() <= 0) return new Vector3(0,1,1);

        // Clamp input texture coordinates to [0,1] x [1,0]
        u = new Interval(0,1).clamp(u);
        v = 1.0 - new Interval(0,1).clamp(v);  // Flip V to image coordinates

        int i = (int) u * image.getWidth();
        int j = (int) v * image.getHeight();

        int clr = image.getRGB(i, j);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;

        double color_scale = 1.0 / 255.0;
        return new Vector3(color_scale * red, color_scale * green, color_scale * blue);
    }
}
