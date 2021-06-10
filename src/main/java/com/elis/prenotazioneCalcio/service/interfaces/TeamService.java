package com.elis.prenotazioneCalcio.service.interfaces;

import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamDTO;
import com.elis.prenotazioneCalcio.dto.team.TeamListDTO;
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
