package com.baeldung.hexagonal;

import com.baeldung.hexagonal.domain.Notification;
import com.baeldung.hexagonal.domain.NotificationHandle;
import com.baeldung.hexagonal.domain.User;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class Fixtures {

    static User randomUser() {
        User user = new User();
        user.setId(randomString());
        user.setName(randomString());
        NotificationHandle handle = new NotificationHandle();
        handle.setType(NotificationHandle.Type.SMS);
        handle.setHandle(randomString());
        user.setHandles(Collections.singletonList(handle));
        return user;
    }

    static Notification randomNotification(User user) {
        Notification notification = new Notification();

        Map<String, String> data = new HashMap<>();
        data.put("msg", randomString());
        data.put("template", randomString());

        notification.setData(data);
        notification.setTargetUsers(Collections.singletonList(user));
        return notification;
    }

    private static String randomString() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

}
