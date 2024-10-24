package net.raytracer;

import javax.swing.*;
import java.awt.*;
import static javax.swing.WindowConstants.*;

public class RenderUtils {

    public static void renderSpheres(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(800, 450));
        frame.setTitle("Random spheres");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DefaultScenes.Spheres(frame);
    }

    public static void renderCheckers(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(800, 450));
        frame.setTitle("Spheres with checkers");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DefaultScenes.SpheresChecker(frame);
    }

    public static void renderQuads(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(600, 600));
        frame.setTitle("Simple quad scene");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DefaultScenes.Quads(frame);
    }

    public static void renderLights(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(800, 450));
        frame.setTitle("Emissive material test");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DefaultScenes.SimpleLight(frame);
    }

    public static void renderCornell(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(600, 600));
        frame.setTitle("Cornell box");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DefaultScenes.CornellBox(frame);
    }

    public static void renderSmoke(JFrame frame){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(600, 600));
        frame.setTitle("Cornell box with smoke mediums");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DefaultScenes.CornellSmoke(frame);
    }

    public static void renderCustomScene(JFrame frame, Scene custom){
        frame.getContentPane().removeAll();
        frame.setSize(new Dimension(custom.cam.image_width, custom.cam.image_height));
        frame.setTitle("Custom scene");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        custom.render(frame);

    }

}
