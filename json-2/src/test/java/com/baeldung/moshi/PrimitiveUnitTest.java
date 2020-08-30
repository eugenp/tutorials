package com.baeldung.moshi;

import java.io.IOException;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

public class PrimitiveUnitTest {
    @Test
    public void whenSerializing_thenCorrectJsonProduced() {
        Moshi moshi = new Moshi.Builder()
          .build();
        JsonAdapter<Post> jsonAdapter = moshi.adapter(Post.class);

        Post post = new Post("My Post", "Baeldung", "This is my post");
        String json = jsonAdapter.toJson(post);
        System.out.println(json);
    }

    @Test
    public void whenDeserializing_thenCorrectJsonConsumed() throws IOException {
        Moshi moshi = new Moshi.Builder()
          .build();
        JsonAdapter<Post> jsonAdapter = moshi.adapter(Post.class);

        String json = "{\"author\":\"Baeldung\",\"text\":\"This is my post\",\"title\":\"My Post\"}";
        Post post = jsonAdapter.fromJson(json);
        System.out.println(post);
    }

    public static class Post {
        private String title;
        private String author;
        private String text;

        public Post() {
        }

        public Post(String title, String author, String text) {
            this.title = title;
            this.author = author;
            this.text = text;
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

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("title", title).append("author", author).append("text", text)
                .toString();
        }
    }
}
