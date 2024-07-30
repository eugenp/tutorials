//> using jvm 21
//> using javaOpt -Xmx2g, -DappName=baeldungApp, --enable-preview
//> using javaProp language=english, country=usa
//> using javacOpt --release 21 --enable-preview
public class JavaArgs {
    public static void main(String[] args) {
        String appName = System.getProperty("appName");
        String language = System.getProperty("language");
        String country = System.getProperty("country");
        String combinedStr = STR."appName = \{ appName } , language = \{ language } and country = \{ country }";
        System.out.println(combinedStr);
    }
}