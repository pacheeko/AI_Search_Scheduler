package control;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import main.Eval;
import model.SearchModel;
import problem.Assignment;
import problem.Course;
import problem.Lab;
import problem.Problem;
import problem.ProblemState;
import problem.Slot;

class LeafComparator implements Comparator<ProblemState> {

    @Override
    public int compare(ProblemState node1, ProblemState node2) {
        if (node1.equals(node2))
            return 0;

        int result = Integer.compare(node1.getProblem().getElements().size(),
                        node2.getProblem().getElements().size());
        if (result == 0)
            result = Float.compare(node1.getEval(), node2.getEval());

        if (result == 0)
            result = Integer.compare(node1.hashCode(), node2.hashCode());
        return result;
    }

}

public class Control {

    ProblemState root;
    TreeSet<ProblemState> leafs;
    ProblemState current_leaf;
    LeafComparator leaf_comparer;
    ArrayList<Slot> slots;
    Eval eval = new Eval();
    boolean DEBUG = false;

    public Control(ProblemState root, ArrayList<Slot> slots) {
        this.slots = slots;
        this.root = root;
        this.leaf_comparer = new LeafComparator();
        this.leafs = new TreeSet<ProblemState>(this.leaf_comparer);
        this.leafs.add(root);
        this.current_leaf = null;
    }

    public void fleaf() {
        if (leafs.isEmpty())
            return;
        current_leaf = leafs.pollFirst(); // Pop the first leaf
    }

    public void ftrans() {
        // Testing, print current assignment
        if (DEBUG && current_leaf.getProblem().getAssignments().size() > 0) {
            Assignment currAssign = current_leaf.getProblem().getAssignments()
                    .get(current_leaf.getProblem().getAssignments().size() - 1);
            // Testing, print current slot
            System.out.println("curr slot: " + currAssign.getSlot().getInfo());
            if (currAssign.getElement() instanceof Course) {
                Course course = (Course) currAssign.getElement();
                System.out.println("curr course: " + course.getDepartment() + " " + course.getNumber() + " "
                        + course.getSection());
            } else {
                Lab lab = (Lab) currAssign.getElement();
                System.out.println(
                        "curr lab: " + lab.getDepartment() + " " + lab.getCourse().getNumber() + " " + lab.getNumber());
            }
        }

        // Check if the current leaf should be discarded, make current_leaf null if so
        if (current_leaf.discardLeaf()) {
            current_leaf = null;
            return;
        }

        if (current_leaf.getProblem().getElements().isEmpty()) {
            if (current_leaf.isBestSolution()) {
                return;
            } else {
                current_leaf = null;
                return;
            }
        }

        ArrayList<Problem> subProblems = SearchModel.Div(current_leaf.getProblem(), slots);
        // If the node cannot be divided into more leaves, discard it
        if (subProblems.isEmpty()) {
            current_leaf = null;
            return;
        }


        for (Problem subProblem : subProblems) {
            ProblemState new_leaf = new ProblemState(subProblem, current_leaf);
            new_leaf.setEval(
                    eval.partialEvaluate(new_leaf.getProblem().getAssignments(), new_leaf.getParentEval()));
            leafs.add(new_leaf);
        }
        current_leaf = null;        
        return;
    }

    public ProblemState getSelectedLeaf() {
        return current_leaf;
    }

    public ProblemState getRoot() {
        return root;
    }

    public TreeSet<ProblemState> getLeafs() {
        return leafs;
    }

}