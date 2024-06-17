package com.Java020.MonieFlex.repository;

import com.Java020.MonieFlex.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}
