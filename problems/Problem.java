package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import users.RANK;
import users.User;

public class Problem implements Serializable {
	
	// 문제 이름, 문제 ID, 문제 URL, 문제 랭크, 문제 랭크 포인트
	private String ProblemName;
	private int ProblemID;
	private String ProblemURL;
	private RANK ProblemRank;
	private int ProblemRankPoint;
	
	/*
	 * Step 1, Step 2, Step 3를 키 값으로 탐색
	 * 키값에 해당하는 <User, String> 해시맵
	 */
	private HashMap<String, HashMap<User, String>> ProblemHint = new HashMap<>();		
	
	// 학습자료, 문제 알고리즘 분류
	private List<String> ProblemReferences = new ArrayList<>();
	private ArrayList<String> ProblemAlgorithm = new ArrayList<>();
	private ArrayList<Integer> ProblemRunTime = new ArrayList<>();
	private ArrayList<Integer> ProblemMemory = new ArrayList<>();	
	
	private static final long serialVersionUID = 1L;	
	// 생성자
	public Problem() {}
	public Problem(String ProblemName, int ID, String URL, RANK Rank, int RankPoint, ArrayList<String> Algorithm) {
		this.ProblemName = ProblemName;
		this.ProblemID = ID;
		this.ProblemURL = URL;
		this.ProblemRank = Rank;
		this.ProblemRankPoint = RankPoint;
		this.ProblemAlgorithm = Algorithm;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("문제 번호 : ").append(ProblemID).append("\n");
	    sb.append("제목 : ").append(ProblemName).append("\n");
	    sb.append("난이도 : ").append(ProblemRank).append("(").append(ProblemRankPoint).append(")\n");
	    sb.append("획득 랭크 포인트: ").append(ProblemRank.getPointGain()).append("\n");
	    sb.append("URL: ").append(ProblemURL).append("\n");
	    sb.append("알고리즘 : ");
	    for (String algorithm : ProblemAlgorithm) {
	        sb.append(algorithm).append(" ");
	    }
	    return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ProblemID);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Problem other = (Problem) obj;
		return ProblemID == other.ProblemID;
	}

	
	// 설정자
	public void setProblemRank(RANK Rank) {
		this.ProblemRank = Rank;
	}
	public void setProblemRankPoint(int Point) {
		this.ProblemRankPoint = Point;
	}
	// 접근자
	public String getProblemName() {
		return this.ProblemName;
	}
	public int getProblemID() {
		return this.ProblemID;
	}
	public String getProblemURL() {
		return this.ProblemURL;
	}
	public RANK getProblemRank() {
		return this.ProblemRank;
	}
	public int getProblemRankPoint() {
		return this.ProblemRankPoint;
	}
	public HashMap<User, String> getProblemHint(String key){
		return this.ProblemHint.get(key);
	}
	public List<String> getProblemReferences(){
		return this.ProblemReferences;
	}
	public ArrayList<String> getProblemAlgorithm(){
		return this.ProblemAlgorithm;
	}
	// 평균 런타임과 평균 메모리 사용량을 반환하는 함수
	public int getProblemAvgRunTime() {
		int sum = 0;
	    
		for(int i = 0; i < this.ProblemRunTime.size(); i++) {
	    	sum += this.ProblemRunTime.get(i);
	    }
		
		return sum / this.ProblemRunTime.size();
	}
	
	public int getProblemAvgMemory() {
		int sum = 0;
	    
		for(int i = 0; i < this.ProblemMemory.size(); i++) {
	    	sum += this.ProblemMemory.get(i);
	    }		
		return sum / this.ProblemMemory.size();
	}
	
	/*
	 *  Problem에 힌트를 추가하는 함수
	 *  key : 'Step 1', 'Step 2', 'Step 3' / user : 유저 정보 / hint : 문제 힌트
	 *  key 값에 해당하는 해시맵을 ProblemHint 해시맵에서 가져옴
	 *  가져온 해시맵(hintList)에 user를 키값으로, hint를 값으로 저장
	 *  이후, 해당 키값과 해시맵(hintList)를 ProblemHint 해시맵에 추가
	 *  changProblem 함수를 이용하여 힌트가 추가된 문제로 최신화
	 */
	public void addProblemHint(String key, User user, String hint) {
	    HashMap<User, String> hintList = ProblemHint.getOrDefault(key, new HashMap<User, String>());
        hintList.put(user, hint);
        ProblemHint.put(key, hintList);
        ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem에 학습 자료를 추가하는 함수
	 *  plbmReferences : 학습자료 문자열
	 *  매개변수로 받은 객체 변수를 ProblemReferences 리스트에 추가
	 *  이후, changProblem 함수를 이용하여 학습 자료가 추가된 문제로 최신화
	 */
	public void addProblemReferences(String plbmReferences) {
		this.ProblemReferences.add(plbmReferences);
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem에 사용자가 얻은 런타임 정보를 추가하는 함수
	 *  RunTime : 사용자가 얻은 런타임 값
	 *  매개변수로 받은 RunTime를 ProblemRunTime 리스트에 추가
	 *  이후, changProblem 함수를 이용하여 학습 자료가 추가된 문제로 최신화 
	 */
	public void addProblemRunTime(int RunTime) {
		this.ProblemRunTime.add(RunTime);
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem에 사용자가 얻은 메모리 사용량 정보를 추가하는 함수
	 *  Memory : 사용자가 얻은 메모리 사용량 값
	 *  매개변수로 받은 Memory를 ProblemMemory 리스트에 추가
	 *  이후, changProblem 함수를 이용하여 학습 자료가 추가된 문제로 최신화 
	 */
	public void addProblemMemory(int Memory) {
		this.ProblemMemory.add(Memory);
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	// 유효한 Problem인지 확인하는 함수
	public boolean isValid() {
		if (this.ProblemID == 0 || 
				this.ProblemName == null || 
					this.ProblemRank == null ||
						this.ProblemURL == null ||
							this.ProblemAlgorithm == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
