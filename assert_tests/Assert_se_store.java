
public class Assert_se_store {

    public boolean Test(boolean x) {
        boolean y = false;
        assert y = x == true;
        return y;
    }

    public boolean Test(int x) {
        boolean y = false;
        assert y = (x == 1);
        return y;
    }
}
