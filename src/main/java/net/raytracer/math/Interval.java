package net.raytracer.math;

public class Interval {
    public double min;
    public double max;

    public Interval(){
        this.min = Double.POSITIVE_INFINITY;
        this.max = Double.NEGATIVE_INFINITY;
    }

    public Interval(double min, double max){
        this.min = min;
        this.max = max;
    }

    public Interval(Interval a, Interval b) {
        this.min = a.min <= b.min ? a.min : b.min;
        this.max = a.max >= b.max ? a.max : b.max;
    }

    public Interval add(double d){
        return new Interval(this.min + d, this.max + d);
    }

    public double size(){
        return max - min;
    }

    public boolean contains(double x){
        return min <= x && x <= max;
    }
    
    public boolean surrounds(double x){
        return min < x && x < max;
    }

    public double clamp(double x){
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }

    public Interval expand(double delta){
        double padding = delta / 2;
        return new Interval(min - padding, max + padding);
    }

    public final static Interval empty = new Interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    public final static Interval universe = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
}
