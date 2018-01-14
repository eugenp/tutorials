package com.baeldung.securityextrafields;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.SpringSecurity5ExtraLoginFieldsApplication;

@WebAppConfiguration
@SpringJUnitWebConfig
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringSecurity5ExtraLoginFieldsApplication.class)
public class SecurityExtraFieldsTest {
    
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    
    private MockMvc mockMvc;  

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .apply(springSecurity(springSecurityFilterChain)).build();
    }

    @DisplayName("Access of root path redirects to index")
    @Test
    public void givenRootPathAccess_thenRedirectToIndex() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/index*"));
    }

    @DisplayName("Unauthenticated access of secured resource redirects to login page")
    @Test
    public void givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication() throws Exception {
        this.mockMvc.perform(get("/user/index"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("**/login"));
    }

    @DisplayName("Succesfull auth on login page redirects and extra field exists")
    @Test
    public void givenAccessSecuredResource_whenAuthenticated_thenAuthHasExtraFields() throws Exception {
        MockHttpServletRequestBuilder securedResourceAccess = get("/user/index");
        MvcResult unauthenticatedResult = mockMvc.perform(securedResourceAccess)
            .andExpect(status().is3xxRedirection())
            .andReturn();

        MockHttpSession session = (MockHttpSession) unauthenticatedResult.getRequest()
            .getSession();
        String loginUrl = unauthenticatedResult.getResponse()
            .getRedirectedUrl();

        User user = getUser();

        mockMvc.perform(post(loginUrl)
            .param("username", user.getUsername())
            .param("password", user.getPassword())
            .param("domain", user.getDomain())
            .session(session)
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("**/user/index"))
            .andReturn();

        mockMvc.perform(securedResourceAccess.session(session))
            .andExpect(status().isOk());
        
        SecurityContext securityContext 
            = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication auth = securityContext.getAuthentication();
        assertEquals(((User)auth.getPrincipal()).getDomain(), user.getDomain());
    }

    private User getUser() {
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        return new User("myusername", "mydomain", "password", true, true, true, true, authorities);
    }
}
