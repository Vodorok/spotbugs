public class Assert_args_double {
    public double getAbs(double x) {
        assert Math.abs(x) != Integer.MIN_VALUE;
        double absX = Math.abs(x);
        return absX;
    }

    public double compDouble(double d) {
        assert d > 0;
        return d;
    }
}
