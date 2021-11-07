import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class HttpRepository implements Repository {

    private final HttpClient httpClient;

    public HttpRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<String> getStockPrice(int noOfDays) {
        final var request = HttpRequest.newBuilder(URI.create("http://123.456.45.45/list?noOfDays=" + noOfDays)).GET().build();
        try {
            return Arrays.stream(httpClient.send(request, BodyHandlers.ofString()).body().split(",")).collect(toList());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching list");
        }
    }
}
