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
	
	// Key = 문제 ID, Value = Problem 객체
	private static HashMap<Integer, Problem> ProblemDBMap = new HashMap<>();

	// 디버깅용
	public static void PrintProblemDBMap() {
		for (Map.Entry<Integer, Problem> entry : ProblemDBMap.entrySet()) {
            Integer key = entry.getKey();
            Problem value = entry.getValue();
            System.out.println("Key: " + key + "\n" + value.toString() + "\n");
        }
	}
	
	/*
	 *  문제를 해시맵 ProblemDBMap에 추가하는 함수
	 *  DB에서 문제를 가져올 때 사용
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
	 *  문제를 .txt 파일에 저장 후 해시맵 ProblemDBMap에 추가하는 함수
	 *  문제를 DB에 새롭게 추가할 때 사용
	 */
	public static boolean createProblem(Problem problem) {
		if (!problem.isValid()) {
			return false;
		}
		else {
			String filename = Integer.toString(problem.getProblemID());
			String filepath = String.format("\\problems\\ProblemDB\\%s.txt", filename);
			FileManager.createUpdateObjectFile(problem, filepath);     // .txt 파일 저장
			ProblemDBMap.put(problem.getProblemID(), problem);		   // 해시맵에 추가
			return true;
		}
	}
	
	/* 
	 *  문제를 바꾸는 함수
	 *  사용자 기여를 통해 문제의 랭크가 변경됐을 때 사용
	 */
	public static boolean changeProblem(int ID, Problem problem) {
		if (!problem.isValid()) {
			return false;
		}
		else {
			if(ProblemDBMap.containsKey(ID)) {
				ProblemDBMap.put(ID, problem);
				String filename = Integer.toString(ID);
				String filepath = String.format("\\problems\\ProblemDB\\%s.txt", filename);
				FileManager.createUpdateObjectFile(problem, filepath);
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	// 문제가 해시맵에 있는지 확인하는 함수
	public static boolean containProblem(int ID) {
		if (ProblemDBMap.containsKey(ID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	// 문제 ID를 통해 문제를 찾는 함수
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
	
	
	// 검색을 통해 얻은 문제를 문제 ID 기준으로 정렬
	public static ArrayList<Problem> findProblemToID(ArrayList<Problem> plbm, boolean sort){
		if (plbm == null) {
			return null;
		}
		
		List<Problem> list = new ArrayList<>(plbm);

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
	
	// 검색을 통해 얻은 문제를 이름 기준으로 정렬 
	public static ArrayList<Problem> findProblemToName(ArrayList<Problem> plbm, boolean sort){
		if (plbm == null) {
			return null;
		}
		
		List<Problem> list = new ArrayList<>(plbm);

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
	
	// 검색을 통해 얻은 문제를 랭크 기준으로 정렬
	
	public static ArrayList<Problem> findProblemToRank(ArrayList<Problem> plbm, boolean sort) {
		if (plbm == null) {
			return null;
		} 
		
		List<Problem> list = new ArrayList<>(plbm);

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
	 *  문제 이름 검색을 통해 문제를 반환하는 함수
	 *  num : 정렬 기준과 오름차순 내림차순에 따라 다르게 전달
	 */
	public static ArrayList<Problem> findProblemSearch(String Name, int num) {
	    ArrayList<Problem> ProblemSearch = new ArrayList<>();
	    
	    for (Problem problem : ProblemDBMap.values()) {
	        if (problem.getProblemName().toLowerCase().contains(Name.toLowerCase())) {
	        	ProblemSearch.add(problem);
	        }
	    }
	    
	    // 문제를 번호 기준 오름차순으로 정렬
	    if (num == 1) {
	    	return findProblemToID(ProblemSearch, true);
	    }
	    // 문제를 번호 기준 내림차순으로 정렬
	    else if (num == 2) {
	    	return findProblemToID(ProblemSearch, false);
	    }
	    // 문제를 이름 기준 오름차순으로 정렬
	    else if (num == 3) {
	    	return findProblemToName(ProblemSearch, true);
	    }
	    // 문제를 이름 기준 내림차순으로 정렬
	    else if (num == 4) {
	    	return findProblemToName(ProblemSearch, false);
	    }
	    // 문제를 랭크 기준 오름차순으로 정렬
	    else if (num == 5) {
	    	return findProblemToRank(ProblemSearch, true);
	    }
	    // 문제를 랭크 기준 내림차순으로 정렬
	    else if (num == 6) {
	    	return findProblemToRank(ProblemSearch, false);
	    }
	    // 정렬 기준이 없을 때 검색한 문제만 반환
	    else {
	    	return ProblemSearch;
	    }
	}
	
	/*
	 *  문제 알고리즘을 통해 문제를 반환하는 함수
	 *  매개변수로 받은 리스트를 모두 포함하고 있는 문제만 반환
	 */
	public static ArrayList<Problem> findProblemAlgorithm(ArrayList<String> algorithm){
		 ArrayList<Problem> ProblemSearch = new ArrayList<>();
		 
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
	
	// 문제 랭크 포인트를 통해 문제를 반환하는 함수
	public static ArrayList<Problem> findProblemRankPoint(int RankPoint){
		return null;
	}
	
	// ProblemDB에 저장된 문제들을 불러 해시맵 ProblemDBManger에 추가하는 함수
	public static void init() {
		String dirpath = String.format("\\problems\\ProblemDB"); // 경로 지정
		// 해당 폴더에 저장된 모든 파일을 Object로 변환하여 ArrayList<Object>로 변환 
		ArrayList<Object> objList = FileManager.readAllObjectFileInDirectory(dirpath);
		try {
			// 각 Object 들을 Problem로 형변환
			for (Object obj : objList) {	
				if(obj instanceof Problem) {
					Problem plbm = (Problem)obj;
					addProblem(plbm.getProblemID(), plbm);				 
				} else {
					throw new ClassCastException("Problem 인스턴스로 변환할 수 없습니다.");
				}
			}
		} catch (ClassCastException e) {
			e.printStackTrace();			
		}
	}	
}
