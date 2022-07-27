public class Assert_args_4 {
    public int args(int x) { return x + 1; }
    public int getAbsAdd(int x, int y) {
        assert Math.abs(x) != Integer.MIN_VALUE;
        assert args(y) != Integer.MIN_VALUE;
        assert Math.abs(args(y)) != Integer.MIN_VALUE;
        int absX = Math.abs(x);
        int absY = Math.abs(y);
        assert (absX <= Integer.MAX_VALUE - absY);
        return absX + absY;
    }
}
