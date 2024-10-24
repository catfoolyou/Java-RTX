package net.raytracer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static void saveFileDialogue(JFrame frame, BufferedImage image) throws IOException {
        int option = JOptionPane.showConfirmDialog(frame, "Save as result.jpg", "Save to file?", JOptionPane.YES_NO_OPTION);
        if(option == JOptionPane.YES_OPTION && image != null){
            ImageIO.write(image, "jpg", new File("result.jpg"));
        }
        else{
            System.out.println("Nothing to save dumbass");
        }
    }
}
