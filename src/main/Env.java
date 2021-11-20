package main;

public class Env {
    static int minPenalty = Integer.MAX_VALUE;


    public static int getMinPenalty() {
        return minPenalty;
    }

    public static void setMinPenalty(int minPen) {
        minPenalty = minPen;
    }
}