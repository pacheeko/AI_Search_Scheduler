package problem;

import java.util.ArrayList;

import main.Constr;
import main.Env;
import main.Eval;

public class ProblemState {

	private Problem problem;
	private Boolean sol = false;
	private int eval;
	private ArrayList<ProblemState> children;
	private ProblemState parent;
    private Constr myConstr = new Constr();
    private Eval myEval = new Eval();
	
	public ProblemState(Problem problem, ProblemState parent) {
		this.problem = problem;
		this.parent = parent;
		this.children = new ArrayList<ProblemState>();
		if (parent == null) return;
		this.myConstr = parent.getConstr();
		parent.addChild(this);
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
	
	//Returns true if this solution is the best solution
    public boolean isBestSolution() {
        eval = myEval.evaluate(problem.getAssignments(), eval);
        if (eval < Env.getMinPenalty()) {
        	Env.setMinPenalty(eval);
        	return true;
        }
        return false;
    }
	
    //Returns true if the leaf should be discarded and not looked further into
    public boolean discardLeaf() {
    	if (!myConstr.checkConstraints(problem.getAssignments()) || (eval > Env.getMinPenalty())) {
    		return true;
    	}
    	return false;
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
	
	public Constr getConstr() {
		return myConstr;
	}
	
	public Assignment getMostRecentAssignment() {
		if (!this.getProblem().assignments.isEmpty()) {
			return this.getProblem().getAssignments().get(this.getProblem().getAssignments().size()-1);
		}
		return null;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof ProblemState)) {
			return false;
		}
		ProblemState state = (ProblemState) obj;
		if (this.getProblem().getAssignments().size() == state.getProblem().getAssignments().size()) {
			if (this.getProblem().getElements().size() == state.getProblem().getElements().size()) {
				for (Assignment assign : this.getProblem().getAssignments()) {
					if (!state.getProblem().getAssignments().contains(assign)) {
						return false;
					}
				}
				for (Element element : this.getProblem().getElements()) {
					if (!state.getProblem().getElements().contains(element)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
}
