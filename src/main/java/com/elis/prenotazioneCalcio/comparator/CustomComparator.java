package com.elis.prenotazioneCalcio.comparator;

import com.elis.prenotazioneCalcio.model.Player;

import java.util.Comparator;

public class CustomComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        return p1.getRating() - p2.getRating();
    }
}
