package net.raytracer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainClass {

    public static void createGUI(){
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
        JMenuItem enable = new JMenuItem("Enable debug");
        JMenuItem disable = new JMenuItem("Disable debug");
        edit.add(save);
        edit.add(enable);
        edit.add(disable);

        JMenu source = new JMenu("Source");
        JMenuItem github = new JMenuItem("Source is available on Github");
        source.add(github);

        JMenu help = new JMenu("Help");
        JMenuItem helpText = new JMenuItem("Docs are available on Github");
        help.add(helpText);

        custom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Scene customScene = FileUtils.uploadCustomScene(frame);
                RenderUtils.renderCustomScene(frame, customScene);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    FileUtils.saveFileDialogue(frame);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        enable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Debugger enabled");
                Camera.debuggerEnabled = true;
            }
        });

        disable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Debugger disabled");
                Camera.debuggerEnabled = false;
            }
        });

        spheres.addActionListener(actionEvent -> {
            new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() throws Exception {
                    FileUtils.scene = RenderUtils.renderSpheres(frame);
                    return null;
                }
            }.execute();
        });

        checker.addActionListener(actionEvent -> {
            new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() throws Exception {
                    FileUtils.scene = RenderUtils.renderCheckers(frame);
                    return null;
                }
            }.execute();
        });

        quad.addActionListener(actionEvent -> {
            new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() throws Exception {
                    FileUtils.scene = RenderUtils.renderQuads(frame);
                    return null;
                }
            }.execute();
        });

        light.addActionListener(actionEvent -> {
            new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() throws Exception {
                    FileUtils.scene = RenderUtils.renderLights(frame);
                    return null;
                }
            }.execute();
        });

        cornell.addActionListener(actionEvent -> {
            new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() throws Exception {
                    FileUtils.scene = RenderUtils.renderCornell(frame);
                    return null;
                }
            }.execute();
        });

        smoke.addActionListener(actionEvent -> {
            new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() throws Exception {
                    FileUtils.scene = RenderUtils.renderSmoke(frame);
                    return null;
                }
            }.execute();
        });

        menu.add(loadFiles);
        menu.add(edit);
        menu.add(source);
        menu.add(help);

        frame.setJMenuBar(menu);
        frame.pack();
        frame.setSize(new Dimension(800, 450));
    }

    public static void main(String[] args) throws IOException {
        createGUI();
    }
}