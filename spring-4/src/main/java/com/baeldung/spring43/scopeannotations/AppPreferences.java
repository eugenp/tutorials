package com.baeldung.spring43.scopeannotations;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
public class AppPreferences extends InstanceCountingService {

}
