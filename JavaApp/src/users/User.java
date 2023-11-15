package users;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import file.FileManager;
import problems.Problem;
import problems.SolvedProblem;


public class User implements Serializable{ // 객체를 바이트형태로 변환할 수 있도록 직렬화함
    private String username;
    private String email;
    private String password_hashed; 
    private RANK rank = RANK.RANK5; // 가장 낮은 랭크부터 시작
    private int rankPoint = 0; // 티어를 올리는데 필요한 포인트(경험치)
    private String pwResetQuestion;
    private String pwResetAnswer;
    
    private HashSet<String> preferredAlgorithmTypeSet = new HashSet<>(); 
	private ArrayList<Problem> solvedProblemList = new ArrayList<>();
    private ArrayList<Date> activityDateList= new ArrayList<>();    
    
    private static final long serialVersionUID = 1L; // 직렬화 버전 설정
    
    // Constructor
    public User() {}
    public User(String username, String email, String password, String pwResetQuestion, String pwResetAnswer) {
        this.username = username;
        this.email = email;
        this.password_hashed = PasswordManager.hashPassword(password, email);
        this.pwResetQuestion = pwResetQuestion;
        this.pwResetAnswer = pwResetAnswer;
    }
    
    // 복사 생성자 ( deepcopy )
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
		return RANK.valueOf(rank.name()); // 깊은 복사를 위해 새로운 열거형 생성
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
    	return new HashSet<String>(this.preferredAlgorithmTypeSet);// 복사 객체 반환		
	}
	
	public List<Problem> getSolvedProblemList() {
		return List.of(solvedProblemList.toArray(new SolvedProblem[0]));// 불변 리스트 반환
	}
	public List<Date> getActivityDateList() {
		return List.of(activityDateList.toArray(new Date[0])); // 불변 리스트 반환
	}
	

	
    @Override
	public String toString() {
		return "User [username=" + username + ",\n email=" + email + ",\n password_hashed=" + password_hashed + ",\n rank="
				+ rank + ",\n rankPoint=" + rankPoint + ",\n pwResetQuestion=" + pwResetQuestion + ",\n pwResetAnswer="
				+ pwResetAnswer + ",\n preferredAlgorithmTypeSet=" + preferredAlgorithmTypeSet + ",\n solvedProblemList="
				+ solvedProblemList + ",\n activityDateList=" + activityDateList + "]\n";
	}
    
    // 유저 인스턴스가 유효한지 확인
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
	
	// 유저의 랭크 포인트를 증가하고 다음 티어 진급을 위한 포인트를 넘기면 티어 상승	
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
    
    
    // 해결된 문제를 문제 리스트에 추가함
    public void addSolvedProblemData(Problem problem) {
    	solvedProblemList.add(problem);
    }
    
	// 활동날짜리스트에 해당 날짜 추가
    public void addActivityDate(Date date) { 
    	if(!activityDateList.contains(date)) {
    		activityDateList.add(date);    	
    	}
    }
    
    public void updateUserFile() {
		String filename = FileManager.emailToFilename(this.getEmail());
		String filepath = String.format("\\users\\UserDB\\%s.txt", filename); // 경로 지정
		FileManager.createUpdateObjectFile(this, filepath); // UserDB 폴더에 객체 텍스트 파일 생성
		System.out.println(this.getEmail() + " 유저 데이터 저장 완료");
    }
}

