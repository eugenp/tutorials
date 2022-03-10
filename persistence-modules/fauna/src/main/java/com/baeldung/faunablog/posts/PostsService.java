package com.baeldung.faunablog.posts;

import com.faunadb.client.FaunaClient;
import com.faunadb.client.types.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.faunadb.client.query.Language.*;

@Component
public class PostsService {
    @Autowired
    private FaunaClient faunaClient;

    Post getPost(String id, Long before) throws ExecutionException, InterruptedException {
        var query = Get(Ref(Collection("posts"), id));
        if (before != null) {
            query = At(Value(before - 1), query);
        }

        var postResult= faunaClient.query(
                Let(
                        "post", query
                ).in(
                        Obj(
                                "post", Var("post"),
                                "author", Get(Select(Arr(Value("data"), Value("authorRef")), Var("post")))
                        )
                )).get();

        return parsePost(postResult);
    }

    List<Post> getAllPosts() throws ExecutionException, InterruptedException {
        var postsResult = faunaClient.query(Map(
                Paginate(
                        Join(
                                Documents(Collection("posts")),
                                Index("posts_sort_by_created_desc")
                        )
                ),
                Lambda(
                        Arr(Value("extra"), Value("ref")),
                        Obj(
                                "post", Get(Var("ref")),
                                "author", Get(Select(Arr(Value("data"), Value("authorRef")), Get(Var("ref"))))
                        )
                )
        )).get();

        var posts = postsResult.at("data").asCollectionOf(Value.class).get();
        return posts.stream().map(this::parsePost).collect(Collectors.toList());
    }

    List<Post> getAuthorPosts(String author) throws ExecutionException, InterruptedException {
        var postsResult = faunaClient.query(Map(
                Paginate(
                        Join(
                                Match(Index("posts_by_author"), Select(Value("ref"), Get(Match(Index("users_by_username"), Value(author))))),
                                Index("posts_sort_by_created_desc")
                        )
                ),
                Lambda(
                        Arr(Value("extra"), Value("ref")),
                        Obj(
                                "post", Get(Var("ref")),
                                "author", Get(Select(Arr(Value("data"), Value("authorRef")), Get(Var("ref"))))
                        )
                )
        )).get();

        var posts = postsResult.at("data").asCollectionOf(Value.class).get();
        return posts.stream().map(this::parsePost).collect(Collectors.toList());
    }

    public void createPost(String author, String title, String contents) throws ExecutionException, InterruptedException {
        faunaClient.query(
                Create(Collection("posts"),
                        Obj(
                                "data", Obj(
                                        "title", Value(title),
                                        "contents", Value(contents),
                                        "created", Now(),
                                        "authorRef", Select(Value("ref"), Get(Match(Index("users_by_username"), Value(author)))))
                        )
                )
        ).get();
    }

    public void updatePost(String id, String title, String contents) throws ExecutionException, InterruptedException {
        faunaClient.query(
                Update(Ref(Collection("posts"), id),
                        Obj(
                                "data", Obj(
                                        "title", Value(title),
                                        "contents", Value(contents))
                        )
                )
        ).get();
    }

    private Post parsePost(Value entry) {
        var author = entry.at("author");
        var post = entry.at("post");

        return new Post(
                post.at("ref").to(Value.RefV.class).get().getId(),
                post.at("data", "title").to(String.class).get(),
                post.at("data", "contents").to(String.class).get(),
                new Author(
                        author.at("data", "username").to(String.class).get(),
                        author.at("data", "name").to(String.class).get()
                ),
                post.at("data", "created").to(Instant.class).get(),
                post.at("ts").to(Long.class).get()
        );
    }
}
