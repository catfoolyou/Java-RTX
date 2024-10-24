package net.raytracer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.swing.WindowConstants.*;

public class RenderUtils {

    public static BufferedImage renderSpheres(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(800, 450));
        frame.setTitle("Random spheres");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        return DefaultScenes.Spheres(frame);
    }

    public static BufferedImage renderCheckers(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(800, 450));
        frame.setTitle("Spheres with checkers");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        return DefaultScenes.SpheresChecker(frame);
    }

    public static BufferedImage renderQuads(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(600, 600));
        frame.setTitle("Simple quad scene");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        return DefaultScenes.Quads(frame);
    }

    public static BufferedImage renderLights(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(800, 450));
        frame.setTitle("Emissive material test");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        return DefaultScenes.SimpleLight(frame);
    }

    public static BufferedImage renderCornell(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(600, 600));
        frame.setTitle("Cornell box");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        return DefaultScenes.CornellBox(frame);
    }

    public static BufferedImage renderSmoke(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(600, 600));
        frame.setTitle("Cornell box with smoke mediums");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        return DefaultScenes.CornellSmoke(frame);
    }

    public static BufferedImage renderCustomScene(JFrame frame, Scene custom){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(custom.cam.image_width, custom.cam.image_height));
        frame.setTitle("Custom scene");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        return custom.render(frame);
    }
}
