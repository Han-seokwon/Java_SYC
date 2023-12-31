package users;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import fetcher.JsonFetcher;
import file.FileManager;
import problems.Problem;
import problems.ProblemDBManager;
import problems.SolvedProblem;

//유저의 데이터를 저장하는 클래스, 기본적인 회원 정보와 유저가 해결한 문제를 저장
public class User implements Serializable{ // 객체를 바이트형태로 변환할 수 있도록 직렬화함

	private String username;
	private String solvedName; // solved.ac에 등록된 프로필 이름
	private String email;
	private String password_hashed; 
	private String selfIntroduction = "";
	private RANK rank = RANK.RANK1; // 가장 낮은 랭크부터 시작
	private int rankPoint = 0; // 티어를 올리는데 필요한 포인트(경험치)
	private int consecutiveActivityDate = 0; // 연속 출석일

	private String pwResetQuestion; 
	private String pwResetAnswer; 

	private HashSet<String> preferredAlgorithmTypeSet = new HashSet<>(); 
	private HashSet<Problem> solvedProblemSet = new HashSet<>();
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
	public User(ResistrationFormat format) {
		this.username = format.getName();
		this.solvedName = format.getSolvedName();
		this.email = format.getEmail();
		this.password_hashed = PasswordManager.hashPassword(format.getPassword(), email);
		this.pwResetQuestion = format.getResetPwQuestion();
		this.pwResetAnswer = format.getResetPwAnswer();
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
		this.solvedProblemSet = user.getSolvedProblemSet(); // 복사된 HashSet 반환됨
		this.activityDateList = new ArrayList<>(user.getActivityDateList()); 
	}

	// Getters and setters

	public String getUsername() {
		return username;
	}
	public String getSolvedName() {
		return solvedName;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword_hashed() {
		return password_hashed;
	}
	public void setPassword_hashed(String password_hashed) {
		this.password_hashed = password_hashed;
		try {
			createUpdateUserFile(); // DB 폴더에 저장된 유저 파일 업데이트 
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}	
	public String getSelfIntroduction() {
		return selfIntroduction;
	}
	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
		try {
			createUpdateUserFile(); // DB 폴더에 저장된 유저 파일 업데이트 
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

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
		return new HashSet<String>(preferredAlgorithmTypeSet);// 복사 객체 반환		
	}	
	public HashSet<Problem> getSolvedProblemSet() {
		return new HashSet<Problem>(solvedProblemSet);// 복사 객체 반환
	}	
	 // SolvedProblemSet(HashSet)을 순서를 정렬시킨 ArrayList로 반환
	public ArrayList<Problem> getSolvedProblemListSorted() {
		//복사 객체 반환
		return ProblemDBManager.findProblemToID(new ArrayList<Problem>(solvedProblemSet), true); 
	}
	public List<Date> getActivityDateList() {
		return List.of(activityDateList.toArray(new Date[0])); // 불변 리스트 반환
	}

	@Override
	public String toString() { 
		return "User [username=" + username + 
				",\n solvedName=" + solvedName + 
				",\n email=" + email + 
				",\n password_hashed=" + password_hashed +
				",\n rank="	+ rank + 
				",\n rankPoint=" + rankPoint + 
				",\nconsecutiveActivityDate = " + consecutiveActivityDate 
				+ ",\n pwResetQuestion=" + pwResetQuestion +
				",\n pwResetAnswer=" + pwResetAnswer + 
				",\n preferredAlgorithmTypeSet=" + preferredAlgorithmTypeSet +
//				",\n solvedProblemSet="	+ solvedProblemSet + // 내용이 많아 주석 처리
//				",\n activityDateList=" + activityDateList +  
				"]\n";
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
	
	// 선호하는 알고리즘타입리스트 초기화
	public void clearPreferredAlgorithmType() {
		preferredAlgorithmTypeSet = new HashSet<String>();
	}

	// 알고리즘 타입(분류)를 선호하는 알고리즘타입리스트에 추가
	public void addPreferredAlgorithmType(String type) {
		preferredAlgorithmTypeSet.add(type);
	}    

	// 유저의 랭크 포인트를 증가하고 다음 티어 진급을 위한 포인트를 넘기면 티어 상승
	// addSolvedProblem() <- updateSolvedProbleList_FromSolvedAC() 에서 호출
	public void addRankPoint( int rankPoint) {		
		RANK nextRank = rank.getNextRank(); 
		this.rankPoint += rankPoint; // 포인트 추가
		if(rank == nextRank ) { // 최고 랭크인 경우
			if(this.rankPoint > RANK.getMaxRequireRankPoint()) { // 최대 축적 가능 포인트를 초과한 경우
				this.rankPoint = RANK.getMaxRequireRankPoint(); // 최대 포인트로 함
			}
		} else { // 최고 랭크가 아닌 경우
			if (this.rankPoint >= nextRank.getRequireRankPoint()) { // 진급 가능한 경우
				rank = nextRank;
				System.out.println(rank + "로 랭크 진급");
			}
		}
	}

	// 해결된 문제를 문제 리스트에 추가하고 문제의 랭크에 맞게 포인트를 증가시킴
	// updateSolvedProbleList_FromSolvedAC()에서 호출
	public void addSolvedProblem(Problem problem) {	
		if(solvedProblemSet.add(problem)) { // 문제 추가 -> 만약 중복된 문제가 아닌 경우
			addRankPoint(problem.getProblemRank().getPointGain()); // 포인트 증가
		}    		
	}

	// 연속출석일 업데이트, today와 activityDateList의 마지막 요소를 비교해 연속출석일+1 하므로 오늘 날짜를 추가하기 전 이 메서드를 호출해야 함
	// addTodayAttendance() <- updateSolvedProbleList_FromSolvedAC() 에서 호출
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
	// 해결한 문제가 늘었을 때만 호출, updateSolvedProbleList_FromSolvedAC() 여기서 호출
	public void addTodayAttendance() {
		long miliseconds = System.currentTimeMillis();
		Date today = new Date(miliseconds);
		if(!activityDateList.contains(today)) {
			updateConsecutiveDate(today); // 연속 출석일 업데이트
			activityDateList.add(today); // 오늘 날짜 리스트에 추가    		
		}
		try {
			createUpdateUserFile(); // DB 폴더에 저장된 유저 파일 업데이트 
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}


	// solved.ac에서 해결한 문제를 가져와 업데이트, 만약 해결한 문제가 늘었다면 출석 반영 ( 회원 가입, 로그인시에 실행 )
	// return : 해결한 문제 개수
	public int updateSolvedProblemList() throws IOException{
		int solvedProblemCnt_before = solvedProblemSet.size();
		JsonFetcher.updateUserSolvedProblemList_FromSolvedAC(this); // solvedProblemList 업데이트
		int solvedProblemCnt_added = solvedProblemSet.size() - solvedProblemCnt_before; // 추가된 해결한 문제 개수
		if(solvedProblemCnt_added > 0) { // 해결한 문제가 늘었다면 출석 반영
			addTodayAttendance(); // 오늘 출석 추가
		}		
		createUpdateUserFile(); // DB 폴더에 저장된 유저 파일 업데이트
		return solvedProblemCnt_added;
	}

	// 저장된 유저 파일을 저장 또는 업데이트
	public void createUpdateUserFile() throws IOException{
		String filename = FileManager.emailToFilename(this.getEmail());
		String filepath = String.format("\\users\\UserDB\\%s.txt", filename); // 경로 지정
		// UserDB 폴더에 객체 텍스트 파일 생성
		if(!FileManager.createUpdateObjectFile(this, filepath)) { // 만약 저장되지 않은 경우 
			throw new IOException("유저 인스턴스를 직렬화 파일로 저장하지 못했습니다.");
		}
	}
}


