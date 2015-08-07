package org.baeldung.event;

import org.baeldung.model.User;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

public class BeforeConvertListener extends AbstractMongoEventListener<User> {
     
    @Override
    public void onBeforeConvert(User user) {
        System.out.println("stop");
    }
}
