package problems;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import file.FileManager;
import users.RANK;

public class ProblemRankManager {
	
	// Key = ���� ��ȣ, Value = Rank ����Ʈ
	private static HashMap<Integer, List<ProblemRank>> ProblemRankMap = new HashMap<>();
	
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
	
	public static List<ProblemRank> getComment(int ID){
		if(ProblemRankMap.get(ID) == null) {
			return null;
		}
		else {
			return ProblemRankMap.get(ID);
		}
	}
	
	// ���̵��� ����Ͽ� ���̵� �缳���ϴ� �Լ�
	public static boolean calcProblemRank(int ID) {
		List<ProblemRank> RankList = ProblemRankMap.get(ID);	
		
		if (RankList.size() % 10 == 0) {
			Problem plbm = ProblemDBManager.findProblem(ID);
			int sum = 0;
		    
			for(int i = 0; i < RankList.size(); i++) {
		    	sum += RankList.get(i).getRANK().getRequireRankPoint();
		    }
		    
		    int value = sum / RankList.size();
	        int remainder = value % 100;
	        int adjustment = remainder < 50 ? 0 : 100;
	        int avg = (value / 100) * 100 + adjustment;
		    
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
		    ProblemDBManager.changeProblem(ID, plbm);
		    ProblemDBManager.addProblem(ID, plbm);
		    return true;
		}
		else {
			return false;
		}	
	}
	
	public static void init() {
		String dirpath = String.format("\\problems\\ProblemRankDB"); // ��� ����
		// �ش� ������ ����� ��� ������ Object�� ��ȯ�Ͽ� ArrayList<Object>�� ��ȯ 
		ArrayList<Object> objList = FileManager.readAllObjectFileInDirectory(dirpath);
		try {
			for (Object obj : objList) {    
				if (obj instanceof List<?>) {
					@SuppressWarnings("unchecked")
				    List<ProblemRank> prm = (List<ProblemRank>) obj;
					ProblemRankMap.put(prm.get(0).getID(), prm);
				} 
				else {
	                throw new ClassCastException("ProblemRankManager �ν��Ͻ��� ��ȯ�� �� �����ϴ�.");
	            }
	        }
		} catch (ClassCastException e) {
			e.printStackTrace();			
		}
	}
}