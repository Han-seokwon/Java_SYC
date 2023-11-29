package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import users.RANK;
import file.FileManager;

public class ProblemDBManager {
	
	// Key = ���� ID, Value = Problem ��ü
	private static HashMap<Integer, Problem> ProblemDBMap = new HashMap<>();

	// ������
	public static void PrintProblemDBMap() {
		for (Map.Entry<Integer, Problem> entry : ProblemDBMap.entrySet()) {
            Integer key = entry.getKey();
            Problem value = entry.getValue();
            System.out.println("Key: " + key + "\n" + value.toString() + "\n");
        }
	}
	
	/*
	 *  ������ �ؽø� ProblemDBMap�� �߰��ϴ� �Լ�
	 *  ID : ���� ��ȣ, problem : Problem ��ü ����
	 *  ProblemDB �������� ������ ������ �� ����Ѵ�.
	 */
	public static boolean addProblem(int ID, Problem problem) {
		if (!problem.isValid()) {
			return false;
		}
		else {
			ProblemDBMap.put(ID, problem);
			return true;
		}
	}
	
	/* 
	 *  ������ .txt�� ��ȯ�Ͽ� ProblemDB ������ ���� �� ProblemDBMap �ؽøʿ� �߰��ϴ� �Լ�
	 *  problem : Problem ��ü ����
	 *  ���Ӱ� ������ Problem ��ü ������ ProblemDB ������ �߰��� �� ����Ѵ�.
	 */
	public static boolean createProblem(Problem problem) {
		if (!problem.isValid()) {
			return false;
		}
		else {
			String filename = Integer.toString(problem.getProblemID());
			String filepath = String.format("\\problems\\ProblemDB\\%s.txt", filename);
			FileManager.createUpdateObjectFile(problem, filepath);     // .txt ���� ����
			ProblemDBMap.put(problem.getProblemID(), problem);		   // �ؽøʿ� �߰�
			return true;
		}
	}
	
	/* 
	 *  ProblemDB ������ ����� ������ ProblemDBMap �ؽøʿ� ����� ������ �ֽ�ȭ�ϴ� �Լ�
	 *  ID : ���� ��ȣ, problem : Problem ��ü ����
	 */
	public static boolean changeProblem(int ID, Problem problem) {
		if (!problem.isValid()) {
			return false;
		}
		else {
			if(ProblemDBMap.containsKey(ID)) {
				ProblemDBMap.put(ID, problem);		   // �ؽøʿ� �߰�
				String filename = Integer.toString(ID);
				String filepath = String.format("\\problems\\ProblemDB\\%s.txt", filename);
				FileManager.createUpdateObjectFile(problem, filepath);     // .txt ���� ����
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	/*
	 *  ������ ProblemDBMap �ؽøʿ� �ִ��� Ȯ���ϴ� �Լ�
	 *  ID : ���� ��ȣ
	 */
	public static boolean containProblem(int ID) {
		if (ProblemDBMap.containsKey(ID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/*
	 *  ���� ID�� ���� ProblemDBMap �ؽøʿ��� ������ ã�� �Լ�
	 *  ID - ���� ��ȣ
	 */
	public static Problem findProblem(int ID) {
		Problem pblm = new Problem();
		
		if (ProblemDBMap.containsKey(ID)) {
			pblm = ProblemDBMap.get(ID);
			return pblm;
		}
		else {
			return pblm;
		}
	}
	
	/*
	 *  ���� ID�� �������� ��������, ������������ �����ؼ� ��ȯ�ϴ� �Լ�
	 *  true - ��������, false - ��������
	 */
	public static ArrayList<Problem> findProblemToID(boolean sort){
		/*
		 *  Keys : ProblemDBMap �ؽøʿ� ����� ��� Ű ��
		 *  ProblemSortID : ���ĵ� Problem ��ü�� ����� ����Ʈ
		 */
		ArrayList<Integer> Keys = new ArrayList<>(ProblemDBMap.keySet());
		ArrayList<Problem> ProblemSortID = new ArrayList<>();
		
		// �Ű������� �������� Keys ����Ʈ�� �������� Ȥ�� ������������ �����Ѵ�.
		if (sort) {
			Collections.sort(Keys);
		}
		else {
			Collections.sort(Keys, Collections.reverseOrder());
		}
		
		// ������ Ű ���� �������� ProblemDBMap���� Problem ��ü�� �ҷ��� ProblemSortID ����Ʈ�� �����Ѵ�.
		for (Integer key : Keys) {
			ProblemSortID.add(ProblemDBMap.get(key));
		}
		
		return ProblemSortID;
	}
	
	/*
	 *  ���� �˻��� ���� ���� ������ ���� ID �������� �����ϴ� �Լ�
	 *  plbm : ���� �˻����� ���� Problem ����Ʈ
	 */
	public static ArrayList<Problem> findProblemToID(ArrayList<Problem> plbm, boolean sort){ 
		List<Problem> list = new ArrayList<>(plbm);
		
		/*
		 *  Comparator Ŭ������ ���
		 *  Collections.sort()�� �������̵��Ͽ� ���� ID �������� ������ �ǵ��� ��
		 */
        Collections.sort(list, new Comparator<Problem>() {
            @Override
            public int compare(Problem o1, Problem o2) {
                if (sort) {
                    return Integer.compare(o1.getProblemID(), o2.getProblemID());
                } else {
                    return Integer.compare(o2.getProblemID(), o1.getProblemID());
                }
            }
        });
        
        return new ArrayList<Problem>(list);
	}
	
	/*
	 *  ���� �̸��� �������� ��������, ������������ �����ؼ� ��ȯ�ϴ� �Լ�
	 *  true - ��������, false - ��������
	 */
	public static ArrayList<Problem> findProblemToName(boolean sort) {
		/*
		 *  ProblemName : key - ���� ��ȣ, value - ���� �̸�
		 *  SortProblem : ���ĵ� Problem ��ü�� ����� ����Ʈ
		 */
	    HashMap<Integer, String> ProblemName = new HashMap<>();
	    ArrayList<Problem> SortProblem = new ArrayList<>();
	    
	    /*
	     * ProblemDBMap �ؽøʿ��� ���� ��ȣ(Ű��)�� Problem ��ü�� �����Ѵ�.
	     * ������ Problem ��ü���� ���� �̸��� �����´�.
	     * ������ ���� ��ȣ�� ���� �̸��� ProblemName �ؽøʿ� �����Ѵ�.
	     */
	    for (Map.Entry<Integer, Problem> entry : ProblemDBMap.entrySet()) {
	        Integer key = entry.getKey();
	        Problem problem = entry.getValue();
	        String name = problem.getProblemName();

	        ProblemName.put(key, name);
	    }
	    
	    // list : ProblemName�� Key, Value�� ������ �ִ� ����Ʈ
	    List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(ProblemName.entrySet());
	    
		/*
		 *  Comparator Ŭ������ ���
		 *  Collections.sort()�� �������̵��Ͽ� �����̸��� �������� ������ �ǵ��� ��
		 *  �̶�, Ű ���� ���� ������ ��
		 */
        Collections.sort(list, new Comparator<>() {
            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                if (sort) {
                	return o1.getValue().compareTo(o2.getValue());
                }
                else {
                	return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
	    
        // list���� ���ĵ� Ű ���� ���� ProblemDBMap���� ������ �������� SortProblem�� �����Ѵ�.
        for(Map.Entry<Integer, String> entry : list) {
        	SortProblem.add(ProblemDBMap.get(entry.getKey()));
        }
	    
	    return SortProblem;
	}
	
	/*
	 *  ���� �˻��� ���� ���� ������ ���� �̸� �������� �����ϴ� �Լ�
	 *  plbm : ���� �˻����� ���� Problem ����Ʈ
	 */
	public static ArrayList<Problem> findProblemToName(ArrayList<Problem> plbm, boolean sort){
		List<Problem> list = new ArrayList<>(plbm);

		/*
		 *  Comparator Ŭ������ ���
		 *  Collections.sort()�� �������̵��Ͽ� ���� �̸� �������� ������ �ǵ��� ��
		 */
        Collections.sort(list, new Comparator<Problem>() {
            @Override
            public int compare(Problem o1, Problem o2) {
                if (sort) {
                    return o1.getProblemName().compareTo(o2.getProblemName());
                } else {
                    return o2.getProblemName().compareTo(o1.getProblemName());
                }
            }
        });
        
        return new ArrayList<Problem>(list);
		
	}
	
	/*
	 *  ���� ��ũ�� �������� ��������, ���������� �ؼ� ��ȯ�ϴ� �Լ�
	 *  true - ��������, false - ��������
	 */
	public static ArrayList<Problem> findProblemToRank(boolean sort){
		/*
		 *  ProblemRank : key - ���� ��ȣ, value - ���� RANK
		 *  SortProblem : ���ĵ� Problem ��ü�� ����� ����Ʈ
		 */
		HashMap<Integer, RANK> ProblemRank = new HashMap<>();
	    ArrayList<Problem> SortProblem = new ArrayList<>();
	    
	    /*
	     * ProblemDBMap �ؽøʿ��� ���� ��ȣ(Ű��)�� Problem ��ü�� �����Ѵ�.
	     * ������ Problem ��ü���� ���� RANK�� �����´�.
	     * ������ ���� ��ȣ�� ���� RANK�� ProblemName �ؽøʿ� �����Ѵ�.
	     */
	    for (Map.Entry<Integer, Problem> entry : ProblemDBMap.entrySet()) {
	        Integer key = entry.getKey();
	        Problem problem = entry.getValue();
	        RANK Rank = problem.getProblemRank();

	        ProblemRank.put(key, Rank);
	    }
	    
	    // list : ProblemRank�� Key, Value�� ������ �ִ� ����Ʈ
	    List<Map.Entry<Integer,RANK>> list = new ArrayList<Map.Entry<Integer,RANK>>(ProblemRank.entrySet());
	    
		/*
		 *  Comparator Ŭ������ ���
		 *  Collections.sort()�� �������̵��Ͽ� ���� RANK�� �������� ������ �ǵ��� ��
		 *  �̶�, Ű ���� ���� ������ ��
		 */
        Collections.sort(list, new Comparator<>() {
            @Override
            public int compare(Map.Entry<Integer, RANK> o1, Map.Entry<Integer, RANK> o2) {
                if (sort) {
                	return o1.getValue().compareTo(o2.getValue());
                }
                else {
                	return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
	    
        // list���� ���ĵ� Ű ���� ���� ProblemDBMap���� ������ �������� SortProblem�� �����Ѵ�.
        for(Map.Entry<Integer, RANK> entry : list) {
        	SortProblem.add(ProblemDBMap.get(entry.getKey()));
        }
	    
	    return SortProblem;
	}
	
	/*
	 *  ���� �˻��� ���� ���� ������ ���� ��ũ �������� �����ϴ� �Լ�
	 *  plbm : ���� �˻����� ���� Problem ����Ʈ
	 */
	public static ArrayList<Problem> findProblemToRank(ArrayList<Problem> plbm, boolean sort) {
		List<Problem> list = new ArrayList<>(plbm);
		
		/*
		 *  Comparator Ŭ������ ���
		 *  Collections.sort()�� �������̵��Ͽ� ���� RANK �������� ������ �ǵ��� ��
		 */
        Collections.sort(list, new Comparator<Problem>() {
            @Override
            public int compare(Problem o1, Problem o2) {
                if (sort) {
                    return o1.getProblemRank().compareTo(o2.getProblemRank());
                } else {
                    return o2.getProblemRank().compareTo(o1.getProblemRank());
                }
            }
        });
        
        return new ArrayList<Problem>(list);
    }
	
	/*
	 *  ���� �̸� �˻��� ���� ������ ��ȯ�ϴ� �Լ�
	 *  num : ���� ���ذ� ���� ���⿡ ���� �ٸ��� ����
	 */
	public static ArrayList<Problem> findProblemSearch(String Name, int num) {
	    ArrayList<Problem> ProblemSearch = new ArrayList<>();
	    
	    /*
	     *  ProblemDBMap���� Problem ��ü�� �����´�.
	     *  ����, ���� �̸��� �Ű������� ���� ���ڰ� ������ �Ǿ������� ProblemSearch ����Ʈ�� �����Ѵ�	  
	     */
	    for (Problem problem : ProblemDBMap.values()) {
	        if (problem.getProblemName().toLowerCase().contains(Name.toLowerCase())) {
	        	ProblemSearch.add(problem);
	        }
	    }
	    
	    // ������ ��ȣ ���� ������������ ����
	    if (num == 1) {
	    	return findProblemToID(ProblemSearch, true);
	    }
	    // ������ ��ȣ ���� ������������ ����
	    else if (num == 2) {
	    	return findProblemToID(ProblemSearch, false);
	    }
	    // ������ �̸� ���� ������������ ����
	    else if (num == 3) {
	    	return findProblemToName(ProblemSearch, true);
	    }
	    // ������ �̸� ���� ������������ ����
	    else if (num == 4) {
	    	return findProblemToName(ProblemSearch, false);
	    }
	    // ������ ��ũ ���� ������������ ����
	    else if (num == 5) {
	    	return findProblemToRank(ProblemSearch, true);
	    }
	    // ������ ��ũ ���� ������������ ����
	    else if (num == 6) {
	    	return findProblemToRank(ProblemSearch, false);
	    }
	    // ���� ������ ���� �� �˻��� ������ ��ȯ
	    else {
	    	return ProblemSearch;
	    }
	}
	
	/*
	 *  ���� �˰����� ���� ������ ��ȯ�ϴ� �Լ�
	 *  �Ű������� ���� ����Ʈ�� ��� �����ϰ� �ִ� ������ ��ȯ
	 *  algorithm : ���� �˰����� ����� ArrayList
	 */
	public static ArrayList<Problem> findProblemAlgorithm(ArrayList<String> algorithm){
		 ArrayList<Problem> ProblemSearch = new ArrayList<>();
		 
		 /*
		  *  ProblemDBMap���� Problem ��ü�� �����´�.
		  *  ����, ���� �˰��򿡿� �Ű������� ���� �˰����� ��� ������ ProblemSearch ����Ʈ�� �����Ѵ�	  
		  */ 
		 for (Problem problem : ProblemDBMap.values()) {
			 if (containsAll(problem.getProblemAlgorithm(), algorithm)) {
				 ProblemSearch.add(problem);
			 }
		 }
		 
		 return ProblemSearch;
	}

	public static <T> boolean containsAll(ArrayList<T> A, ArrayList<T> B) {
        return A.containsAll(B);
    }
	
	/*
	 *  ���� ��ũ ����Ʈ�� ���� ������ ��ȯ�ϴ� �Լ�
	 *  �Ű������� ���� ��������Ʈ�� �������� +- 25�� �ش��ϴ� ������ ��ȯ
	 *  RankPoint : ���� ����Ʈ
	 */
	public static ArrayList<Problem> findProblemRankPoint(int RankPoint){
		ArrayList<Problem> ProblemSearch = new ArrayList<>();
		
		/*
		 *  ProblemDBMap���� Problem ��ü�� �����´�.
		 *  ����, ���� ��ũ ����Ʈ�� �Ű������� ���� ���� ���� �̳��� ������ ProblemSearch ����Ʈ�� �����Ѵ�	  
		 */ 
		for (Problem problem : ProblemDBMap.values()) {
			int point = problem.getProblemRankPoint();
			if (point > RankPoint - 25 && point < RankPoint + 25) {				
				ProblemSearch.add(problem);
			}
		}
		
		return ProblemSearch;
	}
	
	// ProblemDB�� ����� �������� �ҷ� ProblemDBMap �ؽøʿ� �߰��ϴ� �Լ�
	public static void init() {
		String dirpath = String.format("\\problems\\ProblemDB"); // ��� ����
		// �ش� ������ ����� ��� ������ Object�� ��ȯ�Ͽ� ArrayList<Object>�� ��ȯ 
		ArrayList<Object> objList = FileManager.readAllObjectFileInDirectory(dirpath);
		try {
			// �� Object ���� Problem�� ����ȯ
			for (Object obj : objList) {	
				if(obj instanceof Problem) {
					Problem plbm = (Problem)obj;
					addProblem(plbm.getProblemID(), plbm);				 
				} else {
					throw new ClassCastException("Problem �ν��Ͻ��� ��ȯ�� �� �����ϴ�.");
				}
			}
		} catch (ClassCastException e) {
			e.printStackTrace();			
		}
	}	
}