package com.baeldung.returnnull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

@GraphQlTest(PostController.class)
@ActiveProfiles("returnnull")
@Import(GraphQlConfig.class)
class PostControllerIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void givenNewPostData_whenExecuteMutation_thenReturnCustomNullScalar() {
        String documentName = "create_post_return_custom_scalar";

        graphQlTester.documentName(documentName)
          .variable("title", "New Post")
          .variable("text", "New post text")
          .variable("category", "category")
          .variable("author", "Alex")
          .execute()
          .path("createPostReturnCustomScalar")
          .valueIsNull();

    }

    @Test
    void givenNewPostData_whenExecuteMutation_thenReturnNullType() {
        String documentName = "create_post_return_nullable_type";

        graphQlTester.documentName(documentName)
          .variable("title", "New Post")
          .variable("text", "New post text")
          .variable("category", "category")
          .variable("author", "Alex")
          .execute()
          .path("createPostReturnNullableType")
          .valueIsNull();

    }

}
