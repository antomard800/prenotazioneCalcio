package com.elis.footballmanager.service;

import com.elis.prenotazioneCalcio.dto.game.GameCreationRequestDTO;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Team;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TeamRepository;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
import com.elis.prenotazioneCalcio.service.GameServiceImpl;
import com.elis.prenotazioneCalcio.service.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceImplTest {
    @Autowired
    GameServiceImpl gameService;

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

    @BeforeEach
    void setUp() {
        playerRepository.deleteAll();
        teamRepository.deleteAll();
        gameRepository.deleteAll();
        tenantRepository.deleteAll();
    }

    @Test
    @Transactional
    void getTenantGames() {
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

        assertNotNull(gameRepository.findAll(), "Games not found");
        assertEquals(gameRepository.findAll().size(), 4, "Games are not 4");
    }

    @Test
    @Transactional
    void createTenantGame() {
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

        assertEquals(tenant, gameRepository.findById(game.getId()).get().getTenant(), "Tenant is different");
        assertEquals(game.getDate(), gameRepository.findById(game.getId()).get().getDate(), "Date is different");
        assertEquals(game.getTime(), gameRepository.findById(game.getId()).get().getTime(), "Time is different");

        assertNotNull(game.getId(), "Id is null");
        assertNotNull(game.getDate(), "Date is null");
        assertNotNull(game.getTime(), "Time is null");
        assertNotNull(game.getTenant(), "Tenant is null");

        assertEquals(game.getDate(), "03/08/2021", "Date is different");
        assertEquals(game.getTime(), "20:50", "Time is different");
        assertEquals(game.getTenant(), tenant, "Tenant is different");
    }

    @Test
    @Transactional
    void getTenantGame() {
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

        assertNotNull(gameRepository.findMatchByTenant_IdAndId(tenant.getId(), game.getId()), "Game not found");
        assertEquals(game.getId(), gameRepository.findMatchByTenant_IdAndId(tenant.getId(), game.getId()).get().getId(), "Game is different");
    }

    @Test
    void deleteTenantGame() {
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

        assertEquals(Optional.empty(), gameRepository.findMatchByTenant_IdAndId(tenant.getId(), firstGame.getId()), "Game not deleted");
        assertEquals(1, gameRepository.findAll().size(), "Game not well deleted");
    }

    @Test
    @Transactional
    void getGamePlayers() {
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

        assertEquals(gameRepository.findById(firstGame.getId()).get().getPlayers().size(), 2, "Registration failed");
        assertEquals(gameRepository.findById(secondGame.getId()).get().getPlayers().size(), 1, "Registration failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size(), 1, "Registration failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size(), 2, "Registration failed");
    }

    @Test
    @Transactional
    void removePlayer() {
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

        assertEquals(gameRepository.findById(firstGame.getId()).get().getPlayers().size(), 1, "Remove failed");
        assertEquals(gameRepository.findById(secondGame.getId()).get().getPlayers().size(), 1, "Remove failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size(), 0, "Remove failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size(), 2, "Remove failed");
    }

    @Test
    @Transactional
    void removePlayers() {
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

        assertEquals(gameRepository.findById(firstGame.getId()).get().getPlayers().size(), 2, "Remove failed");
        assertEquals(gameRepository.findById(secondGame.getId()).get().getPlayers().size(), 1, "Remove failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), firstPlayer.getId()).get().getGames().size(), 1, "Remove failed");
        assertEquals(playerRepository.findPlayerByTenant_IdAndId(tenant.getId(), secondPlayer.getId()).get().getGames().size(), 2, "Remove failed");
        assertEquals(teamRepository.findTeamByTenant_IdAndId(tenant.getId(), firstTeam.getId()).get().getPlayers().size(), 0, "Remove failed");
    }

    @Test
    @Transactional
    void buildTeams(){
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

        assertEquals(2, game.getKeepers().size(), "There are more than 2 keepers");
    }
}