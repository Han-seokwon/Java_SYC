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
		
		
		//������ ��ġ�� �� �� ��ư ����, �߰�
		JLabel hintlabel = new JLabel("��Ʈ");
		STEP1Button = new JButton("STEP1");
		STEP2Button = new JButton("STEP2");
		STEP3Button = new JButton("STEP3");
		JLabel spacelabel = new JLabel("");
		dataBButton = new JButton("�н� �ڷ�");
		efficiencyButton = new JButton("�ڵ� ȿ���� ��");
		levelButton = new JButton("���̵� ��");
		mainButton = new JButton("����ȭ��");
		
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
		

		
		//���� �г�
		JPanel Problem1 = new JPanel();
		Problem1.setBounds(32, 63, 119, 250);
		contentPane.add(Problem1);
		Problem1.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel id = new JLabel("���� ��ȣ");
		JLabel name = new JLabel("����");
		JLabel hard = new JLabel("���̵�");
		JLabel point = new JLabel("ȹ�� ����Ʈ");
		JLabel url = new JLabel("URL");
		JLabel algo = new JLabel("�˰���");
		
		id.setHorizontalAlignment(SwingConstants.CENTER); //�� ���� ��ġ(�߾�)
		id.setBorder(new LineBorder(new Color(0, 0, 0))); //�׵θ� ��
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
		
		JLabel ppoint = new JLabel("##����Ʈ ���߿� �߰�##");
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
		
		JLabel comment = new JLabel("���� ���̵� �ڸ�Ʈ");
		comment.setHorizontalAlignment(SwingConstants.CENTER);
		comment.setBounds(32, 327, 119, 25);
		contentPane.add(comment);
		
		setVisible(true); // ������ ǥ��
		
		purl.addMouseListener(new MyMouseListener());
		
//        purl.addMouseListener(new MouseAdapter() { //purl �� Ŭ���� �ش� ��ũ�� �̵�
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
		
	

