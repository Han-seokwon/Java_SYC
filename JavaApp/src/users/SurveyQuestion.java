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

/// 설문조사에 사용되는 질문항목을 저장하는 클래스로 문제와 선택지(객관식) 정보를 저장
public class SurveyQuestion {

	protected String question;
	protected ArrayList<String> choiceList = new ArrayList<>();

	public SurveyQuestion() {};	
	public SurveyQuestion(String question, ArrayList<String> choiceList) {
		this.question = question;
		this.choiceList = choiceList;
	}	

	@Override
	public String toString() {
		return "SurveyQuestion [question=" + question + ",\n choiceList=" + choiceList + "]\n";
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public ArrayList<String> getChoiceList() {
		return choiceList;
	}
	public void setChoiceList(ArrayList<String> choiceList) {
		this.choiceList = choiceList;
	}
	
	/*
	 * json 데이터를 SurveyQuestion으로 변환하여 반환
	 */	
	protected static SurveyQuestion createQuestionFromJson(JsonObject questionJsonObj) {
		// question 항목의 데이터를 문자열로 가져옴
		String question = questionJsonObj.get("question").getAsString(); 
		// choiceList 항목의 데이터를 JsonArray로 가져옴
		JsonArray choiceListJsonArray = questionJsonObj.get("choiceList").getAsJsonArray();		
		ArrayList<String> choiceList = new ArrayList<>(); // 객관식 선택지를 저장할 배열
		// JsonArray을 순회하며 JsonElement를 String으로 변환하여 배열에 저장
		for(JsonElement choiceListJson : choiceListJsonArray) {
			choiceList.add(choiceListJson.getAsString());
		}
		return new SurveyQuestion(question, choiceList);	
	}
	
	/*
	 * 질문 데이터가 저장된 json데이터를 ArrayList<Question> 로 변환하여 반환
	 */
	public static ArrayList<SurveyQuestion> loadQuestionList(){
		ArrayList<SurveyQuestion> questionList = new ArrayList<>();		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/users/Questions.json"), "UTF-8"))){
			// JsonParser 생성
			JsonParser jsonParser = new JsonParser();            
			// BufferedReader를 JsonElement로 파싱
			JsonObject jsonObj = jsonParser.parse(reader).getAsJsonObject();
			// 각 질문들을 JsonArray로 가져옴
			JsonArray questionJsonArray= jsonObj.get("items").getAsJsonArray();			
			for( JsonElement item : questionJsonArray) {
				// json데이터를 Question로 변환하여 반환하여 리스트에 추가
				questionList.add(createQuestionFromJson(item.getAsJsonObject()));
			}		
						
		} catch (IOException e) {
			System.out.println("Questions.json을 읽어오지 못했습니다.");
            e.printStackTrace(); 
        }
		return questionList;	
		
	}
	
	

}
