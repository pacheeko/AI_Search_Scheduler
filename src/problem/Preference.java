package problem;

public class Preference extends Assignment {

	private int weight;
	
	public Preference(Element element, Slot slot, int weight) {
		super(element, slot);
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}

}
