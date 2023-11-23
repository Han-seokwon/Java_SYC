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
// 특정 api에 대해 HTTP GET 요청을 보내 json 데이터를 받아오고 이를 파싱하는 클래스
public class JsonFetcher {
	private final static int PROBLEM_CNT_PER_PAGE = 50; // 한 페이지당 최대로 가져올 수 있는 문제 개수
	
	/*
	 * URL에 fetch(HTTP GET request)하여 응답받은 JSON 문자열을 JsonElement로 파싱하여 반환
	 * param : fetch할 URL 문자열
	 * return : 응답 JSON 데이터를 파싱한 JsonElement
	 */
	public static JsonObject fetchJsonElementFromUrl(String urlString) throws IOException, TooManyRequestsException{  // 호출한 쪽에서 예외를 처리하게 함
		int httpResponseCode = 0;
		try {
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// 응답 상태 확인
			httpResponseCode = conn.getResponseCode();
			System.out.println("Request -> "+  urlString);
			System.out.println("Response status : " + httpResponseCode + ' ' + conn.getResponseMessage());

			// 응답 JSON 데이터를 UTF-8 인코딩하여 하나의 Reader로 가져옴 
			// 	1. InputStream을 통해 바이트 단위로 데이터를 입력 받음
			// 	2. InputStreamReader을 통해 바이트 단위의 데이터를 UTF-8형식의 문자단위(char)로 변환함
			// 	3. BufferedReader 버퍼를 통해 각 문자들을 하나의 문자열로 만듦
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			// JsonParser 생성
			JsonParser jsonParser = new JsonParser();            
			// BufferedReader를 JsonElement로 파싱
			JsonElement jsonElement = jsonParser.parse(reader);

			reader.close(); // 버퍼 닫기
			conn.disconnect(); // 서버와 연결해제

			return jsonElement.getAsJsonObject(); // JsonObject로 변환하여 반환
		}
		catch (MalformedURLException e) {
			throw new IOException("fetching 실패 : " + urlString + " 은 유효하지 않은 주소입니다.");
		}
		catch (IOException e) {
			if(httpResponseCode == 429) {
				throw new TooManyRequestsException("429 Too Many Requests : 짧은 시간동안 너무 많은 요청을 보냈습니다."); // 429에러에 대한 예외 생성
			}
			throw new IOException("fetching 실패 : " + urlString + " 해당 주소로 sovled api 서버에 연결할 수 없습니다.");
		}
	}
	
	public static boolean checkUserRegisteredInSolvedAc(String solvedacUsername) {
		String urlString = "https://solved.ac/api/v3/user/show?handle="; // 사용자 데이터 가져오는 api 주소
		try {
			// 유저 이름으로 api를 호출
			fetchJsonElementFromUrl(urlString + solvedacUsername);
		} catch (IOException e) { // 존재하지 않는 경우
			System.out.println(e.getMessage());
			return false;
		}
		System.out.println(solvedacUsername + " 유저를 찾았습니다. ");
		return true;
	}
	

	/*
	 * solved ac에 등록된 유저의 정보를 가져와 해결한 문제개수(solvedCount)를 토대로 해결한 문제데이터를 가져오는데 필요한 총 페이지 개수 리턴
	 * return : 총 페이지 수
	 */
	public static int getUserSolvedProblemPageCnt_FromSolvedAC(String solvedacUsername) {
		int pageCnt = 0;
		String urlString = "https://solved.ac/api/v3/user/show?handle="; // 사용자 데이터 가져오는 api 주소	
		try {
			// solved ac에 등록된 유저의 정보를 JsonElement로 가져옴
			JsonObject userJson = fetchJsonElementFromUrl(urlString + solvedacUsername);
			// user JSON 파일에서 solvedCount 만 가져오기
			int problemCnt = userJson.get("solvedCount").getAsInt(); 
			System.out.println("solved.ac에 등록된 " + solvedacUsername + "가 해결한 문제 개수 : " +  problemCnt);
			pageCnt = problemCnt/PROBLEM_CNT_PER_PAGE + 1;
			System.out.println("solved.ac에서 가져올 " + solvedacUsername +"의 문제리스트 페이지 개수 : " +  pageCnt);
		} catch (IOException e) {
			System.out.println("이름이 " + solvedacUsername + "인 유저가 존재하지 않습니다.");
			System.out.println(e.getMessage());
		}
		return pageCnt;
	}

	/*
	 * 사용자가 해결한 문제에 대한 JSON에서 items의 problemId 값만 ArrayList에 추가하여 반환
	 * param : fetch할 URL 문자열
	 * return : 응답 JSON 데이터를 파싱한 JsonElement
	 */
	public static ArrayList<Integer> getSolvedProblemIdList_FromSolvedAC(int pageCnt, String username) {	
		ArrayList<Integer> solvedProblemIdList = new ArrayList<>();
		String urlString = "https://solved.ac/api/v3/search/problem?query=solved_by:" + username +"&page="; 		
		for (int pageIdx = 1; pageIdx < pageCnt+1; pageIdx++) {
			try {
				// URL로 fetch하여 해당 페이지의 JSON데이터를  JsonElement로 반환
				JsonObject problemPage =  fetchJsonElementFromUrl(urlString + pageIdx); // throws IOException				
				// 파싱한 JsonElement을 'items' 키에 해당하는 항목들만 요소로 가지는 JsonArray 배열로 변환
				JsonArray itemsArray = problemPage.getAsJsonArray("items");
				// 배열을 순회하여 problemId 키값을 int로 가져오고 solvedProblemIdList에 추가
				for( JsonElement item : itemsArray) {
					int problemId = item.getAsJsonObject().get("problemId").getAsInt();
					solvedProblemIdList.add(problemId);
				}
			} catch (JsonSyntaxException  e) {
				System.out.println("파싱한 JSON 파일에 items 또는 problemId  키가 존재하지 않습니다.");
			} catch (IOException e) {
				System.out.println(e.getMessage()); 
			}
		}
		return solvedProblemIdList;
	}
	

	/*
	 * solved ac에 등록된 유저이름을 받아 해당 유저가 해결한 문제 데이터를 User 객체의 solvedProblemList에 추가함
	 * notice : 이 메서드를 호출하기 전 ProblemDB에 solved ac에서 가져온 문제데이터들이 저장되어 있어야 함
	 */
	public static void updateUserSolvedProblemList_FromSolvedAC(User user) {
		int problemPageCnt = getUserSolvedProblemPageCnt_FromSolvedAC(user.getSolvedName());
		ArrayList<Integer> solvedProblemIdList = getSolvedProblemIdList_FromSolvedAC(problemPageCnt, user.getSolvedName());
		System.out.println("해결한 문제 ID 리스트  : " + solvedProblemIdList);
		System.out.println("해결한 문제 개수 : " +solvedProblemIdList.size());		
		// 가져온 문제들 중에 현재 ProblemDB에 추가되지 않은 문제가 있을 수 있음 따라서 ProblemDBManager에 추가된 문제만 추가함
		for(int problemId : solvedProblemIdList) {
			Problem problem = ProblemDBManager.findProblem(problemId);
			if(problem.isValid()) {
				user.addSolvedProblem(problem); // 문제 추가
			}
		}
	}

	/*
	 * solved에 등록된 모든 문제 개수 가져와 이를 토대로 응답 문제리스트 페이지 개수를 계산해서 반환
	 */
	public static int getProblemPageCnt_FromSolvedAC() {
		int pageCnt = 0;
		try {
			String urlString = "https://solved.ac/api/v3/site/stats"; // solved 통계데이터 가져오는 api 주소		
			JsonObject solvedACStatistics = fetchJsonElementFromUrl(urlString);	
			int totalProblemCnt = solvedACStatistics.get("problemCount").getAsInt();
			System.out.println("solved.ac에 현재 등록된 총 문제 개수 : " + totalProblemCnt);
			pageCnt = totalProblemCnt/PROBLEM_CNT_PER_PAGE + 1;
			System.out.println("solved.ac에서 가져올 문제리스트 page 개수 : " + pageCnt);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return pageCnt;
	}
	
	/*
	 * 해당 문제에 대한 알고리즘 분류 데이터를 가져와 ArrayList에 저장해서 반환
	 */
	public static ArrayList<String> getAlgorithmTagList(int problemId) throws IOException{ // 호출한 쪽에서 예외를 처리하게 함
		ArrayList<String> algorithmTagList = new ArrayList<>();
		String urlString = "https://solved.ac/api/v3/problem/show?problemId=" + problemId;
		try {
			JsonObject problemJson = fetchJsonElementFromUrl(urlString); // throws IOException
			// "tags" 항목을 JsonArray로 가져오기 (tags : 문제 종류에 대한 데이터를 저장하는 항목)
			JsonArray tagsArray = problemJson.getAsJsonArray("tags");
			for (JsonElement tagElement : tagsArray) {
				// "tags" 배열에서 "displayNames"가 여러개 존재하므로 JsonArray로 가져오기 (displayNames : 문제 종류 이름을 저장하는 항목 )
				JsonArray displayNamesArray = tagElement.getAsJsonObject().getAsJsonArray("displayNames");
				// "displayNames"에서 "language"가 "ko"인 "name" 값(= algorithmTag 이름)을 가져와 algorithmTagList에 추가			
				for (JsonElement element : displayNamesArray) {
					JsonObject displayNameObject = element.getAsJsonObject();
					// 한글 이름만 가져옴
					if ("ko".equals(displayNameObject.get("language").getAsString())) {
						String tagName = displayNameObject.get("name").getAsString();
						algorithmTagList.add(tagName); // 리스트에 추가
					}
				}
			}
		} catch (IOException e) { // api 요청중 문제 발생
			throw new IOException(e.getMessage()); 
		} catch (NullPointerException e) { 
			System.out.println(problemId + "번 문제에 대한 알고리즘 분류 데이터를 가져올 수 없습니다.");
			System.out.println(urlString);
			throw new IOException(e.getMessage());
		}
		// 결과 반환
		return algorithmTagList;
	}
		
	
	/*
	 * JSON 데이터에서 problemId, titleKo, 알고리즘 분류 데이터를 가져와 Problem 객체를 생성 후 반환
	 */
	public static Problem createProblemFromJsonElement(JsonElement items) throws IOException{
		final String BOJ_PROBLEM_PATH = "https://www.acmicpc.net/problem/"; // 백준 문제조회페이지 url path
		// 문제 JSON파일의 items 항목에 대한 JsonObject
		JsonObject itemsJsonObj = items.getAsJsonObject();
		// 문제 번호
		int problemId = itemsJsonObj.get("problemId").getAsInt();
		// 문제 제목
		String problemName = itemsJsonObj.get("titleKo").getAsString();
		// 문제 URL
		String url = BOJ_PROBLEM_PATH + problemId;
		// 문제 랭크
		int level = itemsJsonObj.get("level").getAsInt();
		// 솔브드 랭크 레벨 0~30의 값을 rank point 0~500의 값으로 변환하여 그에 맞는 RANK 열거형 설정
		RANK rank = changeSolvedLevelToRANK(level);		
		// 문제 알고리즘 종류
		// 알고리즘 정보는 현재 json 파일에 없어, 문제번호를 쿼리로 하는 추가적 api 요청 필요
		ArrayList<String> algorithmTagList = new ArrayList<>();
		try {
			algorithmTagList = getAlgorithmTagList(problemId);
		} catch (IOException e) { // api 요청중 문제 발생
			throw new IOException(e.getMessage()); 
		}
		
		// 위 데이터들을 가지고 Problem 객체 생성
		return new Problem(problemName, problemId, url, rank, algorithmTagList);
	}
	
	// 솔브드 랭크 레벨 0~30의 값을 rank point 0~500의 값으로 변환하여 그에 맞는 RANK 열거형 반환
	private static RANK changeSolvedLevelToRANK(int level) {
		final int SOLVED_LEVEL_MAX = 30; 
		final int RANK_POINT_MAX = RANK.getMaxRequireRankPoint();
        double percentage = (double)level/SOLVED_LEVEL_MAX; // 0.0 ~ 1.0
        int rankPoint = (int) (percentage * RANK_POINT_MAX); // 0 ~ RANK max point
        return RANK.getRankForPoint(rankPoint);
	}

	/*
	 * solved에 등록된 문제데이터를 가져와 Problem 객체로 변환한 다음 ProblemDBManager와 ProblemDB 폴더에에 추가
	 */
	public static void updateProblemDB_FromSolvedAC() {
		try {
			String urlString = "https://solved.ac/api/v3/search/problem?query&page="; // solved에 등록된 문제 데이터 가져오는 api 주소	
			int problemPageCnt = getProblemPageCnt_FromSolvedAC();
			for( int pageIdx=1 ; pageIdx < problemPageCnt ; pageIdx++) {
				JsonObject problemPage = fetchJsonElementFromUrl(urlString + pageIdx); // throws IOException	
				// 파싱한 JsonObject을 'items' 키에 해당하는 항목들만 요소로 가지는 JsonArray 배열로 변환
				JsonArray itemsArray = problemPage.getAsJsonArray("items");
				// 배열을 순회하며 Problem을 생성하는데 필요한 값만 JSON에서 추출하여 Problem 객체 생성
				for( JsonElement items : itemsArray) {
					Problem problem = createProblemFromJsonElement(items);
					// 생성된 Problem 인스턴스 ProblemDBManager의 ProblemDBMap에 추가하고 ProblemDB 폴더에 저장
					System.out.println(problem);
					ProblemDBManager.createProblem(problem);
				}
			}			

		} catch (IOException e) {
			System.out.println(e.getMessage());	
			return; //종료
		}
	}
}

// 429 에러에 대한 예외 클래스 생성
// unchecked 예외로 하기위해 RuntimeException 상속
class TooManyRequestsException extends RuntimeException{ 
	public TooManyRequestsException() {
		super();
	}
	public TooManyRequestsException(String errMsg) {
		super(errMsg);
	}
}




