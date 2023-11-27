package problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import users.*;

//모든 문제 리스트만 가져오면 해결
public class RecommendProblems {
//	// 사용자가 선호하는 알고리즘별로 문제를 추천하는 코드(정렬X, 사용자가 해결한 문제는 하지 않는다.)-- 오버로딩
//    public static List<Problem> recommendProblemsByAlgorithm(User user) {
//        List<Problem> recommendedProblems = new ArrayList<>();
//
//        // 사용자가 선호하는 알고리즘 타입 가져오기
//        HashSet<String> preferredAlgorithms = user.getPreferredAlgorithmTypeSet();
//
//        // 각 알고리즘에 해당하는 문제 추천
//        for (String algorithm : preferredAlgorithms) {
//            List<Problem> algorithmProblems = findProblemsByAlgorithm(algorithm);
//
//            // 사용자가 이미 해결한 문제는 추천에서 제외
////            algorithmProblems.removeAll(user.getSolvedProblemList());
//
//            // 추천 문제 리스트에 추가
//            recommendedProblems.addAll(algorithmProblems);
//        }
//
//        return recommendedProblems;
//    }
//
//    // 사용자의 랭크별로 문제를 추천하는 코드(정렬X, 사용자가 해결한 문제는 하지 않는다.)-- 오버로딩
//    public static List<Problem> recommendProblemsByRank(User user) {
//        List<Problem> recommendedProblems = new ArrayList<>();
//
//        // 사용자의 현재 랭크
//        RANK userRank = user.getRank();
//
//        // 랭크에 해당하는 문제 추천
//        List<Problem> rankProblems = findProblemsByRank(userRank);
//
//        // 사용자가 이미 해결한 문제는 추천에서 제외
////        rankProblems.removeAll(user.getSolvedProblemList());
//
//        // 추천 문제 리스트에 추가
//        recommendedProblems.addAll(rankProblems);
//
//        return recommendedProblems;
//    }
//
//    //모든 문제리스트에서 선호하는 알고리즘 리스트만 저장
//    private static List<Problem> findProblemsByAlgorithm(String algorithm) {
//        
//        return ProblemDBManager.findProblemByAlgorithm(algorithm);
//    }
//
//    //모든 문제 리스트에서 Rank가 같은 문제 리스트만 저장
//    private static List<Problem> findProblemsByRank(RANK rank) {
//        
//        return ProblemDBManager.findProblemByRank(rank);
//    }
}