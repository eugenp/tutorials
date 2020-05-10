package com.baeldung.spring43.scopeannotations;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserPreferences extends InstanceCountingService {

}
