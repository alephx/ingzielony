package pl.apala.ing.onlinegame.model;

public class Clan {

    private int numberOfPlayers;
    private int points;

    @Override
    public String toString() {
        return "Clan[" + numberOfPlayers + " points:" + points + "]";
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
