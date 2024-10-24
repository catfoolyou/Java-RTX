package net.raytracer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static BufferedImage scene;

    public static void saveFileDialogue(JFrame frame) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);

        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIO.write(scene, "jpg", file);
        }

    }
}
