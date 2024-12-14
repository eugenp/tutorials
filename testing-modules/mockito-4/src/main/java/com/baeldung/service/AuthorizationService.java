package com.baeldung.service;
import com.baeldung.user.CmsUser;
import com.baeldung.action.ActionEnum;

public interface AuthorizationService {
    boolean authorize(CmsUser user, ActionEnum actionEnum);
}
