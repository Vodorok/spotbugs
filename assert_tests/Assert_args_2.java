public class Assert_args_2 {
    public Object print_x(Object x) {
        assert x != null;
        Double f = 3.0;
        System.out.println(f.toString());
        return x;
    }

    public static String Helper(String s) { return s; }
    public static void Test(String s) {
        assert Helper(s) == null : "This is not a false pos" + s; 
    }
}
