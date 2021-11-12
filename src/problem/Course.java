package problem;

public class Course extends Element {

    int number;
    int section;

    public Course(String department, int number, int section) {
        super(department, 0);
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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Course))
            return false;
        Course other = (Course) o;
        return other.number == this.number 
            && other.section == this.section 
            && other.department == this.department;
    }

}
