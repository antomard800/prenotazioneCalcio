package com.elis.prenotazioneCalcio.dto.team;

import com.elis.prenotazioneCalcio.model.Team;

public class TeamDTO {
    //Attributes to send to front-end
    public Long id;
    public String name;
    public String color;
    public Long tenant;

    //Transform a Team into a TeamDTO
    public static TeamDTO of(Team team) {
        TeamDTO teamDTO = new TeamDTO();

        teamDTO.id = team.getId();
        teamDTO.name = team.getName();
        teamDTO.color = team.getColor();
        teamDTO.tenant = team.getTenant().getId();

        return teamDTO;
    }
}
