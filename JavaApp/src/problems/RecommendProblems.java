package problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import users.*;

//��� ���� ����Ʈ�� �������� �ذ�
public class RecommendProblems {
//	// ����ڰ� ��ȣ�ϴ� �˰��򺰷� ������ ��õ�ϴ� �ڵ�(����X, ����ڰ� �ذ��� ������ ���� �ʴ´�.)-- �����ε�
//    public static List<Problem> recommendProblemsByAlgorithm(User user) {
//        List<Problem> recommendedProblems = new ArrayList<>();
//
//        // ����ڰ� ��ȣ�ϴ� �˰��� Ÿ�� ��������
//        HashSet<String> preferredAlgorithms = user.getPreferredAlgorithmTypeSet();
//
//        // �� �˰��� �ش��ϴ� ���� ��õ
//        for (String algorithm : preferredAlgorithms) {
//            List<Problem> algorithmProblems = findProblemsByAlgorithm(algorithm);
//
//            // ����ڰ� �̹� �ذ��� ������ ��õ���� ����
////            algorithmProblems.removeAll(user.getSolvedProblemList());
//
//            // ��õ ���� ����Ʈ�� �߰�
//            recommendedProblems.addAll(algorithmProblems);
//        }
//
//        return recommendedProblems;
//    }
//
//    // ������� ��ũ���� ������ ��õ�ϴ� �ڵ�(����X, ����ڰ� �ذ��� ������ ���� �ʴ´�.)-- �����ε�
//    public static List<Problem> recommendProblemsByRank(User user) {
//        List<Problem> recommendedProblems = new ArrayList<>();
//
//        // ������� ���� ��ũ
//        RANK userRank = user.getRank();
//
//        // ��ũ�� �ش��ϴ� ���� ��õ
//        List<Problem> rankProblems = findProblemsByRank(userRank);
//
//        // ����ڰ� �̹� �ذ��� ������ ��õ���� ����
////        rankProblems.removeAll(user.getSolvedProblemList());
//
//        // ��õ ���� ����Ʈ�� �߰�
//        recommendedProblems.addAll(rankProblems);
//
//        return recommendedProblems;
//    }
//
//    //��� ��������Ʈ���� ��ȣ�ϴ� �˰��� ����Ʈ�� ����
//    private static List<Problem> findProblemsByAlgorithm(String algorithm) {
//        
//        return ProblemDBManager.findProblemByAlgorithm(algorithm);
//    }
//
//    //��� ���� ����Ʈ���� Rank�� ���� ���� ����Ʈ�� ����
//    private static List<Problem> findProblemsByRank(RANK rank) {
//        
//        return ProblemDBManager.findProblemByRank(rank);
//    }
}