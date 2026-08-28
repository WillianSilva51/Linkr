package br.com.github.williiansilva51.linkr.repository;

import br.com.github.williiansilva51.linkr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
