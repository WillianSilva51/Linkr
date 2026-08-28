package br.com.github.williiansilva51.linkr.repository;

import br.com.github.williiansilva51.linkr.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Integer> {
    Domain findByDomainUrl(String domainUrl);
}
