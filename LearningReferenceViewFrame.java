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
	
	private JPanel infopanel, referencetitlepanel, referencecontentpanel, referenceViewButtonpanel; 
	private String referenceTitle, referenceContent;
	private JLabel problemName, writer, title;
	private JButton referenceclosebtn;
	
	public LearningReferenceViewFrame(Problem problem, int row, String user) {
		setTitle("LearningReferenceViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.referenceTitle = problem.getProblemReferences().get(row)[1]; // 학습자료 제목
		this.referenceContent = problem.getProblemReferences().get(row)[2]; // 학습자료 내용
		
		addinfopanel(user, problem); // 기본정보
		addlearningreferencecontentpanel(); // 학습자료
		addreferenceViewButtonpanel(); // 학습자료닫기버튼
		
		setVisible(true); 
	}
	
	public void addinfopanel(String user, Problem problem) { // 기본정보 
		infopanel = new JPanel();
		infopanel.setBackground(new Color(255, 255, 255));
		infopanel.setLayout(new GridLayout(3,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(1070, 100); 
		
		problemName = new JLabel(problem.getProblemName());
		writer = new JLabel("작성자 : " + user); 
		title = new JLabel("제 목: "+referenceTitle);
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,25));
		writer.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.add(writer);
		getContentPane().add(infopanel);
	} 
	
	
	public void addlearningreferencecontentpanel() { // 학습자료
		// 패널생성
		referencecontentpanel = new JPanel();
		referencecontentpanel.setBackground(new Color(255, 255, 255));
		referencecontentpanel.setLayout(new GridLayout(1,1)); 
		referencecontentpanel.setBounds(120, 160, 1070, 493);
		referencecontentpanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "내 용"));
	
		getContentPane().add(referencecontentpanel);
		JScrollPane scrollPane = new JScrollPane();
		
		JTextArea referenceContentField = new JTextArea(this.referenceContent);
		referencecontentpanel.add(referenceContentField);
		referenceContentField.add(scrollPane); // 스크롤팬
		referenceContentField.setWrapStyleWord(true);
		referenceContentField.setLineWrap(true);
		referenceContentField.setEditable(false);
		 
	}

public void addreferenceViewButtonpanel() { // 학습자료보기버튼패널
		// 패널 생성
		referenceViewButtonpanel = new JPanel();
		referenceViewButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10)); 
		referenceViewButtonpanel.setLocation(770, 670); // 위치
		referenceViewButtonpanel.setSize(420, 70); // 크기
		// 학습자료 닫기버튼
		DesignedButton referenceViewClosebtn = new DesignedButton("닫    기", 150, 40, COLOR.AQUA_ISLAND);
		referenceViewClosebtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceViewClosebtn.setSize(120, 40);
		
		referenceViewClosebtn.addActionListener (new ActionListener() { //익명클래스 학습자료닫기버튼 리스너
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	
		//프레임에 추가
		referenceViewButtonpanel.add(referenceViewClosebtn);
		referenceViewButtonpanel.setOpaque(false);
		getContentPane().add(referenceViewButtonpanel);
		
	}
}
