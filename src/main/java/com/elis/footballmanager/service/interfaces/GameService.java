package com.elis.footballmanager.service.interfaces;

import com.elis.footballmanager.dto.match.GameCreationRequestDTO;
import com.elis.footballmanager.dto.match.GameCreationResponseDTO;
import com.elis.footballmanager.dto.match.GameDTO;
import com.elis.footballmanager.dto.match.GameListDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import org.springframework.stereotype.Service;

@Service
public interface GameService {
    GameListDTO getTenantMatches(Long tenantId);

    GameCreationResponseDTO createTenantMatch(Long tenantId, GameCreationRequestDTO gameCreationRequestDTO);

    GameDTO getTenantMatch(Long tenantId, Long matchId);

    GameCreationResponseDTO updateTenantMatch(Long tenantId, Long matchId, GameCreationRequestDTO gameCreationRequestDTO);

    GameDTO deleteTenantMatch(Long tenantId, Long matchId);

    PlayerListDTO getMatchPlayers(Long tenantId, Long matchId);
}
