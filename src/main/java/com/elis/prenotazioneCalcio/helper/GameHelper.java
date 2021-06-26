package com.elis.prenotazioneCalcio.helper;

import com.elis.prenotazioneCalcio.comparator.CustomComparator;
import com.elis.prenotazioneCalcio.dto.game.GameCreationRequestDTO;
import com.elis.prenotazioneCalcio.dto.game.GameCreationResponseDTO;
import com.elis.prenotazioneCalcio.dto.game.GameDTO;
import com.elis.prenotazioneCalcio.dto.game.GameListDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerDTO;
import com.elis.prenotazioneCalcio.dto.player.PlayerListDTO;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Team;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Transactional
public class GameHelper {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    public Game findByTenantIdAndGameId(Long tenantId, Long matchId) {
        //Find game by tenant's id and his id
        return gameRepository.findGameByTenant_IdAndId(tenantId, matchId).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    public GameListDTO getTenantMatches(Long tenantId) {
        //Create a list of gameDTO
        GameListDTO gameListDTO = new GameListDTO();
        //Use findGamesByTenant_Id to find games of that tenant, then convert them into GameDTO and add them to gameDTO list
        gameListDTO.games = gameRepository.findGamesByTenant_Id(tenantId).stream().map(GameDTO::of).collect(Collectors.toList());
        return gameListDTO;
    }

    public GameCreationResponseDTO createTenantMatch(Tenant tenant, GameCreationRequestDTO gameCreationRequestDTO) {
        Game game = new Game();

        //Set attribute and save new game
        game.setDate(gameCreationRequestDTO.date);
        game.setTime(gameCreationRequestDTO.time);
        game.setTenant(tenant);

        gameRepository.save(game);

        return null;
    }

    public GameDTO getTenantMatch(Long tenantId, Long gameId) {
        //Find game by tenant and his id and convert it into gameDTO
        return GameDTO.of(gameRepository.findGameByTenant_IdAndId(tenantId, gameId).orElseThrow(() -> new RuntimeException("Match not found")));
    }

    public GameCreationResponseDTO updateTenantMatch(Tenant tenant, Long matchId, GameCreationRequestDTO gameCreationRequestDTO) {
        Game game = new Game();

        game.setId(matchId);
        game.setTenant(tenant);
        game.setTeams(gameRepository.findGameByTenant_IdAndId(tenant.getId(), matchId).get().getTeams());
        game.setPlayers(gameRepository.findGameByTenant_IdAndId(tenant.getId(), matchId).get().getPlayers());
        //If sent data are not null, update game in database
        if (gameCreationRequestDTO.date != null) {
            game.setDate(gameCreationRequestDTO.date);
        } else {
            game.setDate(gameRepository.findGameByTenant_IdAndId(tenant.getId(), matchId).get().getDate());
        }
        if (gameCreationRequestDTO.time != null) {
            game.setTime(gameCreationRequestDTO.time);
        } else {
            game.setTime(gameRepository.findGameByTenant_IdAndId(tenant.getId(), matchId).get().getTime());
        }

        gameRepository.save(game);

        return null;
    }

    public GameDTO deleteTenantMatch(Long matchId) {
        //If game exist, delete it from database
        if (gameRepository.existsById(matchId)) {
            gameRepository.deleteById(matchId);

            return null;
        } else {
            throw new RuntimeException("Match id not found");
        }
    }

    public PlayerListDTO getMatchPlayers(Game game) {
        PlayerListDTO playerListDTO = new PlayerListDTO();
        //Get players signed to game, convert them into playerDTO and add them into playerDTO list
        playerListDTO.players = game.getPlayers().stream().map(PlayerDTO::of).collect(Collectors.toList());
        return playerListDTO;
    }

    public GameCreationResponseDTO removePlayer(Tenant tenant, Game game, Player player) {
        //If player is signed to game, remove him from that game
        player.getGames().removeIf(gameTmp -> gameTmp.getId().equals(game.getId()));

        playerRepository.save(player);

        return null;
    }

    @Transactional
    public GameCreationResponseDTO removePlayers(Game game) {
        //Remove player from team in that game
        for (Player playerLoop : game.getPlayers()) {
            playerLoop.setTeam(null);

            playerRepository.save(playerLoop);
        }

        return null;
    }

    public List<Long> buildTeams(Tenant tenant, Game game) {
        //Check if player signed to match are 14
        if (gameRepository.findGameByTenant_IdAndId(tenant.getId(), game.getId()).get().getPlayers().size() < 14) {
            throw new RuntimeException("Insufficient number of players");
        }

        //Check if team saved in database for tenant are 2
        if (teamRepository.findTeamsByTenant_Id(tenant.getId()).size() < 2) {
            throw new RuntimeException("Insufficient number of teams");
        }

        //Set teams' game null, useful because teams are chosen randomly from database
        if (!Objects.isNull(gameRepository.findGameByTenant_IdAndId(tenant.getId(), game.getId()).get().getTeams())) {
            for (Team teamLoop : gameRepository.findGameByTenant_IdAndId(tenant.getId(), game.getId()).get().getTeams()) {
                teamLoop.setGame(null);
                teamRepository.save(teamLoop);
            }
        }

        //Choose random firstTeam from database
        Team firstTeam = tenant.getTeams().get((int) (Math.random() * tenant.getTeams().size()));
        game.addTeam(firstTeam);
        //Choose random secondTeam from database
        Team secondTeam = tenant.getTeams().get((int) (Math.random() * tenant.getTeams().size()));
        //Until random secondTeam is chosen for another game, choose another one randomly
        while (secondTeam.getGame() != null && secondTeam.getGame().equals(game)) {
            secondTeam = tenant.getTeams().get((int) (Math.random() * tenant.getTeams().size()));
        }
        game.addTeam(secondTeam);

        //Save backs signed to match
        List<Player> backs = game.getBacks();
        //Order them in descending order
        backs.sort(new CustomComparator().reversed());
        for (Player player : backs) {
            /*If firstTeam has higher rating than secondTeam's one and if secondTeam has not yet 2 player saved,
                save player in secondTeam, else save him in firstTeam
             */
            if (firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating() && secondTeam.getPlayers().size() < 2) {
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
            }
            playerRepository.save(player);
        }

        //Save midfielders signed to match
        List<Player> midfielders = game.getMidfielders();
        midfielders.sort(new CustomComparator().reversed());
        for (Player player : midfielders) {
            /*If firstTeam has higher rating than secondTeam's one and if secondTeam has not yet 4 player saved,
                save player in secondTeam, else save him in firstTeam
             */
            if (firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating() && secondTeam.getPlayers().size() < 4) {
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
            }
            playerRepository.save(player);
        }

        //Save strikers signed to match
        List<Player> strikers = game.getStrikers();
        strikers.sort(new CustomComparator().reversed());
        for (Player player : strikers) {
            /*If firstTeam has higher rating than secondTeam's one and if secondTeam has not yet 6 player saved,
                save player in secondTeam, else save him in firstTeam
             */
            if (firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating() && secondTeam.getPlayers().size() < 6) {
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
            }
            playerRepository.save(player);
        }

        //Save keepers signed to match
        List<Player> keepers = game.getKeepers();
        keepers.sort(new CustomComparator().reversed());
        for (Player player : keepers) {
            /*If firstTeam has higher rating than secondTeam's one and if secondTeam has not yet 7 player saved,
                save player in secondTeam, else save him in firstTeam
             */
            if (firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating() && secondTeam.getPlayers().size() < 7) {
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
            }
            playerRepository.save(player);
        }

        teamRepository.save(firstTeam);
        teamRepository.save(secondTeam);

        //Save game's team ids
        List<Long> teamIds = new ArrayList<>();

        teamIds.add(firstTeam.getId());
        teamIds.add(secondTeam.getId());

        return teamIds;
    }
}
