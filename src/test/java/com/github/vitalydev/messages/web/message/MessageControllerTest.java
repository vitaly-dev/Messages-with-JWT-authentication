package com.github.vitalydev.messages.web.message;

import com.github.vitalydev.messages.model.Message;
import com.github.vitalydev.messages.repository.MessagesRepository;
import com.github.vitalydev.messages.util.JsonUtil;
import com.github.vitalydev.messages.web.AbstractControllerTest;
import com.github.vitalydev.messages.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.github.vitalydev.messages.web.message.MessageTestData.*;
import static com.github.vitalydev.messages.web.user.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessageControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MessageController.REST_URL + '/';

    @Autowired
    private MessagesRepository messagesRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_MESSAGE_1_ID).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(ADMIN_MESSAGE_1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MESSAGE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_MESSAGE_1_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MESSAGE_1_ID))
                .andExpect(status().isNoContent());
        assertFalse(messagesRepository.get(MESSAGE_1_ID, UserTestData.USER_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteDataConflict() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ADMIN_MESSAGE_1_ID))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Message updated = MessageTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MESSAGE_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(messagesRepository.getById(MESSAGE_1_ID), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception {
        Message newMessage = MessageTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMessage)));

        Message created = MEAL_MATCHER.readFromJson(action);
        int newId = created.id();
        newMessage.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMessage);
        MEAL_MATCHER.assertMatch(messagesRepository.getById(newId), newMessage);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Message invalid = new Message(null, null, "Dummy");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalid() throws Exception {
        Message invalid = new Message(MESSAGE_1_ID, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL + MESSAGE_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Message invalid = new Message(MESSAGE_1_ID, LocalDateTime.now(), "<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + MESSAGE_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = USER_MAIL)
    void updateDuplicate() {
        Message invalid = new Message(MESSAGE_1_ID, MESSAGE_2.getDateTime(), "Dummy");
        assertThrows(Exception.class, () ->
                perform(MockMvcRequestBuilders.put(REST_URL + MESSAGE_1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                        .andDo(print())
        );
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() {
        Message invalid = new Message(null, ADMIN_MESSAGE_1.getDateTime(), "Dummy");
        assertThrows(Exception.class, () ->
                perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                        .andDo(print())
                        .andExpect(status().isUnprocessableEntity())
        );
    }
}