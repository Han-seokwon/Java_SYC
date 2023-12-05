package problems;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import file.FileManager;
import users.RANK;

public class ProblemRankManager {
	
	// Key = ���� ��ȣ, Value = ProblemRank ����Ʈ
	private static HashMap<Integer, List<ProblemRank>> ProblemRankMap = new HashMap<>();
	
	// �����
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
	 *  ProblemRank�� ProblemRankMap �ؽøʿ� �����ϴ� �Լ�
	 *  ProblemRankDB �������� ProblemRank�� ������ �� ���
	 *  ID : ���� ��ȣ, problemRank : ProblemRank ��ü ����
	 */
	public static boolean addRank(int ID, ProblemRank problemRank) {
	    if (!problemRank.isValid()) {
	        return false;
	    } else {
	    	/*
	    	 *  getOrDefault
	    	 *  Ű ���� �ش��ϴ� ���� ������ ���Ӱ� ����Ʈ ����
	    	 *  �ش��ϴ� ���� ������ �ش��ϴ� ����Ʈ ��ȯ
	    	 */
	        List<ProblemRank> problemRanks = ProblemRankMap.getOrDefault(ID, new ArrayList<>());
	        problemRanks.add(problemRank);
	        ProblemRankMap.put(ID, problemRanks);
	        return true;
	    }
	}
	
	/* 
	 *  ����� �⿩������ .txt�� ��ȯ�Ͽ� ProblemRankDB ������ ���� 
	 *  ����, ProblemRankMap �ؽøʿ� �߰��ϴ� �Լ�
	 *  problemRank : ProblemRank ��ü ����
	 *  ���Ӱ� ������ ProblemRank ��ü ������ ProblemRankDB ������ �߰��� �� ����Ѵ�.
	 *  ����, ���� ���̵��� �����Ѵ�. 
	 */
	public static boolean createRank(ProblemRank problemRank) {
	    if (!problemRank.isValid()) {
	        return false;
	    } else {
	        int id = problemRank.getID();      // ���� ��ȣ ����
	        addRank(id, problemRank);          // �ؽøʿ� �߰�
			String filename = Integer.toString(id);
			String filepath = String.format("\\problems\\ProblemRankDB\\%s.txt", filename);
	        FileManager.createUpdateObjectFile(ProblemRankMap.get(id), filepath);     // .txt ���� ����
	        calcProblemRank(id);
	        return true;
	    }
	}
	
	/*
	 *  ������ ���� ����� �⿩ ������ ��� �Լ�
	 *  ID : ���� ��ȣ
	 */
	public static List<ProblemRank> getComment(int ID){
		return ProblemRankMap.get(ID);
	}
	
	/*
	 *  ���� ���̵��� �����ϴ� �Լ�
	 *  ID : ���� ��ȣ
	 *  �ش� ������ �⿩ ���� 10�� ������ �Ǹ� ���� ���̵��� ����
	 *  ���� ��ũ �� ���� ��ũ ����Ʈ�� ����� �⿩�� ��հ����� ���򰡰� ��
	 */
	public static boolean calcProblemRank(int ID) {
		List<ProblemRank> RankList = ProblemRankMap.get(ID);	
		
		if (RankList.size() % 10 == 0) {
			// ProblemDBManager���� ��ȣ�� �ش��ϴ� ������ ������
			Problem plbm = ProblemDBManager.findProblem(ID);
			// ���� ��ũ �� ���� ��ũ ����Ʈ ���
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
		     * setProblemRankPoint�� �̿��Ͽ� �ش� ������ ���̵��� �缳��
		     * ����. ProblemDBManager���� �ش� ������ �ֽ�ȭ
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
	
	// ProblemRankDB�� ����� �������� �ҷ� ProblemRankMap �ؽøʿ� �߰��ϴ� �Լ�
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