package com.github.vitalydev.messages.to;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;

    @NotBlank
    @Size(max = 256)
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
}
