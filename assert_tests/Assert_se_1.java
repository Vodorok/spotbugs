import java.util.List;
import java.util.ArrayList;

public class Assert_se_1 {
    private List<String> l = new ArrayList<String>();
    public void add_remove() {
        l.add("a");
        assert l.remove(null);
    }
}
