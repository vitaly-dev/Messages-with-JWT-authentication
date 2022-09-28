package com.github.vitalydev.messages.service;

import com.github.vitalydev.messages.model.Message;
import com.github.vitalydev.messages.repository.MessagesRepository;
import com.github.vitalydev.messages.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MealService {
    private final MessagesRepository messagesRepository;
    private final UserRepository userRepository;

    @Transactional
    public Message save(Message message, int userId) {
        message.setUser(userRepository.getById(userId));
        return messagesRepository.save(message);
    }
}
