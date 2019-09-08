package com.baeldung.truth;

import com.google.common.truth.ComparableSubject;
import com.google.common.truth.FailureStrategy;
import com.google.common.truth.IterableSubject;
import com.google.common.truth.SubjectFactory;
import com.google.common.truth.Truth;

public class UserSubject extends ComparableSubject<UserSubject, User> {

    private UserSubject(FailureStrategy failureStrategy, User target) {
        super(failureStrategy, target);
    }

    private static final SubjectFactory<UserSubject, User> USER_SUBJECT_FACTORY = new SubjectFactory<UserSubject, User>() {
        @Override
        public UserSubject getSubject(FailureStrategy failureStrategy, User target) {
            return new UserSubject(failureStrategy, target);
        }
    };

    public static UserSubject assertThat(User user) {
        return Truth.assertAbout(USER_SUBJECT_FACTORY)
            .that(user);
    }

    // Our API begins here
    public void hasName(String name) {
        if (!actual().getName()
            .equals(name)) {
            fail("has name", name);
        }
    }

    public void hasNameIgnoringCase(String name) {
        if (!actual().getName()
            .equalsIgnoreCase(name)) {
            fail("has name ignoring case", name);
        }
    }

    public IterableSubject emails() {
        return Truth.assertThat(actual().getEmails());
    }

}