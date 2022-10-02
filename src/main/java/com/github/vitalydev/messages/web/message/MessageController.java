package com.github.vitalydev.messages.web.message;

import com.github.vitalydev.messages.model.Message;
import com.github.vitalydev.messages.repository.MessagesRepository;
import com.github.vitalydev.messages.service.MealService;
import com.github.vitalydev.messages.util.validation.ValidationUtil;
import com.github.vitalydev.messages.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = MessageController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MessageController {
    static final String REST_URL = "/api/messages";

    private final MessagesRepository repository;
    private final MealService service;

    @GetMapping("/{id}")
    public ResponseEntity<Message> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get message {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.get(id, authUser.id()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete {} for user {}", id, authUser.id());
        Message message = repository.checkBelong(id, authUser.id());
        repository.delete(message);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> saveOrGetLast(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Message message) {
        int userId = authUser.id();
        log.info("create {} for user {}", message, userId);
        ValidationUtil.checkNew(message);
        return service.saveOrGetLast(message, userId);
    }
}