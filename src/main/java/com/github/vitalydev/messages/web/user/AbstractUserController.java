package com.github.vitalydev.messages.web.user;

import com.github.vitalydev.messages.model.User;
import com.github.vitalydev.messages.repository.UserRepository;
import com.github.vitalydev.messages.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    @Autowired
    private UniqueMailValidator emailValidator;

    // use binder for User, UserTo classes and not for AuthenticationRequest.class due to:
    // IllegalStateException: Invalid target for Validator [UniqueMailValidator]: AuthenticationRequest
    // https://stackoverflow.com/questions/17341543/initbinder-not-working-for-specific-model-attribute
    @InitBinder({"user", "userTo"})
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }
}