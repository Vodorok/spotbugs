
public class ThrowTest {
    public ThrowTest() {
        throw new RuntimeException(); // Error, constructor throw.
    }
    
    final void finalize(Object object) {
        System.out.println("I am not overriding the void#finalize() method");
    }
}
