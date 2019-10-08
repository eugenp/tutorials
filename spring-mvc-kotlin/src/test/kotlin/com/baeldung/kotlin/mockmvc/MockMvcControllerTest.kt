package com.baeldung.kotlin.mockmvc

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity.status
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@WebMvcTest
class MockMvcControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var mapper: ObjectMapper

    @Test
    fun `when supported user is given then raw MockMvc-based validation is successful`() {
        mockMvc.perform(MockMvcRequestBuilders
                                .post("/mockmvc/validate")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(Request(Name("admin", "")))))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("{}"))
    }

    @Test
    fun `when supported user is given then kotlin DSL-based validation is successful`() {
        doTest(Request(Name("admin", "")), Response(null))
    }

    @Test
    fun `when unsupported user is given then validation is failed`() {
        doTest(Request(Name("some-name", "some-surname")), Response(MockMvcController.ERROR))
    }

    private fun doTest(input: Request, expectation: Response) {
        mockMvc.post("/mockmvc/validate") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(input)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(mapper.writeValueAsString(expectation)) }
        }
    }
}

@SpringBootApplication
class MockMvcApplication