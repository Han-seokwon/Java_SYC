package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import users.RANK;

public class Problem implements Serializable {
	
	// ���� �̸�, ���� ID, ���� URL, ���� ��ũ, ���� ��ũ ����Ʈ
	private String ProblemName;
	private int ProblemID;
	private String ProblemURL;
	private RANK ProblemRank;
	private int ProblemRankPoint;
	
	/*
	 * Step 1, Step 2, Step 3�� Ű ������ Ž��
	 * Ű���� �ش��ϴ� ProblemHint ��ü ����Ʈ�� ����
	 */
	private HashMap<String, List<HintSave>> ProblemHint = new HashMap<>();		
	
	// �н��ڷ�, ���� �˰��� �з�
	private List<LearningMaterialsSave> ProblemReferences = new ArrayList<>();
	private ArrayList<String> ProblemAlgorithm = new ArrayList<>();
	private ArrayList<Integer> ProblemRunTime = new ArrayList<>();
	private ArrayList<Integer> ProblemMemory = new ArrayList<>();	
	
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
	public List<HintSave> getProblemHint(String key){
		return this.ProblemHint.get(key);
	}
	public List<LearningMaterialsSave> getProblemReferences(){
		return this.ProblemReferences;
	}
	public ArrayList<String> getProblemAlgorithm(){
		return this.ProblemAlgorithm;
	}
	// ��� ��Ÿ�Ӱ� ��� �޸� ��뷮�� ��ȯ�ϴ� �Լ�
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
	 *  Problem�� ��Ʈ�� �߰��ϴ� �Լ�
	 *  key : 'Step 1', 'Step 2', 'Step 3' / plbmHint : HintSave ��ü ����
	 *  key ���� �ش��ϴ� ����Ʈ�� ProblemHint �ؽøʿ��� ������
	 *  ����Ʈ�� �Ѱܹ��� HintSave ��ü ������ �߰�
	 *  ����, �ش� Ű���� ����Ʈ�� ProblemHint �ؽøʿ� �߰�
	 *  changProblem �Լ��� �̿��Ͽ� ��Ʈ�� �߰��� ������ �ֽ�ȭ
	 */
	public void addProblemHint(String key, HintSave plbmHint) {
	    List<HintSave> hintList = ProblemHint.getOrDefault(key, new ArrayList<>());
        hintList.add(plbmHint);
        ProblemHint.put(key, hintList);
        ProblemDBManager.changeProblem(this.getProblemID(), this);
	}
	
	/*
	 *  Problem�� �н� �ڷḦ �߰��ϴ� �Լ�
	 *  plbmReferences : LearningMaterialsSave ��ü ����
	 *  �Ű������� ���� ��ü ������ ProblemReferences ����Ʈ�� �߰�
	 *  ����, changProblem �Լ��� �̿��Ͽ� �н� �ڷᰡ �߰��� ������ �ֽ�ȭ
	 */
	public void addProblemReferences(LearningMaterialsSave plbmReferences) {
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