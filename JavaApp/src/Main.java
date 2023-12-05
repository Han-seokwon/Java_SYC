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
	private static void createTestUserData() { // �׽�Ʈ ���� ������ ����
		
		try {
			// �Ʒ� �������� �ʱ� �����ͷ� �ʱ�ȭ��			
			AccountManager.createAccount(new ResistrationFormat("qwer", "hoh9170", "qwr@na.com", "d1dd", "d1dd", "1. �����ϴ� ������?", "����"));
			AccountManager.createAccount(new ResistrationFormat("vbnm","hoh9170", "vbnm12@na.com", "saddwq12", "saddwq12", "2. ��� �ʵ��б���?", "��õ��"));
			AccountManager.createAccount(new ResistrationFormat("tttt", "hoh9170","ttttt@na.com", "wqers1",  "wqers1", "3. �����ϴ� ������?", "���"));
			AccountManager.createAccount(new ResistrationFormat("�Ѽ���","hoh9170", "han@naver.com", "qwer1234", "qwer1234", "3. �����ϴ� ������?", "���"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	static void initDBs() { // ���α׷� ���࿡ �ʿ��� ��� DB �ʱ�ȭ
		// UserDB �ʱ�ȭ
		UserDBManager.init();
		
        long startTime = System.currentTimeMillis();        
		// ProblemDB �ʱ�ȭ
		ProblemDBManager.init();		
		// ProblemDB�� �ʱ�ȭ �ð��� ���� ���� �ҿ�ǹǷ� �ð� ���� ��� �ֿܼ� ���
		System.out.println("ProblemDB �ʱ�ȭ �ҿ� �ð� : " + (System.currentTimeMillis() - startTime) + "ms");   
		
		ProblemRankManager.init(); // ���� ��ũ DB �ʱ�ȭ
	}	
	
	public static void main(String[] args) {
		
		// < 1. ������ ���� >
//		createTestUserData(); // �׽�Ʈ ���� ������ ����
		// SolvedAC���� ���� ������ �����ͼ� ProblemDB�� �߰��ϱ� (900���� �������� 429���� �߻�)
//		JsonFetcher.updateProblemDB_FromSolvedAC();	

		// < 2. DB �ʱ�ȭ >
		initDBs(); // ���α׷� ���࿡ �ʿ��� ��� DB �ʱ�ȭ
		
		// < 3. ���� ������ ����>
		// ���� ������ 
		MainFrame main = new MainFrame();
		// �׽�Ʈ�� ���� �α����� ���·� ����
		User user1 = UserDBManager.findUserByEmail("han@naver.com"); 
		main.logInComponents(user1);
		

	
							

	}

}
