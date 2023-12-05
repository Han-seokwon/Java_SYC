package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import users.RANK;
import users.User;

public class Problem implements Serializable {
	
	private String ProblemName;  	// 문제 이름
	private int ProblemID;		 	// 문제 ID
	private String ProblemURL;	 	// 문제 URL
	private RANK ProblemRank;	 	// 문제 랭크
	private int ProblemRankPoint;	// 문제 랭크 포인트
	private HashMap<Integer, ArrayList<Hint>> ProblemHint = new HashMap<>();	// 문제 힌트
	private ArrayList<LearningReference> ProblemReferences = new ArrayList<>();  // 문제 학습자료
	private ArrayList<String> ProblemAlgorithm = new ArrayList<>();	// 문제 알고리즘 분류
	private ArrayList<Integer> ProblemRunTime = new ArrayList<>();	// 해당 문제를 푼 사용자들의 실행 시간 
	private ArrayList<Integer> ProblemMemory = new ArrayList<>();   // 해당 문제를 푼 사용자들의 메모리 사용량
	private int ProblemSolvedPeople;								// 해당 문제를 해결한 유저 수
	
	private static final long serialVersionUID = 1L;				// 직렬화 버전 설정	
	
	// 기본 생성자
	public Problem() {}
	// 생성자
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
	public ArrayList<Hint> getProblemHint(int key){
		return this.ProblemHint.get(key);
	}
	public ArrayList<LearningReference> getProblemReferences(){
		return this.ProblemReferences;
	}
	public ArrayList<String> getProblemAlgorithm(){
		return this.ProblemAlgorithm;
	}
	public int getProblemSolvedPeople() {
		return this.ProblemSolvedPeople;
	}
	public ArrayList<Integer> getProblemRunTime() {
		return ProblemRunTime;
	}
	public ArrayList<Integer> getProblemMemory() {
		return ProblemMemory;
	}
	
	// 평균 런타임을 반환하는 함수
	public double getProblemAvgRunTime() {
		if(ProblemRunTime.size() == 0) {
			return 0;
		}
		int sum = 0;
	    
		for(int i = 0; i < this.ProblemRunTime.size(); i++) {
	    	sum += this.ProblemRunTime.get(i);
	    }
		System.out.println("getProblemAvgRunTime : " +ProblemRunTime);
		
		return (double)sum / this.ProblemRunTime.size();
	}
	// 평균 메모리 사용량을 반환하는 함수
	public double getProblemAvgMemory() {
		if(ProblemMemory.size() == 0) {
			return 0;
		}
		int sum = 0;
	    
		for(int i = 0; i < this.ProblemMemory.size(); i++) {
	    	sum += this.ProblemMemory.get(i);
	    }		
		return (double)sum / this.ProblemMemory.size();
	}
	
	/*
	 *  Problem에 힌트를 추가하는 함수
	 *  key : 힌트 난이도 1, 2, 3 / user : 유저 정보 / hint : 문제 힌트
	 *  key 값에 해당하는 ArrayList<Hint>을 ProblemHint 해시맵에서 가져옴
	 *  가져온 ArrayList<Hint>에 Hink 추가
	 *  changProblem 함수를 이용하여 힌트가 추가된 문제로 최신화
	 */
	public void addProblemHint(int key, User user, String hint) {
		if(!ProblemHint.containsKey(key)) { // 해당 힌트 자료가 없는 경우
			ProblemHint.put(key, new ArrayList<Hint>()); // 힌트 저장 리스트 추가
		}
		ProblemHint.get(key).add(new Hint(key, user, hint)); // 힌트 추가
        ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem에 학습 자료를 추가하는 함수
	 *  LearningReference : 학습 참고 자료 데이터
	 *  매개변수로 받은 객체 변수를 ProblemReferences 리스트에 추가
	 *  이후, changProblem 함수를 이용하여 학습 자료가 추가된 문제로 최신화
	 */
	public void addProblemReferences(User user, String referenceTitle, String plbmReferences) {
		this.ProblemReferences.add(new LearningReference(user, referenceTitle, plbmReferences));
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem에 사용자가 얻은 메모리 사용량, 런타임 정보를 추가하는 함수
	 *  Memory : 사용자가 얻은 메모리 사용량 값
	 *  RunTime : 사용자가 얻은 런타임 값
	 *  매개변수로 받은 RunTime과 Memory를 해당하는 필드에 추가, ProblemSolvedPeople 1 증가
	 *  이후, changProblem 함수를 이용하여 학습 자료가 추가된 문제로 최신화 
	 */
	public void addProblemefficiency(int RunTime, int Memory) {
		this.ProblemRunTime.add(RunTime);
		this.ProblemMemory.add(Memory);
		this.ProblemSolvedPeople += 1;
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	// 런타임의 표준편차를 계산하는 메서드
	public double getProblemStdDevRunTime() {
	    double sum = 0;
	    double mean = getProblemAvgRunTime();

	    for (int i = 0; i < this.ProblemRunTime.size(); i++) {
	        sum += Math.pow(this.ProblemRunTime.get(i) - mean, 2);
	    }

	    return Math.sqrt(sum / this.ProblemRunTime.size());
	}

	// 메모리 사용량의 표준편차를 계산하는 메서드
	public double getProblemStdDevMemory() {
	    double sum = 0;
	    double mean = getProblemAvgMemory();

	    for (int i = 0; i < this.ProblemMemory.size(); i++) {
	        sum += Math.pow(this.ProblemMemory.get(i) - mean, 2);
	    }

	    return Math.sqrt(sum / this.ProblemMemory.size());
	}
	
	// 유효한 Problem인지 확인하는 함수
	public boolean isValid() {
		if (this.ProblemID == 0 ||this.ProblemName == null || 
				this.ProblemRank == null ||this.ProblemURL == null || this.ProblemAlgorithm == null) {
			return false;
		}
		else {
			return true;
		}
	}
}