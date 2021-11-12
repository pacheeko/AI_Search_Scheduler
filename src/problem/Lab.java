package problem;

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Lab))
            return false;
        Lab other = (Lab) o;
        return this.course.equals(other.course)
            && other.number == this.number;            
    }

}