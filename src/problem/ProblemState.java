package problem;

public class ProblemState {

	private Problem problem;
	private Boolean sol = null;
	private int eval;
	
	public ProblemState(Problem problem) {
		this.problem = problem;
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
