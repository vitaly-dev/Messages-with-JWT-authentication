package com.github.vitalydev.messages.web.message;

import com.github.vitalydev.messages.model.Message;
import com.github.vitalydev.messages.repository.MessageRepository;
import com.github.vitalydev.messages.util.JsonUtil;
import com.github.vitalydev.messages.web.AbstractControllerTest;
import com.github.vitalydev.messages.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.vitalydev.messages.web.MatcherFactory.Matcher.getContent;
import static com.github.vitalydev.messages.web.message.MessageTestData.*;
import static com.github.vitalydev.messages.web.user.UserTestData.admin;
import static com.github.vitalydev.messages.web.user.UserTestData.user;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessageControllerTest extends AbstractControllerTest {

    private static final String API_URL = MessageController.API_URL + '/';

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL + ADMIN_MESSAGE_1_ID).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MESSAGE_MATCHER.contentJson(ADMIN_MESSAGE_1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL + MESSAGE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL + ADMIN_MESSAGE_1_ID).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(user)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(API_URL + MESSAGE_1_ID).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(user)))
                .andExpect(status().isNoContent());
        assertFalse(messageRepository.get(MESSAGE_1_ID, UserTestData.USER_ID).isPresent());
    }

    @Test
    void deleteDataConflict() throws Exception {
        perform(MockMvcRequestBuilders.delete(API_URL + ADMIN_MESSAGE_1_ID).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(user)))
                .andExpect(status().isConflict());
    }

    @Test
        // Here we test for creating new Message
    void createMessageSaveOrGetLast() throws Exception {
        Message newMessage = MessageTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(API_URL).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMessage)));

        Message created = JsonUtil.readValues(getContent(action.andReturn()), Message.class).get(0);
        int newId = created.id();
        newMessage.setId(newId);
        newMessage.setUserId(created.getUserId());
        MESSAGE_MATCHER.assertMatch(created, newMessage);
        MESSAGE_MATCHER.assertMatch(messageRepository.getById(newId), newMessage);
    }

    @Test
        // Here we test for returning existing last 10 messages to client
    void returnMessagesSaveOrGetLast() throws Exception {
        Message newMessage = MessageTestData.getNew();
        newMessage.setMessage(HISTORY + NUM_MESS_TO_RETURN);
        perform(MockMvcRequestBuilders.post(API_URL).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMessage)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MESSAGE_MATCHER.contentJson(MESSAGES));
    }

    @Test
    void createInvalid() throws Exception {
        Message invalid = new Message(null, null, "Dummy", null, 2);
        perform(MockMvcRequestBuilders.post(API_URL).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}