package main;

//Static class which keeps track of 
public class Env {

    private static float minPenalty = Float.MAX_VALUE;
    private static float minfilledWeight;
    private static float prefWeight;
    private static float pairWeight;
    private static float secdiffWeight;
    private static float pen_coursemin;
    private static float pen_labmin;
    private static float pen_section;
    private static float pen_not_paired;

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

	public static float getPen_coursemin() {
		return pen_coursemin;
	}

	public static void setPen_coursemin(float pen_coursemin) {
		Env.pen_coursemin = pen_coursemin;
	}

	public static float getPen_labmin() {
		return pen_labmin;
	}

	public static void setPen_labmin(float pen_labmin) {
		Env.pen_labmin = pen_labmin;
	}

	public static float getPen_section() {
		return pen_section;
	}

	public static void setPen_section(float pen_section) {
		Env.pen_section = pen_section;
	}

	public static float getPen_not_paired() {
		return pen_not_paired;
	}

	public static void setPen_not_paired(float pen_not_paired) {
		Env.pen_not_paired = pen_not_paired;
	}
    
}