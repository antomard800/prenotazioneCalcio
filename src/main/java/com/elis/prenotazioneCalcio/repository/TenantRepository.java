package com.elis.prenotazioneCalcio.repository;

import com.elis.prenotazioneCalcio.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Tenant findByEmail(String email);
}
