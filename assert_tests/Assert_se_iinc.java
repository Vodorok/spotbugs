
public class Assert_se_iinc {

    public int Test(int x) {
        assert x++ == 0;
        return x;
    }

}
