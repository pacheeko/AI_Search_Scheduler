package problem;

import java.util.ArrayList;

public class ProblemState {

	private Problem problem;
	private Boolean sol = null;
	private int eval;
	private ArrayList<ProblemState> children;
	private ProblemState parent;
	
	public ProblemState(Problem problem, ProblemState parent) {
		this.problem = problem;
		this.parent = parent;
	}
	
	
	public void addChild(ProblemState child) {
		this.children.add(child);
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
	
	
	
	
	
	
	
	public ArrayList<ProblemState> getChildren() {
		return children;
	}
	
	public ProblemState getParent() {
        return parent;
    }
	
	public void setParent(ProblemState parent) {
        this.parent = parent;
    }

	public void setChildren(ArrayList<ProblemState> children) {
		this.children = children;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Boolean getSol() {
		return sol;
	}

	public void setSol(Boolean sol) {
		this.sol = sol;
	}

	public int getEval() {
		return eval;
	}

	public void setEval(int eval) {
		this.eval = eval;
	}
	
	
}
