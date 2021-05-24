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

    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY)
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

    public List<Player> getKeepers(){
        List<Player> keepers = new ArrayList<>();

        for(Player player : players){
            if(player.getRole().equalsIgnoreCase("portiere")){
                keepers.add(player);
            }
        }

        return keepers;
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

    public List<Player> getBacks(){
        List<Player> backs = new ArrayList<>();

        for(Player player : players){
            if(player.getRole().equalsIgnoreCase("difensore")){
                backs.add(player);
            }
        }
        return backs;
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

    public List<Player> getMidfielders(){
        List<Player> midfielders = new ArrayList<>();

        for(Player player : players){
            if(player.getRole().equalsIgnoreCase("centrocampista")){
                midfielders.add(player);
            }
        }

        return midfielders;
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

    public List<Player> getStrikers(){
        List<Player> strikers = new ArrayList<>();

        for(Player player : players){
            if(player.getRole().equalsIgnoreCase("attaccante")){
                strikers.add(player);
            }
        }

        return strikers;
    }
}
