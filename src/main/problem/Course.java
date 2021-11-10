package main.problem;

public class Course extends Element {

    int number;
    int section;    

    public Course(String department, int number, int section) {
        super(department);
        this.number = number;
        this.section = section;
    }

    public int getSection() {
        return section;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(Element o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
