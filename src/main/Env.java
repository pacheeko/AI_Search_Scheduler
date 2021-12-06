package main;

//Static class which keeps track of 
public class Env {
	private static float minPenalty = Float.MAX_VALUE;
	private static float minfilledWeight;
	private static float prefWeight;
	private static float pairWeight;
	private static float secdiffWeight;

	private static float pen_coursemin;
	private static float pen_labsmin;
	private static float pen_notpaired;
	private static float pen_section;

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

	public static float getPenCoursemin() {
		return pen_coursemin;
	}

	public static float getPenLabsmin() {
		return pen_labsmin;
	}

	public static float getPenNotpaired() {
		return pen_notpaired;
	}

	public static float getPenSection() {
		return pen_section;
	}

	public static void setPenCoursemin(float new_value) {
		pen_coursemin = new_value;
	}

	public static void setPenlabsmin(float new_value) {
		pen_labsmin = new_value;
	}

	public static void setPenNotpaired(float new_value) {
		pen_notpaired = new_value;
	}

	public static void setPenSection(float new_value) {
		pen_section = new_value;
	}

}