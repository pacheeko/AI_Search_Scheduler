package problem;

public class Lab extends Element {
    
    Course course;
    int number;
    String name;
    boolean openToAllSections;

    public Lab(Course course, int number, boolean openToAllSections) {
        super(course.getDepartment(), 1);
        this.course = (Course) course.clone();
        this.openToAllSections = openToAllSections;
        name = course.getName() + " TUT " + number;
        this.number = number;
        super.setName(name);
    }

    public void setCourse(Course course) {
        this.course = course.clone();
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
            && other.number == this.number
            && other.name == this.name
            && other.openToAllSections == this.openToAllSections;
    }


    @Override
    public int hashCode() {
        int result = (int) (number ^ (number >>> 32));
        result = 31 * result + Boolean.hashCode(openToAllSections);
        result = 31 * result + name.hashCode();
        result = 31 * result + course.hashCode();
        return result;
    }

    @Override
    protected Lab clone() {
        Lab clone = (Lab) super.clone();   
        clone.setCourse(this.course.clone());
        return clone;
    }
}