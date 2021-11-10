package main.problem;

public class Lab extends Element {
    
    Course course;
    int number;
    boolean openToAllSections;

    public Lab(Course course, int number, boolean openToAllSections) {
        super(course.getDepartment());
        this.course = course;
        this.openToAllSections = openToAllSections;
    }

    @Override
    public int compareTo(Element o) {
        // TODO Auto-generated method stub
        return 0;
    }

}