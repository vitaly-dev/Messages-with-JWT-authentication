package com.github.vitalydev.messages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.vitalydev.messages.HasIdAndEmail;
import com.github.vitalydev.messages.util.validation.NoHtml;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends NamedEntity implements HasIdAndEmail, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml   // https://stackoverflow.com/questions/17480809
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(max = 256)
    // https://stackoverflow.com/a/12505165/548473
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

   // field 'messages' need for OnDeleteAction.CASCADE
    @OneToMany(fetch = FetchType.LAZY)//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @OrderBy("dateTime DESC")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE) //https://stackoverflow.com/a/44988100/548473
    @Schema(hidden = true)
    @ToString.Exclude
    private List<Message> messages;

    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.registered);
    }

    public User(Integer id, String name, String email, String password) {
        this(id, name, email, password, new Date());
    }

    public User(Integer id, String name, String email, String password, Date registered) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "User:" + id + '[' + email + ']';
    }
}