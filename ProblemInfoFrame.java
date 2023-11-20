package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.net.*;
import javax.swing.border.LineBorder;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import problems.*;
import users.*;

public class ProblemInfoFrame extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton STEP1Button, STEP2Button, STEP3Button, dataBButton, efficiencyButton, levelButton, mainButton;
	private Problem pproblem;
	private JLabel purl;

	public ProblemInfoFrame(Problem problem) {
		
		pproblem = problem;
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 459);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(530, 63, 119, 297);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		//우측에 위치할 라벨 및 버튼 생성, 추가
		JLabel hintlabel = new JLabel("힌트");
		STEP1Button = new JButton("STEP1");
		STEP2Button = new JButton("STEP2");
		STEP3Button = new JButton("STEP3");
		JLabel spacelabel = new JLabel("");
		dataBButton = new JButton("학습 자료");
		efficiencyButton = new JButton("코드 효율성 비교");
		levelButton = new JButton("난이도 비교");
		mainButton = new JButton("메인화면");
		
		panel.add(hintlabel);
		panel.add(STEP1Button);
		panel.add(STEP2Button);
		panel.add(STEP3Button);
		panel.add(spacelabel);
		panel.add(dataBButton);
		panel.add(efficiencyButton);
		panel.add(levelButton);
		panel.add(mainButton);		
		
		ActionListener actionListener = new MyActionListener();
		STEP1Button.addActionListener(actionListener);
		STEP2Button.addActionListener(actionListener);
		STEP3Button.addActionListener(actionListener);
		dataBButton.addActionListener(actionListener);
		efficiencyButton.addActionListener(actionListener);
		levelButton.addActionListener(actionListener);
		mainButton.addActionListener(actionListener);
		

		
		//문제 패널
		JPanel Problem1 = new JPanel();
		Problem1.setBounds(32, 63, 119, 250);
		contentPane.add(Problem1);
		Problem1.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel id = new JLabel("문제 번호");
		JLabel name = new JLabel("제목");
		JLabel hard = new JLabel("난이도");
		JLabel point = new JLabel("획득 포인트");
		JLabel url = new JLabel("URL");
		JLabel algo = new JLabel("알고리즘");
		
		id.setHorizontalAlignment(SwingConstants.CENTER); //라벨 문자 위치(중앙)
		id.setBorder(new LineBorder(new Color(0, 0, 0))); //테두리 색
		Problem1.add(id);
		
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setBorder(new LineBorder(new Color(0, 0, 0)));
		Problem1.add(name);
		
		hard.setHorizontalAlignment(SwingConstants.CENTER);
		hard.setBorder(new LineBorder(new Color(0, 0, 0)));
		Problem1.add(hard);
		
		point.setHorizontalAlignment(SwingConstants.CENTER);
		point.setBorder(new LineBorder(new Color(0, 0, 0)));
		Problem1.add(point);
		
		url.setHorizontalAlignment(SwingConstants.CENTER);
		url.setBorder(new LineBorder(new Color(0, 0, 0)));
		Problem1.add(url);

		algo.setHorizontalAlignment(SwingConstants.CENTER);
		algo.setBorder(new LineBorder(new Color(0, 0, 0)));
		Problem1.add(algo);
		
		//문제 패널
		JPanel ProblemData = new JPanel();
		ProblemData.setBounds(151, 63, 287, 250);
		contentPane.add(ProblemData);
		ProblemData.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel pID = new JLabel(String.valueOf(problem.getProblemID()));
		pID.setHorizontalAlignment(SwingConstants.CENTER);
		pID.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProblemData.add(pID);
		
		JLabel pname = new JLabel(problem.getProblemName());
		pname.setHorizontalAlignment(SwingConstants.CENTER);
		pname.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProblemData.add(pname);
		
		JLabel prank = new JLabel(String.valueOf(problem.getProblemRank()));
		prank.setHorizontalAlignment(SwingConstants.CENTER);
		prank.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProblemData.add(prank);
		
		JLabel ppoint = new JLabel("##포인트 나중에 추가##");
		ppoint.setHorizontalAlignment(SwingConstants.CENTER);
		ppoint.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProblemData.add(ppoint);
		
		purl = new JLabel(problem.getProblemURL());
		purl.setHorizontalAlignment(SwingConstants.CENTER);
		purl.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProblemData.add(purl);
		
		JLabel palgo = new JLabel(String.valueOf(problem.getProblemAlgorithm()));
		palgo.setHorizontalAlignment(SwingConstants.CENTER);
		palgo.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProblemData.add(palgo);

		JScrollPane commentScrollPane = new JScrollPane();
		commentScrollPane.setBounds(32, 352, 449, 60);
		contentPane.add(commentScrollPane);
		
		JLabel comment = new JLabel("문제 난이도 코멘트");
		comment.setHorizontalAlignment(SwingConstants.CENTER);
		comment.setBounds(32, 327, 119, 25);
		contentPane.add(comment);
		
		setVisible(true); // 프레임 표시
		
		purl.addMouseListener(new MyMouseListener());
		
//        purl.addMouseListener(new MouseAdapter() { //purl 라벨 클릭시 해당 링크로 이동
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                try {
//                    String url = problem.getProblemURL();
//                    if (url != null && !url.isEmpty() && url.startsWith("http")) {
//                        Desktop.getDesktop().browse(new URI(url));
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
		
		
	}
	
    private class MyMouseListener extends MouseAdapter { //url(purl 라벨) 클릭 이벤트 제어
    	public void mouseClicked(MouseEvent e) {
            try {
                String url = pproblem.getProblemURL();
                if (url != null && !url.isEmpty() && url.startsWith("http")) {
                    Desktop.getDesktop().browse(new URI(url));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        @Override
        public void mouseEntered(MouseEvent e) {
            purl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            purl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
	
	

	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
				if(e.getSource() == STEP1Button) { // 
					System.out.println("STEP1Button");
				}
				if (e.getSource() == STEP2Button) { // 
					System.out.println("STEP2Button");
				}	
				if (e.getSource() == STEP3Button) { //
					System.out.println("STEP3Button");
				}
				if (e.getSource() == dataBButton) { // 
					System.out.println("dataBButton");
				}
				if (e.getSource() == efficiencyButton) {  //
					System.out.println("efficiencyButton");
				}
				if (e.getSource() == levelButton) { // 
					System.out.println("levelButton");
				}
				if (e.getSource() == mainButton) { // 
					System.out.println("mainButton");
				}
		}
	}
}
		
	

