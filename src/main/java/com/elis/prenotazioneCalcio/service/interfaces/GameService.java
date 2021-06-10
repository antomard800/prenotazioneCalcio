package com.elis.prenotazioneCalcio.service.interfaces;

import com.elis.prenotazioneCalcio.dto.game.GameCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.game.GameCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.game.GameDTO;
import com.elis.prenotazioneCalcio.dto.game.GameListDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {
    GameListDTO getTenantMatches(Long tenantId);

    GameCreationResponseDTO createTenantMatch(Long tenantId, GameCreationRequestDTO gameCreationRequestDTO);

    GameDTO getTenantMatch(Long tenantId, Long gameId);

    GameCreationResponseDTO updateTenantMatch(Long tenantId, Long gameId, GameCreationRequestDTO gameCreationRequestDTO);

    GameDTO deleteTenantMatch(Long tenantId, Long gameId);

    PlayerListDTO getMatchPlayers(Long tenantId, Long gameId);

    GameCreationResponseDTO removePlayer(Long tenantId, Long gameId, Long playerId/*, GameCreationRequestDTO gameCreationRequestDTO*/);

    GameCreationResponseDTO removePlayers(Long tenantId, Long gameId);

    List<Long> buildTeams(Long tenantId, Long gameId);
}
