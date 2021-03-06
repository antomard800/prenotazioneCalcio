package com.elis.prenotazioneCalcio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PrenotazioneCalcioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrenotazioneCalcioApplication.class, args);
    }

}
