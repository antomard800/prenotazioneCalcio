package com.elis.footballmanager.comparator;

import com.elis.footballmanager.model.Player;

import java.util.Comparator;

public class CustomComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        return p1.getRating() - p2.getRating();
    }
}
