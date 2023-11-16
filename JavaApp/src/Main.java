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
		
		// �׽�Ʈ ���� ������
		User user = new User("qwer", "hoh9170", "qwr@na.com", "d1dd", "1. �����ϴ� ������?", "����");
		AccountManager.createAccount(user);
		User user2 = new User("vbnm","hoh9170", "vbnm12@na.com", "saddwq12", "2. ��� �ʵ��б���?", "��õ��");
		AccountManager.createAccount(user2);
		User user3 = new User("tttt", "hoh9170","ttttt@na.com", "wqers1", "3. �����ϴ� ������?", "���");		
		AccountManager.createAccount(user3);
		User user4 = new User("�Ѽ���","hoh9170", "han@naver.com", "qwer1234", "3. �����ϴ� ������?", "���");		
		AccountManager.createAccount(user4);
		
		
//		// UserDB �ʱ�ȭ
		UserDBManager.init();
//		User user1 = UserDBManager.findUserByEmail("han@naver.com");
//		System.out.println(user1.toString());
//		
//		// UserDB ��� 
////	UserDBManager.printUserDBMap();
//		 
//		// �������� ������ �ε� Ȯ��
//		Question.loadQuestionList();
//		EvaluationQuestion.loadEvaluationQuestionList();
//		
//		// �������� ������
//		new SurveyFrame(user1);
//		System.out.println(user1.toString());
//		System.out.println(user1.toString());

		// ���� ������ 
		new MainFrame();
		
		
		// SolvedAC���� ���� ������ �����ͼ� ProblemDB�� �߰��ϱ� (900���� �������� 429���� �߻�)
//		JsonFetcher.updateProblemDB_FromSolvedAC();
		

        long startTime = System.currentTimeMillis();        
		// ProblemDB �ʱ�ȭ
		ProblemDBManager.init();		
		System.out.println("ProblemDB �ʱ�ȭ �ҿ� �ð� : " + (System.currentTimeMillis() - startTime) + "ms");   
		
//		// ProblemDB ���
//		ProblemDBManager.PrintProblemDBMap();
//		
		
		User user1 = UserDBManager.findUserByEmail("han@naver.com");
		System.out.println("\n<<< User Data >>>\n");
		System.out.println(user1);
		
		// ��¥ ���� Ȯ��
		
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
