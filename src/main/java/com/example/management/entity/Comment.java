package com.example.management.entity;

import com.example.management.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "management", name = "comments")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.DETACH)
    private User author;

    @Column(name = "description")
    private String description;

    @Column(name = "date_create")
    private LocalDate dateCreate;

    @Column(name = "tasks_id")
    private Integer taskId;

    public Comment(User author, String description, Integer taskId) {
        this.author = author;
        this.description = description;
        this.taskId = taskId;
    }

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UserDto userAuthorDto;

    @PostLoad
    public void postLoad(){
        if (author != null){
            userAuthorDto = new UserDto(author.getName(),author.getSurname());
        }
    }
}
