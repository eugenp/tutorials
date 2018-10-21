package com.baeldung.akkahttp;

/**
 * Defines all messages related to User Actor
 * 
 */
public interface UserRegistryMessages {

    class GetUsers implements Serializable {

        private static final long serialVersionUID = 1L;
    }

    class ActionPerformed implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String description;

        public ActionPerformed(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    class CreateUser implements Serializable {

        private static final long serialVersionUID = 1L;
        private final User user;

        public CreateUser(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    class GetUser implements Serializable {

        private static final long serialVersionUID = 1L;
        private final String name;

        public GetUser(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class DeleteUser implements Serializable {

        private static final long serialVersionUID = 1L;
        private final String name;

        public DeleteUser(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
