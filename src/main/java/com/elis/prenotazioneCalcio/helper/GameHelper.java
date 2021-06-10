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

    public GameListDTO getTenantMatches(Long tenantId) {
        GameListDTO gameListDTO = new GameListDTO();
        gameListDTO.matches = gameRepository.findMatchesByTenant_Id(tenantId).stream().map(GameDTO::of).collect(Collectors.toList());
        return gameListDTO;
    }

    public GameCreationResponseDTO createTenantMatch(Tenant tenant, GameCreationRequestDTO gameCreationRequestDTO) {
        Game game = new Game();

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
        if (gameCreationRequestDTO.date != null) {
            game.setDate(gameCreationRequestDTO.date);
        } else {
            game.setDate(gameRepository.findMatchByTenant_IdAndId(tenant.getId(), matchId).get().getDate());
        }
        if (gameCreationRequestDTO.time != null) {
            game.setTime(gameCreationRequestDTO.time);
        } else {
            game.setTime(gameRepository.findMatchByTenant_IdAndId(tenant.getId(), matchId).get().getTime());
        }

        gameRepository.save(game);

        return null;
    }

    public GameDTO deleteTenantMatch(Long tenantId, Long matchId) {
        if (gameRepository.existsById(matchId)) {
            gameRepository.deleteById(matchId);

            return null;
        } else {
            throw new RuntimeException("Match id not found");
        }
    }

    public Game findByTenantIdAndGameId(Long tenantId, Long matchId) {
        return gameRepository.findMatchByTenant_IdAndId(tenantId, matchId).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    public PlayerListDTO getMatchPlayers(Game game) {
        PlayerListDTO playerListDTO = new PlayerListDTO();
        playerListDTO.players = game.getPlayers().stream().map(PlayerDTO::of).collect(Collectors.toList());
        return playerListDTO;
    }

    public GameCreationResponseDTO removePlayer(Tenant tenant, Game game, Player player) {
        int i = 0;

        player.getGames().removeIf(gameTmp -> gameTmp.getId().equals(game.getId()));

        playerRepository.save(player);

        return null;
    }

    @Transactional
    public GameCreationResponseDTO removePlayers(Game game) {
        for (Player playerLoop : game.getPlayers()) {
            playerLoop.setTeam(null);

            playerRepository.save(playerLoop);
        }

        return null;
    }

    public List<Long> buildTeams(Tenant tenant, Game game) {
        if (gameRepository.findMatchByTenant_IdAndId(tenant.getId(), game.getId()).get().getPlayers().size() < 14) {
            throw new RuntimeException("Insufficient number of players");
        }

        if (teamRepository.findTeamsByTenant_Id(tenant.getId()).size() < 2) {
            throw new RuntimeException("Insufficient number of teams");
        }

        if(!Objects.isNull(gameRepository.findMatchByTenant_IdAndId(tenant.getId(), game.getId()).get().getTeams())){
            for (Team teamLoop : gameRepository.findMatchByTenant_IdAndId(tenant.getId(), game.getId()).get().getTeams()) {
                teamLoop.setGame(null);
                teamRepository.save(teamLoop);
            }
        }

        Team firstTeam = tenant.getTeams().get((int) (Math.random() * tenant.getTeams().size()));
        game.addTeam(firstTeam);
        Team secondTeam = tenant.getTeams().get((int) (Math.random() * tenant.getTeams().size()));
        while (secondTeam.getGame() != null && secondTeam.getGame().equals(game)) {
            secondTeam = tenant.getTeams().get((int) (Math.random() * tenant.getTeams().size()));
        }
        game.addTeam(secondTeam);

        List<Player> backs = game.getBacks();
        backs.sort(new CustomComparator().reversed());
        for (Player player : backs) {
            if (firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating() && secondTeam.getPlayers().size() < 2) {
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
            }
            playerRepository.save(player);
        }

        List<Player> midfielders = game.getMidfielders();
        midfielders.sort(new CustomComparator().reversed());
        for (Player player : midfielders) {
            if (firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating() && secondTeam.getPlayers().size() < 4) {
                player.setTeam(secondTeam);
                //secondTeam.addPlayer(player);
                secondTeam.getPlayers().add(player);
            } else {
                player.setTeam(firstTeam);
                //firstTeam.addPlayer(player);
                firstTeam.getPlayers().add(player);
            }
            playerRepository.save(player);
        }

        List<Player> strikers = game.getStrikers();
        strikers.sort(new CustomComparator().reversed());
        for (Player player : strikers) {
            if (firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating() && secondTeam.getPlayers().size() < 6) {
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
            }
            playerRepository.save(player);
        }

        List<Player> keepers = game.getKeepers();
        keepers.sort(new CustomComparator().reversed());
        for (Player player : keepers) {
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

        List<Long> teamIds = new ArrayList<>();

        teamIds.add(firstTeam.getId());
        teamIds.add(secondTeam.getId());

        return teamIds;
    }

    public Game saveOrUpdate(Game game) {
        return gameRepository.save(game);
    }
}
