package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import gui.HintViewFrame;
import problems.Problem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LearningReferenceViewFrame  extends DesignedJFrame{

	private JPanel infopanel, referenceContentpanel, referenceViewButtonpanel; 
	private String referenceTitle, referenceContent;
	private JLabel problemName, writer, title;
	private JTextArea referenceContentField;
	private JButton referenceViewClosebtn;

	public LearningReferenceViewFrame(Problem problem, int row, String userName) { // 생성자
		// 프레임 설정
		setTitle("LearningReferenceViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.referenceTitle = problem.getProblemReferences().get(row).getTitle(); // 학습자료 제목
		this.referenceContent = problem.getProblemReferences().get(row).getContent(); // 학습자료 내용

		addInfoPanel(userName, problem); // 기본정보 패널
		addLearningReferenceContentPanel(); // 학습자료 패널
		addLearningReferenceViewButtonPanel(); // 버튼 패널

		setVisible(true); 
	}

	public void addInfoPanel(String userName, Problem problem) { // 기본정보 
		// 기본정보 패널 설정
		infopanel = new JPanel();
		infopanel.setBackground(new Color(255, 255, 255));
		infopanel.setLayout(new GridLayout(3,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(1070, 100); 
		
		// 기본 정보
		problemName = new JLabel(problem.getProblemName()); // 문제 제목
		writer = new JLabel("작성자 : " + userName); // 학습자료 작성자
		title = new JLabel("제 목: "+referenceTitle); // 학습자료 제목
		// 폰트 설정
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,25));
		writer.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		// 프레임에 추가
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.add(writer);
		getContentPane().add(infopanel);
	} 


	public void addLearningReferenceContentPanel() { // 학습자료 내용 
		// 학습자료 패널 설정
		referenceContentpanel = new JPanel();
		referenceContentpanel.setBackground(new Color(255, 255, 255));
		referenceContentpanel.setLayout(new GridLayout(1,1)); 
		referenceContentpanel.setBounds(120, 160, 1070, 493);
		referenceContentpanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "내 용")); // 테두리 선
		
		// 학습자료 내용 필드
		referenceContentField = new JTextArea(this.referenceContent);
		referenceContentpanel.add(referenceContentField);
		referenceContentField.add(new JScrollPane()); // 스크롤팬
		referenceContentField.setWrapStyleWord(true);
		referenceContentField.setLineWrap(true);
		referenceContentField.setEditable(false);
		
		// 프레임에 추가
		getContentPane().add(referenceContentpanel);
	}

	public void addLearningReferenceViewButtonPanel() { // 학습자료보기버튼패널
		// 버튼 패널 생성
		referenceViewButtonpanel = new JPanel();
		referenceViewButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10)); 
		referenceViewButtonpanel.setLocation(770, 670); // 위치
		referenceViewButtonpanel.setSize(420, 70); // 크기
		
		// 학습자료 닫기버튼
		referenceViewClosebtn = new DesignedButton("닫    기", 150, 40, COLOR.AQUA_ISLAND);
		referenceViewClosebtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceViewClosebtn.setSize(120, 40);
		
		//익명클래스 학습자료닫기버튼 리스너
		referenceViewClosebtn.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // 창 닫기
			}
		});

		//프레임에 추가
		referenceViewButtonpanel.add(referenceViewClosebtn);
		referenceViewButtonpanel.setOpaque(false);
		getContentPane().add(referenceViewButtonpanel);

	}
}