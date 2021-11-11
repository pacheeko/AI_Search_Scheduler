package main.control;

import main.TreeNode;

public class Ftrans {

	public void selectTransition(TreeNode node) {
		// Performs a transition on the given node
		
		// If no more courses or labs to assign, all hard constraints are fulfilled, 
		// and the eval(node) is lower than the minPenalty, then set sol = yes and set minPenalty = eval(node)
		
		// If the node meets a solution condition, set sol = yes
		
		// If There is a division possible for the node, create children for the node and assign them
		// using node.setChildren()
		
		// Otherwise, set sol = yes
	}
}
