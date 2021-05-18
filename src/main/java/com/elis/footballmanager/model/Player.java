package com.elis.footballmanager.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("player")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Player extends User{
    private Integer rating;
    private String role;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Judgement> judgements = new ArrayList<>();
}
