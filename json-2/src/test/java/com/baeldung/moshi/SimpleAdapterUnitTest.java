package com.baeldung.moshi;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

public class SimpleAdapterUnitTest {
    @Test
    public void whenSerializing_thenAdapterUsed() {
        Moshi moshi = new Moshi.Builder()
          .add(new AuthorAdapter())
          .build();
        JsonAdapter<Post> jsonAdapter = moshi.adapter(Post.class);

        Post post = new Post("My Post", new Author("Baeldung", "baeldung@example.com"), "This is my post");
        String json = jsonAdapter.toJson(post);
        System.out.println(json);
    }

    @Test
    public void whenDeserializing_thenAdapterUsed() throws IOException {
        Moshi moshi = new Moshi.Builder()
          .add(new AuthorAdapter())
          .build();
        JsonAdapter<Post> jsonAdapter = moshi.adapter(Post.class);

        String json = "{\"author\":\"Baeldung <baeldung@example.com>\",\"text\":\"This is my post\",\"title\":\"My Post\"}";
        Post post = jsonAdapter.fromJson(json);
        System.out.println(post);
    }
    public static class AuthorAdapter {
        private Pattern pattern = Pattern.compile("^(.*) <(.*)>$");
        @ToJson
        public String toJson(Author author) {
            return author.name + " <" + author.email + ">";
        }

        @FromJson
        public Author fromJson(String author) {
            Matcher matcher = pattern.matcher(author);
            return matcher.find() ? new Author(matcher.group(1), matcher.group(2)) : null;
        }
    }

    public static class Author {
        private String name;
        private String email;

        public Author() {
        }

        public Author(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("name", name).append("email", email).toString();
        }
    }
    public static class Post {
        private String title;
        private Author author;
        private String text;

        public Post() {
        }

        public Post(String title, Author author, String text) {
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

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
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
