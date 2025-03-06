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
@Table(name = "commentinteractions")
public class CommentInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private int interaction_id;

    @Column(name = "comment_id", nullable = false)
    private int comment_id;

    @Column(name = "user_id", nullable = false)
    private int user_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_type", nullable = false)
    private InteractionType interaction_type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    public enum InteractionType {
        LIKE, DISLIKE
    }
}
