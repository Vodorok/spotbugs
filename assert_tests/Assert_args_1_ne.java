public class Assert_args_1_ne {
    public static String FalsePos(String s) {
        assert false : s;
        return s;
    }

    public static String FalsePos1(String s) {
        assert false;
        return s;
    }

    public static boolean FalsePos2(boolean b) {
        boolean l = b;
        assert l: b;
        return b;
    }

    public static void FalsePos3(int x, String s) {
        if (x < 0) {
            assert false : "This is a false pos" + s; 
        }
    }

}
