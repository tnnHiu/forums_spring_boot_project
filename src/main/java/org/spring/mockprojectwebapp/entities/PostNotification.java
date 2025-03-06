package org.spring.mockprojectwebapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "postnotifications")
public class PostNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_notification_id")
    private int post_notification_id;

    @Column(name = "post_id", nullable = false)
    private int post_id;

    @Column(name = "user_id", nullable = false)
    private int user_id;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.UNREAD;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    public enum Status {
        UNREAD, READ
    }
}
