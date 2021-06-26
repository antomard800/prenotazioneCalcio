package com.elis.prenotazioneCalcio.repository;

import com.elis.prenotazioneCalcio.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    //Return all players of one tenant
    List<Player> findPlayersByTenant_Id(Long tenantId);

    //Return one player based on his id and tenant's id
    Optional<Player> findPlayerByTenant_IdAndId(Long tenantId, Long playerId);

    //Return one player based on his email and password
    Optional<Player> findByEmailAndPassword(String email, String password);

    //Return true if email exists in the database
    boolean existsByEmail(String email);

    //Return one player based on his email
    Player findByEmail(String email);
}
