package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import problems.Problem;
import users.User;


public class HintAddFrame extends DesignedJFrame{
	
	JPanel infopanel, hintguidepanel, selectsteppanel, hintwritepanel, hintAddbuttonpanel; 
	JLabel problemName;
	JTable hintguide;
	JButton hintregistbtn;
	String[] steplist;
	JComboBox selectstep;
	JTextArea hintwritefield;
	DesignedContentPane background;
	int step = 1;
	
	User user = new User();
	Problem problem = new Problem();

	public HintAddFrame(Problem problem, User user) {
		setTitle("HintAddFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addinfopanel(problem); // 기본정보
		addhintguidepanel(); // 힌트작성가이드
		addselectsteppanel(); // step 선택
		addhintwritepanel(); // 힌트 작성필드
		addHintViewButtonpanel(problem, user); //버튼 패널
		
		
		setVisible(true); 
	}
	
	public void addinfopanel(Problem problem) { // 기본정보 
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		JLabel problemName = new JLabel(problem.getProblemName());
		JLabel title = new JLabel("   힌트 작성하기"); 
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	} 
	
	
	public void addhintguidepanel() { // 힌트 작성 가이드
		hintguidepanel = new JPanel();
		hintguidepanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "힌트 작성 가이드"));
		hintguidepanel.setLayout(new GridLayout(1,1)); 
		hintguidepanel.setLocation(150,150); // 위치
		hintguidepanel.setSize(450, 170); // 크기
		
		
		String step1guide = " 문제 접근 방법 제시";
		String step2guide = " 사용되는 핵심 알고리즘 및 일부 로직 공개";
		String step3guide = " 전체 해결 방법 공개";
		String header[] = {"STEP", "힌트 작성 가이드"};
		String content[][] = { {" STEP 1", step1guide},{" STEP 2", step2guide}, {" STEP 3", step3guide}}; //힌트 가이드 내용
		DefaultTableModel dtm = new DefaultTableModel(content, header) { //수정불가능하도록
			public boolean isCellEditable(int row, int  column) {
		        return false;
		      } 
		};
		 
		JTable hintguide = new JTable(dtm);
		hintguide.setFont(new Font("Sunflower Medium",Font.PLAIN,15)); // 폰트 변경
		hintguide.getColumnModel().getColumn(0).setPreferredWidth(100); //첫번째 열 크기 조정
		hintguide.getColumnModel().getColumn(1).setPreferredWidth(500); //두번째 열 크기 조정
		hintguide.setRowHeight(50); // 높이 변경  
		
		hintguidepanel.add(hintguide);
		hintguidepanel.setOpaque(false);
		getContentPane().add(hintguidepanel);
	}
	
	public void addselectsteppanel() { // 힌트 step 선택
		selectsteppanel = new JPanel();
		selectsteppanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "STEP 선택"));
		selectsteppanel.setLayout(new GridLayout(1,1)); 
		selectsteppanel.setLocation(620,150); // 위치
		selectsteppanel.setSize(350, 60); // 크기
		
		String[] steplist = {"Step 1", "Step 2", "Step 3"};
		JComboBox<String> selectstep = new JComboBox<String>(steplist);
		
		// 액션 리스너
		selectstep.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				JComboBox<String> stp = (JComboBox<String>)e.getSource(); //Action이벤트가 발생한 콤보박스 알아내기
				int idx = stp.getSelectedIndex(); // 선택된 아이템의 인덱스 번호 알아내기
				step = idx + 1; //힌트 단계
			}
		});
		
		selectstep.setFont(new Font("Sunflower Medium",Font.PLAIN,15));
		selectsteppanel.add(selectstep);
		selectsteppanel.setOpaque(false);
		getContentPane().add(selectsteppanel);
		
	}
	
	public void addhintwritepanel() { // 힌트 작성필드
		hintwritepanel = new JPanel();
		hintwritepanel.setLayout(new GridLayout(1,1)); 
		hintwritepanel.setLocation(140,350); // 위치
		hintwritepanel.setSize(getDefalutWindowWidth() - 230, 300); // 크기
		
		hintwritefield = new JTextArea("내용을 입력해 주세요."); // 힌트 작성
		hintwritefield.setLineWrap(true); // 자동줄바꿈
		hintwritefield.setWrapStyleWord(true); //단어 단위의 줄바꿈(영어)

		hintwritepanel.add(new JScrollPane(hintwritefield)); // 스크롤팬
		hintwritepanel.setOpaque(false);
		getContentPane().add(hintwritepanel);
		
	}
	 
		public void addHintViewButtonpanel(Problem problem, User user) { // 힌트보기프레임버튼패널
			// 패널 생성
			hintAddbuttonpanel = new JPanel();
			hintAddbuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
			hintAddbuttonpanel.setLocation(850, 670); // 위치
			hintAddbuttonpanel.setSize(380, 70); // 크기
			
			// 힌트 작성버튼 추가
			DesignedButton hintRegistbtn = new DesignedButton("등록하기", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
			hintRegistbtn.setLocation(getDefalutWindowWidth() - 230, 680);
			hintRegistbtn.setSize(150, 40);

			hintRegistbtn.addActionListener (new ActionListener() { //익명클래스 힌트작성버튼 리스너
				public void actionPerformed(ActionEvent e) {
					
					// 힌트 리스트에 추가 리스너
					String referenceContent = hintwritefield.getText();
					String hintstep = "Step " + String.valueOf(step);
					
					problem.addProblemHint(hintstep, user, referenceContent);
					
					// 힌트 리스트에 작성한 힌트내용 추가
					new HintViewFrame(step, problem, user);
					dispose();
				}
			});
			
						
			// 닫기 버튼
			DesignedButton hintCancelBtn = new DesignedButton("닫  기", 150, 40, COLOR.AQUA_ISLAND);
			hintCancelBtn.setLocation(getDefalutWindowWidth() - 370, 680);
			hintCancelBtn.setSize(120, 40);
			
			hintCancelBtn.addActionListener (new ActionListener() { //익명클래스 학습자료닫기버튼 리스너
				public void actionPerformed(ActionEvent e) {
					new HintViewFrame(step, problem, user);				
					dispose();
				}
			});
			
			
			//프레임에 추가
			hintAddbuttonpanel.add(hintRegistbtn);
			hintAddbuttonpanel.add(hintCancelBtn);
			hintAddbuttonpanel.setOpaque(false);
			getContentPane().add(hintAddbuttonpanel);
		}
}