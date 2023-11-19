package problems;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import file.FileManager;
import users.RANK;

public class ProblemRankManager {
	
	// Key = 문제 번호, Value = Rank 리스트
	private static HashMap<Integer, List<ProblemRank>> ProblemRankMap = new HashMap<>();
	
	// 디버깅
	public static void PrintProblemRankMap() {
	    for (Map.Entry<Integer, List<ProblemRank>> entry : ProblemRankMap.entrySet()) {
	        Integer key = entry.getKey();
	        List<ProblemRank> ranks = entry.getValue();

	        System.out.println("Key: " + key);
	        for (ProblemRank rank : ranks) {
	            System.out.println("  " + rank.toString());
	        }
	        System.out.println();
	    }
	}
	
	/*
	 *  사용자 기여를 해시맵에 저장하는 함수
	 *  DB에서 사용자 기여 정보를 가져올 때 사용
	 */
	public static boolean addRank(int ID, ProblemRank problemRank) {
	    if (!problemRank.isValid()) {
	        return false;
	    } else {
	        List<ProblemRank> problemRanks = ProblemRankMap.getOrDefault(ID, new ArrayList<>());
	        problemRanks.add(problemRank);
	        ProblemRankMap.put(ID, problemRanks);
	        return true;
	    }
	}
	
	/* 
	 *  사용자 기여를 추가하는 함수
	 *  새롭게 추가된 사용자 기여 정보를 해시맵에 먼저 저장 후 DB에 저장
	 */
	public static boolean createRank(ProblemRank problemRank) {
	    if (!problemRank.isValid()) {
	        return false;
	    } else {
	        int id = problemRank.getID();
	        addRank(id, problemRank);
			String filename = Integer.toString(id);
			String filepath = String.format("\\problems\\ProblemRankDB\\%s.txt", filename);
	        FileManager.createUpdateObjectFile(ProblemRankMap.get(id), filepath);
	        return true;
	    }
	}
	
	/*
	 *  문제에 대한 사용자 기여 정보를 얻는 함수
	 */
	public static List<ProblemRank> getComment(int ID){
		if(ProblemRankMap.get(ID) == null) {
			return null;
		}
		else {
			return ProblemRankMap.get(ID);
		}
	}
	
	// 난이도를 계산하여 난이도 재설정하는 함수
	public static boolean calcProblemRank(int ID) {
		List<ProblemRank> RankList = ProblemRankMap.get(ID);	
		
		if (RankList.size() % 10 == 0) {
			Problem plbm = ProblemDBManager.findProblem(ID);
			int sum = 0;
			int point = 0;
		    
			for(int i = 0; i < RankList.size(); i++) {
		    	sum += RankList.get(i).getRANK().getRequireRankPoint();
		    	point += RankList.get(i).getRankPoint();
		    }
		    
		    int value = sum / RankList.size();
	        int remainder = value % 100;
	        int adjustment = remainder < 50 ? 0 : 100;
	        int avg = (value / 100) * 100 + adjustment;
	        
	        int pointavg = point / RankList.size();
		    
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
		    	plbm.setProblemRank(RANK.RANK2);
		    }
		    else {
		    	plbm.setProblemRank(RANK.RANK1);
		    }
	        
		    plbm.setProblemRankPoint(pointavg);
		    ProblemDBManager.changeProblem(ID, plbm);
		    ProblemDBManager.addProblem(ID, plbm);
		    return true;
		}
		else {
			return false;
		}	
	}
	
	public static void init() {
		String dirpath = String.format("\\problems\\ProblemRankDB"); // 경로 지정
		// 해당 폴더에 저장된 모든 파일을 Object로 변환하여 ArrayList<Object>로 변환 
		ArrayList<Object> objList = FileManager.readAllObjectFileInDirectory(dirpath);
		try {
			for (Object obj : objList) {    
				if (obj instanceof List<?>) {
					@SuppressWarnings("unchecked")
				    List<ProblemRank> prm = (List<ProblemRank>) obj;
					ProblemRankMap.put(prm.get(0).getID(), prm);
				} 
				else {
	                throw new ClassCastException("ProblemRankManager 인스턴스로 변환할 수 없습니다.");
	            }
	        }
		} catch (ClassCastException e) {
			e.printStackTrace();			
		}
	}
}
