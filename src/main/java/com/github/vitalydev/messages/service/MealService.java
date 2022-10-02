package com.github.vitalydev.messages.service;

import com.github.vitalydev.messages.model.Message;
import com.github.vitalydev.messages.repository.MessagesRepository;
import com.github.vitalydev.messages.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MealService {
    private final MessagesRepository messageRepository;
    //private final UserRepository userRepository;
    private static final String HISTORY = "history";

    //@Transactional
    public List<Message> saveOrGetLast(Message message, int userId) {
        String mess = message.getMessage();
        if (mess.contains(HISTORY)) {
            String trimmedMess = mess.replaceFirst(HISTORY, "").trim();
            int numMess = trimmedMess.length() > 0 ? Integer.parseInt(trimmedMess) : 0;
            return messageRepository.getLast(userId, numMess);
        } else {
            //message.setUser(userRepository.getById(userId));
            message.setUserId(userId);
            message.setDateTime(LocalDateTime.now());
            List<Message> list = new ArrayList<>();
            list.add(messageRepository.save(message));
            return list;
        }
    }
}
