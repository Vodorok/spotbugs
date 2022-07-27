public class Assert_args_2_ne {
    public static boolean FalsePos1(boolean b) {
        boolean l = b;
        assert l: b;
        return b;
    }
    public static boolean FalsePos2(boolean b) {
        boolean l = false;
        if (b == true) {
            l = b;
        }
        assert l: b;
        return b;
    }
}
