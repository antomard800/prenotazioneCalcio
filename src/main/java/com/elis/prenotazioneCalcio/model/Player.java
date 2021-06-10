package com.elis.prenotazioneCalcio.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("player")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private Integer rating;
    private String role;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "game_player",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<Game> games;

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Judgement> judgements;

    public void addGame(Game game){
        if(Objects.isNull(games)){
            games = new ArrayList<>();
        }
        this.games.add(game);
    }
}
