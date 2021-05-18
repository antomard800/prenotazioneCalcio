package com.elis.footballmanager.repository;

import com.elis.footballmanager.dto.tenant.TenantDTO;
import com.elis.footballmanager.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
