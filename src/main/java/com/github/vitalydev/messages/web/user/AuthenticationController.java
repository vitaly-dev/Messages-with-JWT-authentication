package com.github.vitalydev.messages.web.user;

import com.github.vitalydev.messages.config.WebSecurity;
import com.github.vitalydev.messages.model.User;
import com.github.vitalydev.messages.to.AuthenticationRequest;
import com.github.vitalydev.messages.to.AuthenticationResponse;
import com.github.vitalydev.messages.to.UserTo;
import com.github.vitalydev.messages.util.TokenUtil;
import com.github.vitalydev.messages.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.github.vitalydev.messages.util.validation.ValidationUtil.checkNew;
import static com.github.vitalydev.messages.web.user.ProfileController.API_URL;

@Slf4j
@RestController
@RequestMapping(value = WebSecurity.AUTH_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController extends AbstractUserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getName(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getName());
        String token = TokenUtil.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = prepareAndSave(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(API_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
