package br.com.github.williiansilva51.linkr.database.repository;

import br.com.github.williiansilva51.linkr.database.model.ClickEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickRepository extends JpaRepository<ClickEntity, Long> {
}
