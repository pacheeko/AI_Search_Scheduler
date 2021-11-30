package main;

//Static class which keeps track of 
public class Env {
    private static float minPenalty = Float.MAX_VALUE;
    private static float minfilledWeight;
    private static float prefWeight;
    private static float pairWeight;
    private static float secdiffWeight;
    
    public static float getMinPenalty() {
        return minPenalty;
    }

    public static void setMinPenalty(float minPen) {
        minPenalty = minPen;
    }

	public static float getMinfilledWeight() {
		return minfilledWeight;
	}

	public static void setMinfilledWeight(float minfilledWeight) {
		Env.minfilledWeight = minfilledWeight;
	}

	public static float getPrefWeight() {
		return prefWeight;
	}

	public static void setPrefWeight(float prefWeight) {
		Env.prefWeight = prefWeight;
	}

	public static float getPairWeight() {
		return pairWeight;
	}

	public static void setPairWeight(float pairWeight) {
		Env.pairWeight = pairWeight;
	}

	public static float getSecdiffWeight() {
		return secdiffWeight;
	}

	public static void setSecdiffWeight(float secdiffWeight) {
		Env.secdiffWeight = secdiffWeight;
	}
    
    
}