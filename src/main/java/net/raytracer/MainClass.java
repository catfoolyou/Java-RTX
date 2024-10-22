package net.raytracer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import static javax.swing.WindowConstants.*;

public class MainClass {

    public static void createGUI() throws IOException {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(800, 450));
        frame.setTitle("Raytracer");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menu = new JMenuBar();

        JMenu loadFiles = new JMenu("File");
        JMenu defaultScenes = new JMenu("Default Scenes");

        JMenuItem spheres = new JMenuItem("Random spheres");
        JMenuItem checker = new JMenuItem("Spheres with checkers");
        JMenuItem quad = new JMenuItem("Simple quad scene");
        JMenuItem light = new JMenuItem("Emissive material test");
        JMenuItem cornell = new JMenuItem("Cornell box");
        JMenuItem smoke = new JMenuItem("Cornell box with smoke mediums");

        defaultScenes.add(spheres);
        defaultScenes.add(checker);
        defaultScenes.add(quad);
        defaultScenes.add(light);
        defaultScenes.add(cornell);
        defaultScenes.add(smoke);

        JMenuItem custom = new JMenuItem("Load custom scene");
        loadFiles.add(defaultScenes);
        loadFiles.add(custom);

        JMenu edit = new JMenu("Edit");
        JMenuItem save = new JMenuItem("Save to file");
        edit.add(save);

        JMenu scene = new JMenu("Scene");
        JMenuItem render = new JMenuItem("Render scene");
        scene.add(render);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Camera.saveFileDialogue(frame);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        light.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    frame.repaint();
                    DefaultScenes.SimpleLight(frame);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        menu.add(loadFiles);
        menu.add(edit);
        menu.add(scene);

        frame.setJMenuBar(menu);
    }

    public static void main(String[] args) throws IOException {
        createGUI();
    }
}