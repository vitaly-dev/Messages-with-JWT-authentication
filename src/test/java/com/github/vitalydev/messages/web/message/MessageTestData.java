package com.github.vitalydev.messages.web.message;

import com.github.vitalydev.messages.model.Message;
import com.github.vitalydev.messages.web.MatcherFactory;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;

public class MessageTestData {
    public static final MatcherFactory.Matcher<Message> MESSAGE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Message.class, "dateTime");

    public static final int MESSAGE_1_ID = 1;
    public static final int ADMIN_MESSAGE_1_ID = 12;
    public static final int NUM_MESS_TO_RETURN = 10;
    public static final String HISTORY = "history ";

    public static final Message MESSAGE_1 = new Message(MESSAGE_1_ID, of(2022, Month.JANUARY, 1, 11, 0), "User message 1", "user", 1);
    public static final Message MESSAGE_2 = new Message(MESSAGE_1_ID + 1, of(2022, Month.JANUARY, 2, 13, 0), "User message 2", "user", 1);
    public static final Message MESSAGE_3 = new Message(MESSAGE_1_ID + 2, of(2022, Month.JANUARY, 3, 20, 0), "User message 3", "user", 1);
    public static final Message MESSAGE_4 = new Message(MESSAGE_1_ID + 3, of(2022, Month.JANUARY, 4, 0, 0), "User message 4", "user", 1);
    public static final Message MESSAGE_5 = new Message(MESSAGE_1_ID + 4, of(2022, Month.JANUARY, 5, 10, 0), "User message 5", "user", 1);
    public static final Message MESSAGE_6 = new Message(MESSAGE_1_ID + 5, of(2022, Month.JANUARY, 6, 13, 0), "User message 6", "user", 1);
    public static final Message MESSAGE_7 = new Message(MESSAGE_1_ID + 6, of(2022, Month.JANUARY, 7, 20, 0), "User message 7", "user", 1);
    public static final Message MESSAGE_8 = new Message(MESSAGE_1_ID + 7, of(2022, Month.JANUARY, 8, 20, 0), "User message 8", "user", 1);
    public static final Message MESSAGE_9 = new Message(MESSAGE_1_ID + 8, of(2022, Month.JANUARY, 9, 21, 0), "User message 9", "user", 1);
    public static final Message MESSAGE_10 = new Message(MESSAGE_1_ID + 9, of(2022, Month.JANUARY, 10, 22, 0), "User message 10", "user", 1);
    public static final Message MESSAGE_11 = new Message(MESSAGE_1_ID + 10, of(2022, Month.JANUARY, 11, 23, 0), "User message 11", "user", 1);
    public static final Message ADMIN_MESSAGE_1 = new Message(ADMIN_MESSAGE_1_ID, of(2022, Month.JANUARY, 1, 14, 0), "Admin message 1", "admin", 2);
    public static final Message ADMIN_MESSAGE_2 = new Message(ADMIN_MESSAGE_1_ID + 1, of(2022, Month.JANUARY, 31, 21, 0), "Admin message 2", "admin", 2);

    public static final List<Message> MESSAGES = List.of(MESSAGE_11, MESSAGE_10, MESSAGE_9, MESSAGE_8, MESSAGE_7, MESSAGE_6, MESSAGE_5, MESSAGE_4, MESSAGE_3, MESSAGE_2);

    public static Message getNew() {
        return new Message(null, null, "new message", "user", null);
    }
}
