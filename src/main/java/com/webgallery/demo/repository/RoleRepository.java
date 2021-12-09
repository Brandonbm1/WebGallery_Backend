package com.webgallery.demo.repository;
//tomado de https://www.bezkoder.com/spring-boot-jwt-authentication/

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webgallery.demo.model.ERole;
import com.webgallery.demo.model.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
