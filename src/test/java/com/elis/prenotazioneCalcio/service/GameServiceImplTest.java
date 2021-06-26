package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.game.GameCreationRequestDTO;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Team;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TeamRepository;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import com.elis.prenotazioneCalcio.service.interfaces.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceImplTest {

    @Autowired
    GameService gameService;

    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerServiceImpl playerService;

    @Autowired
    TeamRepository teamRepository;

    @Before
    public void setUp() {
        playerRepository.deleteAll();
        teamRepository.deleteAll();
        gameRepository.deleteAll();
        tenantRepository.deleteAll();
    }

    @Test
    @Transactional
    public void getTenantGames() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Game firstGame = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());
        Game secondGame = gameRepository.save(Game.builder()
                .date("02/08/2021")
                .time("17:30")
                .tenant(tenant)
                .build());
        Game thirdGame = gameRepository.save(Game.builder()
                .date("04/08/2021")
                .time("12:00")
                .tenant(tenant)
                .build());
        Game fourthGame = gameRepository.save(Game.builder()
                .date("05/08/2021")
                .time("09:00")
                .tenant(tenant)
                .build());

        gameService.getTenantMatches(tenant.getId());

        assertNotNull("Games not found", gameRepository.findAll());
        assertEquals("Games are not 4", 4, gameRepository.findAll().size());
    }

    @Test
    @Transactional
    public void createTenantGame() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Game game = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());

        GameCreationRequestDTO gameCreationRequestDTO = new GameCreationRequestDTO();
        gameCreationRequestDTO.date = game.getDate();
        gameCreationRequestDTO.time = game.getTime();

        gameService.createTenantMatch(tenant.getId(), gameCreationRequestDTO);

        assertEquals("Tenant is different", tenant, gameRepository.findById(game.getId()).get().getTenant());
        assertEquals("Date is different", game.getDate(), gameRepository.findById(game.getId()).get().getDate());
        assertEquals("Time is different", game.getTime(), gameRepository.findById(game.getId()).get().getTime());

        assertNotNull("Id is null", game.getId());
        assertNotNull("Date is null", game.getDate());
        assertNotNull("Time is null", game.getTime());
        assertNotNull("Tenant is null", game.getTenant());

        assertEquals("Date is different", "03/08/2021", game.getDate());
        assertEquals("Time is different", "20:50", game.getTime());
        assertEquals("Tenant is different", tenant, game.getTenant());
    }

    @Test
    @Transactional
    public void getTenantGame() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Game game = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());

        gameService.getTenantMatch(tenant.getId(), game.getId());

        assertNotNull("Game not found", gameRepository.findGameByTenant_IdAndId(tenant.getId(), game.getId()));
        assertEquals("Game is different", game.getId(), gameRepository.findGameByTenant_IdAndId(tenant.getId(), game.getId()).get().getId());
    }

    @Test
    public void deleteTenantGame() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Game firstGame = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());
        Game secondGame = gameRepository.save(Game.builder()
                .date("02/08/2021")
                .time("17:30")
                .tenant(tenant)
                .build());

        gameService.deleteTenantMatch(tenant.getId(), firstGame.getId());

        assertEquals("Game not deleted", Optional.empty(), gameRepository.findGameByTenant_IdAndId(tenant.getId(), firstGame.getId()));
        assertEquals("Game not well deleted", 1, gameRepository.findAll().size());
    }

    @Test
    @Transactional
    public void getGamePlayers() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Player firstPlayer = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build());
        Player secondPlayer = playerRepository.save(Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .tenant(tenant)
                .build());

        Game firstGame = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());
        Game secondGame = gameRepository.save(Game.builder()
                .date("02/08/2021")
                .time("17:30")
                .tenant(tenant)
                .build());

        playerService.signToMatch(tenant.getId(), firstPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), secondGame.getId());

        firstGame.addPlayer(firstPlayer);
        firstGame.addPlayer(secondPlayer);
        secondGame.addPlayer(secondPlayer);

        gameService.getMatchPlayers(tenant.getId(), firstGame.getId());
        gameService.getMatchPlayers(tenant.getId(), secondGame.getId());

        assertEquals("Registration failed", 2, gameRepository.findById(firstGame.getId()).get().getPlayers().size());
        assertEquals("Registration failed", 1, gameRepository.findById(secondGame.getId()).get().getPlayers().size());
        assertEquals("Registration failed", 1, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size());
        assertEquals("Registration failed", 2, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size());
    }

    @Test
    @Transactional
    public void removePlayer() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Player firstPlayer = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build());
        Player secondPlayer = playerRepository.save(Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .tenant(tenant)
                .build());

        Game firstGame = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());
        Game secondGame = gameRepository.save(Game.builder()
                .date("02/08/2021")
                .time("17:30")
                .tenant(tenant)
                .build());

        playerService.signToMatch(tenant.getId(), firstPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), secondGame.getId());

        firstGame.addPlayer(firstPlayer);
        firstGame.addPlayer(secondPlayer);
        secondGame.addPlayer(secondPlayer);

        gameService.removePlayer(tenant.getId(), firstGame.getId(), firstPlayer.getId());

        firstGame.getPlayers().remove(firstPlayer);

        assertEquals("Remove failed", 1, gameRepository.findById(firstGame.getId()).get().getPlayers().size());
        assertEquals("Remove failed", 1, gameRepository.findById(secondGame.getId()).get().getPlayers().size());
        assertEquals("Remove failed", 0, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size());
        assertEquals("Remove failed", 2, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size());
    }

    @Test
    @Transactional
    public void removePlayers() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Game firstGame = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());
        Game secondGame = gameRepository.save(Game.builder()
                .date("02/08/2021")
                .time("17:30")
                .tenant(tenant)
                .build());

        Team firstTeam = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .game(firstGame)
                .build());

        Player firstPlayer = playerRepository.save(playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(4)
                .role("Centrocampista")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build()));
        Player secondPlayer = playerRepository.save(playerRepository.save(Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .tenant(tenant)
                .build()));


        playerService.signToMatch(tenant.getId(), firstPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), firstGame.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), secondGame.getId());

        firstGame.addPlayer(firstPlayer);
        firstGame.addPlayer(secondPlayer);
        secondGame.addPlayer(secondPlayer);

        firstTeam.addPlayer(firstPlayer);
        firstTeam.addPlayer(secondPlayer);

        gameService.removePlayers(tenant.getId(), firstGame.getId());

        firstTeam.getPlayers().remove(firstPlayer);
        firstTeam.getPlayers().remove(secondPlayer);

        assertEquals("Remove failed", 2, gameRepository.findById(firstGame.getId()).get().getPlayers().size());
        assertEquals("Remove failed", 1, gameRepository.findById(secondGame.getId()).get().getPlayers().size());
        assertEquals("Remove failed", 1, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size());
        assertEquals("Remove failed", 2, playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size());
        assertEquals("Remove failed", 0, teamRepository.findTeamByTenant_IdAndId(tenant.getId(), firstTeam.getId()).get().getPlayers().size());
    }

    @Test
    @Transactional
    public void buildTeams() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Player firstPlayer = playerRepository.save(Player.builder()
                .name("Antonio")
                .surname("DAddetta")
                .rating(3)
                .role("Difensore")
                .email("antonio@gmail.com")
                .password("antonio")
                .tenant(tenant)
                .build());
        Player secondPlayer = playerRepository.save(Player.builder()
                .name("Giacomo")
                .surname("Calianno")
                .rating(5)
                .role("Difensore")
                .email("giacomo@gmail.com")
                .password("giacomo")
                .tenant(tenant)
                .build());
        Player thirdPlayer = playerRepository.save(Player.builder()
                .name("Nelson")
                .surname("Dida")
                .rating(5)
                .role("Portiere")
                .email("nelson@gmail.com")
                .password("nelson")
                .tenant(tenant)
                .build());
        Player fourthPlayer = playerRepository.save(Player.builder()
                .name("Maarten")
                .surname("Stekelenburg")
                .rating(2)
                .role("Portiere")
                .email("maarten@gmail.com")
                .password("maarten")
                .tenant(tenant)
                .build());
        Player fifthPlayer = playerRepository.save(Player.builder()
                .name("Alessandro")
                .surname("Nesta")
                .rating(4)
                .role("Difensore")
                .email("alessandro@gmail.com")
                .password("alessandro")
                .tenant(tenant)
                .build());
        Player sixthPlayer = playerRepository.save(Player.builder()
                .name("Massimo")
                .surname("Oddo")
                .rating(4)
                .role("Difensore")
                .email("massimo@gmail.com")
                .password("massimo")
                .tenant(tenant)
                .build());
        Player seventhPlayer = playerRepository.save(Player.builder()
                .name("Clarence")
                .surname("Seedorf")
                .rating(5)
                .role("Centrocampista")
                .email("clarence@gmail.com")
                .password("clarence")
                .tenant(tenant)
                .build());
        Player eighthPlayer = playerRepository.save(Player.builder()
                .name("Nigel")
                .surname("De Jong")
                .rating(3)
                .role("Centrocampista")
                .email("nigel@gmail.com")
                .password("nigel")
                .tenant(tenant)
                .build());
        Player ninthPlayer = playerRepository.save(Player.builder()
                .name("Roberto")
                .surname("Gagliardini")
                .rating(2)
                .role("Centrocampista")
                .email("roberto@gmail.com")
                .password("roberto")
                .tenant(tenant)
                .build());
        Player tenthPlayer = playerRepository.save(Player.builder()
                .name("Nicolò")
                .surname("Barella")
                .rating(5)
                .role("Centrocampista")
                .email("nicolo@gmail.com")
                .password("nicolo")
                .tenant(tenant)
                .build());
        Player eleventhPlayer = playerRepository.save(Player.builder()
                .name("Zlatan")
                .surname("Ibrahimovic")
                .rating(5)
                .role("Attaccante")
                .email("zlatan@gmail.com")
                .password("zlatan")
                .tenant(tenant)
                .build());
        Player twelfthPlayer = playerRepository.save(Player.builder()
                .name("Mattia")
                .surname("Destro")
                .rating(3)
                .role("Attaccante")
                .email("mattia@gmail.com")
                .password("mattia")
                .tenant(tenant)
                .build());
        Player thirteenthPlayer = playerRepository.save(Player.builder()
                .name("Carlos")
                .surname("Bacca")
                .rating(4)
                .role("Attaccante")
                .email("carlos@gmail.com")
                .password("carlos")
                .tenant(tenant)
                .build());
        Player fourteenthPlayer = playerRepository.save(Player.builder()
                .name("Vedat")
                .surname("Muriqi")
                .rating(2)
                .role("Attaccante")
                .email("vedat@gmail.com")
                .password("vedat")
                .tenant(tenant)
                .build());

        Game game = gameRepository.save(Game.builder()
                .date("03/08/2021")
                .time("20:50")
                .tenant(tenant)
                .build());

        Team firstTeam = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .game(game)
                .build());
        Team secondTeam = teamRepository.save(Team.builder()
                .name("Inter")
                .color("Blue")
                .tenant(tenant)
                .game(game)
                .build());

        playerService.signToMatch(tenant.getId(), firstPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), secondPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), thirdPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), fourthPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), fifthPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), sixthPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), seventhPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), eighthPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), ninthPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), tenthPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), eleventhPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), twelfthPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), thirteenthPlayer.getId(), game.getId());
        playerService.signToMatch(tenant.getId(), fourteenthPlayer.getId(), game.getId());

        game.addPlayer(firstPlayer);
        game.addPlayer(secondPlayer);
        game.addPlayer(thirdPlayer);
        game.addPlayer(fourthPlayer);
        game.addPlayer(fifthPlayer);
        game.addPlayer(sixthPlayer);
        game.addPlayer(seventhPlayer);
        game.addPlayer(eighthPlayer);
        game.addPlayer(ninthPlayer);
        game.addPlayer(tenthPlayer);
        game.addPlayer(eleventhPlayer);
        game.addPlayer(twelfthPlayer);
        game.addPlayer(thirteenthPlayer);
        game.addPlayer(fourteenthPlayer);

        tenant.addTeam(firstTeam);
        tenant.addTeam(secondTeam);

        game.addTeam(firstTeam);
        game.addTeam(secondTeam);

        gameService.buildTeams(tenant.getId(), game.getId());

        assertEquals("There are more than 2 keepers", 2, game.getKeepers().size());
        assertEquals("There are more than 4 backs", 4, game.getBacks().size());
        assertEquals("There are more than 4 midfielders", 4, game.getMidfielders().size());
        assertEquals("There are more than 4 strikers", 4, game.getStrikers().size());
    }
}