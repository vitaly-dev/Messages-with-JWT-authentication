package com.github.vitalydev.messages.web.user;

import com.github.vitalydev.messages.model.User;
import com.github.vitalydev.messages.to.UserTo;
import com.github.vitalydev.messages.util.JsonUtil;
import com.github.vitalydev.messages.util.UserUtil;
import com.github.vitalydev.messages.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.vitalydev.messages.web.user.UserTestData.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileControllerTest extends AbstractControllerTest {

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileController.API_URL).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(user));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileController.API_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ProfileController.API_URL).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(user)))
                .andExpect(status().isNoContent());
        UserTestData.USER_MATCHER.assertMatch(userRepository.findAll(), UserTestData.admin, UserTestData.guest);
    }

    @Test
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", UserTestData.USER_MAIL, "newPassword");
        perform(MockMvcRequestBuilders.put(ProfileController.API_URL).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        UserTestData.USER_MATCHER.assertMatch(userRepository.getById(UserTestData.USER_ID), UserUtil.updateFromTo(new User(user), updatedTo));
    }

    @Test
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "wrongEmail", null);
        perform(MockMvcRequestBuilders.put(ProfileController.API_URL).secure(true)
                .header(HttpHeaders.AUTHORIZATION, token(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}