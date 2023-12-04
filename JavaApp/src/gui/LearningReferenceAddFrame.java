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
	
	JPanel infopanel, referencetitlepanel, referencewritepanel, ReferenceRegistButtonpanel; 
	JLabel problemName;
	DesignedButton referenceRegistbtn, referenceCancelBtn;
	JTextArea referencewritefield;
	
	public LearningReferenceAddFrame(Problem problem, User user) {
		setTitle("학습 자료 작성");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addinfopanel(problem); // 기본정보
		addlearningreferencewritepanel(); // 학습자료 작성필드
		addReferenceRegistButton(problem, user); // 학습자료등록버튼
		
		setVisible(true); 
	}
	
	public void addinfopanel(Problem problem) { // 기본정보 
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(1000, 100); 
		
		JLabel problemName = new JLabel(problem.getProblemName()); // 수정필요
		JLabel title = new JLabel("문제 학습 자료를 작성해주세요."); 
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	}
	 
	public void addlearningreferencewritepanel() { // 학습자료 작성필드
		referencewritepanel = new JPanel();
		referencewritepanel.setLayout(new GridLayout(1,1)); 
		referencewritepanel.setLocation(150,250); // 위치
		referencewritepanel.setSize(1000, 400); // 크기
		
		referencewritefield = new JTextArea(" 내용을 입력하세요."); // 학습자료 작성
		referencewritefield.setLineWrap(true); // 자동줄바꿈
		referencewritefield.setWrapStyleWord(true); //단어 단위의 줄바꿈(영어)

		referencewritepanel.add(new JScrollPane(referencewritefield)); // 스크롤팬
		getContentPane().add(referencewritepanel);
		
	}

	public void addReferenceRegistButton(Problem problem, User user) { // 학습자료등록버튼
		// 패널 생성
		ReferenceRegistButtonpanel = new JPanel();
		ReferenceRegistButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		ReferenceRegistButtonpanel.setLocation(850, 670); // 위치
		ReferenceRegistButtonpanel.setSize(380, 70); // 크기
		// 학습자료 등록버튼 추가
		DesignedButton referenceRegistbtn = new DesignedButton("등록하기", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		referenceRegistbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		referenceRegistbtn.setSize(150, 40);
		
		referenceRegistbtn.addActionListener (new ActionListener() { //익명클래스 학습자료작성버튼 리스너
			public void actionPerformed(ActionEvent e) {
				JButton referenceRegist = (JButton)e.getSource();
				
				//등록버튼 기능 작성
				String referenceContent = referencewritefield.getText();
				problem.addProblemReferences(referenceContent, user);
//				new LearningMaterialsHintAdd(referenceContent, problem, user); // 추가로 처리하는 작업이 없으면 굳이 객체 생성 필요 없음
				
				//창전환
				dispose();
				LearningReferenceListFrame LRLF= new LearningReferenceListFrame(problem, user);				
			}
		});
		// 닫기 버튼
		DesignedButton referenceCancelBtn = new DesignedButton("닫    기", 150, 40, COLOR.AQUA_ISLAND);
		referenceCancelBtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceCancelBtn.setSize(120, 40);
		
		referenceCancelBtn.addActionListener (new ActionListener() { //익명클래스 학습자료닫기버튼 리스너
			public void actionPerformed(ActionEvent e) {
				dispose();
				LearningReferenceListFrame LRLF= new LearningReferenceListFrame(problem, user);
				LRLF.setVisible(true);
			}
		});
		
		//프레임에 추가
		ReferenceRegistButtonpanel.add(referenceRegistbtn);
		ReferenceRegistButtonpanel.add(referenceCancelBtn);
		ReferenceRegistButtonpanel.setOpaque(false);
		getContentPane().add(ReferenceRegistButtonpanel);
	}
}