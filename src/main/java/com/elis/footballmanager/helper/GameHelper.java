package com.elis.footballmanager.helper;

import com.elis.footballmanager.comparator.CustomComparator;
import com.elis.footballmanager.dto.match.GameCreationRequestDTO;
import com.elis.footballmanager.dto.match.GameCreationResponseDTO;
import com.elis.footballmanager.dto.match.GameDTO;
import com.elis.footballmanager.dto.match.GameListDTO;
import com.elis.footballmanager.dto.player.PlayerDTO;
import com.elis.footballmanager.dto.player.PlayerListDTO;
import com.elis.footballmanager.model.Game;
import com.elis.footballmanager.model.Player;
import com.elis.footballmanager.model.Team;
import com.elis.footballmanager.model.Tenant;
import com.elis.footballmanager.repository.GameRepository;
import com.elis.footballmanager.repository.PlayerRepository;
import com.elis.footballmanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
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
        int i = 0;
        /*for(Player playerLoop : game.getPlayers()){

            System.out.println(playerLoop.getEmail());
            System.out.println(player.getEmail());
            if(playerLoop.getEmail().equalsIgnoreCase(player.getEmail())){
                System.out.println("IF");
                //System.out.println(game.getPlayers());
            } else {
                i++;
            }
        }
        game.getPlayers().remove(i);*/
        //player.setGame(null);
        for(Game gameLoop : player.getGames()){
            if(gameLoop.getId().equals(game.getId())){
                player.getGames().remove(i);
            }
        }

        //gameRepository.save(game);
        playerRepository.save(player);

        return null;
    }

    public List<Long> buildTeams(Tenant tenant, Game game) {
        if(game.getPlayers().size() < 14){
            throw new RuntimeException("Insufficient number of players");
        }

        if(teamRepository.findTeamsByTenant_Id(tenant.getId()).size() < 2){
            throw new RuntimeException("Insufficient number of teams");
        }

        Team firstTeam = tenant.getTeams().get((int) (Math.random() * tenant.getTeams().size()));
        firstTeam.setGame(game);
        Team secondTeam = tenant.getTeams().get((int) (Math.random() * tenant.getTeams().size()));
        while(secondTeam.getGame() != null && secondTeam.getGame().equals(game)){
            secondTeam = tenant.getTeams().get((int) (Math.random() * tenant.getTeams().size()));
        }
        secondTeam.setGame(game);

        List<Player> backs = game.getBacks();
        backs.sort(new CustomComparator().reversed());
        for(Player player : backs){
            if(firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating()){
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
                playerRepository.save(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
                playerRepository.save(player);
            }
        }

        List<Player> midfielders = game.getMidfielders();
        midfielders.sort(new CustomComparator().reversed());
        for(Player player : midfielders){
            if(firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating()){
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
                playerRepository.save(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
                playerRepository.save(player);
            }
        }

        List<Player> strikers = game.getStrikers();
        strikers.sort(new CustomComparator().reversed());
        for(Player player : strikers){
            if(firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating()){
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
                playerRepository.save(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
                playerRepository.save(player);
            }
        }

        List<Player> keepers = game.getKeepers();
        keepers.sort(new CustomComparator().reversed());
        for(Player player : keepers){
            if(firstTeam.getPlayersTotalRating() > secondTeam.getPlayersTotalRating()){
                player.setTeam(secondTeam);
                secondTeam.getPlayers().add(player);
                playerRepository.save(player);
            } else {
                player.setTeam(firstTeam);
                firstTeam.getPlayers().add(player);
                playerRepository.save(player);
            }
        }

        teamRepository.save(firstTeam);
        teamRepository.save(secondTeam);

        List<Long> teamIds = new ArrayList<>();

        teamIds.add(firstTeam.getId());
        teamIds.add(secondTeam.getId());

        return teamIds;
    }
}
