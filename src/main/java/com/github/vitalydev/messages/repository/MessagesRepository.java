package com.github.vitalydev.messages.repository;

import com.github.vitalydev.messages.error.DataConflictException;
import com.github.vitalydev.messages.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MessagesRepository extends BaseRepository<Message> {

    @Query("SELECT m FROM Message m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Message> getAll(int userId);

    @Query("SELECT m from Message m WHERE m.user.id=:userId AND m.dateTime >= :startDate AND m.dateTime < :endDate ORDER BY m.dateTime DESC")
    List<Message> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId);

    @Query("SELECT m FROM Message m WHERE m.id = :id and m.user.id = :userId")
    Optional<Message> get(int id, int userId);

    @Query("SELECT m FROM Message m JOIN FETCH m.user WHERE m.id = :id and m.user.id = :userId")
    Optional<Message> getWithUser(int id, int userId);

    default Message checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Meal id=" + id + " doesn't belong to User id=" + userId));
    }
}