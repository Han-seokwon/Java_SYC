package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.HintAddFrame;
import gui.DesignedJFrame;
import gui.DesignedButton;

public class HintViewFrame extends DesignedJFrame {
	
	JPanel infopanel, hintlistpanel, hintViewbuttonpanel;
	JLabel problemName, title;
	JTable hintlist;
	JButton hintAddbtn;
	private int step;
	
	public HintViewFrame(int step) { 
		setTitle("HintViewFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.step = step;
		
		addinfopanel(); // 기본정보
		addhintListpanel(); // 힌트 리스트
		addHintViewButtonpanel(); //힌트작성버튼
		setVisible(true);
	}
	
	public void addinfopanel() { // 기본정보 
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		JLabel problemName = new JLabel("(문제 제목)"); // 수정필요
		JLabel title = new JLabel("   STEP"+step+" 힌트 리스트");
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
 
	} 
	
	
	public void addhintListpanel() { // 힌트 리스트
		hintlistpanel = new JPanel();
		hintlistpanel.setBorder(new LineBorder(Color.black, 2));
		hintlistpanel.setLayout(new GridLayout(1,1)); 
		hintlistpanel.setLocation(120,150); // 위치
		hintlistpanel.setSize(getDefalutWindowWidth() - 200, 500); // 크기
		
		
		String header[] = {"작성자", "STEP "+step+"  힌트 내용"};
		String contentlist[][] = { {"작성자1", "내용1"}, 
				                   {"작성자2", "내용2"}, 
								   {"작성자3", "내용3"}, 
								   {"작성자1", "내용1"}, 
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"},
								   {"작성자1", "내용1"}}; //수정
				
		
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
 
	public void addHintViewButtonpanel() { // 힌트보기프레임버튼패널
		// 패널 생성
		hintViewbuttonpanel = new JPanel();
		hintViewbuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		hintViewbuttonpanel.setLocation(850, 670); // 위치
		hintViewbuttonpanel.setSize(380, 70); // 크기
		
		// 힌트 작성버튼 추가
		DesignedButton hintAddbtn = new DesignedButton("힌트 작성하기", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		hintAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		hintAddbtn.setSize(150, 40);

		hintAddbtn.addActionListener (new ActionListener() { //익명클래스 힌트작성버튼 리스너
			public void actionPerformed(ActionEvent e) {
				JButton hintadd = (JButton)e.getSource();
					// 힌트 리스트에 추가 리스너
					new HintAddFrame();
					setVisible(false);  
			}
		});
		
		// 뒤로가기 버튼
		DesignedButton hintbackBtn = new DesignedButton("뒤로가기", 150, 40, COLOR.AQUA_ISLAND);
		hintbackBtn.setLocation(getDefalutWindowWidth() - 370, 680);
		hintbackBtn.setSize(120, 40);
		
		hintbackBtn.addActionListener (new ActionListener() { //익명클래스 학습자료리스트 닫기버튼 리스너
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		//프레임에 추가
		hintViewbuttonpanel.add(hintAddbtn);
		hintViewbuttonpanel.add(hintbackBtn);
		hintViewbuttonpanel.setOpaque(false);
		getContentPane().add(hintViewbuttonpanel);
	}
	
	
	public static void main(String[] args) { // 메인함수
		new HintViewFrame(1); //매개변수 - step
		//new HintViewFrame(2);
		//new HintViewFrame(3);
	}
}
