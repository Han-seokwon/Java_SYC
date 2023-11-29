package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import users.RANK;
import users.User;

public class Problem implements Serializable {
	
	// ���� �̸�, ���� ID, ���� URL, ���� ��ũ, ���� ��ũ ����Ʈ
	private String ProblemName;
	private int ProblemID;
	private String ProblemURL;
	private RANK ProblemRank;
	private int ProblemRankPoint;
	
	/*
	 * Step 1, Step 2, Step 3�� Ű ������ Ž��
	 * Ű���� �ش��ϴ� <User, String> �ؽø�
	 */
	private HashMap<String, HashMap<User, String>> ProblemHint = new HashMap<>();		
	
	// �н��ڷ�, ���� �˰��� �з�
	private List<String> ProblemReferences = new ArrayList<>();
	private ArrayList<String> ProblemAlgorithm = new ArrayList<>();
	private ArrayList<Integer> ProblemRunTime = new ArrayList<>();
	private ArrayList<Integer> ProblemMemory = new ArrayList<>();
	// �ش� ������ �ذ��� ���� ��
	private int ProblemSolvedPeople;
	
	private static final long serialVersionUID = 1L;	
	// ������
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
	    sb.append("���� ��ȣ : ").append(ProblemID).append("\n");
	    sb.append("���� : ").append(ProblemName).append("\n");
	    sb.append("���̵� : ").append(ProblemRank).append("(").append(ProblemRankPoint).append(")\n");
	    sb.append("ȹ�� ��ũ ����Ʈ: ").append(ProblemRank.getPointGain()).append("\n");
	    sb.append("URL: ").append(ProblemURL).append("\n");
	    sb.append("�˰��� : ");
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

	
	// ������
	public void setProblemRank(RANK Rank) {
		this.ProblemRank = Rank;
	}
	public void setProblemRankPoint(int Point) {
		this.ProblemRankPoint = Point;
	}
	// ������
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
	public int getProblemSolvedPeople() {
		return this.ProblemSolvedPeople;
	}
	// ��� ��Ÿ�Ӱ� ��� �޸� ��뷮�� ��ȯ�ϴ� �Լ�
	public double getProblemAvgRunTime() {
		int sum = 0;
	    
		for(int i = 0; i < this.ProblemRunTime.size(); i++) {
	    	sum += this.ProblemRunTime.get(i);
	    }
		
		return sum / this.ProblemRunTime.size();
	}
	
	public double getProblemAvgMemory() {
		int sum = 0;
	    
		for(int i = 0; i < this.ProblemMemory.size(); i++) {
	    	sum += this.ProblemMemory.get(i);
	    }		
		return sum / this.ProblemMemory.size();
	}
	
	/*
	 *  Problem�� ��Ʈ�� �߰��ϴ� �Լ�
	 *  key : 'Step 1', 'Step 2', 'Step 3' / user : ���� ���� / hint : ���� ��Ʈ
	 *  key ���� �ش��ϴ� �ؽø��� ProblemHint �ؽøʿ��� ������
	 *  ������ �ؽø�(hintList)�� user�� Ű������, hint�� ������ ����
	 *  ����, �ش� Ű���� �ؽø�(hintList)�� ProblemHint �ؽøʿ� �߰�
	 *  changProblem �Լ��� �̿��Ͽ� ��Ʈ�� �߰��� ������ �ֽ�ȭ
	 */
	public void addProblemHint(String key, User user, String hint) {
	    HashMap<User, String> hintList = ProblemHint.getOrDefault(key, new HashMap<User, String>());
        hintList.put(user, hint);
        ProblemHint.put(key, hintList);
        ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem�� �н� �ڷḦ �߰��ϴ� �Լ�
	 *  plbmReferences : �н��ڷ� ���ڿ�
	 *  �Ű������� ���� ��ü ������ ProblemReferences ����Ʈ�� �߰�
	 *  ����, changProblem �Լ��� �̿��Ͽ� �н� �ڷᰡ �߰��� ������ �ֽ�ȭ
	 */
	public void addProblemReferences(String plbmReferences) {
		this.ProblemReferences.add(plbmReferences);
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem�� ����ڰ� ���� ��Ÿ�� ������ �߰��ϴ� �Լ�
	 *  RunTime : ����ڰ� ���� ��Ÿ�� ��
	 *  �Ű������� ���� RunTime�� ProblemRunTime ����Ʈ�� �߰�
	 *  ����, changProblem �Լ��� �̿��Ͽ� �н� �ڷᰡ �߰��� ������ �ֽ�ȭ 
	 */
	public void addProblemRunTime(int RunTime) {
		this.ProblemRunTime.add(RunTime);
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem�� ����ڰ� ���� �޸� ��뷮 ������ �߰��ϴ� �Լ�
	 *  Memory : ����ڰ� ���� �޸� ��뷮 ��
	 *  �Ű������� ���� Memory�� ProblemMemory ����Ʈ�� �߰�
	 *  ����, changProblem �Լ��� �̿��Ͽ� �н� �ڷᰡ �߰��� ������ �ֽ�ȭ 
	 */
	public void addProblemMemory(int Memory) {
		this.ProblemMemory.add(Memory);
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem�� ����ڰ� ���� �޸� ��뷮, ��Ÿ�� ������ �߰��ϴ� �Լ�
	 *  Memory : ����ڰ� ���� �޸� ��뷮 ��
	 *  RunTime : ����ڰ� ���� ��Ÿ�� ��
	 *  �Ű������� ���� Memory�� ProblemMemory ����Ʈ�� �߰�
	 *  ����, changProblem �Լ��� �̿��Ͽ� �н� �ڷᰡ �߰��� ������ �ֽ�ȭ 
	 */
	public void addProblemefficiency(int RunTime, int Memory) {
		this.ProblemRunTime.add(RunTime);
		this.ProblemMemory.add(Memory);
		this.ProblemSolvedPeople += 1;
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	// ��Ÿ���� ǥ�������� ����ϴ� �޼���
	public double getProblemStdDevRunTime() {
	    double sum = 0;
	    double mean = getProblemAvgRunTime();

	    for (int i = 0; i < this.ProblemRunTime.size(); i++) {
	        sum += Math.pow(this.ProblemRunTime.get(i) - mean, 2);
	    }

	    return Math.sqrt(sum / this.ProblemRunTime.size());
	}

	// �޸� ��뷮�� ǥ�������� ����ϴ� �޼���
	public double getProblemStdDevMemory() {
	    double sum = 0;
	    double mean = getProblemAvgMemory();

	    for (int i = 0; i < this.ProblemMemory.size(); i++) {
	        sum += Math.pow(this.ProblemMemory.get(i) - mean, 2);
	    }

	    return Math.sqrt(sum / this.ProblemMemory.size());
	}
	
	// ��ȿ�� Problem���� Ȯ���ϴ� �Լ�
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