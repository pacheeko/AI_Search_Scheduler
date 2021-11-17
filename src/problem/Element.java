package problem;

public abstract class Element {
    String department;
    //Course = 0, Lab = 1
    private int type;
    private String name;

    public Element(String department, int type) {
        this.department = department;
        this.type = type;
    }

    public String getDepartment() {
        return department;
    }

    public int getType(){
        return type;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
