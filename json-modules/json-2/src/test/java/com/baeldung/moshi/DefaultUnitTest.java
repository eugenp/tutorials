package com.baeldung.moshi;

import java.io.IOException;
import java.time.Instant;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

public class DefaultUnitTest {

    @Test
    public void whenDeserializing_thenFieldsGetDefaultValues() throws IOException {
        Moshi moshi = new Moshi.Builder()
          .build();
        JsonAdapter<Post> jsonAdapter = moshi.adapter(Post.class);

        String json = "{\"title\":\"My Post\"}";
        Post post = jsonAdapter.fromJson(json);
        System.out.println(post);
    }
    public static class Post {
        private String title;
        private String author;
        private String posted;

        public Post() {
            posted = Instant.now().toString();
        }

        public Post(String title, String author, String posted) {
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

        public String getPosted() {
            return posted;
        }

        public void setPosted(String posted) {
            this.posted = posted;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("title", title).append("author", author).append("posted", posted)
                .toString();
        }
    }
}
