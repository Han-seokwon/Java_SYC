import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import fetcher.JsonFetcher;
import gui.MainFrame;
import gui.SurveyFrame;
import problems.ProblemDBManager;
import problems.ProblemRankManager;
import users.AccountManager;
import users.EvaluationQuestion;
import users.ResistrationFormat;
import users.SurveyQuestion;
import users.User;
import users.UserDBManager;

public class Main {	
	private static void createTestUserData() { // 테스트 유저 데이터 생성
		
		try {
			// 아래 유저들은 초기 데이터로 초기화됨			
			AccountManager.createAccount(new ResistrationFormat("qwer", "hoh9170", "qwr@na.com", "d1dd", "d1dd", "1. 좋아하는 동물은?", "사자"));
			AccountManager.createAccount(new ResistrationFormat("vbnm","hoh9170", "vbnm12@na.com", "saddwq12", "saddwq12", "2. 출신 초등학교는?", "목천초"));
			AccountManager.createAccount(new ResistrationFormat("tttt", "hoh9170","ttttt@na.com", "wqers1",  "wqers1", "3. 좋아하는 음식은?", "사과"));
			AccountManager.createAccount(new ResistrationFormat("한석원","hoh9170", "han@naver.com", "qwer1234", "qwer1234", "3. 좋아하는 음식은?", "사과"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	static void initDBs() { // 프로그램 실행에 필요한 모든 DB 초기화
		// UserDB 초기화
		UserDBManager.init();
		
        long startTime = System.currentTimeMillis();        
		// ProblemDB 초기화
		ProblemDBManager.init();		
		// ProblemDB는 초기화 시간이 비교적 오래 소요되므로 시간 측정 결과 콘솔에 출력
		System.out.println("ProblemDB 초기화 소요 시간 : " + (System.currentTimeMillis() - startTime) + "ms");   
		
		ProblemRankManager.init(); // 문제 랭크 DB 초기화
	}	
	
	public static void main(String[] args) {
		
		// < 1. 데이터 생성 >
//		createTestUserData(); // 테스트 유저 데이터 생성
		// SolvedAC에서 문제 데이터 가져와서 ProblemDB에 추가하기 (900개쯤 가져오면 429에러 발생)
//		JsonFetcher.updateProblemDB_FromSolvedAC();	

		// < 2. DB 초기화 >
		initDBs(); // 프로그램 실행에 필요한 모든 DB 초기화
		
		// < 3. 메인 프레임 생성>
		// 메인 프레임 
		MainFrame main = new MainFrame();
		// 테스트를 위해 로그인한 상태로 시작
		User user1 = UserDBManager.findUserByEmail("han@naver.com"); 
		main.logInComponents(user1);
		

	
							

	}

}
