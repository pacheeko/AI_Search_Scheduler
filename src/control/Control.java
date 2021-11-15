package control;

import java.util.ArrayList;
import java.util.Comparator;
import main.TreeNode;
import model.SearchModel;
import problem.Problem;
import problem.ProblemState;
import problem.Slot;

class LeafComparator implements Comparator<TreeNode> {

    @Override
    public int compare(TreeNode node1, TreeNode node2) {
        if (!node1.getChildren().isEmpty() && !node2.getChildren().isEmpty())
            return 0;
        if (!node1.getChildren().isEmpty())
            return 1;
        if (!node2.getChildren().isEmpty())
            return -1;
        
        ProblemState problem1 = node1.getState();
        ProblemState problem2 = node2.getState();        

        return Integer.compare(problem1.getEval(), problem2.getEval());        
    }

}


public class Control {

    TreeNode root;
    ArrayList<TreeNode> leafs;
    TreeNode current_leaf;
    LeafComparator leaf_comparer;
    ArrayList<Slot> slots;

    public Control(TreeNode root, ArrayList<Slot> slots) {
        this.slots = slots;
        this.root = root;
        this.leafs = new ArrayList<TreeNode>();
        this.leafs.add(root);
        this.current_leaf = leafs.get(0);
        this.leaf_comparer = new LeafComparator();
    }

    public void fleaf() {
        if (leafs.isEmpty())
            return;
        current_leaf = leafs.get(0);
    }

    public TreeNode ftrans() {
        ProblemState current_state = current_leaf.getState();
        if (current_state.getSol())
            return null;

        Problem current_problem = current_state.getProblem();
        if (current_problem.isSolved()) {
            ProblemState new_state = new ProblemState(current_problem);
            new_state.setSol(true);
            TreeNode new_leaf = new TreeNode(new_state, current_leaf);
            current_leaf.addChild(new_leaf);
            leafs.remove(current_leaf);
            leafs.add(new_leaf);
            leafs.sort(leaf_comparer);
            current_leaf = null;
            return new_leaf;
        }


        ArrayList<Problem> subProblems = SearchModel.Div(current_problem, slots);
        if (subProblems.isEmpty()) {
            return null;
        }

        for(Problem subProblem : subProblems) {
            ProblemState new_state = new ProblemState(subProblem);
            TreeNode new_leaf = new TreeNode(new_state, current_leaf);
            current_leaf.addChild(new_leaf);
            leafs.add(new_leaf);
        }

        leafs.remove(current_leaf);
        leafs.sort(leaf_comparer);
        return null;
    }

}