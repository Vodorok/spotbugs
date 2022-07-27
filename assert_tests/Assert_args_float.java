public class Assert_args_float {
    public float getAbs(float x) {
        assert Math.abs(x) != Integer.MIN_VALUE;
        float absX = Math.abs(x);
        return absX;
    }

    public float compFloat(float f) {
        assert f < 0;
        return f;
    }
}
