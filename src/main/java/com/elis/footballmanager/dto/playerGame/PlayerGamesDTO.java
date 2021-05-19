package com.elis.footballmanager.dto.playerGame;

import com.elis.footballmanager.model.Game;
import com.elis.footballmanager.model.Player;

public class PlayerGamesDTO {
    public String date;
    public String time;
    public String role;

    public static PlayerGamesDTO of(Player player, Game game){
        PlayerGamesDTO playerGamesDTO = new PlayerGamesDTO();

        playerGamesDTO.date = game.getDate();
        playerGamesDTO.time = game.getTime();
        playerGamesDTO.role = player.getRole();

        return playerGamesDTO;
    }
}
