package com.elis.prenotazioneCalcio.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String address;
    private Integer cap;
    private String email;
    private String password;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pitch> pitches = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Player> players;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Game> games;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Judgement> judgements;

    public void addTeam(Team team) {
        if(Objects.isNull(teams)){
            teams = new ArrayList<>();
        }

        teams.add(team);
    }
}
