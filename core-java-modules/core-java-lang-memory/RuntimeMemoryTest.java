public class RuntimeMemoryTest {
 
    public static void main(String[] args) {
        System.out.println("Free Memory   : " + Runtime.getRuntime().freeMemory() + " bytes");
        System.out.println("Total Memory  : " + Runtime.getRuntime().totalMemory() + " bytes");
        System.out.println("Max Memory    : " + Runtime.getRuntime().maxMemory() + " bytes");
    }
}
