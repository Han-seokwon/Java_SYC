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

// Question Ŭ������ ��ӹ��� Ŭ������ �������翡 ���Ǵ� ���� �׽�Ʈ �������� ����, ������ ������, ����, ���� ��� ���� �� �ִ� ��ũ ����Ʈ�� �ʵ�� ������ ����
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
	 * json�����͸� EvaluationQuestion�� ��ȯ�Ͽ� ��ȯ
	 * */
	private static EvaluationQuestion createEvaluationQuestionFromJson(JsonObject questionJsonObj) {
		SurveyQuestion question = createQuestionFromJson(questionJsonObj);
		int answerNum = questionJsonObj.get("answerNum").getAsInt();
		int point = questionJsonObj.get("point").getAsInt();
		return new EvaluationQuestion(question.getQuestion(), question.getChoiceList(), answerNum, point);
	}	
	
	/*
	 * �Ƿ� �׽�Ʈ ���������Ͱ� ����� json�����͸� ArrayList<EvaluationQuestion> �� ��ȯ�Ͽ� ��ȯ
	 * */
	public static ArrayList<EvaluationQuestion> loadEvaluationQuestionList(){
		ArrayList<EvaluationQuestion> questionList = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/users/EvaluationQuestions.json"), "UTF-8"))){
			// JsonParser ����
			JsonParser jsonParser = new JsonParser();            
			// BufferedReader�� JsonElement�� �Ľ�
			JsonObject jsonObj = jsonParser.parse(reader).getAsJsonObject();
			// �� �������� JsonArray�� ������
			JsonArray questionJsonArray= jsonObj.get("items").getAsJsonArray();			
			for( JsonElement item : questionJsonArray) {
				// json�����͸� EvaluationQuestion�� ��ȯ�Ͽ� ��ȯ�Ͽ� ����Ʈ�� �߰�
				questionList.add(createEvaluationQuestionFromJson(item.getAsJsonObject()));
			}		
						
		} catch (IOException e) {
			System.out.println("EvaluationQuestions.json�� �о���� ���߽��ϴ�.");
            e.printStackTrace(); 
        }
		
		return questionList;			
	}	

}
