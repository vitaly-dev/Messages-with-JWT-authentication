package com.github.vitalydev.messages.web;

import com.github.vitalydev.messages.web.user.UserTestData;
import org.junit.jupiter.api.Test;

import static com.github.vitalydev.messages.util.JwtUtil.getUserLoginNameFromToken;
import static com.github.vitalydev.messages.web.user.UserTestData.admin;
import static com.github.vitalydev.messages.web.user.UserTestData.user;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationControllerTest extends AbstractControllerTest {

    // user login credentials consist from: name(user Email), password(user password)

    @Test
    public void adminLoginTest() throws Exception {
        AuthenticationResponse authResponse = login(new AuthenticationRequest(admin.getEmail(), admin.getPassword()));
        String loginName = getUserLoginNameFromToken(authResponse.getToken());
        assertEquals(admin.getEmail(), loginName);
    }

    @Test
    public void userLoginTest() throws Exception {
        AuthenticationResponse authResponse = login(new AuthenticationRequest(user.getEmail(), user.getPassword()));
        String loginName = getUserLoginNameFromToken(authResponse.getToken());
        assertEquals(UserTestData.user.getEmail(), loginName);
    }
}
