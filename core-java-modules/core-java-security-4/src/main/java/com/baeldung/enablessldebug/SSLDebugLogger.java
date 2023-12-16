package com.baeldung.enablessldebug;

public class SSLDebugLogger {
    public static void enableSSLDebugUsingSystemProperties() {
        System.setProperty("javax.net.debug", "ssl");
    }

    public static void makeHttpsRequest() throws Exception {
        String url = "https://github.com/eugenp/tutorials";
        URL httpsUrl = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) httpsUrl.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            System.out.println("Response from " + url + ":");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println();
        }
    }
}
