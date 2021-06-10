package com.elis.prenotazioneCalcio.repository;

import com.elis.prenotazioneCalcio.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findPlayersByTenant_Id(Long tenantId);

    Optional<Player> findPlayerByTenant_IdAndId(Long tenantId, Long playerId);

    Optional<Player> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Player findByEmail(String email);
}
