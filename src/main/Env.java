package main;

//Static class which keeps track of 
public class Env {
    private static int minPenalty = Integer.MAX_VALUE;
    private static int minfilledWeight;
    private static int prefWeight;
    private static int pairWeight;
    private static int secdiffWeight;
    
    public static int getMinPenalty() {
        return minPenalty;
    }

    public static void setMinPenalty(int minPen) {
        minPenalty = minPen;
    }

	public static int getMinfilledWeight() {
		return minfilledWeight;
	}

	public static void setMinfilledWeight(int minfilledWeight) {
		Env.minfilledWeight = minfilledWeight;
	}

	public static int getPrefWeight() {
		return prefWeight;
	}

	public static void setPrefWeight(int prefWeight) {
		Env.prefWeight = prefWeight;
	}

	public static int getPairWeight() {
		return pairWeight;
	}

	public static void setPairWeight(int pairWeight) {
		Env.pairWeight = pairWeight;
	}

	public static int getSecdiffWeight() {
		return secdiffWeight;
	}

	public static void setSecdiffWeight(int secdiffWeight) {
		Env.secdiffWeight = secdiffWeight;
	}
    
    
}