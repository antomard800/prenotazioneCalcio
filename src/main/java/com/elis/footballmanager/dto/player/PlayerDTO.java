package com.elis.footballmanager.dto.player;

import com.elis.footballmanager.model.Player;

public class PlayerDTO {
    public Long id;
    public String name;
    public String surname;
    public String email;
    public Integer rating;
    public String role;
    public Long tenant;
    //public Long game;

    public static PlayerDTO of(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.id = player.getId();
        playerDTO.name = player.getName();
        playerDTO.surname = player.getSurname();
        playerDTO.email = player.getEmail();
        playerDTO.rating = player.getRating();
        playerDTO.role = player.getRole();
        playerDTO.tenant = player.getTenant().getId();
        //playerDTO.game = player.getGame().getId();

        return playerDTO;
    }
}
