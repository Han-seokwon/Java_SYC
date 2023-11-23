package users;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Question 클래스를 상속받은 클래스로 설문조사에 사용되는 개념 테스트 문제들을 저장, 문제와 선택지, 정답, 맞춘 경우 얻을 수 있는 랭크 포인트를 필드로 가지고 있음
public class EvaluationQuestion extends SurveyQuestion{

	private int answerNum;
	private int point;
	
	public EvaluationQuestion() {};	
	public EvaluationQuestion(String question, ArrayList<String> choiceList, int answerNum, int point) {
		super(question, choiceList);
		this.answerNum = answerNum;
		this.point = point;
	}		

	@Override
	public String toString() {
		return "EvaluationQuestion [question=" + question + "\n, choiceList=" + choiceList + "\n, answerNum=" + answerNum
				+ ",\n point=" + point + "]\n";
	}
	public int getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	/*
	 * json데이터를 EvaluationQuestion로 변환하여 반환
	 * */
	private static EvaluationQuestion createEvaluationQuestionFromJson(JsonObject questionJsonObj) {
		SurveyQuestion question = createQuestionFromJson(questionJsonObj);
		int answerNum = questionJsonObj.get("answerNum").getAsInt();
		int point = questionJsonObj.get("point").getAsInt();
		return new EvaluationQuestion(question.getQuestion(), question.getChoiceList(), answerNum, point);
	}	
	
	/*
	 * 실력 테스트 질문데이터가 저장된 json데이터를 ArrayList<EvaluationQuestion> 로 변환하여 반환
	 * */
	public static ArrayList<EvaluationQuestion> loadEvaluationQuestionList(){
		ArrayList<EvaluationQuestion> questionList = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/users/EvaluationQuestions.json"), "UTF-8"))){
			// JsonParser 생성
			JsonParser jsonParser = new JsonParser();            
			// BufferedReader를 JsonElement로 파싱
			JsonObject jsonObj = jsonParser.parse(reader).getAsJsonObject();
			// 각 질문들을 JsonArray로 가져옴
			JsonArray questionJsonArray= jsonObj.get("items").getAsJsonArray();			
			for( JsonElement item : questionJsonArray) {
				// json데이터를 EvaluationQuestion로 변환하여 반환하여 리스트에 추가
				questionList.add(createEvaluationQuestionFromJson(item.getAsJsonObject()));
			}		
						
		} catch (IOException e) {
			System.out.println("EvaluationQuestions.json을 읽어오지 못했습니다.");
            e.printStackTrace(); 
        }
		
		return questionList;			
	}	

}
