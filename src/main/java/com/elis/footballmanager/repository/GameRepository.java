package com.elis.footballmanager.repository;

import com.elis.footballmanager.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findMatchesByTenant_Id(Long tenantId);

    Optional<Game> findMatchByTenant_IdAndId(Long tenantId, Long matchId);
}
