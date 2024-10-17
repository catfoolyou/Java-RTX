package net.raytracer.math;

public class Ray {
    public Vector3 origin;
    public Vector3 direction;
    public double time;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;

        if (direction.length() != 1) {
            direction = direction.normalize();
        }
        this.direction = direction;
        this.time = 0;
    }

    public Ray(Vector3 origin, Vector3 direction, double time) {
        this.origin = origin;

        if (direction.length() != 1) {
            direction = direction.normalize();
        }
        this.direction = direction;
        this.time = time;
    }

    public Vector3 at(float t){
        return origin.add(direction.multiply(t));
    }

    public Vector3 at(double t){
        return origin.add(direction.multiply((float) t));
    }

}
