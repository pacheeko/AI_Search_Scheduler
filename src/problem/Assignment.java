package problem;

public class Assignment {
    Element element;
    Slot slot;

    public Assignment(Element element, Slot slot) {
        this.element = element;
        this.slot = slot;
    }


    
    /** 
     * 
     * @param o Object to compare with
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof Element) {
            Element other = (Element) o;
            return this.element.equals(other);
        }
        if (!(o instanceof Assignment)) return false;
        Assignment other = (Assignment) o;
        return this.element.equals(other.element);
    }

    public Element getElement() {
        return element;
    }

    public Slot getSlot() {
        return slot;
    }
}