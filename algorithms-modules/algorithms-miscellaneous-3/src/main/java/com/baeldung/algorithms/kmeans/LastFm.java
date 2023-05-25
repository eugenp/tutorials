package com.baeldung.algorithms.kmeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static java.util.stream.Collectors.toSet;

public class LastFm {

    private static OkHttpClient okHttp = new OkHttpClient.Builder()
      .addInterceptor(new LastFmService.Authenticator("put your API key here"))
      .build();

    private static Retrofit retrofit = new Retrofit.Builder()
      .client(okHttp)
      .addConverterFactory(JacksonConverterFactory.create())
      .baseUrl("http://ws.audioscrobbler.com/")
      .build();

    private static LastFmService lastFm = retrofit.create(LastFmService.class);

    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        List<String> artists = getTop100Artists();
        Set<String> tags = getTop100Tags();
        List<Record> records = datasetWithTaggedArtists(artists, tags);

        Map<Centroid, List<Record>> clusters = KMeans.fit(records, 7, new EuclideanDistance(), 1000);
        // Print the cluster configuration
        clusters.forEach((key, value) -> {
            System.out.println("------------------------------ CLUSTER -----------------------------------");

            System.out.println(sortedCentroid(key));
            String members = String.join(", ", value
              .stream()
              .map(Record::getDescription)
              .collect(toSet()));
            System.out.print(members);

            System.out.println();
            System.out.println();
        });

        Map<String, Object> json = convertToD3CompatibleMap(clusters);
        System.out.println(mapper.writeValueAsString(json));
    }

    private static Map<String, Object> convertToD3CompatibleMap(Map<Centroid, List<Record>> clusters) {
        Map<String, Object> json = new HashMap<>();
        json.put("name", "Musicians");
        List<Map<String, Object>> children = new ArrayList<>();
        clusters.forEach((key, value) -> {
            Map<String, Object> child = new HashMap<>();
            child.put("name", dominantGenre(sortedCentroid(key)));
            List<Map<String, String>> nested = new ArrayList<>();
            for (Record record : value) {
                nested.add(Collections.singletonMap("name", record.getDescription()));
            }
            child.put("children", nested);

            children.add(child);
        });
        json.put("children", children);
        return json;
    }

    private static String dominantGenre(Centroid centroid) {
        return centroid
          .getCoordinates()
          .keySet()
          .stream()
          .limit(2)
          .collect(Collectors.joining(", "));
    }

    private static Centroid sortedCentroid(Centroid key) {
        List<Map.Entry<String, Double>> entries = new ArrayList<>(key
          .getCoordinates()
          .entrySet());
        entries.sort((e1, e2) -> e2
          .getValue()
          .compareTo(e1.getValue()));

        Map<String, Double> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : entries) {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return new Centroid(sorted);
    }

    private static List<Record> datasetWithTaggedArtists(List<String> artists, Set<String> topTags) throws IOException {
        List<Record> records = new ArrayList<>();
        for (String artist : artists) {
            Map<String, Double> tags = lastFm
              .topTagsFor(artist)
              .execute()
              .body()
              .all();

            // Only keep popular tags.
            tags
              .entrySet()
              .removeIf(e -> !topTags.contains(e.getKey()));

            records.add(new Record(artist, tags));
        }
        return records;
    }

    private static Set<String> getTop100Tags() throws IOException {
        return lastFm
          .topTags()
          .execute()
          .body()
          .all();
    }

    private static List<String> getTop100Artists() throws IOException {
        List<String> artists = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            artists.addAll(lastFm
              .topArtists(i)
              .execute()
              .body()
              .all());
        }

        return artists;
    }
}
