package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import gui.HintAddFrame;
import gui.DesignedJFrame;
import gui.DesignedButton;

import problems.*;
import users.User;

public class HintViewFrame extends DesignedJFrame {
	
	JPanel infopanel, hintlistpanel, hintViewbuttonpanel;
	JLabel problemName, title;
	JTable hintlist;
	JButton hintAddbtn;
	private int step;
	
	// type : 보여줄 힌트 스텝 번호
	public HintViewFrame(int type, Problem problem, User user) { 
		setTitle("HintViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.step = type;
		
		addinfopanel(problem); // 기본정보
		addhintListpanel(problem, step); // 힌트 리스트
		addHintViewButtonpanel(step, problem, user); //힌트작성버튼
		setVisible(true);
		
		System.out.println("HintViewFrame : " + user);
	}
	
	public void addinfopanel(Problem problem) { // 기본정보 
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		JLabel problemName = new JLabel(problem.getProblemName());
		JLabel title = new JLabel("   STEP"+step+" 힌트 리스트");
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
 
	} 
	
	
	public void addhintListpanel(Problem problem, int step) { // 힌트 리스트
		hintlistpanel = new JPanel();
		hintlistpanel.setBorder(new LineBorder(Color.black, 2));
		hintlistpanel.setLayout(new GridLayout(1,1)); 
		hintlistpanel.setLocation(120,150); // 위치
		hintlistpanel.setSize(getDefalutWindowWidth() - 200, 500); // 크기
		
		// 힌트 출력
		ArrayList<Hint> hintList = problem.getProblemHint(step);
		// null 값일 경우 리스트 초기화
		hintList = (hintList != null) ? hintList : new ArrayList<>();
		
		String header[] = {"작성자", "STEP "+step+"  힌트 내용"};
		String contentlist[][] = new String[hintList.size()][2];
		
		int row = 0;
		for(Hint hint : hintList) {
			contentlist[row][0] = hint.getWriter().getUsername();
			contentlist[row][1] = hint.getContent();
			row++;
		}
		
		DefaultTableModel dtm = new DefaultTableModel(contentlist, header) { //수정불가능하도록
			public boolean isCellEditable(int row, int  column) {
		        return false;
		      }
		};
		 
		JTable hintlist = new JTable(dtm);
		hintlist.getColumnModel().getColumn(0).setPreferredWidth(100); //첫번째 열 크기 조정
		hintlist.getColumnModel().getColumn(1).setPreferredWidth(700); //두번째 열 크기 조정
		hintlist.setRowHeight(50); // 높이 변경  
		hintlist.setFont(new Font("Sunflower Medium",Font.PLAIN,15)); // 폰트 변경
		// 테이블 헤더 설정
		JTableHeader hintlistHeader = hintlist.getTableHeader();
		hintlistHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		hintlistHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		hintlistHeader.setFont(new Font("Sunflower Medium",Font.BOLD,18));
		Dimension headerSize = hintlistHeader.getPreferredSize();
		headerSize.height = 40;
		hintlistHeader.setPreferredSize(headerSize);
		
		// 프레임에 추가
		JScrollPane hintlistscrollPane = new JScrollPane(hintlist);
		hintlist.setOpaque(false);
		hintlistpanel.add(hintlistscrollPane);
		hintlistpanel.setOpaque(false);
		getContentPane().add(hintlistpanel);
		
	}
 
	public void addHintViewButtonpanel(int step, Problem problem, User user) { // 힌트보기프레임버튼패널
		// 패널 생성
		hintViewbuttonpanel = new JPanel();
		hintViewbuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		hintViewbuttonpanel.setLocation(850, 670); // 위치
		hintViewbuttonpanel.setSize(380, 70); // 크기
		
		// 힌트 작성버튼 추가
		DesignedButton hintAddbtn = new DesignedButton("작성하기", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		hintAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		hintAddbtn.setSize(150, 40);

		hintAddbtn.addActionListener (new ActionListener() { //익명클래스 힌트작성버튼 리스너
			public void actionPerformed(ActionEvent e) {
				// 힌트 리스트에 추가 리스너
				new HintAddFrame(problem, user);
				dispose();  
			}
		});
		
		// 뒤로가기 버튼
		DesignedButton hintbackBtn = new DesignedButton("뒤로가기", 150, 40, COLOR.AQUA_ISLAND);
		hintbackBtn.setLocation(getDefalutWindowWidth() - 370, 680);
		hintbackBtn.setSize(120, 40);
		
		hintbackBtn.addActionListener (new ActionListener() { //익명클래스 학습자료리스트 닫기버튼 리스너
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		
		//프레임에 추가
		hintViewbuttonpanel.add(hintAddbtn);
		hintViewbuttonpanel.add(hintbackBtn);
		hintViewbuttonpanel.setOpaque(false);
		getContentPane().add(hintViewbuttonpanel);
	}
}