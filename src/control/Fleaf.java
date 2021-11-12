package control;

import main.TreeNode;

import java.util.ArrayList;


public class Fleaf {


	public TreeNode selectLeaf(ArrayList<TreeNode> leaves) {
		// Takes an arrayList of treeNode leaves and decides which leaf to perform the next transition on
		// based on which leaf has the lowest current evaluation (leaf.getProblemState().getEval())
		return leaves.get(0);
	}
	
	public ArrayList<TreeNode> getLeaves(TreeNode node) {
		// Takes a single node of the tree and returns all leaves of the tree. Use this function to get all
		// the leaves of the tree, and then pass all leaves into the selectLeaf function to choose a leaf
		//TODO: can also keep track of the leaves in a variable in each node instead of recursing each time
		return node.getChildren();
	}
}
