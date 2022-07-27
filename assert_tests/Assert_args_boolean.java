public class Assert_args_boolean {
    public boolean compBool1(boolean b) {
        assert b == true;
        return b;
    }

    public boolean compBool2(boolean b) {
        assert b;
        return b;
    }
}
