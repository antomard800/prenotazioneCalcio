package com.elis.footballmanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import com.elis.footballmanager.model.Tenant;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;
    /*private Integer wins;
    private Integer loses;
    private Integer draws;*/

    @ManyToOne
    private Tenant tenant;

    @ManyToOne
    private Game game;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();

    public Integer getPlayersTotalRating() {
        Integer totalRating = 0;
        for(Player player : players){
            totalRating += player.getRating();
        }

        return totalRating;
    }
}
