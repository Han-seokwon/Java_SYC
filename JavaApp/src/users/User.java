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


public class User implements Serializable{ // 객체를 바이트형태로 변환할 수 있도록 직렬화함
	
    private String username;
    private String solvedName; // solved.ac에 등록된 프로필 이름
    private String email;
    private String password_hashed; 
    private RANK rank = RANK.RANK5; // 가장 낮은 랭크부터 시작
    private int rankPoint = 0; // 티어를 올리는데 필요한 포인트(경험치)
    private int consecutiveActivityDate = 0; // 연속 출석일

	private String pwResetQuestion;
    private String pwResetAnswer;
    
    private HashSet<String> preferredAlgorithmTypeSet = new HashSet<>(); 
	private ArrayList<Problem> solvedProblemList = new ArrayList<>();
    private ArrayList<Date> activityDateList= new ArrayList<>();    
    
    private static final long serialVersionUID = 1L; // 직렬화 버전 설정
    
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
    
    // 복사 생성자 ( deepcopy )
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
		return RANK.valueOf(rank.name()); // 깊은 복사를 위해 새로운 열거형 생성
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
		return "User [username=" + username + ",\n solvedName=" + solvedName + ",\n email=" + email + ",\n password_hashed=" + password_hashed + ",\n rank="
				+ rank + ",\n rankPoint=" + rankPoint + ",\nconsecutiveActivityDate = " + consecutiveActivityDate + ",\n pwResetQuestion=" + pwResetQuestion + ",\n pwResetAnswer="
				+ pwResetAnswer + ",\n preferredAlgorithmTypeSet=" + preferredAlgorithmTypeSet + ",\n solvedProblemList="
				+ solvedProblemList + ",\n activityDateList=" + activityDateList + "]\n";
	}
    
    // 유저 인스턴스가 유효한지 확인
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
	
	// 유저의 랭크 포인트를 증가하고 다음 티어 진급을 위한 포인트를 넘기면 티어 상승	
    public void addRankPoint( int rankPoint) {
    	this.rankPoint += rankPoint;
    	RANK nextRank = rank.getNextRank();
    	if (this.rankPoint >= nextRank.getRequireRankPoint()) {
			rank = nextRank;
			System.out.println(rank + "로 랭크 진급");
		}
    }
    
    // 알고리즘 타입(분류)를 선호하는 알고리즘타입리스트에 추가
    public void addPreferredAlgorithmType(String type) {
    	preferredAlgorithmTypeSet.add(type);
    }    
    
    // 해결된 문제를 문제 리스트에 추가하고 문제의 랭크에 맞게 포인트를 증가시킴
    public void addSolvedProblem(Problem problem) {
    	solvedProblemList.add(problem);
    	addRankPoint(problem.getProblemRank().getPointGain()); // 포인트 증가    	
    }
    
    // 연속출석일 업데이트, today와 activityDateList의 마지막 요소를 비교해 연속출석일+1 하므로 오늘 날짜를 추가하기 전 이 메서드를 호출해야 함
    private void updateConsecutiveDate(Date today) {
    	if(activityDateList.size() == 0) { // 아직 추가된 날짜가 없는 경우
    		consecutiveActivityDate++;
    		return; // 메서드 종료
    	}
    	Date before = activityDateList.get(activityDateList.size() - 1); // before : (오늘 제외)가장 최근에 출석한 날짜
        // 밀리초로 변환하여 차이 계산
        long difference_ms = Math.abs(today.getTime() - before.getTime());
        // 일 단위로 변환
        long daysDifference = difference_ms / (24 * 60 * 60 * 1000);
        if(daysDifference == 1L) {// 차이가 1이라면 두 날짜는 연속
        	consecutiveActivityDate++;
        } else {
        	consecutiveActivityDate = 1;
        }
    }
    
    // 오늘 출석이 되어 있지 않으면 Date를 추가, 연속 출석 업데이트
    // (notice) 해결한 문제가 늘었을 때만 호출, updateSolvedProbleList_FromSolvedAC() 여기서 호출
    public void addTodayAttendance() {
	    long miliseconds = System.currentTimeMillis();
	    Date today = new Date(miliseconds);
    	if(!activityDateList.contains(today)) {
    		updateConsecutiveDate(today); // 연속 출석일 업데이트
    		activityDateList.add(today); // 오늘 날짜 리스트에 추가    		
    	}
    }
    
	// (테스트용) 활동날짜리스트에 해당 날짜 추가
    // 특정 일자를 입력하게 하면 리스트 내에서 날짜 순서가 바뀔 수 있음, 
    // 따라서 addTodayAttendance()만 남기고 이 메서드는 추후 삭제
    public void addActivityDate(Date date) { 
    	if(!activityDateList.contains(date)) {
    		updateConsecutiveDate(date); // 연속 출석일 업데이트
    		activityDateList.add(date); // 오늘 날짜 리스트에 추가    		  	
    	}
    }
    
    // solved.ac에서 해결한 문제를 가져와 업데이트, 만약 해결한 문제가 늘었다면 출석 반영
    // 회원 가입, 로그인시에 실행
    public void updateSolvedProbleList_FromSolvedAC() {
    	int solvedProblemCnt_before = solvedProblemList.size();
    	JsonFetcher.updateUserSolvedProblemList_FromSolvedAC(this); // solvedProblemList 업데이트
    	if(solvedProblemList.size() > solvedProblemCnt_before) { // 해결한 문제가 늘었다면 출석 반영
    	    addTodayAttendance(); // 오늘 출석 추가
    	}
    	
    }
    
    // 저장된 유저 파일을 업데이트(없는 경우 생성)
    public void updateUserFile() {
		String filename = FileManager.emailToFilename(this.getEmail());
		String filepath = String.format("\\users\\UserDB\\%s.txt", filename); // 경로 지정
		FileManager.createUpdateObjectFile(this, filepath); // UserDB 폴더에 객체 텍스트 파일 생성
		System.out.println(this.getEmail() + " 유저 데이터 저장 완료");
    }
}

