package problems;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import users.RANK;

public class ProblemRankManager {
	
	// 공사중 : 유저 정보와 코멘트 저장 방법, ProblemRankDB에 저장할 방법 (11.10)
	
	// Key = 문제 번호, Value = Rank 리스트
	private static HashMap<Integer, List<RANK>> ProblemRankMap = new HashMap<>();
	
	// 난이도 기여를 추가하는 함수
	public static boolean AddProblemRank(int ID, RANK Rank) {
		if (ProblemRankMap.containsKey(ID)) {        
			List<RANK> RankList = ProblemRankMap.get(ID);
            RankList.add(Rank);
        } 
		else {                
            List<RANK> RankList = new ArrayList<>();
            RankList.add(Rank);
            ProblemRankMap.put(ID, RankList);
        }
		return true;
	}
	
	// 난이도를 계산하여 난이도 재설정하는 함수
	public static boolean CalcProblemRank(int ID) {
		Problem plbm = ProblemDBManager.FindProblem(ID);
		List<RANK> RankList = ProblemRankMap.get(ID);
		
		if (RankList.size() % 10 == 0) {
			int sum = 0;
		    for (RANK rank : RankList) {
		        sum += rank.getRequireRankPoint();
		    }
		    int avg = (Math.round((int)((sum / RankList.size()) * 100))) / 100;
		    
		    if (avg == 0) {
		    	plbm.setProblemRank(RANK.RANK5);
		    }
		    else if(avg == 100) {
		    	plbm.setProblemRank(RANK.RANK4);
		    }
		    else if(avg == 200) {
		    	plbm.setProblemRank(RANK.RANK3);
		    }
		    else if(avg == 300) {
		    	plbm.setProblemRank(RANK.RANK3);
		    }
		    else if(avg == 400) {
		    	plbm.setProblemRank(RANK.RANK2);
		    }
		    else {
		    	plbm.setProblemRank(RANK.RANK1);
		    }
		    ProblemDBManager.ChangeProblem(ID, plbm);
		    ProblemDBManager.AddProblem(ID, plbm);
		    return true;
		}
		else {
			return false;
		}	
	}
	
}