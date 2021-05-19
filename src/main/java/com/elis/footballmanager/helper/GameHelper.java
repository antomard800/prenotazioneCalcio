package com.elis.footballmanager.helper;

import com.elis.footballmanager.dto.match.GameCreationRequestDTO;
import com.elis.footballmanager.dto.match.GameCreationResponseDTO;
import com.elis.footballmanager.dto.match.GameDTO;
import com.elis.footballmanager.dto.match.GameListDTO;
import com.elis.footballmanager.dto.player.PlayerDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.model.Game;
import com.elis.footballmanager.model.Player;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameHelper {
    @Autowired
    GameRepository gameRepository;

    public GameListDTO getTenantMatches(Long tenantId) {
        GameListDTO gameListDTO = new GameListDTO();
        gameListDTO.matches = gameRepository.findMatchesByTenant_Id(tenantId).stream().map(GameDTO::of).collect(Collectors.toList());
        return gameListDTO;
    }

    public GameCreationResponseDTO createTenantMatch(Tenant tenant, GameCreationRequestDTO gameCreationRequestDTO){
        Game game = new Game();

        /*Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(matchCreationRequestDTO.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDate matchDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm:ss");
        LocalTime time = LocalTime.from(dtf.parse(matchCreationRequestDTO.time));*/

        game.setDate(gameCreationRequestDTO.date);
        game.setTime(gameCreationRequestDTO.time);
        game.setTenant(tenant);

        gameRepository.save(game);

        return null;
    }

    public GameDTO getTenantMatch(Long tenantId, Long matchId) {
        return GameDTO.of(gameRepository.findMatchByTenant_IdAndId(tenantId, matchId).orElseThrow(() -> new RuntimeException("Match not found")));
    }

    public GameCreationResponseDTO updateTenantMatch(Tenant tenant, Long matchId, GameCreationRequestDTO gameCreationRequestDTO) {
        Game game = new Game();

        game.setId(matchId);
        game.setTenant(tenant);
        game.setTeams(gameRepository.findMatchByTenant_IdAndId(tenant.getId(), matchId).get().getTeams());
        game.setPlayers(gameRepository.findMatchByTenant_IdAndId(tenant.getId(), matchId).get().getPlayers());
        if(gameCreationRequestDTO.date != null){
            game.setDate(gameCreationRequestDTO.date);
        } else {
            game.setDate(gameRepository.findMatchByTenant_IdAndId(tenant.getId(), matchId).get().getDate());
        }
        if(gameCreationRequestDTO.time != null){
            game.setTime(gameCreationRequestDTO.time);
        } else {
            game.setTime(gameRepository.findMatchByTenant_IdAndId(tenant.getId(), matchId).get().getTime());
        }

        gameRepository.save(game);

        return null;
    }

    public GameDTO deleteTenantMatch(Long tenantId, Long matchId) {
        if(gameRepository.existsById(matchId)){
            gameRepository.deleteById(matchId);

            return null;
        } else {
            throw new RuntimeException("Match id not found");
        }
    }

    public Game findByTenant_IdAndId(Long tenantId, Long matchId) {
        return gameRepository.findMatchByTenant_IdAndId(tenantId, matchId).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    public PlayerListDTO getMatchPlayers(Game game) {
        PlayerListDTO playerListDTO = new PlayerListDTO();
        playerListDTO.players = game.getPlayers().stream().map(PlayerDTO::of).collect(Collectors.toList());
        return playerListDTO;
    }

    public GameCreationResponseDTO removePlayer(Tenant tenant, Game game, Player player) {
        for(Player playerLoop : game.getPlayers()){
            if(playerLoop.getEmail().equalsIgnoreCase(player.getEmail())){
                game.getPlayers().remove(player);
            }
        }

        gameRepository.save(game);

        return null;
    }
}
