package com.github.vitalydev.messages.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.vitalydev.messages.util.validation.NoHtml;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "message", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"user"})
public class Message extends BaseEntity {

    @Column(name = "date_time", nullable = false)
    @Schema(hidden = true)
    //@NotNull
    private LocalDateTime dateTime;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @NoHtml
    private String name;

    @Column(name = "message", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @NoHtml
    private String message;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @Schema(hidden = true)
    private User user;

    public Message(Integer id, LocalDateTime dateTime, String message) {
        super(id);
        this.dateTime = dateTime;
        this.message = message;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
