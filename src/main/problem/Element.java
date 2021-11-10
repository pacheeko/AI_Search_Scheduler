package main.problem;

public abstract class Element implements Comparable<Element>{   
    String department;    

    public Element(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}
