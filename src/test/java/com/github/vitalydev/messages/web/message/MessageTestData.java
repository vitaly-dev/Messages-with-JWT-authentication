package com.github.vitalydev.messages.web.message;

import com.github.vitalydev.messages.model.Message;
import com.github.vitalydev.messages.web.MatcherFactory;

import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.LocalDateTime.of;

public class MessageTestData {
    public static final MatcherFactory.Matcher<Message> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Message.class, "user");
    //public static MatcherFactory.Matcher<Message> MEAL_TO_MATCHER = MatcherFactory.usingEqualsComparator(Message.class);

    public static final int MESSAGE_1_ID = 1;
    public static final int ADMIN_MESSAGE_1_ID = 11;

    public static final Message MESSAGE_1 = new Message(MESSAGE_1_ID, of(2020, Month.JANUARY, 30, 10, 0), "Завтрак");
    public static final Message MESSAGE_2 = new Message(MESSAGE_1_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Обед");
    public static final Message MESSAGE_3 = new Message(MESSAGE_1_ID + 2, of(2020, Month.JANUARY, 30, 20, 0), "Ужин");
    public static final Message MESSAGE_4 = new Message(MESSAGE_1_ID + 3, of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение");
    public static final Message MESSAGE_5 = new Message(MESSAGE_1_ID + 4, of(2020, Month.JANUARY, 31, 10, 0), "Завтрак");
    public static final Message MESSAGE_6 = new Message(MESSAGE_1_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Обед");
    public static final Message MESSAGE_7 = new Message(MESSAGE_1_ID + 6, of(2020, Month.JANUARY, 31, 20, 0), "Ужин");
    public static final Message ADMIN_MESSAGE_1 = new Message(ADMIN_MESSAGE_1_ID, of(2022, Month.JANUARY, 01, 14, 0), "Admin message 1");
    public static final Message ADMIN_MESSAGE_2 = new Message(ADMIN_MESSAGE_1_ID + 1, of(2020, Month.JANUARY, 31, 21, 0), "Admin message 2");

    public static final List<Message> MESSAGES = List.of(MESSAGE_7, MESSAGE_6, MESSAGE_5, MESSAGE_4, MESSAGE_3, MESSAGE_2, MESSAGE_1);

    public static Message getNew() {
        return new Message(null, of(2020, Month.FEBRUARY, 1, 18, 0), "Созданный ужин");
    }

    public static Message getUpdated() {
        return new Message(MESSAGE_1_ID, MESSAGE_1.getDateTime().plus(2, ChronoUnit.MINUTES), "Обновленный завтрак");
    }
}
