package br.com.github.williiansilva51.linkr.database.repository;

import br.com.github.williiansilva51.linkr.database.model.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DomainRepository extends JpaRepository<DomainEntity, Integer> {
    Optional<DomainEntity> findByDomainUrl(String domainUrl);
}
