package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import users.EvaluationQuestion;

public class EvaluationFrame extends JFrame {

    private ArrayList<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
    private ArrayList<ButtonGroup> choiceRadioBtnGroupList = new ArrayList<>();    

    public EvaluationFrame() {
    	// 실력 테스트 질문 불러오기
        evaluationQuestions = EvaluationQuestion.loadEvaluationQuestionList();

        // 실력 테스트 패널
        JPanel evaluationPanel = new JPanel(new GridBagLayout()); 
        
        // 실력 테스트 타이틀용 패널
        JPanel evaluationTitlePanel = new JPanel();
        evaluationTitlePanel.add(new JLabel("알고리즘 및 자료구조 개념 테스트"));
        evaluationTitlePanel.setSize(800, 500);
        GridBagConstraints gbc = new GridBagConstraints(); // 레이아웃 제약 조건 설정
        gbc.gridy = 0;
        evaluationPanel.add(evaluationTitlePanel, gbc);
        
        // 실력 테스트 질문 패널
        JPanel evaluationQuestionListPanel = new JPanel(new GridLayout(evaluationQuestions.size() , 1, 15, 15));
        evaluationQuestionListPanel.setSize(800, 800);
        GridBagConstraints gbcQuestion = new GridBagConstraints(); // 레이아웃 제약 조건 설정
        gbcQuestion.gridy = 1; // evaluationTitlePanel 다음 행에 시작
        evaluationPanel.add(evaluationQuestionListPanel, gbcQuestion);
        
        
        // 질문, 선택지(라디오 버튼) 세팅        
        for (int i = 0 ; i < evaluationQuestions.size(); i++ ) {        	
        	JPanel questionPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        	// 질문 라벨 추가
        	EvaluationQuestion question = evaluationQuestions.get(i);
        	questionPanel.add(new JLabel(String.format("(%d번) ", i+1) + question.getQuestion()));
        	
        	// 선택지 라디오 버튼 추가
        	JPanel choiceListPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        	ArrayList<String> choiceList = question.getChoiceList(); // 선택지 저장용 패널        	
        	ButtonGroup choiceRadioBtnGroup = new ButtonGroup();// 각 선택지 라디오 버튼을 저장할 버튼 그룹 
        	// 라디오 버튼에 선택지에 해당하는 문자열을 넣어서 버튼 그룹과 패널에 추가
        	for(int j = 0 ; j < choiceList.size(); j++ ) {
        		JRadioButton choiceBtn = new JRadioButton(choiceList.get(j));
        		choiceRadioBtnGroup.add(choiceBtn);
        		choiceListPanel.add(choiceBtn); // 패널에도 버튼 추가       		
        	}
        	choiceRadioBtnGroupList.add(choiceRadioBtnGroup); // 선택지버튼그룹리스트에 선택지버튼그룹 추가
        	questionPanel.add(choiceListPanel); // 질문 패널에 선택지 추가
        	evaluationQuestionListPanel.add(questionPanel); // 질문리스트모델에 질문패널 추가
        }
        
     // JScrollPane에 evaluationQuestionPanel 추가
        JScrollPane scrollPane = new JScrollPane(evaluationPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 제출 버튼
        JButton submitButton = new JButton("Submit");        
        submitButton.addActionListener(new SubmitButtonListener());

        // 각 테스트 패널 프레임에 추가
        add(scrollPane);
//        add(submitButton);

                
     // 프레임 속성 설정
        setTitle("설문 조사");
        setSize(1000, 1000);
        this.setLocationRelativeTo(null); // 창 가운데 위치
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(areAllEvaluationQuestionsAnswered()) { // 모든 테스트 항목에 답한 경우 
				
			} else { // 하나라도 선택되지 않은 항목이 있는 경우
				JOptionPane.showMessageDialog(null, "모든 항목에 답을 선택해주세요.", "제출 오류", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
    
    
    private boolean areAllEvaluationQuestionsAnswered() {
        for (ButtonGroup group : choiceRadioBtnGroupList) {
            if (group.getSelection() == null) { // 하나라도 선택되지 않은 항목이 있는 경우
                return false; 
            }
        }
        return true; // 모든 항목이 선택ehla
    }

}
