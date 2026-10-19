package br.com.github.williiansilva51.linkr.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Domain")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String domainUrl;

    public void setDomainUrl(String domainUrl) {
        if (domainUrl == null || domainUrl.isEmpty()) {
            throw new IllegalArgumentException("Domain URL cannot be null or empty");
        }

        this.domainUrl = domainUrl;
    }
}
