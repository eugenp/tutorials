package org.baeldung.spring43.scopeannotations;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class LoginAction extends InstanceCountingService {

}
