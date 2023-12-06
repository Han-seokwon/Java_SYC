package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import problems.Problem;
import users.RANK;
import users.User;


public class HintAddFrame extends DesignedJFrame{
	
	private JPanel infopanel, hintGuidepanel, selectSteppanel, hintWritepanel, hintAddButtonpanel; 
	private JLabel problemName, frameTitle;
	private JTable hintGuide;
	private JComboBox<String> selectStep;
	private JTextArea hintWriteField;
	private DesignedButton hintRegistbtn, hintCancelbtn;
	int step = 1;
	

	public HintAddFrame(Problem problem, User user) { // 생성자
		setTitle("HintAddFrame"); // 프레임이름
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addInfoPanel(problem); // 기본정보패널추가
		addHintGuidePanel(); // 힌트작성가이드패널추가
		addSelectStepPanel(); // step 선택패널 추가
		addHintWritePanel(); // 힌트 작성패널 추가
		addHintViewButtonPanel(problem, user); //버튼 패널
		
		setVisible(true); 
	}
	
	public void addInfoPanel(Problem problem) { // 기본정보 
		// 기본정보 패널
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		problemName = new JLabel(problem.getProblemName()); // 문제 제목
		frameTitle = new JLabel("   힌트 작성하기"); // 프레임 안내글
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35)); // 문제 제목 폰트설정
		frameTitle.setFont(new Font("Sunflower Medium",Font.PLAIN,18)); // 프레임 안내글 폰트설정
		
		// 프레임에 해당 패널 추가
		infopanel.add(problemName);
		infopanel.add(frameTitle);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	} 
	
	
	public void addHintGuidePanel() { // 힌트 작성 가이드
		// 힌트 작성 가이드 패널
		hintGuidepanel = new JPanel();
		hintGuidepanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "힌트 작성 가이드")); // 테두리선
		hintGuidepanel.setLayout(new GridLayout(1,1)); 
		hintGuidepanel.setLocation(150,150); // 위치
		hintGuidepanel.setSize(450, 170); // 크기
		
		// 힌트 단계별 가이드
		String step1guide = " 문제 접근 방법 제시";
		String step2guide = " 사용되는 핵심 알고리즘 및 일부 로직 공개";
		String step3guide = " 전체 해결 방법 공개";
		String header[] = {"STEP", "힌트 작성 가이드"};
		String content[][] = { {" STEP 1", step1guide},{" STEP 2", step2guide}, {" STEP 3", step3guide}}; //힌트 가이드 내용
		DefaultTableModel dtm = new DefaultTableModel(content, header) { 
			public boolean isCellEditable(int row, int  column) {
		        return false; //힌트 작성 가이드 표 내용 수정불가능하도록
		      } 
		};
		
		// 힌트 작성가이드 표
		hintGuide = new JTable(dtm);
		hintGuide.setFont(new Font("Sunflower Medium",Font.PLAIN,15)); // 폰트 설정
		hintGuide.getColumnModel().getColumn(0).setPreferredWidth(100); //첫번째 열 크기 조정
		hintGuide.getColumnModel().getColumn(1).setPreferredWidth(500); //두번째 열 크기 조정
		hintGuide.setRowHeight(50); // 높이 설정
		
		// 작성가이드 패널을 프레임에 추가
		hintGuidepanel.add(hintGuide);
		hintGuidepanel.setOpaque(false);
		getContentPane().add(hintGuidepanel);
}
	
	public void addSelectStepPanel() { // 힌트 step 선택
		// 힌트 step 선택 패널
		selectSteppanel = new JPanel();
		selectSteppanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "STEP 선택")); // 테두리선
		selectSteppanel.setLayout(new GridLayout(1,1)); 
		selectSteppanel.setLocation(620,150); // 위치
		selectSteppanel.setSize(350, 60); // 크기
		
		
		String[] steplist = {"Step 1", "Step 2", "Step 3"}; // 힌트 단계 선택 리스트
		selectStep = new JComboBox<String>(steplist); // 콤보박스
		
		// 힌트 단게 선택 콤보박스 액션 리스너
		selectStep.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				JComboBox<String> stp = (JComboBox<String>)e.getSource(); //Action이벤트가 발생한 콤보박스 알아내기
				int idx = stp.getSelectedIndex(); // 선택된 아이템의 인덱스 번호 알아내기
				step = idx + 1; //힌트 단계
			}
		});
		
		// 폰트설정및 프레임에 추가
		selectStep.setFont(new Font("Sunflower Medium",Font.PLAIN,15));
		selectSteppanel.add(selectStep);
		selectSteppanel.setOpaque(false);
		getContentPane().add(selectSteppanel);
		
	}
	
	public void addHintWritePanel() { // 힌트 작성필드
		int textCntLimit = 60; // 글자 수 제한
		hintWritepanel = new JPanel();
		hintWritepanel.setLayout(new GridLayout(1,1)); 
		hintWritepanel.setLocation(140,350); // 위치
		hintWritepanel.setSize(getDefalutWindowWidth() - 230, 300); // 크기
		
		hintWriteField = new JTextArea("내용을 입력해 주세요." + "(글자 수 제한 " + textCntLimit + "자)"); // 힌트 작성
		hintWriteField.setLineWrap(true); // 자동줄바꿈
		hintWriteField.setWrapStyleWord(true); //단어 단위의 줄바꿈(영어)
		hintWriteField.addKeyListener(new TextCntLimitListener(textCntLimit));
		hintWritepanel.add(new JScrollPane(hintWriteField)); // 스크롤팬
		hintWritepanel.setOpaque(false);
		getContentPane().add(hintWritepanel);
		
	}
	// 최대 글자수 제한
	class TextCntLimitListener extends KeyAdapter{
		private int maxTextCnt;
		public TextCntLimitListener() {
			this(60);
		}
		public TextCntLimitListener(int maxTextCnt) {
			this.maxTextCnt = maxTextCnt;
		}
		
		@Override
        public void keyTyped(KeyEvent ke) {
			//글자 수 초과시 글자 초과되는 글자 자동 삭제
			if(hintWriteField.getText().length() > maxTextCnt) { 
				ke.consume();
				hintWriteField.setText(hintWriteField.getText().substring(0, maxTextCnt));
			}
        }
	}
	 
	public void addHintViewButtonPanel(Problem problem, User user) { // 힌트작성프레임버튼패널
		// 패널 생성
		hintAddButtonpanel = new JPanel();
		hintAddButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		hintAddButtonpanel.setLocation(850, 670); // 위치
		hintAddButtonpanel.setSize(380, 70); // 크기
		
		// 힌트 작성버튼 추가
		hintRegistbtn = new DesignedButton("등록하기", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		hintRegistbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		hintRegistbtn.setSize(150, 40);
		
		//익명클래스 힌트작성버튼 리스너
		hintRegistbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				
				// 힌트 리스트에 작성한 힌트내용 추가
				String referenceContent = hintWriteField.getText();
				problem.addProblemHint(step, user, referenceContent);
				
				// 창 전환
				new HintViewFrame(step, problem, user);
				dispose();
			}
		});
					
		// 뒤로가기 버튼
		hintCancelbtn = new DesignedButton("뒤로가기", 150, 40, COLOR.AQUA_ISLAND);
		hintCancelbtn.setLocation(getDefalutWindowWidth() - 370, 680);
		hintCancelbtn.setSize(120, 40);
		
		//익명클래스 학습자료닫기버튼 리스너
		hintCancelbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				// 창전환
				new HintViewFrame(step, problem, user);				
				dispose();
			}
		});
		
		//프레임에 추가
		hintAddButtonpanel.add(hintRegistbtn);
		hintAddButtonpanel.add(hintCancelbtn);
		hintAddButtonpanel.setOpaque(false);
		getContentPane().add(hintAddButtonpanel);
	}
}