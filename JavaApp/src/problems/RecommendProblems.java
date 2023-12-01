package problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import users.*;

//��� ���� ����Ʈ�� �������� �ذ�
public class RecommendProblems {
	// ����ڰ� ��ȣ�ϴ� �˰��򺰷� ������ ��õ�ϴ� �ڵ�(����X, ����ڰ� �ذ��� ������ ���� �ʴ´�.)-- �����ε�
	public static List<Problem> recommendProblemsByAlgorithm(User user) {
	    List<Problem> recommendedProblems = new ArrayList<>();

	    // ����ڰ� ��ȣ�ϴ� �˰��� Ÿ�� ��������
	    HashSet<String> preferredAlgorithms = user.getPreferredAlgorithmTypeSet();
	    List<Problem> recommendedProblemList = ProblemDBManager.findProblemAlgorithm(preferredAlgorithms);
	   
	    // ����ڰ� �̹� �ذ��� ������ ��õ���� ����
	    HashSet<Problem> solvedUserProblems = user.getSolvedProblemSet();
	    for (Problem problem : recommendedProblemList) {
	        if (!solvedUserProblems.contains(problem)) {
	            recommendedProblems.add(problem);
	        }
	    }

	    return recommendedProblems;
	}

    // ������� ��ũ���� ������ ��õ�ϴ� �ڵ�(����X, ����ڰ� �ذ��� ������ ���� �ʴ´�.)-- �����ε�
    public static List<Problem> recommendProblemsByRank(User user) {
        List<Problem> recommendedProblems = new ArrayList<>();

        // ������� ���� ��ũ
        RANK userRank = user.getRank();
        HashSet<Problem> solvedUserProblems = user.getSolvedProblemSet();

        // ��ũ�� �ش��ϴ� ���� ��õ
        List<Problem> rankProblems = ProblemDBManager.findProblemRank(userRank);

        // ����ڰ� �̹� �ذ��� ������ ��õ���� ����
        for (Problem problem : rankProblems) {
            if (!solvedUserProblems.contains(problem)) {
                recommendedProblems.add(problem);
            }
        }

        return recommendedProblems;
    }

}