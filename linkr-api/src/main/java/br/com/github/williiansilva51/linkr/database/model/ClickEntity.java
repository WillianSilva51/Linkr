package br.com.github.williiansilva51.linkr.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Click",
        indexes = @Index(name = "idx_link_id_created_at", columnList = "link_id, created_at"))
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, length = 50)
    String country;

    @Column(nullable = false, length = 50)
    String userAgent;

    @Column(nullable = false, length = 50)
    String referer;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private LinkEntity link;
}
