public class Interval {
    double min;
    double max;

    final Interval empty = new Interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    final Interval universe = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

    public Interval(){
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
    }

    public Interval(double min, double max){
        this.min = min;
        this.max = max;
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
}
