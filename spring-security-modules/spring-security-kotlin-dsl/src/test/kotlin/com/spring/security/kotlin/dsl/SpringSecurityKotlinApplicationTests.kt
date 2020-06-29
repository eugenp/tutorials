package com.spring.security.kotlin.dsl

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityKotlinApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `ordinary user not permitted to access the endpoint`() {
        this.mockMvc
                .perform(get("/greetings")
                .with(httpBasic("user", "password")))
                .andExpect(unauthenticated())
    }
}
