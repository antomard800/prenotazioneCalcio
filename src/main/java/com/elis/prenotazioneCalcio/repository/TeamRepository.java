package com.elis.prenotazioneCalcio.repository;

import com.elis.prenotazioneCalcio.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findTeamByTenant_IdAndId(Long tenantId, Long teamId);

    List<Team> findTeamsByTenant_Id(Long tenantId);

    boolean existsByName(String name);

//    void deleteByTenant_IdAndId(Long tenantId, Long teamId);
}
