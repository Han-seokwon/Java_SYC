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
	private static void createTestUserData() { // �׽�Ʈ ���� ������ ����
		// �Ʒ� �������� �ʱ� �����ͷ� �ʱ�ȭ��
		User user = new User("qwer", "hoh9170", "qwr@na.com", "d1dd", "1. �����ϴ� ������?", "����");
		AccountManager.createAccount(user);
		User user2 = new User("vbnm","hoh9170", "vbnm12@na.com", "saddwq12", "2. ��� �ʵ��б���?", "��õ��");
		AccountManager.createAccount(user2);
		User user3 = new User("tttt", "hoh9170","ttttt@na.com", "wqers1", "3. �����ϴ� ������?", "���");		
		AccountManager.createAccount(user3);
		User user4 = new User("�Ѽ���","hoh9170", "han@naver.com", "qwer1234", "3. �����ϴ� ������?", "���");		
		AccountManager.createAccount(user4);
	}
	
	private static void testSurveyData() { // �������� �׽�Ʈ
//		�������� ������ �ε� Ȯ��
		SurveyQuestion.loadQuestionList();
		EvaluationQuestion.loadEvaluationQuestionList();
		User user = UserDBManager.findUserByEmail("han@naver.com");		
		AccountManager.createAccount(user);
//		�������� ������
		new SurveyFrame(user);
		System.out.println(user.toString());
	}
	
	private static void testActivityDate() { // ���� Ȱ�� ��¥ ���� Ȯ��
		User user1 = UserDBManager.findUserByEmail("han@naver.com");
		System.out.println("\n<<< User Data >>>\n");
		System.out.println(user1);
		
		// ���� ��¥ ����
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);

        // ������ ���� ����
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // ���ú��� 10�� ������ Date�� ���� ActivityDate �ʵ忡 �߰�
        Calendar calendar = Calendar.getInstance();
        for (int i = -10; i <= 0; i++) {
            calendar.setTimeInMillis(currentTimeMillis);
            calendar.add(Calendar.DAY_OF_YEAR, i);
            user1.addActivityDate(new Date(calendar.getTimeInMillis()));
        }

		System.out.println("\n<<< User Data >>>\n");
		System.out.println(user1);
	}
	
	static void initDBs() { // ���α׷� ���࿡ �ʿ��� ��� DB �ʱ�ȭ
		// UserDB �ʱ�ȭ
		UserDBManager.init();
		UserDBManager.printUserDBMap(); // ���
		
        long startTime = System.currentTimeMillis();        
		// ProblemDB �ʱ�ȭ
		ProblemDBManager.init();		
		System.out.println("ProblemDB �ʱ�ȭ �ҿ� �ð� : " + (System.currentTimeMillis() - startTime) + "ms");   
		
		ProblemDBManager.PrintProblemDBMap(); // ProblemDB ���
	}
	
	
	public static void main(String[] args) {
		
		// < 1. ������ ���� >
//		createTestUserData(); // �׽�Ʈ ���� ������ ����
		// SolvedAC���� ���� ������ �����ͼ� ProblemDB�� �߰��ϱ� (900���� �������� 429���� �߻�)
		JsonFetcher.updateProblemDB_FromSolvedAC();	

		// < 2. DB �ʱ�ȭ >
//		initDBs(); // ���α׷� ���࿡ �ʿ��� ��� DB �ʱ�ȭ
//		testSurveyData();
		// < 3. ���� ������ ����>
		// ���� ������ 
//		new MainFrame();		
		
		
		// <  ��Ÿ ������ �׽�Ʈ >
//		new SurveyFrame(null);
//		new RecommendProblemFrame(null); // ���� ��õ ������
//		new RateProblemFrame(null, null);		
	
							

	}

}
