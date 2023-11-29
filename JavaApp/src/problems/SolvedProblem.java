package problems;

import java.io.Serializable;

public class SolvedProblem extends Problem implements Serializable {

    private boolean Solved;
    private static final long serialVersionUID = 1L;
    
    public SolvedProblem(Problem problem) {
//        super(problem);
        this.Solved = false;
    }

    public void setSolved(boolean solved) {
        this.Solved = solved;
    }

    public boolean isSolved() {
        return Solved;
    }
}
