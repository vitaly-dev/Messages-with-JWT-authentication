package com.github.vitalydev.messages.web.user;

import com.github.vitalydev.messages.model.Role;
import com.github.vitalydev.messages.model.User;
import com.github.vitalydev.messages.util.JsonUtil;
import com.github.vitalydev.messages.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static com.github.vitalydev.messages.web.message.MessageTestData.*;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "messages", "password");
    public static MatcherFactory.Matcher<User> USER_WITH_MEALS_MATCHER =
            MatcherFactory.usingAssertions(User.class,
                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "messages.user", "password").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password");
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin");
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "guest");

    static {
        user.setMessages(MESSAGES);
        admin.setMessages(List.of(ADMIN_MESSAGE_2, ADMIN_MESSAGE_1));
    }

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", new Date());
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", new Date());
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
