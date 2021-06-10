package com.elis.prenotazioneCalcio.service.interfaces;

import com.elis.prenotazioneCalcio.dto.game.GameListDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import org.springframework.stereotype.Service;

@Service
public interface PlayerService {
    PlayerListDTO getTenantPlayers(Long tenantId);

    PlayerCreationResponseDTO createTenantPlayer(Long tenantId, PlayerCreationRequestDTO playerCreationRequestDTO);

    PlayerDTO getTenantPlayer(Long tenantId, Long playerId);

    PlayerCreationResponseDTO updateTenantPlayer(Long tenantId, Long playerId, PlayerCreationRequestDTO playerCreationRequestDTO);

    //PlayerDTO deleteTenantPlayer(Long tenantId, Long playerId);

    PlayerDTO loginPlayer(String email, String password);

    PlayerDTO signToMatch(Long tenantId, Long playerId, Long gameId);

    PlayerCreationResponseDTO updateTenantPlayerRating(Long tenantId, Long gameId, Long playerId, PlayerCreationRequestDTO playerCreationRequestDTO);

    GameListDTO getPlayerMatches(Long tenantId, Long playerId);
}
