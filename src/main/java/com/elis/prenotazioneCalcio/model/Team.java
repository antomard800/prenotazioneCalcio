package com.elis.prenotazioneCalcio.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;

    @ManyToOne
    private Tenant tenant;

    @ManyToOne
    private Game game;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Player> players;

    //Get total rating of players that are in team
    public Integer getPlayersTotalRating() {
        //If players is not instantiated, create it as new ArrayList<>()
        if (Objects.isNull(players)) {
            players = new ArrayList<>();
        }

        Integer totalRating = 0;
        for (Player player : players) {
            totalRating += player.getRating();
        }

        return totalRating;
    }

    //Add player in players
    public void addPlayer(Player player) {
        //If is not instantiated, create it as new ArrayList<>(), then add player
        if (Objects.isNull(players)) {
            players = new ArrayList<>();
        }

        players.add(player);
    }
}
