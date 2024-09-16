public class Interval {
    double min;
    double max;

    public Interval(){
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
    }

    public Interval(double min, double max){
        this.min = min;
        this.max = max;
    }

    public Interval(Interval a, Interval b) {
        this.min = a.min <= b.min ? a.min : b.min;
        this.max = a.max >= b.max ? a.max : b.max;
    }

    public double size(){
        return max - min;
    }

    boolean contains(double x){
        return min <= x && x <= max;
    }
    
    boolean surrounds(double x){
        return min < x && x < max;
    }

    double clamp(double x){
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }

    Interval expand(double delta){
        double padding = delta / 2;
        return new Interval(min - padding, max + padding);
    }

    public final static Interval empty = new Interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    public final static Interval universe = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
}
