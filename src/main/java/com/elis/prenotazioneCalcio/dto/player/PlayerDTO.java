package com.elis.prenotazioneCalcio.dto.player;

import com.elis.prenotazioneCalcio.model.Player;

public class PlayerDTO {
    public Long id;
    public String name;
    public String surname;
    public String email;
    public Integer rating;
    public String role;
    public Long tenant;
    public String teamName;
    public String teamColor;

    public static PlayerDTO of(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.id = player.getId();
        playerDTO.name = player.getName();
        playerDTO.surname = player.getSurname();
        playerDTO.email = player.getEmail();
        playerDTO.rating = player.getRating();
        playerDTO.role = player.getRole();
        playerDTO.tenant = player.getTenant().getId();
        if (player.getTeam() != null) {
            playerDTO.teamName = player.getTeam().getName();
            playerDTO.teamColor = player.getTeam().getColor();
        }

        return playerDTO;
    }
}
