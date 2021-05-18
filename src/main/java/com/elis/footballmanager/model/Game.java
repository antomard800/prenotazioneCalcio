package com.elis.footballmanager.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    List<Team> teams = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    public Integer numberOfKeepers(){
        Integer counter = 0;

        for(Player player : players){
            if(player.getRole().equalsIgnoreCase("portiere")){
                counter += 1;
            }
        }

        return counter;
    }

    public Integer numberOfBacks(){
        Integer counter = 0;

        for(Player player : players){
            if(player.getRole().equalsIgnoreCase("difensore")){
                counter += 1;
            }
        }

        return counter;
    }

    public Integer numberOfMidfielders(){
        Integer counter = 0;

        for(Player player : players){
            if(player.getRole().equalsIgnoreCase("centrocampista")){
                counter += 1;
            }
        }

        return counter;
    }

    public Integer numberOfStrikers(){
        Integer counter = 0;

        for(Player player : players){
            if(player.getRole().equalsIgnoreCase("attaccante")){
                counter += 1;
            }
        }

        return counter;
    }
}
