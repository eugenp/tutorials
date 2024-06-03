package com.baeldung.autowiremultipleimplementations.candidates;

import com.baeldung.autowiremultipleimplementations.condition.OnFeatureEnabledCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
@Conditional(OnFeatureEnabledCondition.class)
public class GoodServiceE implements GoodService {

    public String getHelloMessage() {
        return "Hello from E!";
    }
}
