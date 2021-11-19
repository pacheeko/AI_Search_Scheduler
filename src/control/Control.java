package control;

import java.util.ArrayList;
import java.util.Comparator;
import model.SearchModel;
import problem.Problem;
import problem.ProblemState;
import problem.Slot;

class LeafComparator implements Comparator<ProblemState> {

    @Override
    public int compare(ProblemState node1, ProblemState node2) {
        if (!node1.getChildren().isEmpty() && !node2.getChildren().isEmpty())
            return 0;
        if (!node1.getChildren().isEmpty())
            return 1;
        if (!node2.getChildren().isEmpty())
            return -1;   

        return Integer.compare(node1.getEval(), node2.getEval());        
    }

}

public class Control {

    ProblemState root;
    ArrayList<ProblemState> leafs;
    ProblemState current_leaf;
    LeafComparator leaf_comparer;
    ArrayList<Slot> slots;

    public Control(ProblemState root, ArrayList<Slot> slots) {
        this.slots = slots;
        this.root = root;
        this.leafs = new ArrayList<ProblemState>();
        this.leafs.add(root);
        this.current_leaf = leafs.get(0);
        this.leaf_comparer = new LeafComparator();
    }

    public void next() {
        fleaf();
        if (current_leaf == null) return;
        ftrans();        
    }

    public void fleaf() {
        if (leafs.isEmpty())
            return;
        current_leaf = leafs.get(0);
    }

    public void ftrans() {
        ProblemState current_state = leafs.get(0);
        if (current_state.getSol())
            return;

        Problem current_problem = current_state.getProblem();
        if (current_problem.isSolved()) {
            ProblemState new_leaf = new ProblemState(current_problem, current_leaf);
            new_leaf.setSol(true);
            leafs.remove(current_leaf);
            leafs.add(new_leaf);
            leafs.sort(leaf_comparer);
            current_leaf = null;
            return;
        }


        ArrayList<Problem> subProblems = SearchModel.Div(current_problem, slots);
        if (subProblems.isEmpty()) {
            return;
        }

        for(Problem subProblem : subProblems) {
            ProblemState new_leaf = new ProblemState(subProblem, current_leaf);
            current_leaf.addChild(new_leaf);
            leafs.add(new_leaf);
        }

        leafs.remove(current_leaf);
        leafs.sort(leaf_comparer);
        return;
    }

    public ProblemState getSelectedLeaf() {
        return current_leaf;
    }

    public ProblemState getRoot() {
        return root;
    }

    public ArrayList<ProblemState> getLeafs() {
        return leafs;
    }

}