package pl.apala.ing.onlinegame;

import pl.apala.ing.onlinegame.model.Clan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Bucket {

    public static final Comparator<Clan> CLAN_COMPARATOR = Comparator.comparingInt(Clan::getPoints).reversed();
    private int pos = 0;

    // lista punktow klanow
    private final List<Clan> clans = new ArrayList<>(256);

    public void addClan(Clan clan) {
        clans.add(clan);
    }

    public void sort() {
        clans.sort(CLAN_COMPARATOR);
    }

    public int points() {
        return pos < clans.size() ? clans.get(pos).getPoints() : -1;
    }


    public Clan currentAndNext() {
        if (pos < clans.size()) {
            return clans.get(pos++);
        } else {
            return null;
        }
    }

}
