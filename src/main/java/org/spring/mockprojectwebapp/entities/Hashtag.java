package org.spring.mockprojectwebapp.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "hashtag")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private int id;

    @Column(name = "hashtag_name")
    private String hashtagName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
