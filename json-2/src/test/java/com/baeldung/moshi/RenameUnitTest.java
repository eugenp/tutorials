package com.baeldung.moshi;

import java.io.IOException;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;

public class RenameUnitTest {

    @Test
    public void whenSerializing_thenFieldsGetRenamed() {
        Moshi moshi = new Moshi.Builder()
          .build();
        JsonAdapter<Post> jsonAdapter = moshi.adapter(Post.class);

        Post post = new Post("My Post", "Baeldung");
        String json = jsonAdapter.toJson(post);
        System.out.println(json);
    }

    @Test
    public void whenSerializing_thenRenamedFieldsGetConsumed() throws IOException {
        Moshi moshi = new Moshi.Builder()
          .build();
        JsonAdapter<Post> jsonAdapter = moshi.adapter(Post.class);

        String json = "{\"authored_by\":\"Baeldung\",\"title\":\"My Post\"}";
        Post post = jsonAdapter.fromJson(json);
        System.out.println(post);
    }
    public static class Post {
        private String title;
        @Json(name = "authored_by")
        private String author;

        public Post() {
        }

        public Post(String title, String author) {
            this.title = title;
            this.author = author;
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

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("title", title).append("author", author).toString();
        }
    }
}
