package net.raytracer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static BufferedImage scene;

    public static Scene uploadCustomScene(JFrame frame){
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        File sceneFile = new File("");

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            sceneFile = fileChooser.getSelectedFile();
        }

        return getSceneFromFile(sceneFile);
    }

    public static Scene getSceneFromFile(File sceneFile){
        return null;
    }

    public static void saveFileDialogue(JFrame frame) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);

        fileChooser.setFileFilter(new FileFilter() {
            public String getDescription() {
                return "JPG Images (*.jpg)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".jpg") || filename.endsWith(".jpeg") ;
                }
            }
        });

        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIO.write(scene, "jpg", file);
        }

    }
}
