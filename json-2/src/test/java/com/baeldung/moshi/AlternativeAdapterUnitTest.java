package com.baeldung.moshi;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Instant;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

public class AlternativeAdapterUnitTest {
    @Test
    public void whenSerializing_thenAlternativeAdapterUsed() {
        Moshi moshi = new Moshi.Builder()
          .add(new EpochMillisAdapter())
          .build();
        JsonAdapter<Post> jsonAdapter = moshi.adapter(Post.class);

        String json = jsonAdapter.toJson(new Post("Introduction to Moshi Json", "Baeldung", Instant.now()));
        System.out.println(json);
    }

    @Test
    public void whenDeserializing_thenAlternativeAdapterUsed() throws IOException {
        Moshi moshi = new Moshi.Builder()
          .add(new EpochMillisAdapter())
          .build();
        JsonAdapter<Post> jsonAdapter = moshi.adapter(Post.class);

        String json = "{\"author\":\"Baeldung\",\"posted\":1582095269204,\"title\":\"Introduction to Moshi Json\"}";
        Post post = jsonAdapter.fromJson(json);
        System.out.println(post);

    }

    public static class Post {
        String title;
        String author;
        @EpochMillis Instant posted;

        public Post() {
        }

        public Post(String title, String author, Instant posted) {
            this.title = title;
            this.author = author;
            this.posted = posted;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Instant getPosted() {
            return posted;
        }

        public void setPosted(Instant posted) {
            this.posted = posted;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("title", title).append("author", author).append("posted", posted)
                .toString();
        }
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
    @JsonQualifier
    public @interface EpochMillis {
    }

    public static class EpochMillisAdapter {
        @ToJson
        public Long toJson(@EpochMillis Instant input) {
            return input.toEpochMilli();
        }
        @FromJson
        @EpochMillis
        public Instant fromJson(Long input) {
            return Instant.ofEpochMilli(input);
        }

    }
}
