package pl.apala.ing.onlinegame;

class RangeMaxFinder {

    private final int[] curr; // ile punktow ma dana grupa
    private final int[] src; // indeks grupy ktora ma najwiecej punktow na lewo od, albo str[x]=x jesli to ona

    private int maxSize = 0;

    RangeMaxFinder(int size) {
        src = new int[size];
        curr = new int[size];
        src[0] = -1; // dummy wartosc, zawsze indeksuemy od 1.
        curr[0] = -1; // -1 oznacza ze nie ma wartosci. ile punktow zawsze jest >0
    }

    // maksymalna wartosc punktow dla grupy w przedziale <1..max>
    private int max(int idx) {
        return idx > 0 && src[idx] > 0 ? curr[src[idx]] : -1;
    }

    void init(int idx, int val) {
        curr[idx] = val;
        if (max(idx - 1) >= val) {  // >= poniewaz zawsze chcemy zeby zwracalo max z grupy ktora ma najmniej zawodnikow
            src[idx] = src[idx - 1];
        } else {
            src[idx] = idx;
        }
        if (idx > maxSize && val > 0) maxSize = idx;
    }

    // indeks grupy z maksymalna wartoscią punktow dla grupy w przedziale <1..max>
    int findMaxValueIndex(int max) {
        if (max > maxSize) return src[maxSize];
        return src[max];
    }

    public void update(int idx, int nexval) {
        curr[idx] = nexval;
        if (nexval == -1 || max(idx - 1) >= nexval) {
            src[idx] = src[idx - 1];
        }

        if (idx == maxSize && nexval == -1) {
            // mini optymalizacja:
            // jesli ustawiamy na -1 skrajnie prawy element, ustaw na maxSize ostatni != -1
            // tak ze kolejna petla <= maxSize ma mniejszy zakres
            while (maxSize > 0 && curr[maxSize] == -1) {
                maxSize--;
            }
        }

        for (int l = idx+1; l <= maxSize; l++) {

            if (src[l] == l) {
                break; // jesli badany element byl juz max, to pomijamy reszte.
            }

            if (curr[l] > max(l - 1)) {
                src[l] = l; // badany element jest nowy max
            } else {
                src[l] = src[l - 1]; // element na lewo jest wiekszy/rowno
            }

        }
    }
}
