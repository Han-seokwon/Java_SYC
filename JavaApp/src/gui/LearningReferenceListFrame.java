package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map.Entry;

import gui.HintAddFrame;
import gui.LearningReferenceAddFrame;
import gui.LearningReferenceViewFrame;

import problems.Problem;
import problems.ProblemDBManager;
import users.User;

public class LearningReferenceListFrame extends DesignedJFrame {
	
	JPanel infopanel, referencelistpanel, referencebuttonpanel;
	JLabel problemName, frametitle;
	JTable Referencelist;
	JButton ReferenceAddbtn;
	
	public LearningReferenceListFrame(Problem problem, User user) { 
		setTitle("LearningReferenceViewFrame");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addinfopanel(problem); // 기본정보
		addlearningreferenceListpanel(problem); // 학습자료 리스트
		addLearningReferenceButtonpanel(problem, user);
		
		setVisible(true);
	}
	
	public void addinfopanel(Problem problem) { // 기본정보 
		infopanel = new JPanel(); 
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		JLabel problemName = new JLabel(problem.getProblemName()); // 수정필요
		JLabel title = new JLabel("학습 자료 리스트");
		problemName.setFont(new Font("Sunflower Medium", Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);

	} 
	
	
	public void addlearningreferenceListpanel(Problem problem) { // 학습자료 리스트
		referencelistpanel = new JPanel();
		referencelistpanel.setBorder(new LineBorder(Color.black, 2));
		referencelistpanel.setLayout(new GridLayout(1,1)); 
		referencelistpanel.setLocation(120,150); // 위치
		referencelistpanel.setSize(getDefalutWindowWidth() - 200, 500); // 크기
		
		// null 값일 경우 리스트 초기화
		ArrayList<String[]> referenceList = problem.getProblemReferences();
		referenceList = (referenceList != null) ? referenceList : new ArrayList<String[]>();

		// 학습자료 내용
		int row = problem.getProblemReferences().size();
		String header[] = {"작성자", "학습자료 제목"};
		String contentlist[][] = new String[row][2];
		
		
		for (int i = 0; i < row; i++) {
	         contentlist[i][0] = referenceList.get(i)[0]; // 작성자
	         contentlist[i][1] = referenceList.get(i)[1]; // 제목
	    }
		
		// 학습자료 표
		DefaultTableModel dtm = new DefaultTableModel(contentlist, header) { //수정불가능하도록
			public boolean isCellEditable(int row, int  column) {
		        return false; 
		      }
		}; 
		 
		JTable referencetable = new JTable(dtm);
		referencetable.getColumnModel().getColumn(0).setPreferredWidth(120); //첫번째 열 크기 조정
		referencetable.getColumnModel().getColumn(1).setPreferredWidth(referencelistpanel.getWidth()-120); //두번째 열 크기 조정
		referencetable.setRowHeight(50); // 높이 변경  
		referencetable.setFont(new Font("Sunflower Medium",Font.PLAIN,15));
		// 테이블 헤더 설정
		JTableHeader referenceHeader = referencetable.getTableHeader();
		referenceHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		referenceHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		referenceHeader.setFont(new Font("Sunflower Medium",Font.BOLD,18));
		Dimension headerSize = referencetable.getTableHeader().getPreferredSize();
		headerSize.height = 40;
		referencetable.getTableHeader().setPreferredSize(headerSize);
		
		// 마우스 클릭 이벤트 처리를 위한 MouseAdapter 추가
		referencetable.addMouseListener(new MouseAdapter() {
			
			@Override
            public void mouseClicked(MouseEvent e) {
            	if(e.getClickCount() == 2) {
            		//더블클릭 처리
            		// 마우스 클릭된 행과 열 인덱스 가져오기
	                int row = referencetable.rowAtPoint(e.getPoint());
	                int col = referencetable.columnAtPoint(e.getPoint());
	
	                // 선택된 셀의 값 가져오기	                
	                String userName = (String) referencetable.getValueAt(row, 0);
	                
	                // 창 띄우기
	                new LearningReferenceViewFrame(problem, row, userName);
            	}
                
            }
        });
		
		JScrollPane referencelistscrollPane = new JScrollPane(referencetable); // 스크롤팬
		referencelistpanel.add(referencelistscrollPane);
		referencelistpanel.setOpaque(false);
		getContentPane().add(referencelistpanel);
		
	}
 
	public void addLearningReferenceButtonpanel(Problem problem, User user) { // 학습자료작성버튼
		// 패널 생성
		referencebuttonpanel = new JPanel();
		referencebuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		referencebuttonpanel.setLocation(850, 670); // 위치
		referencebuttonpanel.setSize(380, 70); // 크기
		// 학습자료 작성버튼 추가
		DesignedButton referenceAddbtn = new DesignedButton("작성하기", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		referenceAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		referenceAddbtn.setSize(150, 40);
		
		referenceAddbtn.addActionListener (new ActionListener() { // 익명클래스 학습자료작성버튼 리스너
			public void actionPerformed(ActionEvent e) {					
				new LearningReferenceAddFrame(problem, user);
				dispose();
			}
		});
		
		// 뒤로가기 버튼
		DesignedButton referencebackBtn = new DesignedButton("뒤로가기", 150, 40, COLOR.AQUA_ISLAND);
		referencebackBtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referencebackBtn.setSize(120, 40);
		
		referencebackBtn.addActionListener (new ActionListener() { //익명클래스 학습자료리스트 닫기버튼 리스너
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//프레임에 추가
		referencebuttonpanel.add(referenceAddbtn);
		referencebuttonpanel.add(referencebackBtn);
		referencebuttonpanel.setOpaque(false);
		getContentPane().add(referencebuttonpanel);
	}
}