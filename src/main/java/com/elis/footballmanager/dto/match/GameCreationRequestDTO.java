package com.elis.footballmanager.dto.match;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class GameCreationRequestDTO {
    /*@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:s")
    public LocalDateTime date;*/
    public String date;
    public String time;
}
