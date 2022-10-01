package com.github.vitalydev.messages.repository;

import com.github.vitalydev.messages.error.DataConflictException;
import com.github.vitalydev.messages.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MessagesRepository extends BaseRepository<Message> {

    @Query(value = "SELECT * FROM Message m WHERE user_id=?1 ORDER BY date_time DESC LIMIT ?2 ", nativeQuery = true)
    List<Message> getLast(int userId, int num);

    @Query("SELECT m FROM Message m WHERE m.id = :id and m.userId = :userId")
    Optional<Message> get(int id, int userId);

    default Message checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Meal id=" + id + " doesn't belong to User id=" + userId));
    }
}