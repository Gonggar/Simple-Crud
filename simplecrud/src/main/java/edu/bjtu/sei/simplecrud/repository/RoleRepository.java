package edu.bjtu.sei.simplecrud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.bjtu.sei.simplecrud.domain.ERole;
import edu.bjtu.sei.simplecrud.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
