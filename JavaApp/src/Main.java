import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import fetcher.JsonFetcher;
import gui.RecommendProblemFrame;
import gui.SurveyFrame;
import problems.ProblemDBManager;
import users.AccountManager;
import users.EvaluationQuestion;
import users.SurveyQuestion;
import users.User;
import users.UserDBManager;

public class Main {	
	private static void createTestUserData() { // 테스트 유저 데이터 생성
		// 아래 유저들은 초기 데이터로 초기화됨
		User user = new User("qwer", "hoh9170", "qwr@na.com", "d1dd", "1. 좋아하는 동물은?", "사자");
		AccountManager.createAccount(user);
		User user2 = new User("vbnm","hoh9170", "vbnm12@na.com", "saddwq12", "2. 출신 초등학교는?", "목천초");
		AccountManager.createAccount(user2);
		User user3 = new User("tttt", "hoh9170","ttttt@na.com", "wqers1", "3. 좋아하는 음식은?", "사과");		
		AccountManager.createAccount(user3);
		User user4 = new User("한석원","hoh9170", "han@naver.com", "qwer1234", "3. 좋아하는 음식은?", "사과");		
		AccountManager.createAccount(user4);
	}
	
	private static void testSurveyData() { // 설문조사 테스트
//		설문조사 데이터 로드 확인
		SurveyQuestion.loadQuestionList();
		EvaluationQuestion.loadEvaluationQuestionList();
		User user = UserDBManager.findUserByEmail("han@naver.com");		
		AccountManager.createAccount(user);
//		설문조사 프레임
		new SurveyFrame(user);
		System.out.println(user.toString());
	}
	
	private static void testActivityDate() { // 유저 활동 날짜 갱신 확인
		User user1 = UserDBManager.findUserByEmail("han@naver.com");
		System.out.println("\n<<< User Data >>>\n");
		System.out.println(user1);
		
		// 오늘 날짜 생성
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);

        // 데이터 포멧 생성
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 오늘부터 10일 전까지 Date를 유저 ActivityDate 필드에 추가
        Calendar calendar = Calendar.getInstance();
        for (int i = -10; i <= 0; i++) {
            calendar.setTimeInMillis(currentTimeMillis);
            calendar.add(Calendar.DAY_OF_YEAR, i);
            user1.addActivityDate(new Date(calendar.getTimeInMillis()));
        }

		System.out.println("\n<<< User Data >>>\n");
		System.out.println(user1);
	}
	
	static void initDBs() { // 프로그램 실행에 필요한 모든 DB 초기화
		// UserDB 초기화
		UserDBManager.init();
		UserDBManager.printUserDBMap(); // 출력
		
        long startTime = System.currentTimeMillis();        
		// ProblemDB 초기화
		ProblemDBManager.init();		
		System.out.println("ProblemDB 초기화 소요 시간 : " + (System.currentTimeMillis() - startTime) + "ms");   
		
		ProblemDBManager.PrintProblemDBMap(); // ProblemDB 출력
	}
	
	
	public static void main(String[] args) {
		
		// < 1. 데이터 생성 >
//		createTestUserData(); // 테스트 유저 데이터 생성
		// SolvedAC에서 문제 데이터 가져와서 ProblemDB에 추가하기 (900개쯤 가져오면 429에러 발생)
		JsonFetcher.updateProblemDB_FromSolvedAC();	

		// < 2. DB 초기화 >
//		initDBs(); // 프로그램 실행에 필요한 모든 DB 초기화
//		testSurveyData();
		// < 3. 메인 프레임 생성>
		// 메인 프레임 
//		new MainFrame();		
		
		
		// <  기타 프레임 테스트 >
//		new SurveyFrame(null);
//		new RecommendProblemFrame(null); // 문제 추천 프레임
//		new RateProblemFrame(null, null);		
	
							

	}

}
