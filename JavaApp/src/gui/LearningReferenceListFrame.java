package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import problems.LearningReference;
import problems.Problem;
import users.User;

public class LearningReferenceListFrame extends DesignedJFrame {
	
	private JPanel infopanel, referencelistpanel, referencebuttonpanel;
	private JLabel problemName, frameTitle;
	private ArrayList<LearningReference> referenceList;
	private JTable referencetable;
	private JScrollPane referencelistscrollPane;
	private DesignedButton referenceAddbtn, referenceBackbtn;
	
	public LearningReferenceListFrame(Problem problem, User user) { //생성자
		// 프레임 설정
		setTitle("LearningReferenceViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addInfoPanel(problem); // 기본정보패널 추가
		addLearningReferenceListPanel(problem); // 학습자료 리스트 패널 추가
		addLearningReferenceButtonPanel(problem, user); // 학습자료 버튼 패널 추가
		
		setVisible(true);
	}
	
	public void addInfoPanel(Problem problem) { // 기본정보 
		// 기본정보 패널 설정
		infopanel = new JPanel(); 
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		// 기본정보
		problemName = new JLabel(problem.getProblemName()); // 문제 제목
		frameTitle = new JLabel("학습 자료 리스트");
		// 폰트 설정
		problemName.setFont(new Font("Sunflower Medium", Font.BOLD,35));
		frameTitle.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		// 프레임에 추가
		infopanel.add(problemName);
		infopanel.add(frameTitle);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);

	} 
	
	
	public void addLearningReferenceListPanel(Problem problem) { // 학습자료 리스트
		// 학습자료 리스트 패널
		referencelistpanel = new JPanel();
		referencelistpanel.setBorder(new LineBorder(Color.black, 2));
		referencelistpanel.setLayout(new GridLayout(1,1)); 
		referencelistpanel.setLocation(120,150); // 위치
		referencelistpanel.setSize(getDefalutWindowWidth() - 200, 500); // 크기
		
		// null 값일 경우 리스트 초기화
		referenceList = problem.getProblemReferences();
		referenceList = (referenceList != null) ? referenceList : new ArrayList<LearningReference>();

		// 학습자료 내용
		int row = problem.getProblemReferences().size();
		String header[] = {"작성자", "학습자료 제목"};
		String contentlist[][] = new String[row][2];
		
		for (int i = 0; i < row; i++) {
	         contentlist[i][0] = referenceList.get(i).getWriter().getUsername(); // 작성자
	         contentlist[i][1] = referenceList.get(i).getTitle(); // 제목
	    }
		
		// 학습자료 테이블 표 생성
		DefaultTableModel dtm = new DefaultTableModel(contentlist, header) { 
			public boolean isCellEditable(int row, int  column) {
		        return false; // 테이블 내용 수정불가능하도록
		      }
		}; 
		
		// 학습자료 테이블 설정
		referencetable = new JTable(dtm);
		referencetable.getColumnModel().getColumn(0).setPreferredWidth(120); //첫번째 열 크기 조정
		referencetable.getColumnModel().getColumn(1).setPreferredWidth(referencelistpanel.getWidth()-120); //두번째 열 크기 조정
		referencetable.setRowHeight(50); // 높이 설정
		referencetable.setFont(new Font("Sunflower Medium",Font.PLAIN,15));
		// 테이블 헤더 설정
		JTableHeader referenceHeader = referencetable.getTableHeader();
		referenceHeader.setBackground(COLOR.AQUA_ISLAND.getColor()); // 배경색 설정
		referenceHeader.setFont(new Font("Sunflower Medium",Font.BOLD,18)); // 폰트설정
		Dimension headerSize = referencetable.getTableHeader().getPreferredSize(); 
		headerSize.height = 40; // 헤더 높이 설정
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
		
		// 프레임에 추가
		referencelistscrollPane = new JScrollPane(referencetable); // 스크롤팬
		referencelistpanel.add(referencelistscrollPane);
		referencelistpanel.setOpaque(false);
		getContentPane().add(referencelistpanel);
		
	}
 
	public void addLearningReferenceButtonPanel(Problem problem, User user) { // 학습자료작성버튼 패널
		// 버튼 패널 생성
		referencebuttonpanel = new JPanel();
		referencebuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		referencebuttonpanel.setLocation(850, 670); // 위치
		referencebuttonpanel.setSize(380, 70); // 크기
		
		// 학습자료 작성버튼
		referenceAddbtn = new DesignedButton("작성하기", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		referenceAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		referenceAddbtn.setSize(150, 40);
		
		// 익명클래스 학습자료작성버튼 리스너
		referenceAddbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {					
				new LearningReferenceAddFrame(problem, user);
				dispose();
			}
		});
		
		// 뒤로가기 버튼
		referenceBackbtn = new DesignedButton("뒤로가기", 150, 40, COLOR.AQUA_ISLAND);
		referenceBackbtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceBackbtn.setSize(120, 40);
		
		//익명클래스 학습자료리스트 뒤로가기버튼 리스너
		referenceBackbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//프레임에 추가
		referencebuttonpanel.add(referenceAddbtn); // 추가 버튼
		referencebuttonpanel.add(referenceBackbtn); // 뒤로가기 버튼
		referencebuttonpanel.setOpaque(false);
		getContentPane().add(referencebuttonpanel);
	}
}