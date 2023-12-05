package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import gui.HintViewFrame;
import problems.*;
import users.User;
import gui.DesignedButton;
import gui.DesignedJFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LearningReferenceAddFrame  extends DesignedJFrame{
	
	private JPanel infopanel, referenceTitlepanel, referenceWritepanel, ReferenceRegistButtonpanel; 
	private JLabel problemName, frameTitle;
	private DesignedButton referenceRegistbtn, referenceCancelbtn;
	private JTextField referenceTitleField;
	private JTextArea referenceWriteField;
	
	public LearningReferenceAddFrame(Problem problem, User user) { // 생성자
		setTitle("학습 자료 작성");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addInfoPanel(problem); // 기본정보 패널 추가
		addLearningReferenceWritePanel(); // 학습자료 내용작성패널 추가
		addReferenceTitlePanel(); // 학습자료 제목작성패널 추가
		addReferenceRegistButtonPanel(problem, user); // 학습자료 버튼 패널 추가
		
		setVisible(true); 
	}
	
	public void addInfoPanel(Problem problem) { // 기본정보 
		// 기본정보 패널 설정
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(1000, 100); 
		
		problemName = new JLabel(problem.getProblemName()); // 문제 제목
		frameTitle = new JLabel("문제 학습 자료 작성하기."); // 프레임 안내글
		// 폰트설정
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		frameTitle.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		// 프레임에 추가
		infopanel.add(problemName);
		infopanel.add(frameTitle);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	}
	
	public void addReferenceTitlePanel() { // 학습자료 제목 작성
		// 학습자료 제목작성 패널 설정
		referenceTitlepanel = new JPanel();
		referenceTitlepanel.setLayout(new GridLayout(1,1)); 
		referenceTitlepanel.setLocation(150,170); // 위치
		referenceTitlepanel.setSize(1000, 50); // 크기
		
		// 제목 입력 칸
		referenceTitleField = new JTextField(" 제목을 입력하세요.");
		
		// 프레임에 추가
		referenceTitlepanel.add(referenceTitleField);
		getContentPane().add(referenceTitlepanel);

	}
	 
	public void addLearningReferenceWritePanel() { // 학습자료 작성필드
		// 학습자료 내용작성 패널 설정
		referenceWritepanel = new JPanel();
		referenceWritepanel.setLayout(new GridLayout(1,1)); 
		referenceWritepanel.setLocation(150,250); // 위치
		referenceWritepanel.setSize(1000, 400); // 크기
		
		// 학습자료 내용 작성 칸
		referenceWriteField = new JTextArea(" 내용을 입력하세요."); // 학습자료 작성
		referenceWriteField.setLineWrap(true); // 자동줄바꿈
		referenceWriteField.setWrapStyleWord(true); //단어 단위의 줄바꿈(영어)

		//프레임에 추가
		referenceWritepanel.add(new JScrollPane(referenceWriteField)); // 스크롤팬
		getContentPane().add(referenceWritepanel);
		
	}

	public void addReferenceRegistButtonPanel(Problem problem, User user) { // 학습자료등록버튼 패널
		// 패널 생성
		ReferenceRegistButtonpanel = new JPanel();
		ReferenceRegistButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		ReferenceRegistButtonpanel.setLocation(850, 670); // 위치
		ReferenceRegistButtonpanel.setSize(380, 70); // 크기
		
		// 학습자료 등록버튼
		referenceRegistbtn = new DesignedButton("등록하기", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		referenceRegistbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		referenceRegistbtn.setSize(150, 40);
		
		//익명클래스 학습자료작성버튼 리스너
		referenceRegistbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {				
				//등록버튼 기능 작성
				String addreferenceTitle = referenceTitleField.getText();
				String addreferenceContent = referenceWriteField.getText(); 
				problem.addProblemReferences(user, addreferenceTitle, addreferenceContent);				
				//창전환
				new LearningReferenceListFrame(problem, user);				
				dispose();
			}
		});
		
		//학습자료뒤로가기버튼
		referenceCancelbtn = new DesignedButton("뒤로가기", 150, 40, COLOR.AQUA_ISLAND);
		referenceCancelbtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceCancelbtn.setSize(120, 40);
		
		//익명클래스 학습자료닫기버튼 리스너
		referenceCancelbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				new LearningReferenceListFrame(problem, user);
				dispose();
			}
		});
		
		//프레임에 추가
		ReferenceRegistButtonpanel.add(referenceRegistbtn); // 학습자료 추가버튼
		ReferenceRegistButtonpanel.add(referenceCancelbtn); // 학습자료 닫기 버튼
		ReferenceRegistButtonpanel.setOpaque(false);
		getContentPane().add(ReferenceRegistButtonpanel);
	}
}