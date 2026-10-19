package br.com.github.williiansilva51.linkr.database.repository;

import br.com.github.williiansilva51.linkr.database.model.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
}
