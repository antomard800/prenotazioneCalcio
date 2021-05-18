package com.elis.footballmanager.dto.match;

import com.elis.footballmanager.helper.PlayerHelper;
import com.elis.footballmanager.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

public class GameDTO {

    public Long id;
    public String date;
    public String time;
    public Integer numberOfPlayers;
    public Integer numberOfKeepers;
    public Integer numberOfBacks;
    public Integer numberOfMidfielders;
    public Integer numberOfStrikers;

    public static GameDTO of(Game game){
        GameDTO gameDTO = new GameDTO();

        gameDTO.id = game.getId();
        gameDTO.date = game.getDate();
        gameDTO.time = game.getTime();
        gameDTO.numberOfPlayers = game.getPlayers().size();
        gameDTO.numberOfKeepers = game.numberOfKeepers();
        gameDTO.numberOfBacks = game.numberOfBacks();
        gameDTO.numberOfMidfielders = game.numberOfMidfielders();
        gameDTO.numberOfStrikers = game.numberOfStrikers();

        return gameDTO;
    }
}
