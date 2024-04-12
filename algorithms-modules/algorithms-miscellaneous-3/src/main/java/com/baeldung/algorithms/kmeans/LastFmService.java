package com.baeldung.algorithms.kmeans;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static java.util.stream.Collectors.toList;

public interface LastFmService {

    @GET("/2.0/?method=chart.gettopartists&format=json&limit=50")
    Call<Artists> topArtists(@Query("page") int page);

    @GET("/2.0/?method=artist.gettoptags&format=json&limit=20&autocorrect=1")
    Call<Tags> topTagsFor(@Query("artist") String artist);

    @GET("/2.0/?method=chart.gettoptags&format=json&limit=100")
    Call<TopTags> topTags();

    /**
     * HTTP interceptor to intercept all HTTP requests and add the API key to them.
     */
    class Authenticator implements Interceptor {

        private final String apiKey;

        Authenticator(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl url = chain
              .request()
              .url()
              .newBuilder()
              .addQueryParameter("api_key", apiKey)
              .build();
            Request request = chain
              .request()
              .newBuilder()
              .url(url)
              .build();

            return chain.proceed(request);
        }
    }

    @JsonAutoDetect(fieldVisibility = ANY)
    class TopTags {

        private Map<String, Object> tags;

        @SuppressWarnings("unchecked")
        public Set<String> all() {
            List<Map<String, Object>> topTags = (List<Map<String, Object>>) tags.get("tag");
            return topTags
              .stream()
              .map(e -> ((String) e.get("name")))
              .collect(Collectors.toSet());
        }
    }

    @JsonAutoDetect(fieldVisibility = ANY)
    class Tags {

        @JsonProperty("toptags") private Map<String, Object> topTags;

        @SuppressWarnings("unchecked")
        public Map<String, Double> all() {
            try {
                Map<String, Double> all = new HashMap<>();
                List<Map<String, Object>> tags = (List<Map<String, Object>>) topTags.get("tag");
                for (Map<String, Object> tag : tags) {
                    all.put(((String) tag.get("name")), ((Integer) tag.get("count")).doubleValue());
                }

                return all;
            } catch (Exception e) {
                return Collections.emptyMap();
            }
        }
    }

    @JsonAutoDetect(fieldVisibility = ANY)
    class Artists {

        private Map<String, Object> artists;

        @SuppressWarnings("unchecked")
        public List<String> all() {
            try {
                List<Map<String, Object>> artists = (List<Map<String, Object>>) this.artists.get("artist");
                return artists
                  .stream()
                  .map(e -> ((String) e.get("name")))
                  .collect(toList());
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }
    }
}
