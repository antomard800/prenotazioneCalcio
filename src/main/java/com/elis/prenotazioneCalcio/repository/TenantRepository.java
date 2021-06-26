package com.elis.prenotazioneCalcio.repository;

import com.elis.prenotazioneCalcio.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    //Return one tenant based on his email and password
    Optional<Tenant> findByEmailAndPassword(String email, String password);

    //Return true if email exists in the database
    boolean existsByEmail(String email);

    //Return one tenant based on his email
    Tenant findByEmail(String email);
}
