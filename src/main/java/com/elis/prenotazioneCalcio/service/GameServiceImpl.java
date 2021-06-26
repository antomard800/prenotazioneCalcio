package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.game.GameCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.game.GameCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.game.GameDTO;
import com.elis.prenotazioneCalcio.dto.game.GameListDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import com.elis.prenotazioneCalcio.helper.GameHelper;
import com.elis.prenotazioneCalcio.helper.PlayerHelper;
import com.elis.prenotazioneCalcio.helper.TenantHelper;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.service.interfaces.GameService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class GameServiceImpl implements GameService {
    @Autowired
    GameHelper gameHelper;

    @Autowired
    TenantHelper tenantHelper;

    @Autowired
    PlayerHelper playerHelper;

    @Override
    public GameListDTO getTenantMatches(Long tenantId) {
        //Check tenant existence
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        return gameHelper.getTenantMatches(tenantId);
    }

    @Override
    public GameCreationResponseDTO createTenantMatch(Long tenantId, GameCreationRequestDTO gameCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        //Check presence of all data
        Preconditions.checkArgument(gameCreationRequestDTO.date != null, "Match date cannot be null");
        Preconditions.checkArgument(gameCreationRequestDTO.time != null, "Match time cannot be null");

        return gameHelper.createTenantMatch(tenant, gameCreationRequestDTO);
    }

    @Override
    public GameDTO getTenantMatch(Long tenantId, Long gameId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(gameId != null, "Match id cannot be null");

        return gameHelper.getTenantMatch(tenantId, gameId);
    }

    @Override
    public GameCreationResponseDTO updateTenantMatch(Long tenantId, Long gameId, GameCreationRequestDTO gameCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(gameId != null, "Match id cannot be null");

        return gameHelper.updateTenantMatch(tenant, gameId, gameCreationRequestDTO);
    }

    @Override
    public GameDTO deleteTenantMatch(Long tenantId, Long gameId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(gameId != null, "Match id cannot be null");

        return gameHelper.deleteTenantMatch(gameId);
    }

    @Override
    public PlayerListDTO getMatchPlayers(Long tenantId, Long gameId) {
        Tenant tenant = tenantHelper.findById(tenantId);
        Game game = gameHelper.findByTenantIdAndGameId(tenantId, gameId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(game), "Game does not exist");

        return gameHelper.getMatchPlayers(game);
    }

    @Override
    public GameCreationResponseDTO removePlayer(Long tenantId, Long gameId, Long playerId) {
        Tenant tenant = tenantHelper.findById(tenantId);
        //Check game and player existence
        Game game = gameHelper.findByTenantIdAndGameId(tenantId, gameId);
        Player player = playerHelper.findByTenantIdAndPlayerId(tenantId, playerId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(game), "Game does not exist");
        Preconditions.checkArgument(!Objects.isNull(player), "Player does not exist");

        return gameHelper.removePlayer(tenant, game, player);
    }

    @Override
    public GameCreationResponseDTO removePlayers(Long tenantId, Long gameId) {
        Tenant tenant = tenantHelper.findById(tenantId);
        Game game = gameHelper.findByTenantIdAndGameId(tenantId, gameId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(game), "Game does not exist");

        return gameHelper.removePlayers(game);
    }

    @Override
    public List<Long> buildTeams(Long tenantId, Long gameId) {
        Tenant tenant = tenantHelper.findById(tenantId);
        Game game = gameHelper.findByTenantIdAndGameId(tenantId, gameId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(game), "Game does not exist");

        return gameHelper.buildTeams(tenant, game);
    }
}
