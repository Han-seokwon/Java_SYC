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
	private static int totalProblemCnt_SolvedAC = 0; // solved.ac에 저장된 총 문제 개수
	
	/*
	 * URL에 fetch(HTTP GET request)하여 응답받은 JSON 문자열을 JsonElement로 파싱하여 반환
	 * param : fetch할 URL 문자열
	 * return : 응답 JSON 데이터를 파싱한 JsonElement
	 */
	public static JsonObject fetchJsonElementFromUrl(String urlString) throws IOException{  // 호출한 쪽에서 예외를 처리하게 함
		int httpResponseCode = 0;
		try {
			URL url = new URL(urlString); // URL 객체 생성
			// 해당 URL에 대한 URLConnection 객체 생성
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // throws IOException
			conn.setRequestMethod("GET"); // 요청 메서드 설정

			// 응답 상태 확인
			httpResponseCode = conn.getResponseCode();
			System.out.println("Request -> "+  urlString);
			System.out.println("Response status : " + httpResponseCode + ' ' + conn.getResponseMessage());

			// 응답 JSON 데이터를 UTF-8 인코딩하여 하나의 Reader로 가져옴 
			// 	1. 서버와 연결 후, InputStream을 통해 바이트 단위로 데이터를 입력 받음
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
				int sleepMin = 15; // 쓰레드 정지할 시간(분) -> 평균적으로 429에러 발생시 15분 후에 다시 호출이 가능했음
				System.out.println("429에러 발생 지정된 시간 동안 최대 api 호출 가능 횟수를 초과하였습니다. " + sleepMin + " 분 대기후 자동으로 api 호출이 다시 시작됩니다.");
				try {
					sleepForMin(sleepMin, 10); //  sleepMin분 동안 스레드 정지, 10초마다 타임 스탬프 출력
				} catch (IOException ex) { // 인터럽트 발생한 경우
					System.out.println(ex.getMessage()); // 에러 메시지 출력
					throw new IOException("다음 api 호출을 위한 대기 과정이 취소 되었습니다.");
				}
				// 만약 인터럽트 없이 정지시간이 모두 경과한 경우 동일한 주소로 api 다시 호출
				return fetchJsonElementFromUrl(urlString); // 재귀 호출
				
			} else { // 429 에러가 아닌 경우 -> 다른 이유로 현재 api를 요청할 수 없음
				throw new IOException("fetching 실패 : " + urlString + " 해당 주소로 sovled api 서버에 연결할 수 없습니다.");				
			}
		}
	}
	
	/*
	 * 지정된 시간동안 쓰레드를 정지시키며, intervalSec 마다 경과시간, 남은시간을 출력함
	 * sleepMin : 정지할 시간(분)
	 * intervalSec : 경과시간, 남은시간을 출력할 간격(초)
	 * */
	// 지정된 시간동안 쓰레드를 정지시키며, intervalSec 마다 경과시간, 남은시간을 출력함
	public static void sleepForMin(int sleepMin, int intervalSec) throws IOException{
		try {
			long totalSleepTime_ms = sleepMin * 60 * 1000; // sleepMin(분) 동안 스레드 정지
			long remainingTime_sec = totalSleepTime_ms; // 남은 시간
			long elapsedTime_sec = 0; // 경과한 시간
			long startTime_ms = System.currentTimeMillis(); // 시작한 시간
			
			while (System.currentTimeMillis() - startTime_ms < totalSleepTime_ms) { // 총 정지 시간이 지나지 않는 동안
				elapsedTime_sec = (System.currentTimeMillis() - startTime_ms) / 1000; // 경과 시간 계산
				remainingTime_sec = (totalSleepTime_ms/1000) - elapsedTime_sec; // 남은 시간 계산
				System.out.println("경과 시간 : " + elapsedTime_sec + " s" + ", 남은 시간: " + remainingTime_sec + " s"); 
				Thread.sleep(intervalSec * 1000); // intervalSec 간격으로 쓰레드 정지
			}

		} catch (InterruptedException ex) { // 인터럽트 발생한 경우
			Thread.currentThread().interrupt(); // 현재 스레드 인터럽트 상태 True로 설정
			// InterruptedException이 발생한 경우 해당 쓰레드의 인터럽트 상태는 자동으로 false가 되므로 다시 True로 변경하여 인터럽트 여부를 저장함
			throw new IOException("쓰레드 정지 취소됨");	
		}
		// 쓰레드 정지시간이 모두 끝남
		System.out.println("쓰레드를 다시 작동시킵니다.");		
	}


	// 해당 유저가 SolvedAc에 등록되었는지 확인
	public static boolean checkUserRegistered_InSolvedAC(String solvedacUsername) {
		String urlString = "https://solved.ac/api/v3/user/show?handle="; // 사용자 데이터 가져오는 api 주소
		try {
			// 유저 이름으로 api를 호출
			fetchJsonElementFromUrl(urlString + solvedacUsername);
		} catch (IOException e) { // 존재하지 않는 경우
			System.out.println(e.getMessage());
			return false;
		}
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
		long startTime = System.currentTimeMillis(); 

		int problemPageCnt = getUserSolvedProblemPageCnt_FromSolvedAC(user.getSolvedName());
		ArrayList<Integer> solvedProblemIdList = getSolvedProblemIdList_FromSolvedAC(problemPageCnt, user.getSolvedName());
		//		System.out.println("해결한 문제 ID 리스트  : " + solvedProblemIdList);
		System.out.println("해결한 문제 개수 : " +solvedProblemIdList.size());
		System.out.println("유저 데이터 업데이트 소요 시간 : " + (System.currentTimeMillis() - startTime) + "ms");
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
			totalProblemCnt_SolvedAC = totalProblemCnt;
			
			pageCnt = totalProblemCnt/PROBLEM_CNT_PER_PAGE + 1; // 가져올 페이지 개수 = 총 문제 개수 / 페이지당 문제 개수 + 1
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
		} catch (NullPointerException e) { // tags, displayNames 등의 알고리즘 분류 키워드(항목)이 없는 경우
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
		// 솔브드 랭크 레벨 0~30의 값을 rank point 0~500의 값으로 변환
		int rankPoint = changeSolvedLevelToRankPoint(level);
		// rankPoint에 맞는 RANK 열거형 반환
		RANK rank = RANK.getRankForPoint(rankPoint);		
		// 문제 알고리즘 종류
		// 알고리즘 정보는 현재 json 파일에 없어, 문제번호를 쿼리로 하는 추가적 api 요청 필요
		ArrayList<String> algorithmTagList = new ArrayList<>();
		try {
			algorithmTagList = getAlgorithmTagList(problemId);
		} catch (IOException e) { // api 요청중 문제 발생
			throw new IOException(e.getMessage()); 
		} 

		// 위 데이터들을 가지고 Problem 객체 생성
		return new Problem(problemName, problemId, url, rank, rankPoint, algorithmTagList);
	}

	// 솔브드 랭크 레벨 0~30의 값을 rank point 0~500의 값으로 변환
	private static int changeSolvedLevelToRankPoint(int level) {
		final int SOLVED_LEVEL_MAX = 30; 
		final int RANK_POINT_MAX = RANK.getMaxRequireRankPoint();
		double percentage = (double)level/SOLVED_LEVEL_MAX; // 0.0 ~ 1.0
		int rankPoint = (int) (percentage * RANK_POINT_MAX); // 0 ~ RANK max point
		return rankPoint;
	}

	/*
	 * solved에 등록된 문제데이터를 가져와 Problem 객체로 변환한 다음 ProblemDBManager와 ProblemDB 폴더에에 추가
	 */
	public static void updateProblemDB_FromSolvedAC() {
		int problemSavedCnt = 0; // 저장된 문제 개수
		try {
			String urlString = "https://solved.ac/api/v3/search/problem?query&page="; // solved에 등록된 문제 데이터 가져오는 api 주소	
			int problemPageCnt = getProblemPageCnt_FromSolvedAC(); // 가져올 문제가 몇 페이지에 결쳐 존재하는지 확인
			
			for( int pageIdx=1 ; pageIdx < problemPageCnt ; pageIdx++) { // 페이지 개수만큼 반복
				
				JsonObject problemPage = fetchJsonElementFromUrl(urlString + pageIdx); // throws IOException	
				// 파싱한 JsonObject을 'items' 키에 해당하는 항목들만 요소로 가지는 JsonArray 배열로 변환
				JsonArray itemsArray = problemPage.getAsJsonArray("items");
				// 배열을 순회하며 Problem을 생성하는데 필요한 값만 JSON에서 추출하여 Problem 객체 생성
				for( JsonElement items : itemsArray) { 	// 해당 페이지에 저장된 문제 개수 만큼 반복
					Problem problem = createProblemFromJsonElement(items); // 문제 객체 생성
					// 생성된 Problem 인스턴스 ProblemDBManager의 ProblemDBMap에 추가하고 ProblemDB 폴더에 저장
					ProblemDBManager.createProblem(problem);
					problemSavedCnt++;
					System.out.println("현재 까지 가져온 문제 개수 : " + problemSavedCnt + ", 총 문제 개수 : " + totalProblemCnt_SolvedAC);
					
//					System.out.println(problem);  // 저장된 문제 출력
				}
			}			

		} catch (IOException e) {
			System.out.println(e.getMessage());	
			return; //종료
		}
	}
}





