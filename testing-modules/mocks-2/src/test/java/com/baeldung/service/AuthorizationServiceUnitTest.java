package com.baeldung.service;

import com.baeldung.action.ActionEnum;
import com.baeldung.user.CmsUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthorizationServiceUnitTest {

    @Mock
    private AuthorizationService authorizationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(this.authorizationService.authorize(any(CmsUser.class), any(ActionEnum.class)))
                .thenAnswer(invocation -> {
                    CmsUser user = invocation.getArgument(0);
                    ActionEnum action = invocation.getArgument(1);
                    switch(user.getRole()) {
                        case "ADMIN": return true;
                        case "EDITOR": return action!=ActionEnum.DELETE;
                        case "VIEWER": return action==ActionEnum.READ;
                        case "GUEST":
                        default: return false;
                    }
                });
    }

    @Test
    public void givenRoles_whenInvokingAuthorizationService_thenReturnExpectedResults() {
        CmsUser adminUser = new CmsUser("Admin User", "ADMIN");
        CmsUser guestUser = new CmsUser("Guest User", "GUEST");
        CmsUser editorUser = new CmsUser("Editor User", "EDITOR");
        CmsUser viewerUser = new CmsUser("Viewer User", "VIEWER");

        for (ActionEnum action : ActionEnum.values()) {
            assertTrue("Admin should have access to all actions",
                    authorizationService.authorize(adminUser, action));
        }

        for (ActionEnum action : ActionEnum.values()) {
            if (action == ActionEnum.DELETE) {
                assertFalse("Editor should not have access to DELETE",
                        authorizationService.authorize(editorUser, action));
            } else {
                assertTrue("Editor should have access to non-DELETE actions",
                        authorizationService.authorize(editorUser, action));
            }
        }

        for (ActionEnum action : ActionEnum.values()) {
            if (action == ActionEnum.READ) {
                assertTrue("Viewer should have access to READ",
                        authorizationService.authorize(viewerUser, action));
            } else {
                assertFalse("Viewer should not have access to non-READ actions",
                        authorizationService.authorize(viewerUser, action));
            }
        }

        for (ActionEnum action : ActionEnum.values()) {
            assertFalse("Guest should not have access to any actions",
                    authorizationService.authorize(guestUser, action));
        }
    }
}
