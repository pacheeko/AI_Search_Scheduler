package problem;

import main.Constr;
import main.Env;
import main.Eval;

public class ProblemState {

	private Problem problem;
	private float eval;
	// private ArrayList<ProblemState> children;
	// private ProblemState parent;
	private Constr myConstr = new Constr();
	private Eval myEval = new Eval();
	private float parent_eval;

	public ProblemState(Problem problem, ProblemState parent) {
		this.problem = problem;		
		// this.parent = parent;
		// this.children = new ArrayList<ProblemState>();
		if (parent == null)
			return;
		this.parent_eval = parent.getEval();
		this.myConstr = new Constr(parent.getConstr());
		// parent.addChild(this);
	}

	// public void addChild(ProblemState child) {
	// 	this.children.add(child);
	// }

	// public boolean isLeaf() {
	// 	if (children.isEmpty()) {
	// 		return true;
	// 	} else {
	// 		return false;
	// 	}
	// }

	// public boolean isRGoot() {
	// if (parent == null) {
	// return true;
	// }
	// else {
	// return false;
	// }
	// }

	// Returns true if this solution is the best solution
	public boolean isBestSolution() {
		eval = myEval.evaluate(problem.getAssignments(), eval);
		if (eval < Env.getMinPenalty()) {
			Env.setMinPenalty(eval);
			return true;
		}
		return false;
	}

	// Returns true if the leaf should be discarded and not looked further into
	public boolean discardLeaf() {
		if (!myConstr.checkConstraints(problem.getAssignments()) || (eval > Env.getMinPenalty())) {
			return true;
		}
		return false;
	}

	// public ArrayList<ProblemState> getChildren() {
	// 	return children;
	// }

	// public ProblemState getParent() {
	// return parent;
	// }

	// public void setParent(ProblemState parent) {
	// this.parent = parent;
	// }

	// public void setChildren(ArrayList<ProblemState> children) {
	// 	this.children = children;
	// }

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public float getParentEval() {
		return this.parent_eval;
	}

	public void setParentEval(int parent_eval) {
		this.parent_eval = parent_eval;
	}

	public float getEval() {
		return eval;
	}

	public void setEval(float eval) {
		this.eval = eval;
	}

	public Constr getConstr() {
		return myConstr;
	}

	public Assignment getMostRecentAssignment() {
		if (!this.getProblem().assignments.isEmpty()) {
			return this.getProblem().getAssignments().get(this.getProblem().getAssignments().size() - 1);
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProblemState))
			return false;
		ProblemState state = (ProblemState) obj;
		return state.getProblem().equals(this.getProblem());
	}

	@Override
	public int hashCode() {
		return this.problem.hashCode();
	}

}
