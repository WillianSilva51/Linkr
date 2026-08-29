package br.com.github.williiansilva51.linkr.database.repository;

import br.com.github.williiansilva51.linkr.database.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);
}
