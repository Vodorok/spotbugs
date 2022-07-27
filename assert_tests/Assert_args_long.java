public class Assert_args_long {
    public long getAbs(long x) {
        assert Math.abs(x) != Integer.MIN_VALUE;
        long absX = Math.abs(x);
        return absX;
    }

    public long compLong(long l) {
        assert l == 0;
        return l;
    }
}
