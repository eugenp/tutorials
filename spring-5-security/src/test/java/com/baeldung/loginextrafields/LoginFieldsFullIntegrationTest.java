package com.baeldung.loginextrafields;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.baeldung.loginextrafieldscustom.ExtraLoginFieldsApplication;
import com.baeldung.loginextrafieldscustom.User;

@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
@SpringBootTest(classes = ExtraLoginFieldsApplication.class)
public class LoginFieldsFullIntegrationTest extends AbstractExtraLoginFieldsIntegrationTest {

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
