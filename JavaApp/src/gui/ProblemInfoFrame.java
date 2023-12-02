package gui;


import java.awt.Color;

import java.awt.GridLayout;

import java.net.*;
import javax.swing.border.LineBorder;


import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import javax.swing.JScrollPane;

import problems.*;
import users.*;

public class ProblemInfoFrame extends DesignedJFrame {
	
	
	private static final long serialVersionUID = 1L;
	private DesignedContentPane contentPane;
	private JButton STEP1Button, STEP2Button, STEP3Button, dataBButton, efficiencyButton, levelButton, mainButton;
	private Problem pproblem;
	private JLabel purl;
	private User user;
	

	public ProblemInfoFrame(Problem problem, User user) {
		this.user = user;
		
		pproblem = problem;
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new DesignedContentPane();
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ActionListener actionListener = new MyActionListener();
		

		
		//���� �г�
		JPanel Problem1 = new JPanel();
		Problem1.setBounds(172, 158, 158, 363);
		contentPane.add(Problem1);
		Problem1.setLayout(new GridLayout(0, 1, 0, 0));
		//Problem1.setOpaque(false);
		Problem1.setBackground(new Color(125, 184, 242));
		Problem1.setOpaque(true);

		JLabel id = new JLabel("���� ��ȣ");
		JLabel name = new JLabel("����");
		JLabel hard = new JLabel("���̵�");
		JLabel point = new JLabel("ȹ�� ����Ʈ");
		JLabel url = new JLabel("URL");
		JLabel algo = new JLabel("�˰���");
		
		id.setHorizontalAlignment(SwingConstants.CENTER); //�� ���� ��ġ(�߾�)
		id.setBorder(new LineBorder(new Color(0, 0, 0)));
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
		
		//���� �г�
		JPanel ProblemData = new JPanel();
		ProblemData.setBounds(330, 158, 509, 363);
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
		
		RANK rank = problem.getProblemRank(); // ��ũ
		int rankPoint = problem.getProblemRankPoint(); // ��ũ ����Ʈ
		JLabel prank = new JLabel(String.format("%s(%d)", rank.getRankName(), rankPoint));
		prank.setHorizontalAlignment(SwingConstants.CENTER);
		prank.setBorder(new LineBorder(new Color(0, 0, 0)));
		ProblemData.add(prank);
		
		JLabel ppoint = new JLabel(String.valueOf(rank.getPointGain())); // ���� �ذ� �� ȹ���� �� �ִ� ����Ʈ
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
		commentScrollPane.setBounds(172, 602, 667, 146);
		commentScrollPane.setOpaque(false);
		commentScrollPane.setBackground(Color.black);
		contentPane.add(commentScrollPane);
		
		JLabel comment = new JLabel("���� ���̵� �ڸ�Ʈ");
		comment.setHorizontalAlignment(SwingConstants.CENTER);
		comment.setBounds(172, 549, 187, 65);
		contentPane.add(comment);
		
		
		//������ ��ġ�� �� �� ��ư ����, �߰�
		JLabel hintlabel = new JLabel("��Ʈ");
		hintlabel.setBounds(954, 108, 195, 46);
		contentPane.add(hintlabel);
		hintlabel.setForeground(new Color(130, 141, 226));
		hintlabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		dataBButton = new DesignedButton("�н��ڷ�", COLOR.JORDY_BLUE);
		dataBButton.setBounds(954, 611, 195, 77);
		contentPane.add(dataBButton);
		efficiencyButton = new DesignedButton("ȿ���� ��", COLOR.JORDY_BLUE);
		efficiencyButton.setBounds(954, 524, 195, 77);
		contentPane.add(efficiencyButton);
		levelButton = new DesignedButton("���̵� �⿩", COLOR.JORDY_BLUE);
		levelButton.setBounds(954, 437, 195, 77);
		contentPane.add(levelButton);
		mainButton = new DesignedButton("���� ��� Ȯ��", COLOR.JORDY_BLUE);
		mainButton.setBounds(954, 697, 195, 77);
		contentPane.add(mainButton);
		STEP3Button = new DesignedButton("STEP 3", COLOR.CHET_WODE_BLUE);
		STEP3Button.setBounds(954, 338, 195, 77);
		contentPane.add(STEP3Button);
		STEP2Button = new DesignedButton("STEP 2", COLOR.CHET_WODE_BLUE);
		STEP2Button.setBounds(954, 251, 195, 77);
		contentPane.add(STEP2Button);
		STEP1Button = new DesignedButton("STEP 1", COLOR.CHET_WODE_BLUE);
		STEP1Button.setBounds(954, 164, 195, 77);
		contentPane.add(STEP1Button);
		
		
		STEP1Button.addActionListener(actionListener);
		STEP2Button.addActionListener(actionListener);
		STEP3Button.addActionListener(actionListener);
		mainButton.addActionListener(actionListener);
		levelButton.addActionListener(actionListener);
		efficiencyButton.addActionListener(actionListener);
		dataBButton.addActionListener(actionListener);
		
		setVisible(true); // ������ ǥ��
		
		purl.addMouseListener(new MyMouseListener());
		
		contentPane.applyFontAndBackgroundToAllComponents(); // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
		

		
			
	}
	
    private class MyMouseListener extends MouseAdapter { //url(purl ��) Ŭ�� �̺�Ʈ ����
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
					CodeEficiency co = new CodeEficiency(pproblem);
					co.setVisible(true);
				}
				if (e.getSource() == levelButton) { // 
					System.out.println("levelButton �̺�Ʈ �߰�");
					new RateProblemFrame(user, pproblem);
				}
				if (e.getSource() == mainButton) { // 
					System.out.println("mainButton");
					dispose();
					
				}
		}
	}
}
		
	

