package pl.apala.ing.onlinegame;

import pl.apala.ing.onlinegame.model.Clan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class OnlinegameSolver {

    private int groupCount;

    private int clans = 0;

    private Bucket[] buckets;


    public void processClan(Clan clan) {
        final var players = clan.getNumberOfPlayers();
        if (buckets[players] == null) {
            buckets[players] = new Bucket();
        }
        buckets[players].addClan(clan);
        clans++;
    }

    public Iterator<List<Clan>> getSolution() {
        return new SolutionIterator();
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
        buckets = new Bucket[groupCount+1];
    }


    private class SolutionIterator implements Iterator<List<Clan>> {

        private RangeMaxFinder rangeMaxFinder;

        private int clansLeft = clans;

        SolutionIterator() {
            prepare();
        }

        private void prepare() {
            rangeMaxFinder = new RangeMaxFinder(groupCount+1);

            for (int i = 1; i < buckets.length; i++) {
                var bucket = buckets[i];
                if (bucket != null) {
                    bucket.sort();
                    rangeMaxFinder.init(i, bucket.points());
                } else {
                    rangeMaxFinder.init(i, -1);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return clansLeft > 0;
        }

        @Override
        public List<Clan> next() {
            var group = new ArrayList<Clan>(groupCount/4 + 1);

            var groupLeft = groupCount; // ile miejsc zostalo w grupie
            while (clansLeft > 0 && groupLeft > 0) { // dopoki zostaly klany do obslugi albo mamy miejsce w grupie
                var clan = chooseClan(groupLeft);
                if (clan != null) {
                    group.add(clan);
                    groupLeft -= clan.getNumberOfPlayers();
                    clansLeft--;
                } else {
                    break; // nie ma klanu o liczebnosci <= groupLeft, aby zapelnic calkiem grupe, wysylamy co mamy
                }
            }
            if (group.isEmpty()) {
                throw new IllegalStateException("Empty group ???"); // nie powinno sie zdarzyc jak algorytm dziala prawidlowo...
            }
            return group;
        }

        /*
            najlepszy klan o maksymalnej liczebnosci maxSize.
            null, jesli taki nie istnie
            jesli istnieje - oznacz go jako wykorzystany (przesun w Bucket na next)
         */
        private Clan chooseClan(int maxSize) {
            var idx = rangeMaxFinder.findMaxValueIndex(maxSize);
            if (idx == -1) return null;
            var current = buckets[idx].currentAndNext();
            rangeMaxFinder.update(idx, buckets[idx].points());
            return current;
        }

    }

}
