package net.raytracer;

import net.raytracer.object.*;
import net.raytracer.util.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Scene {
    ArrayList<Material> materialList = new ArrayList<Material>();
    HittableList world;
    public Camera cam;

    final double infinity = Double.POSITIVE_INFINITY;
    final double pi = 3.1415926535897932385;

    public Scene(){
        this.world = new HittableList();
        this.cam = new Camera();
    }

    public void add(Hittable h){
        this.world.add(h);
    }

    public void addMaterial(Material m){
        this.materialList.add(m);
    }

    public void render(JFrame frame) throws IOException{
        this.cam.render(this.world, frame);
    }
}
