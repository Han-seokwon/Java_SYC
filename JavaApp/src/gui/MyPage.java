package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import problems.Problem;
import users.User;
import users.UserDBManager;

//사용자 정보 불러오기만 하면 끝
public class MyPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField searchField;
	private MainFrame mainFrame;
	private User user = new User();
	private JTable table;
	
	// 프레임 생성자
	public MyPage(MainFrame mainFrame, User user) {
		this.user = user;
		this.mainFrame = mainFrame;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 501);
		
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.darkShadow"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// "메인 화면" 버튼
		JButton btnNewButton = new JButton("메인 화면");
	    btnNewButton.setBackground(UIManager.getColor("Button.highlight"));
	    btnNewButton.addActionListener(new MyPageButtonActionListener(mainFrame));
		btnNewButton.setBounds(12, 24, 143, 43);
		contentPane.add(btnNewButton);
		
		//검색 필(검색하면 현재 user데이터에서 다른 유저 데이터를 가져옴
		searchField = new JTextField();
		searchField.setBounds(479, 24, 192, 43);
		contentPane.add(searchField);
		searchField.setColumns(10);
		JButton searchButton = new JButton("검색");
		searchButton.setBounds(574, 76, 97, 23);
		contentPane.add(searchButton);
		SearchButtonActionListener searchListener = new SearchButtonActionListener(searchField, user);
		searchButton.addActionListener(searchListener);
		
		
		// 기존 코드 유지하면서 테이블 모델에 데이터 추가
		// 출력 결과를 보고 몇 개의 열을 쪼갤가 생각중 입니다.
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("문제 ID");

		ArrayList<Problem> solvedProblems = new ArrayList<>(user.getSolvedProblemListSorted());

		StringBuilder problemIDs = new StringBuilder();
		for (Problem problem : solvedProblems) {
			problemIDs.append(problem.getProblemID()).append(" ");
		}

		Object[] rowData = {
				problemIDs.toString().trim()
		};
		/* Ex. 리스트에  [12564, 15567, 153556, 12345, 21513, 54546]되 있으면
		 * toString()에서 " [12564, 15567, 153556, 12345, 21513, 54546]" 이런식으로 변환이되고
		 * trim()을 사용하여 숫자부분만 분리
		 */
		model.addRow(rowData);

		// 테이블 초기화 이후에 setModel 호출
		table = new JTable(model);

		// table.setModel(model); // 이 부분은 더 이상 필요하지 않습니다
		table.setBounds(12, 255, 455, 197);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setTableHeader(null);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		contentPane.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 255, 455, 197);
		contentPane.add(scrollPane);

		// 내용 가운데 정렬
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		// 사용자 이름과 랭크
		JPanel panel = new JPanel();
		panel.setBounds(12, 109, 380, 43);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel UserName = new JLabel(user.getUsername());
		UserName.setFont(new Font("굴림", Font.PLAIN, 20));
		UserName.setBounds(12, 10, 159, 23);
		panel.add(UserName);
		
		JLabel UserRank = new JLabel(user.getRank().toString());
		UserRank.setFont(new Font("굴림", Font.PLAIN, 20));
		UserRank.setBounds(183, 10, 90, 23);
		panel.add(UserRank);
		
		JLabel UserRankPoint = new JLabel(String.valueOf(user.getRankPoint()));
		UserRankPoint.setHorizontalAlignment(SwingConstants.CENTER);
		UserRankPoint.setBounds(285, 10, 90, 23);
		UserRankPoint.setFont(new Font("굴림", Font.PLAIN, 20));
		contentPane.add(UserRankPoint);
		panel.add(UserRankPoint);
		
		// 사용자 소개(어디에 저장할지는 아직 미정)
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 157, 659, 93);
		contentPane.add(textArea);
		JButton Save = new JButton("저장");
		Save.setBounds(574, 129, 97, 23);
		contentPane.add(Save);
		
		Save.addActionListener(new SaveButtonActionListener(textArea));
		
		//연속 출석일 수
		JLabel lblNewLabel = new JLabel(String.valueOf(user.getConsecutiveActivityDate()) + "일");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 55));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(479, 256, 192, 196);
		contentPane.add(lblNewLabel);
		
		setVisible(true);
		
	}
	 class SaveButtonActionListener implements ActionListener {
	        private JTextArea textArea;

	        public SaveButtonActionListener(JTextArea textArea) {
	            this.textArea = textArea;
	        }

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String selfIntroduction = textArea.getText();
//	            setSelfIntroduction(selfIntroduction);
	        }
	    }

	    // "메인 화면" 버튼의 이벤트 리스너
	 class MyPageButtonActionListener implements ActionListener {
	        private MainFrame mainFrame;

	        public MyPageButtonActionListener(MainFrame mainFrame) {
	            this.mainFrame = mainFrame;
	        }

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            dispose(); // 현재 MyPage 프레임 닫기
	            if (mainFrame != null) { // mainFrame이 null이 아닐 경우
	                mainFrame.setVisible(true);
	            }
	        }
	}
	
	 public class SearchButtonActionListener implements ActionListener {
		    private JTextField searchField;
		    private User user;

		    public SearchButtonActionListener(JTextField searchField, User user) {
		        this.searchField = searchField;
		        this.user = user;
		    }

		    @Override
		    public void actionPerformed(ActionEvent event) {
		        // 검색 필드에서 검색어를 가져옵니다.
		        String searchName = searchField.getText();

		        try {
		            // 검색어를 사용하여 사용자를 데이터베이스에서 찾습니다.
		            User searchedUser = UserDBManager.findUserByEmail(searchName);

		            // 검색된 사용자 정보를 이용하여 화면을 업데이트하는 코드를 추가합니다.
		            // 새로운 MyPage를 만들어서 해당 사용자의 정보로 초기화하고 보여줍니다.
		            MyPage newPage = new MyPage(mainFrame, searchedUser);
		            newPage.setVisible(true);

		        } catch (NullPointerException ex) {
		            // 사용자를 찾지 못한 경우 또는 예외가 발생한 경우
		            System.err.println("경고: 사용자를 찾을 수 없습니다.");
		            ex.printStackTrace(); // 디버그를 위해 스택 트레이스를 출력합니다.
		        }
		    }
		}
	
	 
}