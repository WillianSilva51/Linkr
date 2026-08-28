package br.com.github.williiansilva51.linkr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Domain")
public class Domain {
    @Column(unique = true, nullable = false)
    String domainUrl;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
