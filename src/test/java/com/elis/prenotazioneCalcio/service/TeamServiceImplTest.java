package com.elis.prenotazioneCalcio.service;

import com.elis.prenotazioneCalcio.dto.team.TeamCreationRequestDTO;
import com.elis.prenotazioneCalcio.model.Game;
import com.elis.prenotazioneCalcio.model.Player;
import com.elis.prenotazioneCalcio.model.Team;
import com.elis.prenotazioneCalcio.model.Tenant;
import com.elis.prenotazioneCalcio.repository.GameRepository;
import com.elis.prenotazioneCalcio.repository.PlayerRepository;
import com.elis.prenotazioneCalcio.repository.TeamRepository;
import com.elis.prenotazioneCalcio.repository.TenantRepository;
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

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamServiceImplTest {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamServiceImpl teamService;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerServiceImpl playerService;

    //Delete data from local database before each test
    @Before
    public void setUp() {
        playerRepository.deleteAll();
        teamRepository.deleteAll();
        gameRepository.deleteAll();
        tenantRepository.deleteAll();
    }

    @Test
    public void getTenantTeams(){
        //Create tenant using builder and save it into local database and in a local variable
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        //Create team using builder and save it into local database and in a local variable
        Team firstTeam = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .build());
        Team secondTeam = teamRepository.save(Team.builder()
                .name("Inter")
                .color("Blue")
                .tenant(tenant)
                .build());

        //Call relative service test method
        teamService.getTenantTeams(tenant.getId());

        //Check if team entity is not null
        assertNotNull("There are no teams" ,teamRepository.findTeamsByTenant_Id(tenant.getId()));
        //Check if team entity records are 2
        assertEquals("Teams are not 2", 2, teamRepository.findTeamsByTenant_Id(tenant.getId()).size());
    }

    @Test
    @Transactional
    public void createTenantTeam(){
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Team team = Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .build();

        //Create TeamCreationRequestDTO variable to call service method createTenantTeam()
        TeamCreationRequestDTO teamCreationRequestDTO = new TeamCreationRequestDTO();
        teamCreationRequestDTO.name = team.getName();
        teamCreationRequestDTO.color = team.getColor();

        teamService.createTenantTeam(tenant.getId(), teamCreationRequestDTO);

        //Save team into database
        team = teamRepository.save(team);

        //Check if data saved in database are equals to insert data
        assertEquals("Tenant is different", tenant, teamRepository.findById(team.getId()).get().getTenant());
        assertEquals("Name is different", team.getName(), teamRepository.findById(team.getId()).get().getName());
        assertEquals("Color is different", team.getColor(), teamRepository.findById(team.getId()).get().getColor());
    }

    @Test
    public void getTenantTeam() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Team team = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .build());

        teamService.getTenantTeam(tenant.getId(), team.getId());

        assertNotNull("There are no team", teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getId());
        //Check if two ids are the same
        assertEquals("Team is different", team.getId(), teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getId());
    }

    @Test
    public void deleteTenantTeam() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        Team firstTeam = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .tenant(tenant)
                .build());
        Team secondTeam = teamRepository.save(Team.builder()
                .name("Inter")
                .color("Blue")
                .tenant(tenant)
                .build());

        teamService.deleteTenantTeam(tenant.getId(), firstTeam.getId());

        //Check if the query return an empty value
        assertEquals("Team not deleted", Optional.empty(), teamRepository.findTeamByTenant_IdAndId(tenant.getId(), firstTeam.getId()));
        assertEquals("Team not deleted", 1, teamRepository.findTeamsByTenant_Id(tenant.getId()).size());
    }

    @Test
    @Transactional
     public void getTeamPlayers() {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Elis")
                .city("Roma")
                .address("Via")
                .cap(159)
                .email("ciao@gmail.com")
                .password("ciao")
                .build());

        //Create game using builder and save it into local database and in a local variable
        Game game = gameRepository.save(Game.builder()
                .date("05/07/2021")
                .time("20:30")
                .tenant(tenant)
                .build());

        Team team = teamRepository.save(Team.builder()
                .name("Milan")
                .color("Red")
                .game(game)
                .tenant(tenant)
                .build());

        //Create player using builder and save it into local database and in a local variable
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

        //Add players to team
        team.addPlayer(firstPlayer);
        team.addPlayer(secondPlayer);
        team.addPlayer(thirdPlayer);
        team.addPlayer(fourthPlayer);

        teamService.getTeamPlayers(tenant.getId(), game.getId(), team.getId());

        assertEquals("There are not 4 players", 4, teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getPlayers().size());
        //Check if team's total rating is equal to the sum of each player's rating
        assertEquals("Total rating is not 15", Integer.valueOf(15), teamRepository.findTeamByTenant_IdAndId(tenant.getId(), team.getId()).get().getPlayersTotalRating());
    }
}
