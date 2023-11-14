import gui.EvaluationFrame;
import users.UserDBManager;

public class Main {

	public static void main(String[] args) {	
		// UserDB 초기화
		UserDBManager.init();
		
		// UserDB 출력 
//		UserDBManager.printUserDBMap();
		
		// 설문조사 데이터 로드 확인
//		Question.loadQuestionList();
//		EvaluationQuestion.loadEvaluationQuestionList();
		
		// 설문조사 프레임
		new EvaluationFrame();
		
		
//		new MainFrame();
		

		// 테스트 유저 데이터
//		User user = new User("qwer", "qwr@na.com", "d1dd", "1. 좋아하는 동물은?", "사자");
//		AccountManager.createAccount(user);
//		User user2 = new User("vbnm", "vbnm12@na.com", "saddwq12", "2. 출신 초등학교는?", "목천초");
//		AccountManager.createAccount(user2);
//		User user3 = new User("tttt", "ttttt@na.com", "wqers1", "3. 좋아하는 음식은?", "사과");		
//		AccountManager.createAccount(user3);
//		User user4 = new User("한석원", "han@naver.com", "qwer1234", "3. 좋아하는 음식은?", "사과");		
//		AccountManager.createAccount(user4);
		
		
		
		// SolvedAC에서 문제 데이터 가져와서 ProblemDB에 추가하기 (900개쯤 가져오면 429에러 발생)
//		JsonFetcher.updateProblemDB_FromSolvedAC();
		

//        long startTime = System.currentTimeMillis();        
//		// ProblemDB 초기화
//		ProblemDBManager.init();		
//		System.out.println("ProblemDB 초기화 소요 시간 : " + (System.currentTimeMillis() - startTime) + "ms");   
//		
//		// ProblemDB 출력
////		ProblemDBManager.PrintProblemDBMap();
//		
//		User user1 = UserDBManager.findUserByEmail("han@naver.com");
//		JsonFetcher.updateUserSolvedProblemList_FromSolvedAC("hoh9170", user1);
//		System.out.println("\n<<< User Data >>>\n");
//		System.out.println(user1);
		

		
		
//		// DB 초기화
//		UserDBManager.init();
//		
//		// DB 출력 
//		UserDBManager.printUserDBMap();
//		

	}

}
