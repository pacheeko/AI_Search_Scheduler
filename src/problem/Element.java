package problem;

public abstract class Element {   
    String department;    

    public Element(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}
