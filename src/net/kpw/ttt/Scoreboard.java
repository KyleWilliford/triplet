package net.kpw.ttt;

public class Scoreboard {

    private int circleScore = 0;
    private int crossScore = 0;

    public int getCircleScore() {
        return circleScore;
    }

    public int getCrossScore() {
        return crossScore;
    }

    public void incrementCircleScore() {
        this.circleScore++;
    }

    public void incrementCrossScore() {
        this.crossScore++;
    }

    public void setCircleScore(int circleScore) {
        this.circleScore = circleScore;
    }

    public void setCrossScore(int crossScore) {
        this.crossScore = crossScore;
    }
}
