package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import users.RANK;
import users.User;

public class Problem implements Serializable {
	
	// 문제 이름, 문제 ID, 문제 URL(백준), 문제 랭크, 문제 랭크 포인트
	private String ProblemName;
	private int ProblemID;
	private String ProblemURL;
	private RANK ProblemRank;
	private int ProblemRankPoint;
	/*
	 * Step 1, Step 2, Step 3, Algorithm Hint를 키 값으로 탐색
	 * 유저의 정보를 키 값으로 가지고 힌트를 값으로 가지는 해시맵을 값으로 가짐
	 */
	private HashMap<String, HashMap<User, String>> ProblemHint = new HashMap<>();
	// 학습자료 URL, 문제 알고리즘 분류
	private ArrayList<String> ProblemReferences = new ArrayList<>();
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
	public Problem(Problem problem) {
		this.ProblemName = problem.ProblemName;
		this.ProblemID = problem.ProblemID;
		this.ProblemURL = problem.ProblemURL;
		this.ProblemRank = problem.ProblemRank;
		this.ProblemAlgorithm = problem.ProblemAlgorithm;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Problem ID: ").append(ProblemID).append("\n");
	    sb.append("Problem Name: ").append(ProblemName).append("\n");
	    sb.append("Problem URL: ").append(ProblemURL).append("\n");
	    sb.append("Problem Rank: ").append(ProblemRank).append("\n");
	    sb.append("Problem RankPoint: ").append(ProblemRankPoint).append("\n");

	    sb.append("Problem Hints: \n");
	    for (String hintKey : ProblemHint.keySet()) {
	        sb.append("  ").append(hintKey).append(":\n");
	        HashMap<User, String> hintMap = ProblemHint.get(hintKey);
	        for (User user : hintMap.keySet()) {
	            String hint = hintMap.get(user);
	            sb.append("    ").append(user.getUsername()).append(": ").append(hint).append("\n");
	        }
	    }

	    sb.append("Problem References: \n");
	    for (String reference : ProblemReferences) {
	        sb.append("  ").append(reference).append("\n");
	    }

	    sb.append("Problem Algorithms: \n");
	    for (String algorithm : ProblemAlgorithm) {
	        sb.append("  ").append(algorithm).append("\n");
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
	public HashMap<User, String> getProblemHint(String key) {
	    HashMap<User, String> userHintMap = this.ProblemHint.get(key);
	    
	    if (userHintMap == null) {
	        return null;
	    }
	    
	    return userHintMap;
	}
	public ArrayList<String> getProblemReferences(){
		return this.ProblemReferences;
	}
	public ArrayList<String> getProblemAlgorithm(){
		return this.ProblemAlgorithm;
	}
	
	// 문제 힌트, 학습자료 추가
	public void addProblemHint(String key, User user, String hint) {
	    HashMap<User, String> userHintMap = this.ProblemHint.get(key);
	    
	    if (userHintMap == null) {
	        userHintMap = new HashMap<>();
	        this.ProblemHint.put(key, userHintMap);
	    }

	    userHintMap.put(user, hint);
	}
	
	public void addProblemReferences(String References) {
		this.ProblemReferences.add(References);
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
