package com.elis.prenotazioneCalcio.dto.game;

import com.elis.prenotazioneCalcio.model.Game;

import java.util.Objects;

public class GameDTO {
    //Attributes to send to front-end
    public Long id;
    public String date;
    public String time;
    public Integer numberOfPlayers;
    public Integer numberOfKeepers;
    public Integer numberOfBacks;
    public Integer numberOfMidfielders;
    public Integer numberOfStrikers;

    //Transform a Game into a GameDTO
    public static GameDTO of(Game game) {
        GameDTO gameDTO = new GameDTO();

        gameDTO.id = game.getId();
        gameDTO.date = game.getDate();
        gameDTO.time = game.getTime();
        //Check if there are players signed to game
        if (!Objects.isNull(game.getPlayers())) {
            gameDTO.numberOfPlayers = game.getPlayers().size();
            gameDTO.numberOfKeepers = game.numberOfKeepers();
            gameDTO.numberOfBacks = game.numberOfBacks();
            gameDTO.numberOfMidfielders = game.numberOfMidfielders();
            gameDTO.numberOfStrikers = game.numberOfStrikers();
        }

        return gameDTO;
    }
}
