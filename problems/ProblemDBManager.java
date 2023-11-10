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
	
	public static void PrintProblemDBMap() { // 디버깅용
		for (Map.Entry<Integer, Problem> entry : ProblemDBMap.entrySet()) {
            Integer key = entry.getKey();
            Problem value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value.toString() + "\n");
        }
	}
	
	// 문제를 해시맵 ProblemDBMap에 추가하는 함수
	public static boolean AddProblem(int ID, Problem problem) {
		if (!problem.isVaild()) {
			return false;
		}
		else {
			ProblemDBMap.put(ID, problem);
			return true;
		}
	}
	
	// 문제를 .txt 파일에 저장 후 해시맵 ProblemDBMap에 추가하는 함수
	public static boolean CreateProblem(Problem problem) {
		
		if (!problem.isVaild()) {
			return false;
		}
		else {
			String filename = Integer.toString(problem.getProblemID());
			String filepath = String.format("\\problems\\ProblemDB\\%s.txt", filename);
			FileManager.createUpdateObjectFile(ProblemDBMap, filepath);
			ProblemDBMap.put(problem.getProblemID(), problem);
			
			return true;
		}
		
	}
	
	// 문제를 바꾸는 함수
	public static boolean ChangeProblem(int ID, Problem problem) {
		if (!problem.isVaild()) {
			return false;
		}
		else {
			if(ProblemDBMap.containsKey(ID)) {
				ProblemDBMap.put(ID, problem);
				String filename = Integer.toString(ID);
				String filepath = String.format("\\problems\\ProblemDB\\%s.txt", filename);
				FileManager.createUpdateObjectFile(ProblemDBMap, filepath);
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	// 문제가 있는지 확인하는 함수
	public static boolean ContainProblem(int ID) {
		if (ProblemDBMap.containsKey(ID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// 문제 ID를 통해 문제를 찾는 함수
	public static Problem FindProblem(int ID) {
		Problem pblm = new Problem();
		if (ProblemDBMap.containsKey(ID)) {
			pblm = ProblemDBMap.get(ID);
			return pblm;
		}
		else {
			return pblm;
		}
	}
	
	// 문제 ID를 기준으로 오름차순, 내림차순으로 정렬해서 반환하는 함수
	public static ArrayList<Problem> FindProblemToID(boolean sort){
		ArrayList<Integer> Keys = new ArrayList<>(ProblemDBMap.keySet());
		ArrayList<Problem> ProblemSortID = new ArrayList<>();
		
		if (sort) {
			Collections.sort(Keys);
		}
		else {
			Collections.sort(Keys, Collections.reverseOrder());
		}
		
		for (Integer key : Keys) {
			ProblemSortID.add(ProblemDBMap.get(key));
		}
		
		return ProblemSortID;
	}
	
	
	// 문제 이름을 기준으로 오름차순, 내림차순으로 정렬해서 반환하는 함수
	public static ArrayList<Problem> FindProblemToName(boolean sort) {
	    HashMap<Integer, String> ProblemName = new HashMap<>();
	    ArrayList<Problem> SortProblem = new ArrayList<>();
	    
	    for (Map.Entry<Integer, Problem> entry : ProblemDBMap.entrySet()) {
	        Integer key = entry.getKey();
	        Problem problem = entry.getValue();
	        String name = problem.getProblemName();

	        ProblemName.put(key, name);
	    }
	    
	    List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(ProblemName.entrySet());
	    
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
	    
        for(Map.Entry<Integer, String> entry : list) {
        	SortProblem.add(ProblemDBMap.get(entry.getKey()));
        }
	    
	    return SortProblem;
	}
	
	// 문제 랭크를 기준으로 오름차순, 내림차순을 해서 반환하는 함수
	public static ArrayList<Problem> FindProblemToRank(boolean sort){
		HashMap<Integer, RANK> ProblemName = new HashMap<>();
	    ArrayList<Problem> SortProblem = new ArrayList<>();
	    
	    for (Map.Entry<Integer, Problem> entry : ProblemDBMap.entrySet()) {
	        Integer key = entry.getKey();
	        Problem problem = entry.getValue();
	        RANK Rank = problem.getProblemRank();

	        ProblemName.put(key, Rank);
	    }
	    
	    List<Map.Entry<Integer,RANK>> list = new ArrayList<Map.Entry<Integer,RANK>>(ProblemName.entrySet());
	    
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
	    
        for(Map.Entry<Integer, RANK> entry : list) {
        	SortProblem.add(ProblemDBMap.get(entry.getKey()));
        }
	    
	    return SortProblem;
	}
	
	// 문제 이름 검색을 통해 문제를 반환하는 함수
	public static ArrayList<Problem> FindProblemSearch(String Name) {
	    ArrayList<Problem> ProblemSearch = new ArrayList<>();
	    
	    for (Problem problem : ProblemDBMap.values()) {
	        if (problem.getProblemName().toLowerCase().contains(Name.toLowerCase())) {
	        	ProblemSearch.add(problem);
	        }
	    }
	    
	    return ProblemSearch;
	}
	
	// ProblemDB에 저장된 문제들을 불러 해시맵 ProblemDBManger에 추가하는 함수
	public static void init() {
		String dirpath = String.format("\\problems\\ProblemDB"); // 경로 지정
		// 해당 폴더에 저장된 모든 파일을 Object로 변환하여 ArrayList<Object>로 변환 
		ArrayList<Object> objList = FileManager.readAllObjectFileInDirectory(dirpath); 
		try {
			// 각 Object 들을 User로 형변환
			for (Object obj : objList) {			 
				if(obj instanceof Problem) {
					Problem plbm = (Problem)obj;
					AddProblem(plbm.getProblemID(), plbm);				 
				} else {
					throw new ClassCastException("Problem 인스턴스로 변환할 수 없습니다.");
				}
			}
		} catch (ClassCastException e) {
			e.printStackTrace();			
		}
	}
	
}
