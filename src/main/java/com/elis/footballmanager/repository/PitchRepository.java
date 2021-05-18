package com.elis.footballmanager.repository;

import com.elis.footballmanager.model.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PitchRepository extends JpaRepository<Pitch, Long> {
    List<Pitch> findPitchesByTenant_Id(Long tenantId);
}
