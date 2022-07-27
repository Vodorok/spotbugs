public class Assert_args_short {
    public int getAbs(short x) {
        assert Math.abs(x) != Integer.MIN_VALUE;
        int absX = Math.abs(x);
        return absX;
    }

    public int compShor(short s) {
        assert s == 0;
        return s;
    }
}
