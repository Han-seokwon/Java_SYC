package users;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import fetcher.JsonFetcher;
import file.FileManager;
import problems.Problem;
import problems.SolvedProblem;


public class User implements Serializable{ // ��ü�� ����Ʈ���·� ��ȯ�� �� �ֵ��� ����ȭ��
	
    private String username;
    private String solvedName; // solved.ac�� ��ϵ� ������ �̸�
    private String email;
    private String password_hashed; 
    private RANK rank = RANK.RANK5; // ���� ���� ��ũ���� ����
    private int rankPoint = 0; // Ƽ� �ø��µ� �ʿ��� ����Ʈ(����ġ)
    private int consecutiveActivityDate = 0; // ���� �⼮��

	private String pwResetQuestion;
    private String pwResetAnswer;
    
    private HashSet<String> preferredAlgorithmTypeSet = new HashSet<>(); 
	private ArrayList<Problem> solvedProblemList = new ArrayList<>();
    private ArrayList<Date> activityDateList= new ArrayList<>();    
    
    private static final long serialVersionUID = 1L; // ����ȭ ���� ����
    
    // Constructor
    public User() {}
    public User(String username, String solvedName, String email, String password, String pwResetQuestion, String pwResetAnswer) {
        this.username = username;
        this.solvedName = solvedName;
        this.email = email;
        this.password_hashed = PasswordManager.hashPassword(password, email);
        this.pwResetQuestion = pwResetQuestion;
        this.pwResetAnswer = pwResetAnswer;
    }
    public User(ResisterationFormat format) {
        this.username = format.getName();
        this.solvedName = format.getSolvedName();
        this.email = format.getEmail();
        this.password_hashed = PasswordManager.hashPassword(format.getPassword(), email);
        this.pwResetQuestion = format.getResetPwQuestion();
        this.pwResetAnswer = format.getAnswer();
    }
    
    // ���� ������ ( deepcopy )
    public User(User user) {
        this.username = user.getUsername();
        this.solvedName = user.getSolvedName();
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
	public String getSolvedName() {
		return solvedName;
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
    public int getConsecutiveActivityDate() {
		return consecutiveActivityDate;
	}
	public void setConsecutiveActivityDate(int consecutiveActivityDate) {
		this.consecutiveActivityDate = consecutiveActivityDate;
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
		return "User [username=" + username + ",\n solvedName=" + solvedName + ",\n email=" + email + ",\n password_hashed=" + password_hashed + ",\n rank="
				+ rank + ",\n rankPoint=" + rankPoint + ",\nconsecutiveActivityDate = " + consecutiveActivityDate + ",\n pwResetQuestion=" + pwResetQuestion + ",\n pwResetAnswer="
				+ pwResetAnswer + ",\n preferredAlgorithmTypeSet=" + preferredAlgorithmTypeSet + ",\n solvedProblemList="
				+ solvedProblemList + ",\n activityDateList=" + activityDateList + "]\n";
	}
    
    // ���� �ν��Ͻ��� ��ȿ���� Ȯ��
	public static boolean isVaild(User user) {    	
    	if( user.getEmail() == null ||
    			user.getUsername() == null ||
    			user.getSolvedName() == null ||
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
			System.out.println(rank + "�� ��ũ ����");
		}
    }
    
    // �˰��� Ÿ��(�з�)�� ��ȣ�ϴ� �˰���Ÿ�Ը���Ʈ�� �߰�
    public void addPreferredAlgorithmType(String type) {
    	preferredAlgorithmTypeSet.add(type);
    }    
    
    // �ذ�� ������ ���� ����Ʈ�� �߰��ϰ� ������ ��ũ�� �°� ����Ʈ�� ������Ŵ
    public void addSolvedProblem(Problem problem) {
    	solvedProblemList.add(problem);
    	addRankPoint(problem.getProblemRank().getPointGain()); // ����Ʈ ����    	
    }
    
    // �����⼮�� ������Ʈ, today�� activityDateList�� ������ ��Ҹ� ���� �����⼮��+1 �ϹǷ� ���� ��¥�� �߰��ϱ� �� �� �޼��带 ȣ���ؾ� ��
    private void updateConsecutiveDate(Date today) {
    	if(activityDateList.size() == 0) { // ���� �߰��� ��¥�� ���� ���
    		consecutiveActivityDate++;
    		return; // �޼��� ����
    	}
    	Date before = activityDateList.get(activityDateList.size() - 1); // before : (���� ����)���� �ֱٿ� �⼮�� ��¥
        // �и��ʷ� ��ȯ�Ͽ� ���� ���
        long difference_ms = Math.abs(today.getTime() - before.getTime());
        // �� ������ ��ȯ
        long daysDifference = difference_ms / (24 * 60 * 60 * 1000);
        if(daysDifference == 1L) {// ���̰� 1�̶�� �� ��¥�� ����
        	consecutiveActivityDate++;
        } else {
        	consecutiveActivityDate = 1;
        }
    }
    
    // ���� �⼮�� �Ǿ� ���� ������ Date�� �߰�, ���� �⼮ ������Ʈ
    // (notice) �ذ��� ������ �þ��� ���� ȣ��, updateSolvedProbleList_FromSolvedAC() ���⼭ ȣ��
    public void addTodayAttendance() {
	    long miliseconds = System.currentTimeMillis();
	    Date today = new Date(miliseconds);
    	if(!activityDateList.contains(today)) {
    		updateConsecutiveDate(today); // ���� �⼮�� ������Ʈ
    		activityDateList.add(today); // ���� ��¥ ����Ʈ�� �߰�    		
    	}
    }
    
	// (�׽�Ʈ��) Ȱ����¥����Ʈ�� �ش� ��¥ �߰�
    // Ư�� ���ڸ� �Է��ϰ� �ϸ� ����Ʈ ������ ��¥ ������ �ٲ� �� ����, 
    // ���� addTodayAttendance()�� ����� �� �޼���� ���� ����
    public void addActivityDate(Date date) { 
    	if(!activityDateList.contains(date)) {
    		updateConsecutiveDate(date); // ���� �⼮�� ������Ʈ
    		activityDateList.add(date); // ���� ��¥ ����Ʈ�� �߰�    		  	
    	}
    }
    
    // solved.ac���� �ذ��� ������ ������ ������Ʈ, ���� �ذ��� ������ �þ��ٸ� �⼮ �ݿ�
    // ȸ�� ����, �α��νÿ� ����
    public void updateSolvedProbleList_FromSolvedAC() {
    	int solvedProblemCnt_before = solvedProblemList.size();
    	JsonFetcher.updateUserSolvedProblemList_FromSolvedAC(this); // solvedProblemList ������Ʈ
    	if(solvedProblemList.size() > solvedProblemCnt_before) { // �ذ��� ������ �þ��ٸ� �⼮ �ݿ�
    	    addTodayAttendance(); // ���� �⼮ �߰�
    	}
    	
    }
    
    // ����� ���� ������ ������Ʈ(���� ��� ����)
    public void updateUserFile() {
		String filename = FileManager.emailToFilename(this.getEmail());
		String filepath = String.format("\\users\\UserDB\\%s.txt", filename); // ��� ����
		FileManager.createUpdateObjectFile(this, filepath); // UserDB ������ ��ü �ؽ�Ʈ ���� ����
		System.out.println(this.getEmail() + " ���� ������ ���� �Ϸ�");
    }
}

