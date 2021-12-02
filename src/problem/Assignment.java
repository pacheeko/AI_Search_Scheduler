package problem;

public class Assignment {
    Element element;
    Slot slot;

    public Assignment(Element element, Slot slot) {
        this.element = element;
        this.slot = slot;
    }

    public Assignment(Assignment assignment) {
        this.element = assignment.element.clone();
        this.slot = assignment.slot.clone();
    }

    /** 
     * 
     * @param o Object to compare with
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;
        Assignment other = (Assignment) o;
        return this.element.equals(other.element)
         && this.slot.equals(other.getSlot());
    }

    @Override
    public int hashCode() {    
        int result = slot.hashCode();
        result = 31 * result + element.hashCode();
        return result;
    }
    
    public Element getElement() {
        return element;
    }

    public Slot getSlot() {
        return slot;
    }
}