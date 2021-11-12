package main;

import problem.ProblemState;
import java.util.ArrayList;


public class TreeNode {

    TreeNode parent;
    ArrayList<TreeNode> children;
    ProblemState state;

    public TreeNode(ProblemState state, TreeNode parent) {
        this.parent = parent;
        this.state = state;
    }

    public TreeNode getParent() {
        return parent;
    }
    public ArrayList<TreeNode> getChildren() {
        return children;
    }
    public ProblemState getState() {
        return state;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public boolean isLeaf() {
        if (children.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isRoot() {
        if (parent == null) {
            return true;
        }
        else {
            return false;
        }
    }
}
