package main;

public class Env {
    int minPenalty;

    public Env(int minPenalty) {
        this.minPenalty = minPenalty;
    }

    public Env() {
        this.minPenalty = Integer.MAX_VALUE;
    }

    public int getMinPenalty() {
        return minPenalty;
    }

    public void setMinPenalty(int minPenalty) {
        this.minPenalty = minPenalty;
    }
}