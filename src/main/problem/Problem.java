package main.problem;

public class Problem {
    Element[] elements;
    Assignment[] assignments; 

    private boolean isValid() {
        boolean valid = isAssignmentsValid() && isElementsValid();
        for (Assignment assignment : assignments) {

        }
        return valid;
    }

    private boolean isAssignmentsValid() {
        boolean valid = true;

        return valid;
    }

    private boolean isElementsValid() {
        boolean valid = true;

        return valid;
    }

}