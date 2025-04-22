package dev.kaykyfreitas.task.manager.api.taskmanagerapi;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;
import net.datafaker.Faker;

public final class Fixture {

    private Fixture() {}

    private static final Faker FAKER = new Faker();

    public static String text(int size) {
        return FAKER.text().text(size);
    }

    public static final class Users {

        private Users() {}

        public static String name() {
            final String name = FAKER.name().firstName();
            return name.length() > 100 ? name.substring(0, 100) : name;
        }

        public static String username() {
            final String username = FAKER.internet().username();
            return username.length() > 15 ? username.substring(0, 15) : username;
        }

        public static String email() {
            return FAKER.internet().emailAddress();
        }

        public static User john() {
            return User.newUser(
                    "John Doe",
                    "john.doe",
                    "johndoe@email.com"
            );
        }

        public static User jane() {
            return User.newUser(
                    "Jane Doe",
                    "jane.doe",
                    "janedoe@email.com"
            );
        }

    }

}
