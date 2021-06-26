package com.elis.prenotazioneCalcio.repository;

import com.elis.prenotazioneCalcio.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    //Return all games of one tenant
    List<Game> findGamesByTenant_Id(Long tenantId);

    //Return one player based on his id and tenant's id
    Optional<Game> findGameByTenant_IdAndId(Long tenantId, Long matchId);
}

