package main.problem;

public class Assignment {
    Element element;
    Slot slot;

    public Assignment(Element element, Slot slot) {
        this.element = element;
        this.slot = slot;
    }

    // An assignment is equal if its elements are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;
        Assignment other = (Assignment) o;
        return this.element.equals(other.element);
    }
}