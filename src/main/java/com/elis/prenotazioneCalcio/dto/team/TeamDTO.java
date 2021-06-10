package com.elis.prenotazioneCalcio.dto.team;

import com.elis.prenotazioneCalcio.model.Team;

public class TeamDTO {
    public Long id;
    public String name;
    public String color;
    /*public Integer wins;
    public Integer loses;
    public Integer draws;*/
    public Long tenant;

    public static TeamDTO of(Team team) {
        TeamDTO teamDTO = new TeamDTO();

        teamDTO.id = team.getId();
        teamDTO.name = team.getName();
        teamDTO.color = team.getColor();
        /*teamDTO.wins = team.getWins();
        teamDTO.loses = team.getLoses();
        teamDTO.draws = team.getDraws();*/
        teamDTO.tenant = team.getTenant().getId();

        return teamDTO;
    }
}
