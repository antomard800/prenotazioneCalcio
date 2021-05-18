package com.elis.footballmanager.service.interfaces;

import com.elis.footballmanager.dto.player.PlayerCreationRequestDTO;
import com.elis.footballmanager.dto.player.PlayerCreationResponseDTO;
import com.elis.footballmanager.dto.player.PlayerDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.dto.tenant.TenantCreationRequestDTO;
import com.elis.footballmanager.dto.tenant.TenantCreationResponseDTO;
import com.elis.footballmanager.dto.tenant.TenantDTO;
import org.springframework.stereotype.Service;

@Service
public interface PlayerService {
    PlayerListDTO getTenantPlayers(Long tenantId);

    PlayerCreationResponseDTO createTenantPlayer(Long tenantId, PlayerCreationRequestDTO playerCreationRequestDTO);

    PlayerDTO getTenantPlayer(Long tenantId, Long playerId);

    PlayerCreationResponseDTO updateTenantPlayer(Long tenantId, Long playerId, PlayerCreationRequestDTO playerCreationRequestDTO);

    PlayerDTO deleteTenantPlayer(Long tenantId, Long playerId);

    PlayerDTO loginPlayer(String email, String password);

    PlayerDTO signToMatch(Long tenantId, Long playerId, Long gameId);

    PlayerCreationResponseDTO updateTenantPlayerRating(Long tenantId, Long gameId, Long playerId, PlayerCreationRequestDTO playerCreationRequestDTO);
}
