package com.github.vitalydev.messages.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.vitalydev.messages.util.validation.NoHtml;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "message", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "messages_unique_user_datetime_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

    @Column(name = "date_time", nullable = false)
    @Schema(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    @Column(name = "user_id", nullable = false)
    @Schema(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer userId;

    public Message(Integer id, LocalDateTime dateTime, String message, String name, Integer userId) {
        super(id);
        this.dateTime = dateTime;
        this.message = message;
        this.name = name;
        this.userId = userId;
    }
}
