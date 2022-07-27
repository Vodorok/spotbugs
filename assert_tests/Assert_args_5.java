public class Assert_args_5 {
    public int args(int x) { return x + 1; }
    public int getAbsAdd(int x, int y) {
        assert Math.abs(args(y)) != Integer.MIN_VALUE;
        int absX = Math.abs(x);
        int absY = Math.abs(y);
        return absX + absY;
    }
}
