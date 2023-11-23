package fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import problems.Problem;
import problems.ProblemDBManager;
import users.RANK;
import users.User;
import users.RANK;
// Ư�� api�� ���� HTTP GET ��û�� ���� json �����͸� �޾ƿ��� �̸� �Ľ��ϴ� Ŭ����
public class JsonFetcher {
	private final static int PROBLEM_CNT_PER_PAGE = 50; // �� �������� �ִ�� ������ �� �ִ� ���� ����
	
	/*
	 * URL�� fetch(HTTP GET request)�Ͽ� ������� JSON ���ڿ��� JsonElement�� �Ľ��Ͽ� ��ȯ
	 * param : fetch�� URL ���ڿ�
	 * return : ���� JSON �����͸� �Ľ��� JsonElement
	 */
	public static JsonObject fetchJsonElementFromUrl(String urlString) throws IOException, TooManyRequestsException{  // ȣ���� �ʿ��� ���ܸ� ó���ϰ� ��
		int httpResponseCode = 0;
		try {
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// ���� ���� Ȯ��
			httpResponseCode = conn.getResponseCode();
			System.out.println("Request -> "+  urlString);
			System.out.println("Response status : " + httpResponseCode + ' ' + conn.getResponseMessage());

			// ���� JSON �����͸� UTF-8 ���ڵ��Ͽ� �ϳ��� Reader�� ������ 
			// 	1. InputStream�� ���� ����Ʈ ������ �����͸� �Է� ����
			// 	2. InputStreamReader�� ���� ����Ʈ ������ �����͸� UTF-8������ ���ڴ���(char)�� ��ȯ��
			// 	3. BufferedReader ���۸� ���� �� ���ڵ��� �ϳ��� ���ڿ��� ����
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			// JsonParser ����
			JsonParser jsonParser = new JsonParser();            
			// BufferedReader�� JsonElement�� �Ľ�
			JsonElement jsonElement = jsonParser.parse(reader);

			reader.close(); // ���� �ݱ�
			conn.disconnect(); // ������ ��������

			return jsonElement.getAsJsonObject(); // JsonObject�� ��ȯ�Ͽ� ��ȯ
		}
		catch (MalformedURLException e) {
			throw new IOException("fetching ���� : " + urlString + " �� ��ȿ���� ���� �ּ��Դϴ�.");
		}
		catch (IOException e) {
			if(httpResponseCode == 429) {
				throw new TooManyRequestsException("429 Too Many Requests : ª�� �ð����� �ʹ� ���� ��û�� ���½��ϴ�."); // 429������ ���� ���� ����
			}
			throw new IOException("fetching ���� : " + urlString + " �ش� �ּҷ� sovled api ������ ������ �� �����ϴ�.");
		}
	}
	
	public static boolean checkUserRegisteredInSolvedAc(String solvedacUsername) {
		String urlString = "https://solved.ac/api/v3/user/show?handle="; // ����� ������ �������� api �ּ�
		try {
			// ���� �̸����� api�� ȣ��
			fetchJsonElementFromUrl(urlString + solvedacUsername);
		} catch (IOException e) { // �������� �ʴ� ���
			System.out.println(e.getMessage());
			return false;
		}
		System.out.println(solvedacUsername + " ������ ã�ҽ��ϴ�. ");
		return true;
	}
	

	/*
	 * solved ac�� ��ϵ� ������ ������ ������ �ذ��� ��������(solvedCount)�� ���� �ذ��� ���������͸� �������µ� �ʿ��� �� ������ ���� ����
	 * return : �� ������ ��
	 */
	public static int getUserSolvedProblemPageCnt_FromSolvedAC(String solvedacUsername) {
		int pageCnt = 0;
		String urlString = "https://solved.ac/api/v3/user/show?handle="; // ����� ������ �������� api �ּ�	
		try {
			// solved ac�� ��ϵ� ������ ������ JsonElement�� ������
			JsonObject userJson = fetchJsonElementFromUrl(urlString + solvedacUsername);
			// user JSON ���Ͽ��� solvedCount �� ��������
			int problemCnt = userJson.get("solvedCount").getAsInt(); 
			System.out.println("solved.ac�� ��ϵ� " + solvedacUsername + "�� �ذ��� ���� ���� : " +  problemCnt);
			pageCnt = problemCnt/PROBLEM_CNT_PER_PAGE + 1;
			System.out.println("solved.ac���� ������ " + solvedacUsername +"�� ��������Ʈ ������ ���� : " +  pageCnt);
		} catch (IOException e) {
			System.out.println("�̸��� " + solvedacUsername + "�� ������ �������� �ʽ��ϴ�.");
			System.out.println(e.getMessage());
		}
		return pageCnt;
	}

	/*
	 * ����ڰ� �ذ��� ������ ���� JSON���� items�� problemId ���� ArrayList�� �߰��Ͽ� ��ȯ
	 * param : fetch�� URL ���ڿ�
	 * return : ���� JSON �����͸� �Ľ��� JsonElement
	 */
	public static ArrayList<Integer> getSolvedProblemIdList_FromSolvedAC(int pageCnt, String username) {	
		ArrayList<Integer> solvedProblemIdList = new ArrayList<>();
		String urlString = "https://solved.ac/api/v3/search/problem?query=solved_by:" + username +"&page="; 		
		for (int pageIdx = 1; pageIdx < pageCnt+1; pageIdx++) {
			try {
				// URL�� fetch�Ͽ� �ش� �������� JSON�����͸�  JsonElement�� ��ȯ
				JsonObject problemPage =  fetchJsonElementFromUrl(urlString + pageIdx); // throws IOException				
				// �Ľ��� JsonElement�� 'items' Ű�� �ش��ϴ� �׸�鸸 ��ҷ� ������ JsonArray �迭�� ��ȯ
				JsonArray itemsArray = problemPage.getAsJsonArray("items");
				// �迭�� ��ȸ�Ͽ� problemId Ű���� int�� �������� solvedProblemIdList�� �߰�
				for( JsonElement item : itemsArray) {
					int problemId = item.getAsJsonObject().get("problemId").getAsInt();
					solvedProblemIdList.add(problemId);
				}
			} catch (JsonSyntaxException  e) {
				System.out.println("�Ľ��� JSON ���Ͽ� items �Ǵ� problemId  Ű�� �������� �ʽ��ϴ�.");
			} catch (IOException e) {
				System.out.println(e.getMessage()); 
			}
		}
		return solvedProblemIdList;
	}
	

	/*
	 * solved ac�� ��ϵ� �����̸��� �޾� �ش� ������ �ذ��� ���� �����͸� User ��ü�� solvedProblemList�� �߰���
	 * notice : �� �޼��带 ȣ���ϱ� �� ProblemDB�� solved ac���� ������ ���������͵��� ����Ǿ� �־�� ��
	 */
	public static void updateUserSolvedProblemList_FromSolvedAC(User user) {
		int problemPageCnt = getUserSolvedProblemPageCnt_FromSolvedAC(user.getSolvedName());
		ArrayList<Integer> solvedProblemIdList = getSolvedProblemIdList_FromSolvedAC(problemPageCnt, user.getSolvedName());
		System.out.println("�ذ��� ���� ID ����Ʈ  : " + solvedProblemIdList);
		System.out.println("�ذ��� ���� ���� : " +solvedProblemIdList.size());		
		// ������ ������ �߿� ���� ProblemDB�� �߰����� ���� ������ ���� �� ���� ���� ProblemDBManager�� �߰��� ������ �߰���
		for(int problemId : solvedProblemIdList) {
			Problem problem = ProblemDBManager.findProblem(problemId);
			if(problem.isValid()) {
				user.addSolvedProblem(problem); // ���� �߰�
			}
		}
	}

	/*
	 * solved�� ��ϵ� ��� ���� ���� ������ �̸� ���� ���� ��������Ʈ ������ ������ ����ؼ� ��ȯ
	 */
	public static int getProblemPageCnt_FromSolvedAC() {
		int pageCnt = 0;
		try {
			String urlString = "https://solved.ac/api/v3/site/stats"; // solved ��赥���� �������� api �ּ�		
			JsonObject solvedACStatistics = fetchJsonElementFromUrl(urlString);	
			int totalProblemCnt = solvedACStatistics.get("problemCount").getAsInt();
			System.out.println("solved.ac�� ���� ��ϵ� �� ���� ���� : " + totalProblemCnt);
			pageCnt = totalProblemCnt/PROBLEM_CNT_PER_PAGE + 1;
			System.out.println("solved.ac���� ������ ��������Ʈ page ���� : " + pageCnt);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return pageCnt;
	}
	
	/*
	 * �ش� ������ ���� �˰��� �з� �����͸� ������ ArrayList�� �����ؼ� ��ȯ
	 */
	public static ArrayList<String> getAlgorithmTagList(int problemId) throws IOException{ // ȣ���� �ʿ��� ���ܸ� ó���ϰ� ��
		ArrayList<String> algorithmTagList = new ArrayList<>();
		String urlString = "https://solved.ac/api/v3/problem/show?problemId=" + problemId;
		try {
			JsonObject problemJson = fetchJsonElementFromUrl(urlString); // throws IOException
			// "tags" �׸��� JsonArray�� �������� (tags : ���� ������ ���� �����͸� �����ϴ� �׸�)
			JsonArray tagsArray = problemJson.getAsJsonArray("tags");
			for (JsonElement tagElement : tagsArray) {
				// "tags" �迭���� "displayNames"�� ������ �����ϹǷ� JsonArray�� �������� (displayNames : ���� ���� �̸��� �����ϴ� �׸� )
				JsonArray displayNamesArray = tagElement.getAsJsonObject().getAsJsonArray("displayNames");
				// "displayNames"���� "language"�� "ko"�� "name" ��(= algorithmTag �̸�)�� ������ algorithmTagList�� �߰�			
				for (JsonElement element : displayNamesArray) {
					JsonObject displayNameObject = element.getAsJsonObject();
					// �ѱ� �̸��� ������
					if ("ko".equals(displayNameObject.get("language").getAsString())) {
						String tagName = displayNameObject.get("name").getAsString();
						algorithmTagList.add(tagName); // ����Ʈ�� �߰�
					}
				}
			}
		} catch (IOException e) { // api ��û�� ���� �߻�
			throw new IOException(e.getMessage()); 
		} catch (NullPointerException e) { 
			System.out.println(problemId + "�� ������ ���� �˰��� �з� �����͸� ������ �� �����ϴ�.");
			System.out.println(urlString);
			throw new IOException(e.getMessage());
		}
		// ��� ��ȯ
		return algorithmTagList;
	}
		
	
	/*
	 * JSON �����Ϳ��� problemId, titleKo, �˰��� �з� �����͸� ������ Problem ��ü�� ���� �� ��ȯ
	 */
	public static Problem createProblemFromJsonElement(JsonElement items) throws IOException{
		final String BOJ_PROBLEM_PATH = "https://www.acmicpc.net/problem/"; // ���� ������ȸ������ url path
		// ���� JSON������ items �׸� ���� JsonObject
		JsonObject itemsJsonObj = items.getAsJsonObject();
		// ���� ��ȣ
		int problemId = itemsJsonObj.get("problemId").getAsInt();
		// ���� ����
		String problemName = itemsJsonObj.get("titleKo").getAsString();
		// ���� URL
		String url = BOJ_PROBLEM_PATH + problemId;
		// ���� ��ũ
		int level = itemsJsonObj.get("level").getAsInt();
		// �ֺ�� ��ũ ���� 0~30�� ���� rank point 0~500�� ������ ��ȯ�Ͽ� �׿� �´� RANK ������ ����
		RANK rank = changeSolvedLevelToRANK(level);		
		// ���� �˰��� ����
		// �˰��� ������ ���� json ���Ͽ� ����, ������ȣ�� ������ �ϴ� �߰��� api ��û �ʿ�
		ArrayList<String> algorithmTagList = new ArrayList<>();
		try {
			algorithmTagList = getAlgorithmTagList(problemId);
		} catch (IOException e) { // api ��û�� ���� �߻�
			throw new IOException(e.getMessage()); 
		}
		
		// �� �����͵��� ������ Problem ��ü ����
		return new Problem(problemName, problemId, url, rank, algorithmTagList);
	}
	
	// �ֺ�� ��ũ ���� 0~30�� ���� rank point 0~500�� ������ ��ȯ�Ͽ� �׿� �´� RANK ������ ��ȯ
	private static RANK changeSolvedLevelToRANK(int level) {
		final int SOLVED_LEVEL_MAX = 30; 
		final int RANK_POINT_MAX = RANK.getMaxRequireRankPoint();
        double percentage = (double)level/SOLVED_LEVEL_MAX; // 0.0 ~ 1.0
        int rankPoint = (int) (percentage * RANK_POINT_MAX); // 0 ~ RANK max point
        return RANK.getRankForPoint(rankPoint);
	}

	/*
	 * solved�� ��ϵ� ���������͸� ������ Problem ��ü�� ��ȯ�� ���� ProblemDBManager�� ProblemDB �������� �߰�
	 */
	public static void updateProblemDB_FromSolvedAC() {
		try {
			String urlString = "https://solved.ac/api/v3/search/problem?query&page="; // solved�� ��ϵ� ���� ������ �������� api �ּ�	
			int problemPageCnt = getProblemPageCnt_FromSolvedAC();
			for( int pageIdx=1 ; pageIdx < problemPageCnt ; pageIdx++) {
				JsonObject problemPage = fetchJsonElementFromUrl(urlString + pageIdx); // throws IOException	
				// �Ľ��� JsonObject�� 'items' Ű�� �ش��ϴ� �׸�鸸 ��ҷ� ������ JsonArray �迭�� ��ȯ
				JsonArray itemsArray = problemPage.getAsJsonArray("items");
				// �迭�� ��ȸ�ϸ� Problem�� �����ϴµ� �ʿ��� ���� JSON���� �����Ͽ� Problem ��ü ����
				for( JsonElement items : itemsArray) {
					Problem problem = createProblemFromJsonElement(items);
					// ������ Problem �ν��Ͻ� ProblemDBManager�� ProblemDBMap�� �߰��ϰ� ProblemDB ������ ����
					System.out.println(problem);
					ProblemDBManager.createProblem(problem);
				}
			}			

		} catch (IOException e) {
			System.out.println(e.getMessage());	
			return; //����
		}
	}
}

// 429 ������ ���� ���� Ŭ���� ����
// unchecked ���ܷ� �ϱ����� RuntimeException ���
class TooManyRequestsException extends RuntimeException{ 
	public TooManyRequestsException() {
		super();
	}
	public TooManyRequestsException(String errMsg) {
		super(errMsg);
	}
}




