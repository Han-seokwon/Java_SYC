package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import users.RANK;

public class Problem implements Serializable {
	
	// 문제 이름, 문제 ID, 문제 URL(백준), 문제 랭크, 문제 랭크 포인트
	private String ProblemName;
	private int ProblemID;
	private String ProblemURL;
	private RANK ProblemRank;
	private int ProblemRankPoint;
	/*
	 * Step 1, Step 2, Step 3를 키 값으로 탐색
	 * 키값에 해당하는 ProblemHint 객체 리스트를 가짐
	 */
	private HashMap<String, List<HintSave>> ProblemHint = new HashMap<>();		
	// 학습자료 URL, 문제 알고리즘 분류
	private List<LearningMaterialsSave> ProblemReferences = new ArrayList<>();
	private ArrayList<String> ProblemAlgorithm = new ArrayList<>();
	
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
	    sb.append("Problem ID: ").append(ProblemID).append("\n");
	    sb.append("Problem Name: ").append(ProblemName).append("\n");
	    sb.append("Problem URL: ").append(ProblemURL).append("\n");
	    sb.append("Problem Rank: ").append(ProblemRank).append("\n");
	    sb.append("Problem RankPoint: ").append(ProblemRankPoint).append("\n");

	    sb.append("Problem Algorithms: ");
	    for (String algorithm : ProblemAlgorithm) {
	        sb.append(algorithm).append(" ");
	    }

	    return sb.toString();
	}
	
	// 설정자
	public void setProblemRank(RANK Rank) {
		this.ProblemRank = Rank;
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
	public List<HintSave> getProblemHint(String key){
		return this.ProblemHint.get(key);
	}
	public List<LearningMaterialsSave> getProblemReferences(){
		return this.ProblemReferences;
	}
	public ArrayList<String> getProblemAlgorithm(){
		return this.ProblemAlgorithm;
	}
	
	// 문제 힌트 추가
	public void addProblemHint(String key, HintSave plbmHint) {
	    if(plbmHint.isValid()) {
	    	List<HintSave> hintList = ProblemHint.getOrDefault(key, new ArrayList<>());
            hintList.add(plbmHint);
            ProblemHint.put(key, hintList);
	    }
	}
	// 학습 자료 추가
	public void addProblemReferences(LearningMaterialsSave plbmReferences) {
		this.ProblemReferences.add(plbmReferences);
	}
	
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
