public class Assert_args_byte {
    public int getAbs(byte x) {
        assert Math.abs(x) != Integer.MIN_VALUE;
        int absX = Math.abs(x);
        return absX;
    }
}
