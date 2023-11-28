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
	 *  ID : 문제 번호, problem : Problem 객체 변수
	 *  ProblemDB 폴더에서 문제를 가져올 때 사용한다.
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
	 *  문제를 .txt로 변환하여 ProblemDB 폴더에 저장 후 ProblemDBMap 해시맵에 추가하는 함수
	 *  problem : Problem 객체 변수
	 *  새롭게 생성된 Problem 객체 변수를 ProblemDB 폴더에 추가할 때 사용한다.
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
	 *  ProblemDB 폴더에 저장된 문제와 ProblemDBMap 해시맵에 저장된 문제를 최신화하는 함수
	 *  ID : 문제 번호, problem : Problem 객체 변수
	 */
	public static boolean changeProblem(int ID, Problem problem) {
		if (!problem.isValid()) {
			return false;
		}
		else {
			if(ProblemDBMap.containsKey(ID)) {
				ProblemDBMap.put(ID, problem);		   // 해시맵에 추가
				String filename = Integer.toString(ID);
				String filepath = String.format("\\problems\\ProblemDB\\%s.txt", filename);
				FileManager.createUpdateObjectFile(problem, filepath);     // .txt 파일 저장
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	/*
	 *  문제가 ProblemDBMap 해시맵에 있는지 확인하는 함수
	 *  ID : 문제 번호
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
	 *  문제 ID를 통해 ProblemDBMap 해시맵에서 문제를 찾는 함수
	 *  ID - 문제 번호
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
	 *  문제 ID를 기준으로 오름차순, 내림차순으로 정렬해서 반환하는 함수
	 *  true - 오름차순, false - 내림차순
	 */
	public static ArrayList<Problem> findProblemToID(boolean sort){
		/*
		 *  Keys : ProblemDBMap 해시맵에 저장된 모든 키 값
		 *  ProblemSortID : 정렬된 Problem 객체가 저장될 리스트
		 */
		ArrayList<Integer> Keys = new ArrayList<>(ProblemDBMap.keySet());
		ArrayList<Problem> ProblemSortID = new ArrayList<>();
		
		// 매개변수를 바탕으로 Keys 리스트를 오름차순 혹은 내림차순으로 정렬한다.
		if (sort) {
			Collections.sort(Keys);
		}
		else {
			Collections.sort(Keys, Collections.reverseOrder());
		}
		
		// 정렬한 키 값을 바탕으로 ProblemDBMap에서 Problem 객체를 불러와 ProblemSortID 리스트에 저장한다.
		for (Integer key : Keys) {
			ProblemSortID.add(ProblemDBMap.get(key));
		}
		
		return ProblemSortID;
	}
	
	/*
	 *  문제 검색을 통해 얻은 문제를 문제 ID 기준으로 정렬하는 함수
	 *  plbm : 문제 검색으로 얻은 Problem 리스트
	 */
	public static ArrayList<Problem> findProblemToID(ArrayList<Problem> plbm, boolean sort){ 
		List<Problem> list = new ArrayList<>(plbm);
		
		/*
		 *  Comparator 클래스를 사용
		 *  Collections.sort()를 오버라이딩하여 문제 ID 기준으로 정렬이 되도록 함
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
	 *  문제 이름을 기준으로 오름차순, 내림차순으로 정렬해서 반환하는 함수
	 *  true - 오름차순, false - 내림차순
	 */
	public static ArrayList<Problem> findProblemToName(boolean sort) {
		/*
		 *  ProblemName : key - 문제 번호, value - 문제 이름
		 *  SortProblem : 정렬된 Problem 객체가 저장될 리스트
		 */
	    HashMap<Integer, String> ProblemName = new HashMap<>();
	    ArrayList<Problem> SortProblem = new ArrayList<>();
	    
	    /*
	     * ProblemDBMap 해시맵에서 문제 번호(키값)과 Problem 객체를 추출한다.
	     * 추출한 Problem 객체에서 문제 이름을 가져온다.
	     * 추출한 문제 번호와 문제 이름을 ProblemName 해시맵에 저장한다.
	     */
	    for (Map.Entry<Integer, Problem> entry : ProblemDBMap.entrySet()) {
	        Integer key = entry.getKey();
	        Problem problem = entry.getValue();
	        String name = problem.getProblemName();

	        ProblemName.put(key, name);
	    }
	    
	    // list : ProblemName의 Key, Value를 가지고 있는 리스트
	    List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(ProblemName.entrySet());
	    
		/*
		 *  Comparator 클래스를 사용
		 *  Collections.sort()를 오버라이딩하여 문제이름을 기준으로 정렬이 되도록 함
		 *  이때, 키 값도 같이 정렬이 됨
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
	    
        // list에서 정렬된 키 값을 통해 ProblemDBMap에서 문제를 가져오고 SortProblem에 저장한다.
        for(Map.Entry<Integer, String> entry : list) {
        	SortProblem.add(ProblemDBMap.get(entry.getKey()));
        }
	    
	    return SortProblem;
	}
	
	/*
	 *  문제 검색을 통해 얻은 문제를 문제 이름 기준으로 정렬하는 함수
	 *  plbm : 문제 검색으로 얻은 Problem 리스트
	 */
	public static ArrayList<Problem> findProblemToName(ArrayList<Problem> plbm, boolean sort){
		List<Problem> list = new ArrayList<>(plbm);

		/*
		 *  Comparator 클래스를 사용
		 *  Collections.sort()를 오버라이딩하여 문제 이름 기준으로 정렬이 되도록 함
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
	 *  문제 랭크를 기준으로 오름차순, 내림차순을 해서 반환하는 함수
	 *  true - 오름차순, false - 내림차순
	 */
	public static ArrayList<Problem> findProblemToRank(boolean sort){
		/*
		 *  ProblemRank : key - 문제 번호, value - 문제 RANK
		 *  SortProblem : 정렬된 Problem 객체가 저장될 리스트
		 */
		HashMap<Integer, RANK> ProblemRank = new HashMap<>();
	    ArrayList<Problem> SortProblem = new ArrayList<>();
	    
	    /*
	     * ProblemDBMap 해시맵에서 문제 번호(키값)과 Problem 객체를 추출한다.
	     * 추출한 Problem 객체에서 문제 RANK를 가져온다.
	     * 추출한 문제 번호와 문제 RANK를 ProblemName 해시맵에 저장한다.
	     */
	    for (Map.Entry<Integer, Problem> entry : ProblemDBMap.entrySet()) {
	        Integer key = entry.getKey();
	        Problem problem = entry.getValue();
	        RANK Rank = problem.getProblemRank();

	        ProblemRank.put(key, Rank);
	    }
	    
	    // list : ProblemRank의 Key, Value를 가지고 있는 리스트
	    List<Map.Entry<Integer,RANK>> list = new ArrayList<Map.Entry<Integer,RANK>>(ProblemRank.entrySet());
	    
		/*
		 *  Comparator 클래스를 사용
		 *  Collections.sort()를 오버라이딩하여 문제 RANK를 기준으로 정렬이 되도록 함
		 *  이때, 키 값도 같이 정렬이 됨
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
	    
        // list에서 정렬된 키 값을 통해 ProblemDBMap에서 문제를 가져오고 SortProblem에 저장한다.
        for(Map.Entry<Integer, RANK> entry : list) {
        	SortProblem.add(ProblemDBMap.get(entry.getKey()));
        }
	    
	    return SortProblem;
	}
	
	/*
	 *  문제 검색을 통해 얻은 문제를 문제 랭크 기준으로 정렬하는 함수
	 *  plbm : 문제 검색으로 얻은 Problem 리스트
	 */
	public static ArrayList<Problem> findProblemToRank(ArrayList<Problem> plbm, boolean sort) {
		List<Problem> list = new ArrayList<>(plbm);
		
		/*
		 *  Comparator 클래스를 사용
		 *  Collections.sort()를 오버라이딩하여 문제 RANK 기준으로 정렬이 되도록 함
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
	 *  문제 이름 검색을 통해 문제를 반환하는 함수
	 *  num : 정렬 기준과 정렬 방향에 따라 다르게 전달
	 */
	public static ArrayList<Problem> findProblemSearch(String Name, int num) {
	    ArrayList<Problem> ProblemSearch = new ArrayList<>();
	    
	    /*
	     *  ProblemDBMap에서 Problem 객체를 가져온다.
	     *  이후, 문제 이름에 매개변수로 받은 문자가 포함이 되어있으면 ProblemSearch 리스트에 저장한다	  
	     */
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
	 *  algorithm : 문제 알고리즘이 저장된 ArrayList
	 */
	public static ArrayList<Problem> findProblemAlgorithm(ArrayList<String> algorithm){
		 ArrayList<Problem> ProblemSearch = new ArrayList<>();
		 
		 /*
		  *  ProblemDBMap에서 Problem 객체를 가져온다.
		  *  이후, 문제 알고리즘에에 매개변수로 받은 알고리즘이 모두 있으면 ProblemSearch 리스트에 저장한다	  
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
	 *  문제 랭크 포인트를 통해 문제를 반환하는 함수
	 *  매개변수로 받은 문제포인트를 기준으로 +- 25에 해당하는 문제만 반환
	 *  RankPoint : 문제 포인트
	 */
	public static ArrayList<Problem> findProblemRankPoint(int RankPoint){
		ArrayList<Problem> ProblemSearch = new ArrayList<>();
		
		/*
		 *  ProblemDBMap에서 Problem 객체를 가져온다.
		 *  이후, 문제 랭크 포인트가 매개변수로 받은 점수 범위 이내에 있으면 ProblemSearch 리스트에 저장한다	  
		 */ 
		for (Problem problem : ProblemDBMap.values()) {
			int point = problem.getProblemRankPoint();
			if (point > RankPoint - 25 && point < RankPoint + 25) {
				ProblemSearch.add(problem);
			}
		}
		
		return ProblemSearch;
	}
	
	/*
	 *  문제 랭크를 통해 문제를 반환하는 함수
	 *  매개변수로 받은 랭크에 해당하는 문제를 반환
	 *  rank : 문제 랭크
	 */
	public static ArrayList<Problem> findProblemRank(RANK rank){
		ArrayList<Problem> ProblemSearch = new ArrayList<>();
		int rankvalue = rank.getRequireRankPoint();
		
		/*
		 *  ProblemDBMap에서 Problem 객체를 가져온다.
		 *  이후, 문제 랭크가 매개변수로 받은 랭크와 같으면 ProblemSearch 리스트에 저장한다	  
		 */ 
		for (Problem problem : ProblemDBMap.values()) {
			int value = problem.getProblemRank().getRequireRankPoint();
			if (rankvalue == value) {
				ProblemSearch.add(problem);
			}
		}
		
		List<Problem> list = new ArrayList<>(ProblemSearch);
		
		Collections.sort(list, new Comparator<Problem>() {
            @Override
            public int compare(Problem o1, Problem o2) {
            	return Integer.compare(o1.getProblemRankPoint(), o2.getProblemRankPoint());
            }
        });
        
        return new ArrayList<Problem>(list);
	}
	
	// ProblemDB에 저장된 문제들을 불러 ProblemDBMap 해시맵에 추가하는 함수
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
