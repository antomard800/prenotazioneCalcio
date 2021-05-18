package com.elis.footballmanager.repository;

import com.elis.footballmanager.dto.team.TeamDTO;
import com.elis.footballmanager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findTeamByTenant_Id(Long tenantId);

    Optional<Team> findTeamByTenant_IdAndId(Long tenantId, Long teamId);

//    void deleteByTenant_IdAndId(Long tenantId, Long teamId);
}
