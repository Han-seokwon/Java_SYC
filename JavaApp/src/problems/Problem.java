package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import users.RANK;
import users.User;

public class Problem implements Serializable {
	
	private String ProblemName;  	// ���� �̸�
	private int ProblemID;		 	// ���� ID
	private String ProblemURL;	 	// ���� URL
	private RANK ProblemRank;	 	// ���� ��ũ
	private int ProblemRankPoint;	// ���� ��ũ ����Ʈ
	private HashMap<Integer, ArrayList<Hint>> ProblemHint = new HashMap<>();	// ���� ��Ʈ
	private ArrayList<LearningReference> ProblemReferences = new ArrayList<>();  // ���� �н��ڷ�
	private ArrayList<String> ProblemAlgorithm = new ArrayList<>();	// ���� �˰��� �з�
	private ArrayList<Integer> ProblemRunTime = new ArrayList<>();	// �ش� ������ Ǭ ����ڵ��� ���� �ð� 
	private ArrayList<Integer> ProblemMemory = new ArrayList<>();   // �ش� ������ Ǭ ����ڵ��� �޸� ��뷮
	private int ProblemSolvedPeople;								// �ش� ������ �ذ��� ���� ��
	
	private static final long serialVersionUID = 1L;				// ����ȭ ���� ����	
	
	// �⺻ ������
	public Problem() {}
	// ������
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
	
	// ��� ��Ÿ���� ��ȯ�ϴ� �Լ�
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
	// ��� �޸� ��뷮�� ��ȯ�ϴ� �Լ�
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
	 *  Problem�� ��Ʈ�� �߰��ϴ� �Լ�
	 *  key : ��Ʈ ���̵� 1, 2, 3 / user : ���� ���� / hint : ���� ��Ʈ
	 *  key ���� �ش��ϴ� ArrayList<Hint>�� ProblemHint �ؽøʿ��� ������
	 *  ������ ArrayList<Hint>�� Hink �߰�
	 *  changProblem �Լ��� �̿��Ͽ� ��Ʈ�� �߰��� ������ �ֽ�ȭ
	 */
	public void addProblemHint(int key, User user, String hint) {
		if(!ProblemHint.containsKey(key)) { // �ش� ��Ʈ �ڷᰡ ���� ���
			ProblemHint.put(key, new ArrayList<Hint>()); // ��Ʈ ���� ����Ʈ �߰�
		}
		ProblemHint.get(key).add(new Hint(key, user, hint)); // ��Ʈ �߰�
        ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem�� �н� �ڷḦ �߰��ϴ� �Լ�
	 *  LearningReference : �н� ���� �ڷ� ������
	 *  �Ű������� ���� ��ü ������ ProblemReferences ����Ʈ�� �߰�
	 *  ����, changProblem �Լ��� �̿��Ͽ� �н� �ڷᰡ �߰��� ������ �ֽ�ȭ
	 */
	public void addProblemReferences(User user, String referenceTitle, String plbmReferences) {
		this.ProblemReferences.add(new LearningReference(user, referenceTitle, plbmReferences));
		ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem�� ����ڰ� ���� �޸� ��뷮, ��Ÿ�� ������ �߰��ϴ� �Լ�
	 *  Memory : ����ڰ� ���� �޸� ��뷮 ��
	 *  RunTime : ����ڰ� ���� ��Ÿ�� ��
	 *  �Ű������� ���� RunTime�� Memory�� �ش��ϴ� �ʵ忡 �߰�, ProblemSolvedPeople 1 ����
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
		if (this.ProblemID == 0 ||this.ProblemName == null || 
				this.ProblemRank == null ||this.ProblemURL == null || this.ProblemAlgorithm == null) {
			return false;
		}
		else {
			return true;
		}
	}
}