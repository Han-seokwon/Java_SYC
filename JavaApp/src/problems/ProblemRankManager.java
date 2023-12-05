package problems;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import file.FileManager;
import users.RANK;

public class ProblemRankManager {
	
	// Key = 문제 번호, Value = ProblemRank 리스트
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
	 *  ProblemRank를 ProblemRankMap 해시맵에 저장하는 함수
	 *  ProblemRankDB 폴더에서 ProblemRank를 가져올 때 사용
	 *  ID : 문제 번호, problemRank : ProblemRank 객체 변수
	 */
	public static boolean addRank(int ID, ProblemRank problemRank) {
	    if (!problemRank.isValid()) {
	        return false;
	    } else {
	    	/*
	    	 *  getOrDefault
	    	 *  키 값에 해당하는 값이 없으면 새롭게 리스트 선언
	    	 *  해당하는 값이 있으면 해당하는 리스트 반환
	    	 */
	        List<ProblemRank> problemRanks = ProblemRankMap.getOrDefault(ID, new ArrayList<>());
	        problemRanks.add(problemRank);
	        ProblemRankMap.put(ID, problemRanks);
	        return true;
	    }
	}
	
	/* 
	 *  사용자 기여정보를 .txt로 변환하여 ProblemRankDB 폴더에 저장 
	 *  이후, ProblemRankMap 해시맵에 추가하는 함수
	 *  problemRank : ProblemRank 객체 변수
	 *  새롭게 생성된 ProblemRank 객체 변수를 ProblemRankDB 폴더에 추가할 때 사용한다.
	 *  이후, 문제 난이도를 재평가한다. 
	 */
	public static boolean createRank(ProblemRank problemRank) {
	    if (!problemRank.isValid()) {
	        return false;
	    } else {
	        int id = problemRank.getID();      // 문제 번호 추출
	        addRank(id, problemRank);          // 해시맵에 추가
			String filename = Integer.toString(id);
			String filepath = String.format("\\problems\\ProblemRankDB\\%s.txt", filename);
	        FileManager.createUpdateObjectFile(ProblemRankMap.get(id), filepath);     // .txt 파일 저장
	        calcProblemRank(id);
	        return true;
	    }
	}
	
	/*
	 *  문제에 대한 사용자 기여 정보를 얻는 함수
	 *  ID : 문제 번호
	 */
	public static List<ProblemRank> getComment(int ID){
		return ProblemRankMap.get(ID);
	}
	
	/*
	 *  문제 난이도를 재평가하는 함수
	 *  ID : 문제 번호
	 *  해당 문제의 기여 수가 10개 단위가 되면 문제 난이도가 재평가
	 *  문제 랭크 및 문제 랭크 포인트가 사용자 기여의 평균값으로 재평가가 됨
	 */
	public static boolean calcProblemRank(int ID) {
		List<ProblemRank> RankList = ProblemRankMap.get(ID);	
		
		if (RankList.size() % 10 == 0) {
			// ProblemDBManager에서 번호에 해당하는 문제를 가져옴
			Problem plbm = ProblemDBManager.findProblem(ID);
			// 문제 랭크 및 문제 랭크 포인트 계산
			int point = 0;
		    
			for(int i = 0; i < RankList.size(); i++) {
		    	point += RankList.get(i).getRankPoint();
		    }
	        int pointavg = point / RankList.size();
		    
		    if (pointavg <= 100) {
		    	plbm.setProblemRank(RANK.RANK1);
		    }
		    else if(pointavg <= 200) {
		    	plbm.setProblemRank(RANK.RANK2);
		    }
		    else if(pointavg <= 300) {
		    	plbm.setProblemRank(RANK.RANK3);
		    }
		    else if(pointavg <= 400) {
		    	plbm.setProblemRank(RANK.RANK4);
		    }
		    else {
		    	plbm.setProblemRank(RANK.RANK5);
		    }
	        
		    /*
		     * setProblemRankPoint를 이용하여 해당 문제의 난이도를 재설정
		     * 이후. ProblemDBManager에서 해당 문제를 최신화
		     */
		    plbm.setProblemRankPoint(pointavg);
		    ProblemDBManager.changeProblem(ID, plbm);
		    ProblemDBManager.addProblem(ID, plbm);
		    return true;
		}
		else {
			return false;
		}	
	}
	
	// ProblemRankDB에 저장된 문제들을 불러 ProblemRankMap 해시맵에 추가하는 함수
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