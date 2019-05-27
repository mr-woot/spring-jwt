package com.paisabazaar.kafka_admin.repository;

import com.paisabazaar.kafka_admin.model.Role;
import com.paisabazaar.kafka_admin.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
