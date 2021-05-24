package com.elis.footballmanager.service.interfaces;

import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.dto.team.TeamCreationRequestDTO;
import com.elis.footballmanager.dto.team.TeamCreationResponseDTO;
import com.elis.footballmanager.dto.team.TeamDTO;
import com.elis.footballmanager.dto.team.TeamListDTO;
import org.springframework.stereotype.Service;

@Service
public interface TeamService {
    TeamListDTO getTenantTeams(Long tenantId);

    TeamCreationResponseDTO createTenantTeam(Long tenantId, TeamCreationRequestDTO teamCreationRequestDTO);

    TeamDTO getTenantTeam(Long tenantId, Long teamId);

    TeamCreationResponseDTO updateTenantTeam(Long tenantId, Long teamId, TeamCreationRequestDTO teamCreationRequestDTO);

    TeamDTO deleteTenantTeam(Long tenantId, Long teamId);

    PlayerListDTO getTeamPlayers(Long tenantId, Long gameId, Long teamId);
}
