import service.GreetingPort;
import service.impl.GreetingPortHelloWorldAdapter;

public class HelloHexagonApplication {
    public static void main(String[] args) {
        GreetingPort greetingPort = new GreetingPortHelloWorldAdapter();
        greetingPort.greet();
    }
}
