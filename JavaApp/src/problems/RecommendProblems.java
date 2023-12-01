package problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import users.*;

//모든 문제 리스트만 가져오면 해결
public class RecommendProblems {
	// 사용자가 선호하는 알고리즘별로 문제를 추천하는 코드(정렬X, 사용자가 해결한 문제는 하지 않는다.)-- 오버로딩
	public static List<Problem> recommendProblemsByAlgorithm(User user) {
	    List<Problem> recommendedProblems = new ArrayList<>();

	    // 사용자가 선호하는 알고리즘 타입 가져오기
	    HashSet<String> preferredAlgorithms = user.getPreferredAlgorithmTypeSet();
	    List<Problem> recommendedProblemList = ProblemDBManager.findProblemAlgorithm(preferredAlgorithms);
	   
	    // 사용자가 이미 해결한 문제는 추천에서 제외
	    HashSet<Problem> solvedUserProblems = user.getSolvedProblemSet();
	    for (Problem problem : recommendedProblemList) {
	        if (!solvedUserProblems.contains(problem)) {
	            recommendedProblems.add(problem);
	        }
	    }

	    return recommendedProblems;
	}

    // 사용자의 랭크별로 문제를 추천하는 코드(정렬X, 사용자가 해결한 문제는 하지 않는다.)-- 오버로딩
    public static List<Problem> recommendProblemsByRank(User user) {
        List<Problem> recommendedProblems = new ArrayList<>();

        // 사용자의 현재 랭크
        RANK userRank = user.getRank();
        HashSet<Problem> solvedUserProblems = user.getSolvedProblemSet();

        // 랭크에 해당하는 문제 추천
        List<Problem> rankProblems = ProblemDBManager.findProblemRank(userRank);

        // 사용자가 이미 해결한 문제는 추천에서 제외
        for (Problem problem : rankProblems) {
            if (!solvedUserProblems.contains(problem)) {
                recommendedProblems.add(problem);
            }
        }

        return recommendedProblems;
    }

}