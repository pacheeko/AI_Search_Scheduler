package problem;

public class Lab extends Element {
    
    Course course;
    int number;
    String name;
    boolean openToAllSections;

    public Lab(Course course, int number, boolean openToAllSections) {
        super(course.getDepartment(), 1);
        this.course = course;
        this.openToAllSections = openToAllSections;
        name = course.getName() + " TUT " + number;
    }

    public Course getCourse(){
        return course;
    }

    public int getNumber(){
        return number;
    }

    public String getName(){
        return name;
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