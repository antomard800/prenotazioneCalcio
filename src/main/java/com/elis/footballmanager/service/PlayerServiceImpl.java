package com.elis.footballmanager.service;

import com.elis.footballmanager.dto.player.PlayerCreationRequestDTO;
import com.elis.footballmanager.dto.player.PlayerCreationResponseDTO;
import com.elis.footballmanager.dto.player.PlayerDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.helper.GameHelper;
import com.elis.footballmanager.helper.PlayerHelper;
import com.elis.footballmanager.helper.TenantHelper;
import com.elis.footballmanager.model.Game;
import com.elis.footballmanager.model.Player;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.service.interfaces.PlayerService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    PlayerHelper playerHelper;

    @Autowired
    TenantHelper tenantHelper;

    @Autowired
    GameHelper gameHelper;

    @Override
    public PlayerListDTO getTenantPlayers(Long tenantId) {
        //check dei dati del dto
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");

        return playerHelper.getTenantPlayers(tenantId);
    }

    @Override
    public PlayerCreationResponseDTO createTenantPlayer(Long tenantId, PlayerCreationRequestDTO playerCreationRequestDTO) {
        //check dei dati del dto
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(playerCreationRequestDTO.name != null, "Player name cannot be null.");
        Preconditions.checkArgument(playerCreationRequestDTO.surname != null, "Player surname cannot be null.");
        Preconditions.checkArgument(playerCreationRequestDTO.email != null, "Player email cannot be null.");
        Preconditions.checkArgument(playerCreationRequestDTO.password != null, "Player password cannot be null.");
        Preconditions.checkArgument(playerCreationRequestDTO.rating != null, "Player rating cannot be null.");
        Preconditions.checkArgument(playerCreationRequestDTO.role != null, "Player role cannot be null.");

        return playerHelper.createTenantPlayer(tenant, playerCreationRequestDTO);
    }

    @Override
    public PlayerDTO getTenantPlayer(Long tenantId, Long playerId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(playerId != null, "Player id cannot be null");

        return playerHelper.getTenantPlayer(tenantId, playerId);
    }

    @Override
    public PlayerCreationResponseDTO updateTenantPlayer(Long tenantId, Long playerId, PlayerCreationRequestDTO playerCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(playerId != null, "Player id cannot be null");
        Preconditions.checkArgument(playerCreationRequestDTO.email != null, "Player email cannot be null");

        return playerHelper.updateTenantPlayer(tenant, playerId, playerCreationRequestDTO);
    }

    @Override
    public PlayerDTO deleteTenantPlayer(Long tenantId, Long playerId) {
        Tenant tenant = tenantHelper.findById(tenantId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(playerId != null, "Player id cannot be null");

        return playerHelper.deleteTenantPlayer(tenantId, playerId);
    }

    @Override
    public PlayerDTO loginPlayer(String email, String password) {
        Preconditions.checkArgument(email != null, "Tenant email cannot be null");
        Preconditions.checkArgument(password != null, "Tenant password cannot be null");

        return playerHelper.loginPlayer(email, password);
    }

    @Override
    public PlayerDTO signToMatch(Long tenantId, Long playerId, Long gameId) {
        Tenant tenant = tenantHelper.findById(tenantId);
        Player player = playerHelper.findByTenant_IdAndId(tenantId, playerId);
        Game game = gameHelper.findByTenant_IdAndId(tenantId, gameId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(player), "Player does not exist");
        Preconditions.checkArgument(!Objects.isNull(game), "Game does not exist");

        return playerHelper.signToMatch(tenant, player, game);
    }

    @Override
    public PlayerCreationResponseDTO updateTenantPlayerRating(Long tenantId, Long gameId, Long playerId, PlayerCreationRequestDTO playerCreationRequestDTO) {
        Tenant tenant = tenantHelper.findById(tenantId);
        Game game = gameHelper.findByTenant_IdAndId(tenantId, gameId);
        Player player = playerHelper.findByTenant_IdAndId(tenantId, playerId);

        Preconditions.checkArgument(!Objects.isNull(tenant), "Tenant does not exist");
        Preconditions.checkArgument(!Objects.isNull(game), "Game does not exist");
        Preconditions.checkArgument(!Objects.isNull(player), "Player does not exist");

        return playerHelper.updateTenantPlayerRating(tenant, game, player, playerCreationRequestDTO);
    }
}
