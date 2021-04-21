package edu.bjtu.sei.simplecrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import edu.bjtu.sei.simplecrud.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	//Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);

}
