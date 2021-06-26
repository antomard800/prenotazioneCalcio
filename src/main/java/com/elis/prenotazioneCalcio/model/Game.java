package com.elis.prenotazioneCalcio.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String time;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Player> players;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Team> teams;

    //Return number of keepers signed to game
    public Integer numberOfKeepers() {
        Integer counter = 0;

        for (Player player : players) {
            if (player.getRole().equalsIgnoreCase("portiere")) {
                counter += 1;
            }
        }

        return counter;
    }

    //Return keepers signed to game
    public List<Player> getKeepers() {
        List<Player> keepers = new ArrayList<>();

        for (Player player : players) {
            if (player.getRole().equalsIgnoreCase("portiere")) {
                keepers.add(player);
            }
        }

        return keepers;
    }

    //Return number of backs signed to game
    public Integer numberOfBacks() {
        Integer counter = 0;

        for (Player player : players) {
            if (player.getRole().equalsIgnoreCase("difensore")) {
                counter += 1;
            }
        }

        return counter;
    }

    //Return backs signed to game
    public List<Player> getBacks() {
        List<Player> backs = new ArrayList<>();

        for (Player player : players) {
            if (player.getRole().equalsIgnoreCase("difensore")) {
                backs.add(player);
            }
        }
        return backs;
    }

    //Return number of midfielders signed to game
    public Integer numberOfMidfielders() {
        Integer counter = 0;

        for (Player player : players) {
            if (player.getRole().equalsIgnoreCase("centrocampista")) {
                counter += 1;
            }
        }

        return counter;
    }

    //Return midfielders signed to game
    public List<Player> getMidfielders() {
        List<Player> midfielders = new ArrayList<>();

        for (Player player : players) {
            if (player.getRole().equalsIgnoreCase("centrocampista")) {
                midfielders.add(player);
            }
        }

        return midfielders;
    }

    //Return number of strikers signed to game
    public Integer numberOfStrikers() {
        Integer counter = 0;

        for (Player player : players) {
            if (player.getRole().equalsIgnoreCase("attaccante")) {
                counter += 1;
            }
        }

        return counter;
    }

    //Return strikers signed to game
    public List<Player> getStrikers() {
        List<Player> strikers = new ArrayList<>();

        for (Player player : players) {
            if (player.getRole().equalsIgnoreCase("attaccante")) {
                strikers.add(player);
            }
        }

        return strikers;
    }

    //Add player in players
    public void addPlayer(Player player) {
        //If is not instantiated, create it as new ArrayList<>(), then add team
        if (Objects.isNull(players)) {
            players = new ArrayList<>();
        }

        this.players.add(player);
    }

    //Add team in teams
    public void addTeam(Team team) {
        //If is not instantiated, create it as new ArrayList<>(), then add player
        if (Objects.isNull(teams)) {
            teams = new ArrayList<>();
        }

        this.teams.add(team);
    }
}
