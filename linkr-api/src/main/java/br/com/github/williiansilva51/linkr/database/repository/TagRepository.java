package br.com.github.williiansilva51.linkr.database.repository;

import br.com.github.williiansilva51.linkr.database.model.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<TagEntity, UUID> {
}
