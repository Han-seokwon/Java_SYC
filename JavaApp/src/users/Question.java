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

public class Question {

	protected String question;
	protected ArrayList<String> choiceList = new ArrayList<>();

	
	
	public Question() {};	
	public Question(String question, ArrayList<String> choiceList) {
		this.question = question;
		this.choiceList = choiceList;
	}	

	@Override
	public String toString() {
		return "EvaluationQuestion [question=" + question + ",\n choiceList=" + choiceList + "]\n";
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
	 * json �����͸� Question���� ��ȯ�Ͽ� ��ȯ
	 */	
	protected static Question createQuestionFromJson(JsonObject questionJsonObj) {
		String question = questionJsonObj.get("question").getAsString();
		JsonArray choiceListJsonArray = questionJsonObj.get("choiceList").getAsJsonArray();
		ArrayList<String> choiceList = new ArrayList<>();
		for(JsonElement choiceListJson : choiceListJsonArray) {
			choiceList.add(choiceListJson.getAsString());
		}
		return new Question(question, choiceList);		
	}
	
	/*
	 * ���� �����Ͱ� ����� json�����͸� ArrayList<Question> �� ��ȯ�Ͽ� ��ȯ
	 */
	public static ArrayList<Question> loadQuestionList(){
		ArrayList<Question> questionList = new ArrayList<>();		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/users/Questions.json"), "UTF-8"))){
			// JsonParser ����
			JsonParser jsonParser = new JsonParser();            
			// BufferedReader�� JsonElement�� �Ľ�
			JsonObject jsonObj = jsonParser.parse(reader).getAsJsonObject();
			// �� �������� JsonArray�� ������
			JsonArray questionJsonArray= jsonObj.get("items").getAsJsonArray();			
			for( JsonElement item : questionJsonArray) {
				// json�����͸� Question�� ��ȯ�Ͽ� ��ȯ�Ͽ� ����Ʈ�� �߰�
				questionList.add(createQuestionFromJson(item.getAsJsonObject()));
				System.out.println(createQuestionFromJson(item.getAsJsonObject()));
			}		
						
		} catch (IOException e) {
			System.out.println("Questions.json�� �о���� ���߽��ϴ�.");
            e.printStackTrace(); 
        }
		
		return questionList;	
		
	}
	
	

}
