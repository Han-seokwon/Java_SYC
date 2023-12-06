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
	
	private JPanel infopanel, hintListpanel, hintViewbuttonpanel;
	private JLabel problemName, frameTitle;
	private ArrayList<Hint> hintList;
	private JTable hintTable;
	private JScrollPane hintlistscrollPane;
	private DesignedButton hintAddbtn, hintBackbtn;
	private int step;
	
	// type : 보여줄 힌트 스텝 번호
	public HintViewFrame(int type, Problem problem, User user) { // 생성자
		setTitle("HintViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.step = type;
		
		addInfoPanel(problem); // 기본정보 패널 추가
		addHintListPanel(problem, step); // 힌트 리스트 패널 추가
		addHintViewButtonPanel(step, problem, user); // 버튼 패널 추가
		setVisible(true);
		
	}
	
	public void addInfoPanel(Problem problem) { // 기본정보 
		// 기본정보 패널
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		// 기본정보
		problemName = new JLabel(problem.getProblemName()); // 문제제목
		frameTitle = new JLabel("   STEP"+step+" 힌트 리스트"); // 프레임 안내글
		// 폰트설정
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		frameTitle.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		// 기본정보 패널을 프레임에 추가
		infopanel.add(problemName);
		infopanel.add(frameTitle);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	} 
	
	
	public void addHintListPanel(Problem problem, int step) { // 힌트 리스트
		// 힌트리스트 패널
		hintListpanel = new JPanel();
		hintListpanel.setBorder(new LineBorder(Color.black, 2)); // 테두리 선
		hintListpanel.setLayout(new GridLayout(1,1)); 
		hintListpanel.setLocation(120,150); // 위치
		hintListpanel.setSize(getDefalutWindowWidth() - 200, 500); // 크기
		
		// 힌트 출력
		hintList = problem.getProblemHint(step);
		// null 값일 경우 리스트 초기화
		hintList = (hintList != null) ? hintList : new ArrayList<>();
		
		String header[] = {"작성자", "STEP "+step+"  힌트 내용"};
		String contentlist[][] = new String[hintList.size()][2];
		
		// 테이블에 힌트 리스트 추가
		int row = 0;
		for(Hint hint : hintList) {
			contentlist[row][0] = hint.getWriter().getUsername();
			contentlist[row][1] = hint.getContent();
			row++;
		}
		
		DefaultTableModel dtm = new DefaultTableModel(contentlist, header) { //수정불가능하도록
			public boolean isCellEditable(int row, int  column) {
		        return false; //테이블 내용 수정불가능하도록
		      }
		};
		
		//힌트 리스트 테이블 생성
		hintTable = new JTable(dtm);
		hintTable.getColumnModel().getColumn(0).setPreferredWidth(120); //첫번째 열 크기 조정
		hintTable.getColumnModel().getColumn(1).setPreferredWidth(hintListpanel.getWidth()-120); //두번째 열 크기 조정
		hintTable.setRowHeight(50); // 높이 변경  
		hintTable.setFont(new Font("Sunflower Medium",Font.PLAIN,15)); // 폰트 변경
		// 테이블 헤더 설정
		JTableHeader hintlistHeader = hintTable.getTableHeader();
		hintlistHeader.setBackground(COLOR.AQUA_ISLAND.getColor());// 헤더 배경색 설정
		hintlistHeader.setFont(new Font("Sunflower Medium",Font.BOLD,18));// 헤더 폰트 설정
		Dimension headerSize = hintlistHeader.getPreferredSize();
		headerSize.height = 40; // 테이블 헤더 높이
		hintlistHeader.setPreferredSize(headerSize);
		
		// 프레임에 추가
		hintlistscrollPane = new JScrollPane(hintTable); // 스크롤 팬
		hintTable.setOpaque(false);
		hintListpanel.add(hintlistscrollPane);
		hintListpanel.setOpaque(false);
		getContentPane().add(hintListpanel);
		
	}
 
	public void addHintViewButtonPanel(int step, Problem problem, User user) { // 힌트보기프레임버튼패널
		// 패널 생성
		hintViewbuttonpanel = new JPanel();
		hintViewbuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		hintViewbuttonpanel.setLocation(850, 670); // 위치
		hintViewbuttonpanel.setSize(380, 70); // 크기
		
		// 힌트 작성버튼 추가
		hintAddbtn = new DesignedButton("작성하기", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		hintAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		hintAddbtn.setSize(150, 40);
		
		//익명클래스 힌트작성버튼 리스너
		hintAddbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				// 힌트 리스트에 추가
				new HintAddFrame(problem, user);
				dispose();  
			}
		});
		
		// 닫기 버튼
		hintBackbtn = new DesignedButton("닫  기", 150, 40, COLOR.AQUA_ISLAND);
		hintBackbtn.setLocation(getDefalutWindowWidth() - 370, 680);
		hintBackbtn.setSize(120, 40);
		
		//익명클래스 힌트리스트프레임 닫기버튼 리스너
		hintBackbtn.addActionListener (new ActionListener() { //익명클래스 학습자료리스트 닫기버튼 리스너
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		
		//프레임에 추가
		hintViewbuttonpanel.add(hintAddbtn); // 힌트작성버튼 패널에 추가
		hintViewbuttonpanel.add(hintBackbtn); // 프레임닫기버튼 패널에 추가
		hintViewbuttonpanel.setOpaque(false); // 배경색 투명
		getContentPane().add(hintViewbuttonpanel); // 패널을 프레임에 추가
	}
}