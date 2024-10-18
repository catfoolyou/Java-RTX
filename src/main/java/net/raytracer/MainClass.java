package net.raytracer;

import java.io.IOException;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.*;

public class MainClass {

    final double infinity = Double.POSITIVE_INFINITY;
    final double pi = 3.1415926535897932385;

    public static void createGUI() throws IOException {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(320, 200));
        frame.setTitle("Raytracer");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menu = new JMenuBar();

        JMenu loadFiles = new JMenu("File");
        JMenuItem defaultScenes = new JMenuItem("Default Scenes");
        JMenuItem custom = new JMenuItem("Load custom scene");
        loadFiles.add(defaultScenes);
        loadFiles.add(custom);

        JMenu edit = new JMenu("Edit");
        JMenuItem save = new JMenuItem("Save to file");
        edit.add(save);

        JMenu scene = new JMenu("Scene");
        JMenuItem render = new JMenuItem("Render scene");
        scene.add(render);

        menu.add(loadFiles);
        menu.add(edit);
        menu.add(scene);

        frame.setJMenuBar(menu);

        DefaultScenes.SimpleLight();
    }

    public static void main(String[] args) throws IOException {
        createGUI();
        /*System.out.println("Enter 1-6 for the default scenes");
        System.out.println("1) Random spheres");
        System.out.println("2) Spheres with checkers");
        System.out.println("3) Simple Quad scene");
        System.out.println("4) Emissive material test");
        System.out.println("5) Cornell box");
        System.out.println("6) Cornell box with smoke mediums");

        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        switch (choice) {
            case 1:
                DefaultScenes.Spheres();
                break;
            case 2:
                DefaultScenes.SpheresChecker();
                break;
            case 3:
                DefaultScenes.Quads();
                break;
            case 4:
                DefaultScenes.SimpleLight();
                break;
            case 5:
                DefaultScenes.CornellBox();
                break;
            case 6:
                DefaultScenes.CornellSmoke();
                break;
            default:
                throw new AssertionError();
        }*/
    }
}