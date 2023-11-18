package gui;

import java.awt.Checkbox;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import users.EvaluationQuestion;
import users.Question;
import users.User;

public class SurveyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	// 현재는 학습 성향 확인용 문제가 1개 밖에 없지만 확장성을 위해 문제를 저장하는 ArrayList로 선언
	private ArrayList<Question> learningStyleCheckQuestions = new ArrayList<>(); 
	private ArrayList<Checkbox[]> learningStyleCheckBtnGroupList = new ArrayList<>();
	
	private ArrayList<EvaluationQuestion> evalQuestions = new ArrayList<>();
    private ArrayList<ButtonGroup> evalChoiceRadioBtnGroupList = new ArrayList<>();   
    private User user;
    
	public SurveyFrame(User user) {	
		this.user = user; // 현재 설문조사를 진행하는 유저 데이터 받아오기
		
		// 학습성향 확인 질문 불러오기		
		learningStyleCheckQuestions = Question.loadQuestionList();
		// 개념 테스트 질문 불러오기
		evalQuestions = EvaluationQuestion.loadEvaluationQuestionList();
		
		// 컴포넌트를 담을 컨텐트 팬 선언
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));	
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		//  컨텐트팬  레이아웃 설정
		gbl_contentPane.columnWidths = new int[]{853, 0};
		gbl_contentPane.rowHeights = new int[]{277, 442, 35, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		

		// < 학습성향 확인 >		
		
		// 학습성향확인 항목을 담을 패널
		JPanel learningStyleCheckPanel = new JPanel();
		learningStyleCheckPanel.setLayout(null);		
		GridBagConstraints gbc_learningStyleCheckPanel = new GridBagConstraints();
		//  learningStyleCheckPanel 레이아웃 설정
		gbc_learningStyleCheckPanel.weighty = 1.0;
		gbc_learningStyleCheckPanel.fill = GridBagConstraints.BOTH;
		gbc_learningStyleCheckPanel.insets = new Insets(0, 0, 5, 0);
		gbc_learningStyleCheckPanel.gridx = 0;
		gbc_learningStyleCheckPanel.gridy = 0;
		contentPane.add(learningStyleCheckPanel, gbc_learningStyleCheckPanel);
		
		// 학습성향확인 제목을 담을 패널
		JPanel learningStyleCheckTitlePanel = new JPanel();
		learningStyleCheckTitlePanel.setBounds(0, 0, 870, 96);
		learningStyleCheckTitlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		// 	컨텐트팬에 추가
		learningStyleCheckPanel.add(learningStyleCheckTitlePanel);
		
		// 학습성향확인 제목
		JLabel learningStyleCheckTitleLabel = new JLabel("학습성향 조사");
		learningStyleCheckTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		learningStyleCheckTitleLabel.setFont(new Font("굴림", Font.PLAIN, 30));
		learningStyleCheckTitlePanel.add(learningStyleCheckTitleLabel);		
		
		// 학습성향확인용 내용을 담을 스크롤팬
		JScrollPane learningStyleCheckContentScrollPane = new JScrollPane();
		learningStyleCheckContentScrollPane.setBounds(0, 95, 870, 172); // 팬 사이즈 조정
		learningStyleCheckContentScrollPane.getVerticalScrollBar().setUnitIncrement(12); // 스크롤바 속도 설정
		learningStyleCheckPanel.add(learningStyleCheckContentScrollPane);
		
		// 학습성향확인용 질문들을 담은 패널
		JPanel learningStyleQeustionListPanel = new JPanel();
		learningStyleQeustionListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		learningStyleQeustionListPanel.setLayout(new GridLayout(1, 0, 0, 0));		
		setLearningStyleCheckAndChoiceButton(learningStyleQeustionListPanel);		
		// 질문들을 학습성향확인 항목을 담을 패널에 추가
		learningStyleCheckContentScrollPane.setViewportView(learningStyleQeustionListPanel);
		

		// < 개념 테스트 >		
		
		// 개념 테스트를 담을 패널
		JPanel evalPanel = new JPanel();
		GridBagConstraints gbc_evalPanel = new GridBagConstraints();
		gbc_evalPanel.weightx = 1.0;
		gbc_evalPanel.fill = GridBagConstraints.BOTH;
		gbc_evalPanel.insets = new Insets(0, 0, 5, 0);
		gbc_evalPanel.gridx = 0;
		gbc_evalPanel.gridy = 1;
		contentPane.add(evalPanel, gbc_evalPanel);
		evalPanel.setLayout(null);
		
		// 개념 테스트 제목을 담을 패널
		JPanel evalTitlePanel = new JPanel();
		evalTitlePanel.setBounds(0, 0, 870, 96); // 팬 사이즈 조정 
		evalPanel.add(evalTitlePanel);
		evalTitlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		// 개념 테스트 제목 라벨
		JLabel evalTitleLabel = new JLabel("알고리즘, 자료구조 개념 테스트(모든 항목 필수 입력)");
		evalTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		evalTitleLabel.setFont(new Font("굴림", Font.PLAIN, 30));
		evalTitlePanel.add(evalTitleLabel);
				
		// 개념 테스트 내용(질문)을 담을 스크롤팬
		JScrollPane evalContentScrollPane = new JScrollPane();
		evalContentScrollPane.setBounds(0, 95, 870, 342); // 팬 사이즈 조정
		evalContentScrollPane.getVerticalScrollBar().setUnitIncrement(12); // 스크롤바 속도 설정
		evalPanel.add(evalContentScrollPane);
		

		
		// 개념 테스트 내용(질문)을 담을 패널
		JPanel evalQuestionListPanel = new JPanel();
		evalQuestionListPanel.setBorder(new EmptyBorder(10, 10, 15, 10));
		evalContentScrollPane.setViewportView(evalQuestionListPanel);
		// 질문 사이즈만큼 그리드 레이아웃 row를 생성
		evalQuestionListPanel.setLayout(new GridLayout(evalQuestions.size(), 1, 0, 0));
		// 각 질문, 버튼 등록
		setEvalQuestionAndChoiceButton(evalQuestionListPanel);
		
		// 제출 버튼
		JButton submitButton = new JButton("제출");
		submitButton.addActionListener(new SubmitButtonListener()); // 리스너 등록		
		submitButton.setFont(new Font("굴림", Font.PLAIN, 20));
		GridBagConstraints gbc_submitButton = new GridBagConstraints();
		gbc_submitButton.anchor = GridBagConstraints.EAST;
		gbc_submitButton.fill = GridBagConstraints.VERTICAL;
		gbc_submitButton.gridx = 0;
		gbc_submitButton.gridy = 2;
		contentPane.add(submitButton, gbc_submitButton);		
		
		// 프레임 속성 설정
		setTitle("설문조사");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // 창 가운데 위치
		setBounds(100, 100, 920, 850);		
		setVisible(true);	
	}
	
	// 학습 성향 확인 질문 및 선택지를 패널에 추가
	void setLearningStyleCheckAndChoiceButton(JPanel learningStyleQuestionListPanel) {
        // 질문, 선택지(체크 박스) 세팅        
        for (int i = 0 ; i < learningStyleCheckQuestions.size(); i++ ) {        	 
        	JPanel questionPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        	// 질문 라벨 추가
        	Question question = learningStyleCheckQuestions.get(i);
        	questionPanel.add(new JLabel(String.format("(%d번) ", i+1) + question.getQuestion()));
        	
        	// 선택지 체크 박스 추가
        	JPanel choiceListPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // 선택지 저장용 패널        	
        	ArrayList<String> choiceStringList = question.getChoiceList(); // 선택지 문자열 가져오기       	
        	Checkbox[] choiceList = new Checkbox[choiceStringList.size()]; // 선택지 개수만큼 체크박스 배열 생성        	
        	// 체크 박스에 선택지에 해당하는 문자열을 넣어서 Checkbox[]과 패널에 추가
        	for(int j = 0 ; j < choiceStringList.size(); j++ ) {
        		Checkbox choiceBtn = new Checkbox(choiceStringList.get(j));
        		choiceList[j] = (choiceBtn);
        		choiceListPanel.add(choiceBtn); // 패널에도 버튼 추가       		
        	}
        	learningStyleCheckBtnGroupList.add(choiceList); // 선택지버튼그룹리스트에 선택지 체크박스 배열 추가
        	questionPanel.add(choiceListPanel); // 질문 패널에 선택지버튼리스트 추가
        	learningStyleQuestionListPanel.add(questionPanel); // 질문리스트패널에 각 질문패널 추가
        }
	}
	
	// 개념 테스트 질문 및 선택지를 패널에 추가
	void setEvalQuestionAndChoiceButton(JPanel evalQuestionListPanel) {
        // 질문, 선택지(라디오 버튼) 세팅        
        for (int i = 0 ; i < evalQuestions.size(); i++ ) {        	
        	JPanel questionPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        	// 질문 라벨 추가
        	EvaluationQuestion question = evalQuestions.get(i);
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
        	evalChoiceRadioBtnGroupList.add(choiceRadioBtnGroup); // 선택지버튼그룹리스트에 선택지버튼그룹 추가
        	questionPanel.add(choiceListPanel); // 질문 패널에 선택지버튼리스트 추가
        	evalQuestionListPanel.add(questionPanel); // 질문리스트패널에 각 질문패널 추가
        }        
	}
	
 
    
	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {			
			
			int totalPoint = 0;
			try {
				totalPoint = gradeEvaluationResult();
			} catch (IOException err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "제출 오류", JOptionPane.WARNING_MESSAGE);
				return; // 리스너 종료
			}
			// 유저의 얻은 포인트만큼 랭크 포인트를 올림
			user.addRankPoint(totalPoint);
			System.out.println("RankPoint : "+user.getRankPoint());
			JOptionPane.showMessageDialog(null, String.format("감사합니다.\n %d만큼 랭크포인트가 증가되었습니다.", totalPoint), "제출 성공", JOptionPane.INFORMATION_MESSAGE);
			
			// 유저가 선택한 선호하는 알고리즘 종류를 유저데이터에 추가
			addPreferredAlgorithmType_ToUser(learningStyleCheckBtnGroupList.get(0));
			System.out.println("AlgorithmType : "+ user.getPreferredAlgorithmTypeSet());
			
			user.updateUserFile(); // 수정된 유저 정보를 DB 유저 파일에 적용
			dispose(); // 창 닫음
		}
	}	
	
	private void addPreferredAlgorithmType_ToUser(Checkbox[] checkboxList){
        for (int i = 0; i < checkboxList.length; i++) {
            if (checkboxList[i].getState()) { // 체크박스가 선택된 경우
            	System.out.println("선택 : " + checkboxList[i].getLabel());
            	user.addPreferredAlgorithmType(checkboxList[i].getLabel()); // 선택된 알고리즘 종류 유저데이터에 추가            	
            }
        }
	}
	
	// 개념 테스트에서 답한 항목의 값을 정답과 비교하며 획득한 총 포인트를 계산하여 반환
	private int gradeEvaluationResult() throws IOException{
		int totalPoint = 0;	// 획득한 총 포인트
        for (int i =0 ; i < evalChoiceRadioBtnGroupList.size() ; i++) {
        	ButtonGroup group = evalChoiceRadioBtnGroupList.get(i);
        	int index = getSelectedIndexInButtonGroup(group);
        	if(index != -1) { // 선택된 버튼이 있는 경우 
        		// 정답이 맞은 경우
        		if(index == evalQuestions.get(i).getAnswerNum()) {
        			totalPoint += evalQuestions.get(i).getPoint(); // 포인트 가산
        		}        
        	} else { // 선택된 버튼이 없는 경우
        		throw new IOException(String.format("개념 테스트 (%d번) 항목에 답이 선택되지 않았습니다.", i+1));
        	}             
        }
        return totalPoint;
	}
	
	// ButtonGroup에서 선택된 항목의 인덱스를 반환
    private int getSelectedIndexInButtonGroup(ButtonGroup group) {
        Enumeration<AbstractButton> buttons = group.getElements();
        int index = 0;

        // iterator를 이용해 열거형에서 선택된 항목의 인덱스 반환
        while (buttons.hasMoreElements()) {
            if (buttons.nextElement().isSelected()) {
                return index+1; // 선택지는 1부터 시작하므로 +1 해줌
            }
            index++;
        }
        return -1; // 선택된 항목이 없는 경우
    }  
    
    
   
	
}
