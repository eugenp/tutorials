package org.baeldung.sampleabstract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasketballService extends BallService {

    @Autowired
    public BasketballService(RuleRepository ruleRepository) {
        super(ruleRepository);
    }
}
