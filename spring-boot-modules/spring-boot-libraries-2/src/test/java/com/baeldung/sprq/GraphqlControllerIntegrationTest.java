package com.baeldung.sprq;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpqrApp.class)
@AutoConfigureMockMvc
public class GraphqlControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookService bookService;

    private static final String GRAPHQL_PATH = "/graphql";

    @Test
    public void givenNoBooks_whenReadAll_thenStatusIsOk() throws Exception {

        String getAllBooksQuery = "{ getAllBooks {id author title } }";

        this.mockMvc.perform(post(GRAPHQL_PATH).content(toJSON(getAllBooksQuery))
            .contentType(
                MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.getAllBooks").isEmpty());
    }

    @Test
    public void whenAddBook_thenStatusIsOk() throws Exception {

        String addBookMutation = "mutation { addBook(newBook: {id: 123, author: \"J.R.R. Tolkien\", "
            + "title: \"The Lord of the Rings\"}) { id author title } }";

        this.mockMvc.perform(post(GRAPHQL_PATH).content(toJSON(addBookMutation))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.addBook.id").value("123"))
            .andExpect(jsonPath("$.addBook.author").value("J.R.R. Tolkien"))
            .andExpect(jsonPath("$.addBook.title").value("The Lord of the Rings"));
    }

    private String toJSON(String query) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", query);
        return jsonObject.toString();
    }
}