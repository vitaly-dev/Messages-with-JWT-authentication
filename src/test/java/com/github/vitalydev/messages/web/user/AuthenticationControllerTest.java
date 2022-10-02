package com.github.vitalydev.messages.web.user;

import com.github.vitalydev.messages.config.WebSecurity;
import com.github.vitalydev.messages.model.User;
import com.github.vitalydev.messages.to.UserTo;
import com.github.vitalydev.messages.util.JsonUtil;
import com.github.vitalydev.messages.util.TokenUtil;
import com.github.vitalydev.messages.util.UserUtil;
import com.github.vitalydev.messages.web.AbstractControllerTest;
import com.github.vitalydev.messages.to.AuthenticationRequest;
import com.github.vitalydev.messages.to.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.vitalydev.messages.web.user.UserTestData.admin;
import static com.github.vitalydev.messages.web.user.UserTestData.user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest extends AbstractControllerTest {

    // user login credentials consist from: name(user Email), password(user password)

    @Test
    public void adminLoginTest() throws Exception {
        AuthenticationResponse authResponse = login(new AuthenticationRequest(admin.getEmail(), admin.getPassword()));
        String loginName = TokenUtil.getUserLoginNameFromToken(authResponse.getToken());
        assertEquals(admin.getEmail(), loginName);
    }

    @Test
    public void userLoginTest() throws Exception {
        AuthenticationResponse authResponse = login(new AuthenticationRequest(user.getEmail(), user.getPassword()));
        String loginName = TokenUtil.getUserLoginNameFromToken(authResponse.getToken());
        assertEquals(UserTestData.user.getEmail(), loginName);
    }

    @Test
    void register() throws Exception {
        UserTo newTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        User newUser = UserUtil.createNewFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(WebSecurity.AUTH_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = UserTestData.USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(userRepository.getById(newId), newUser);
    }
}
