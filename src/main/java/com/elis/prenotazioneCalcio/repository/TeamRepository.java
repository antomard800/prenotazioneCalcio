package com.elis.prenotazioneCalcio.repository;

import com.elis.prenotazioneCalcio.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    //Return all teams of one tenant
    List<Team> findTeamsByTenant_Id(Long tenantId);

    //Return one team based on his id and tenant's id
    Optional<Team> findTeamByTenant_IdAndId(Long tenantId, Long teamId);

    //Return true if name exists in the database
    boolean existsByName(String name);
}
