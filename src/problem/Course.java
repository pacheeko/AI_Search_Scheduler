package problem;

public class Course extends Element {

    int number;
    int section;
    private String name;

    public Course(String department, int number, int section) {
        super(department, 0);
        this.number = number;
        this.section = section;
        name = department + " " + number + " " + section;
        super.setName(name);
    }

    public int getSection() {
        return section;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
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
