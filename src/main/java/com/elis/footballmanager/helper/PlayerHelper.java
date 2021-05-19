package com.elis.footballmanager.helper;

import com.elis.footballmanager.dto.match.GameDTO;
import com.elis.footballmanager.dto.match.GameListDTO;
import com.elis.footballmanager.dto.player.PlayerCreationRequestDTO;
import com.elis.footballmanager.dto.player.PlayerCreationResponseDTO;
import com.elis.footballmanager.dto.player.PlayerDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.dto.playerGame.PlayerGamesDTO;
import com.elis.footballmanager.dto.playerGame.PlayerGamesListDTO;
import com.elis.footballmanager.model.Game;
import com.elis.footballmanager.model.Player;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlayerHelper {
    @Autowired
    PlayerRepository playerRepository;

    public PlayerListDTO getTenantPlayers(Long tenantId) {
        PlayerListDTO playerListDTO = new PlayerListDTO();
        playerListDTO.players = playerRepository.findPlayersByTenant_Id(tenantId).stream().map(PlayerDTO::of).collect(Collectors.toList());
        return playerListDTO;
    }

    public PlayerCreationResponseDTO createTenantPlayer(Tenant tenant, PlayerCreationRequestDTO playerCreationRequestDTO) {
        Player player = new Player();

        if(playerRepository.existsByEmail(playerCreationRequestDTO.email)){
            throw new RuntimeException("This email already exist");
        } else {
            player.setName(playerCreationRequestDTO.name);
            player.setSurname(playerCreationRequestDTO.surname);
            player.setEmail(playerCreationRequestDTO.email);
            player.setPassword(playerCreationRequestDTO.password);
            player.setRating(playerCreationRequestDTO.rating);
            player.setRole(playerCreationRequestDTO.role);
            player.setTenant(tenant);

            playerRepository.save(player);

            return null;
        }
    }

    public PlayerDTO getTenantPlayer(Long tenantId, Long playerId) {
        return PlayerDTO.of(playerRepository.findPlayerByTenant_IdAndId(tenantId, playerId).orElseThrow(() -> new RuntimeException("Player not found")));
    }

    public PlayerCreationResponseDTO updateTenantPlayer(Tenant tenant, Long playerId, PlayerCreationRequestDTO playerCreationRequestDTO) {
        Player player = new Player();

        player.setId(playerId);
        player.setTenant(tenant);
        player.setEmail(playerCreationRequestDTO.email);
        if(playerCreationRequestDTO.name != null){
            player.setName(playerCreationRequestDTO.name);
        } else {
            player.setName(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getName());
        }
        if(playerCreationRequestDTO.surname != null){
            player.setSurname(playerCreationRequestDTO.surname);
        } else {
            player.setSurname(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getSurname());
        }
        if(playerCreationRequestDTO.rating != null){
            player.setRating(playerCreationRequestDTO.rating);
        } else {
            player.setRating(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getRating());
        }
        if(playerCreationRequestDTO.role != null){
            player.setRole(playerCreationRequestDTO.role);
        } else {
            player.setRole(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getRole());
        }
        if(playerCreationRequestDTO.password != null){
            player.setPassword(playerCreationRequestDTO.password);
        } else {
            player.setPassword(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), playerId).get().getPassword());
        }

        playerRepository.save(player);

        return null;
    }

    public PlayerDTO deleteTenantPlayer(Long tenantId, Long playerId) {
        if(playerRepository.existsById(playerId)){
            playerRepository.deleteById(playerId);

            return null;
        } else {
            throw new RuntimeException("Player id not found");
        }
    }

    public PlayerDTO loginPlayer(String email, String password) {
        return PlayerDTO.of(playerRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new RuntimeException("Wrong credentials")));
    }

    public PlayerDTO signToMatch(Tenant tenant, Player player, Game game) {
        player.getGames().add(game);

        playerRepository.save(player);

        return null;
    }

    public Player findByTenant_IdAndId(Long tenantId, Long playerId) {
        return playerRepository.findPlayerByTenant_IdAndId(tenantId, playerId).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public PlayerCreationResponseDTO updateTenantPlayerRating(Tenant tenant, Game game, Player player, PlayerCreationRequestDTO playerCreationRequestDTO) {
        player.setRating((player.getRating() + playerCreationRequestDTO.rating)/2);

        playerRepository.save(player);

        return null;
    }

    public Player findById(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public GameListDTO getPlayerMatches(Player player) {
        GameListDTO gameListDTO = new GameListDTO();

        gameListDTO.matches = player.getGames().stream().map(GameDTO::of).collect(Collectors.toList());

        return gameListDTO;
    }
}
