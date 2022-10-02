package com.github.vitalydev.messages.web;

import com.github.vitalydev.messages.model.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.ArrayList;


@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), new ArrayList<>());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}