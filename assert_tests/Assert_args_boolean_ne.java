public class Assert_args_boolean_ne {
    public boolean getAbs(boolean x) {
        boolean y = !x;
        assert y == true;
        return y;
    }
}
