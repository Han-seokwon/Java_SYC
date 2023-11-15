package users;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import file.FileManager;
import problems.Problem;
import problems.SolvedProblem;


public class User implements Serializable{ // ��ü�� ����Ʈ���·� ��ȯ�� �� �ֵ��� ����ȭ��
    private String username;
    private String email;
    private String password_hashed; 
    private RANK rank = RANK.RANK5; // ���� ���� ��ũ���� ����
    private int rankPoint = 0; // Ƽ� �ø��µ� �ʿ��� ����Ʈ(����ġ)
    private String pwResetQuestion;
    private String pwResetAnswer;
    
    private HashSet<String> preferredAlgorithmTypeSet = new HashSet<>(); 
	private ArrayList<Problem> solvedProblemList = new ArrayList<>();
    private ArrayList<Date> activityDateList= new ArrayList<>();    
    
    private static final long serialVersionUID = 1L; // ����ȭ ���� ����
    
    // Constructor
    public User() {}
    public User(String username, String email, String password, String pwResetQuestion, String pwResetAnswer) {
        this.username = username;
        this.email = email;
        this.password_hashed = PasswordManager.hashPassword(password, email);
        this.pwResetQuestion = pwResetQuestion;
        this.pwResetAnswer = pwResetAnswer;
    }
    
    // ���� ������ ( deepcopy )
    public User(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password_hashed = user.getPassword_hashed();
        this.rank = user.getRank();
        this.rankPoint = user.getRankPoint();
        this.pwResetQuestion = user.getPwResetQuestion();
        this.pwResetAnswer = user.getPwResetAnswer();
        this.solvedProblemList = new ArrayList<>(user.getSolvedProblemList());
        this.activityDateList = new ArrayList<>(user.getActivityDateList()); 
    }
    
    // Getters and setters

	public String getUsername() {
        return username;
    }
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword_hashed() {
		return password_hashed;
	}
	public void setPassword_hashed(String password_hashed) {
		this.password_hashed = password_hashed;
	}

	public RANK getRank() {
		return RANK.valueOf(rank.name()); // ���� ���縦 ���� ���ο� ������ ����
	}
	public int getRankPoint() {
		return rankPoint; 
	}
	
	
	public String getPwResetQuestion() {
		return pwResetQuestion;
	}
	public String getPwResetAnswer() {
		return pwResetAnswer;
	}
    public HashSet<String> getPreferredAlgorithmTypeSet() {
    	return new HashSet<String>(this.preferredAlgorithmTypeSet);// ���� ��ü ��ȯ		
	}
	
	public List<Problem> getSolvedProblemList() {
		return List.of(solvedProblemList.toArray(new SolvedProblem[0]));// �Һ� ����Ʈ ��ȯ
	}
	public List<Date> getActivityDateList() {
		return List.of(activityDateList.toArray(new Date[0])); // �Һ� ����Ʈ ��ȯ
	}
	

	
    @Override
	public String toString() {
		return "User [username=" + username + ",\n email=" + email + ",\n password_hashed=" + password_hashed + ",\n rank="
				+ rank + ",\n rankPoint=" + rankPoint + ",\n pwResetQuestion=" + pwResetQuestion + ",\n pwResetAnswer="
				+ pwResetAnswer + ",\n preferredAlgorithmTypeSet=" + preferredAlgorithmTypeSet + ",\n solvedProblemList="
				+ solvedProblemList + ",\n activityDateList=" + activityDateList + "]\n";
	}
    
    // ���� �ν��Ͻ��� ��ȿ���� Ȯ��
	public static boolean isVaild(User user) {    	
    	if( user.getEmail() == null ||
    			user.getUsername() == null ||
    			user.getPassword_hashed() == null ||
    			user.getPwResetAnswer() == null) {
    		return false;
    	} else {
    		return true;
    	}
    }
	
	// ������ ��ũ ����Ʈ�� �����ϰ� ���� Ƽ�� ������ ���� ����Ʈ�� �ѱ�� Ƽ�� ���	
    public void addRankPoint( int rankPoint) {
    	this.rankPoint += rankPoint;
    	RANK nextRank = rank.getNextRank();
    	if (this.rankPoint >= nextRank.getRequireRankPoint()) {
			rank = nextRank;
		}
    }
    
    public void addPreferredAlgorithmType(String type) {
    	preferredAlgorithmTypeSet.add(type);
    }
    
    
    // �ذ�� ������ ���� ����Ʈ�� �߰���
    public void addSolvedProblemData(Problem problem) {
    	solvedProblemList.add(problem);
    }
    
	// Ȱ����¥����Ʈ�� �ش� ��¥ �߰�
    public void addActivityDate(Date date) { 
    	if(!activityDateList.contains(date)) {
    		activityDateList.add(date);    	
    	}
    }
    
    public void updateUserFile() {
		String filename = FileManager.emailToFilename(this.getEmail());
		String filepath = String.format("\\users\\UserDB\\%s.txt", filename); // ��� ����
		FileManager.createUpdateObjectFile(this, filepath); // UserDB ������ ��ü �ؽ�Ʈ ���� ����
		System.out.println(this.getEmail() + " ���� ������ ���� �Ϸ�");
    }
}

