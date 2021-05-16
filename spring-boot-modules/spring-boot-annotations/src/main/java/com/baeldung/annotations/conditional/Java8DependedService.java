package java.com.baeldung.annotations.conditional;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
@Conditional(Java8Condition.class)
public class Java8DependedService {
}
