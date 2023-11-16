import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gui.MainFrame;
import problems.ProblemDBManager;
import users.AccountManager;
import users.User;
import users.UserDBManager;

public class Main {

	public static void main(String[] args) {
		
		// 테스트 유저 데이터
		User user = new User("qwer", "hoh9170", "qwr@na.com", "d1dd", "1. 좋아하는 동물은?", "사자");
		AccountManager.createAccount(user);
		User user2 = new User("vbnm","hoh9170", "vbnm12@na.com", "saddwq12", "2. 출신 초등학교는?", "목천초");
		AccountManager.createAccount(user2);
		User user3 = new User("tttt", "hoh9170","ttttt@na.com", "wqers1", "3. 좋아하는 음식은?", "사과");		
		AccountManager.createAccount(user3);
		User user4 = new User("한석원","hoh9170", "han@naver.com", "qwer1234", "3. 좋아하는 음식은?", "사과");		
		AccountManager.createAccount(user4);
		
		
//		// UserDB 초기화
		UserDBManager.init();
//		User user1 = UserDBManager.findUserByEmail("han@naver.com");
//		System.out.println(user1.toString());
//		
//		// UserDB 출력 
////	UserDBManager.printUserDBMap();
//		 
//		// 설문조사 데이터 로드 확인
//		Question.loadQuestionList();
//		EvaluationQuestion.loadEvaluationQuestionList();
//		
//		// 설문조사 프레임
//		new SurveyFrame(user1);
//		System.out.println(user1.toString());
//		System.out.println(user1.toString());

		// 메인 프레임 
		new MainFrame();
		
		
		// SolvedAC에서 문제 데이터 가져와서 ProblemDB에 추가하기 (900개쯤 가져오면 429에러 발생)
//		JsonFetcher.updateProblemDB_FromSolvedAC();
		

        long startTime = System.currentTimeMillis();        
		// ProblemDB 초기화
		ProblemDBManager.init();		
		System.out.println("ProblemDB 초기화 소요 시간 : " + (System.currentTimeMillis() - startTime) + "ms");   
		
//		// ProblemDB 출력
//		ProblemDBManager.PrintProblemDBMap();
//		
		
		User user1 = UserDBManager.findUserByEmail("han@naver.com");
		System.out.println("\n<<< User Data >>>\n");
		System.out.println(user1);
		
		// 날짜 갱신 확인
		
		// Get today's date
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);

        // Create a SimpleDateFormat to format dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Create a list to store java.sql.Date objects
        List<Date> sqlDateList = new ArrayList<>();

        // Loop to get dates from 10 days before today to today
        Calendar calendar = Calendar.getInstance();
        for (int i = -5; i <= -2; i++) {
            calendar.setTimeInMillis(currentTimeMillis);
            calendar.add(Calendar.DAY_OF_YEAR, i);
            Date currentDate = new Date(calendar.getTimeInMillis());
            sqlDateList.add(currentDate);
        }

        // Print the list of java.sql.Dates
        for (Date sqlDate : sqlDateList) {
        	user1.addActivityDate(sqlDate);
        }
        
		user1.updateSolvedProbleList_FromSolvedAC();

		System.out.println("\n<<< User Data >>>\n");
		System.out.println(user1);

					

	}

}
