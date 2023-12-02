package gui;

/*
 * 
 * 텍스트 필드에 제목 입력 후 엔터 또는 버튼 클릭시 입력값에 해당되는  
 * 
 * 
 * 
 */
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import problems.ProblemDBManager;
import problems.Problem;
import gui.MainFrame;
import gui.ProblemViewerFrame;
import problems.*;
import users.AccountManager;
import users.User;
import users.UserDBManager;
import java.net.*;
import fetcher.JsonFetcher;
import users.User;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.regex.PatternSyntaxException;

public class ProblemViewerFrame extends DesignedJFrame implements ItemListener {

    private JPanel panel; // 검색 관련 컴포넌트를 담을 패널
	private DesignedContentPane contentPane;
    private JLabel label; // "문제 검색" 레이블
    private JTextField textField; // 검색어 입력을 위한 텍스트 필드
    private JButton button; // 검색 버튼
    private ProblemTable table;
    private JTextField searchField;
    private JRadioButton number;
    private JRadioButton name;
    private JRadioButton rank;
    private JRadioButton sorted;
    private JRadioButton resorted;
    private JScrollPane scrollPane;
    private boolean sort;
    private boolean name1;
    private boolean id1;
    private boolean rank1;
    private boolean search1;
	private User user;// 현재 로그인된 유저
	private ProblemTable recommendedProblemTable;// 추천된 문제를 담는 테이블
	private List<Problem> ProblemList;
	private String text;


    public ProblemViewerFrame(User user) {
        super("문제 리스트"); // 프레임의 제목 설정
        
		contentPane = new DesignedContentPane();
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
        panel = new JPanel(new GridLayout(3, 2));
        panel.setForeground(new Color(130, 141, 226));
        panel.setBounds(205, 101, 301, 144);
        panel.setOpaque(false);
        
        
        //라디오 버튼 생성
        MakeRadioButton();

        sort = true;
        id1 = true;
        search1 = false;

		//문제 테이블 생성
        table = new ProblemTable(user);
        ProblemList = ProblemDBManager.findProblemToID(sort);
        table.updateProblemListToTable(ProblemList);
		scrollPane = new JScrollPane(table);
        scrollPane.setBounds(205, 359, 867, 394);
        contentPane.add(scrollPane);
        
        
	    // 셀 단위의 선택을 활성화
	    //table.setCellSelectionEnabled(false);
	    // JTable의 헤더를 통해 열의 크기를 조절하는 것을 비활성화
	    table.getTableHeader().setResizingAllowed(false);
	    // 열의 순서를 변경하는 것을 비활성화
	    table.getTableHeader().setReorderingAllowed(false);
	    // 기본 편집기를 설정하고, null로 설정하여 셀을 편집할 수 없게 함
	    table.setDefaultEditor(Object.class, null);
	    table.addMouseListener(new MyMouseListener());
        
        //문제 정렬 라벨 생성
        JLabel lblNewLabel = new JLabel("문제 정렬");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(273, 33, 117, 58);
        contentPane.add(lblNewLabel);
        
        
        ActionListener actionListener = new MyActionListener();
        //검색 버튼 생성
        button = new JButton("검색");
        button.setForeground(new Color(130, 141, 226));
        button.setBackground(new Color(148, 212, 214));
        button.setBounds(1022, 147, 70, 35);
        contentPane.add(button);

        //검색 텍스트 필드 생성
        searchField = new JTextField();
        searchField.setBounds(857, 148, 165, 34);
        contentPane.add(searchField);
        searchField.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("제목 검색");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(888, 56, 117, 58);
        contentPane.add(lblNewLabel_1);
        
        //
        button.addActionListener(actionListener);
        searchField.addActionListener(actionListener);
        
    	contentPane.applyFontAndBackgroundToAllComponents(); // 전체 폰트 적용 및 패널 배경색 투명하게 적용
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임이 닫힐 때 프로그램 종료
        setVisible(true); // 프레임 표시
    }
    
    public void MakeRadioButton() {
        //라디오버튼 생성

        number = new JRadioButton("번호");
        name = new JRadioButton("제목");
        rank = new JRadioButton("난이도");
        sorted = new JRadioButton("오름차순");
        resorted = new JRadioButton("내림차순");
        
        
        
        number.setOpaque(false);
        name.setOpaque(false);
        rank.setOpaque(false);
        sorted.setOpaque(false);
        resorted.setOpaque(false);
        
        number.setSelected(true);
        sorted.setSelected(true);
        
        //라디오버튼 리스너 등록
        number.addItemListener(this);
        name.addItemListener(this);
        rank.addItemListener(this);
        sorted.addItemListener(this);
        resorted.addItemListener(this);
        
        //라디오버튼 그룸 생성
        ButtonGroup aa = new ButtonGroup();
        ButtonGroup bb = new ButtonGroup();

        //라디오버튼 그룹에 추가
        aa.add(number);
        aa.add(name);
        aa.add(rank);
        bb.add(sorted);
        bb.add(resorted);

        //패널에 라디오버튼 추가
        panel.add(number);
        panel.add(sorted);
        panel.add(name);
        panel.add(resorted);
        panel.add(rank);
        
        //panel.applyFontAndBackgroundToAllComponents();
        
        contentPane.setLayout(null);
        contentPane.add(panel); // 패널을 프레임의 상단에 추가
    }

    public void itemStateChanged(ItemEvent e) { //정렬 라디오 버튼 이벤트 처리
        

        if (e.getStateChange() == ItemEvent.SELECTED) {

            if (e.getSource() == number) {
                name1 = false;
                id1 = true;
                rank1 = false;
            } else if (e.getSource() == name) {
                name1 = true;
                id1 = false;
                rank1 = false;
            } else if (e.getSource() == rank) {
                name1 = false;
                id1 = false;
                rank1 = true;
            }
            
            if (e.getSource() == sorted) {
                sort = true;
            }
            else if (e.getSource() == resorted) {
            	sort = false;
            }
            
        	if (search1 == false){
        		
                if (name1 == true) {
                    ProblemList = ProblemDBManager.findProblemToName(sort);
                    table.updateProblemListToTable(ProblemList);
                }
                else if (id1 == true) {
                	ProblemList = ProblemDBManager.findProblemToID(sort);
                	table.updateProblemListToTable(ProblemList);
                }
                else if (rank1 == true) {
                	ProblemList = ProblemDBManager.findProblemToRank(sort);
                	table.updateProblemListToTable(ProblemList);
                	
                }
        	}
        	
        	else {
        		if (sort == true){
                    if (name1 == true) {
                        ProblemList = ProblemDBManager.findProblemSearch(text,3);;
                        table.updateProblemListToTable(ProblemList);
                    }
                    else if (id1 == true) {
                    	ProblemList = ProblemDBManager.findProblemSearch(text,1);
                    	table.updateProblemListToTable(ProblemList);
                    }
                    else if (rank1 == true) {
                    	ProblemList = ProblemDBManager.findProblemSearch(text,5);
                    	table.updateProblemListToTable(ProblemList); 	
                    }	
        		}
        		else {
                    if (name1 == true) {
                        ProblemList = ProblemDBManager.findProblemSearch(text,4);
                        table.updateProblemListToTable(ProblemList);
                    }
                    else if (id1 == true) {
                    	ProblemList = ProblemDBManager.findProblemSearch(text,2);
                    	table.updateProblemListToTable(ProblemList);
                    }
                    else if (rank1 == true) {
                    	ProblemList = ProblemDBManager.findProblemSearch(text,6);
                    	table.updateProblemListToTable(ProblemList); 	
                    }	
        		}
        		}
        	}
        }
    
    private class MyMouseListener extends MouseAdapter { //테이블 클릭 이벤트 제어
    	public void mouseClicked(MouseEvent e) {

    	if (e.getClickCount() == 2) { //더블클릭시 ProblemInfoFrame 생성
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object selectedID = table.getValueAt(selectedRow, 0);
            	System.out.println(selectedID);
            	new ProblemInfoFrame(ProblemDBManager.findProblem(Integer.parseInt(String.valueOf(selectedID))), user);
            	} 
            

    		}
    	}
    }
    
    private class MyActionListener implements ActionListener { // 텍스트 필드, 검색 버튼 이벤트 제어
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            JTextField source = null;

            if (e.getSource() == button) {
                // 검색 버튼 클릭 시
                source = searchField;
            } else if (e.getSource() == searchField) {
                // 엔터키 눌렀을 때
                source = (JTextField) e.getSource();
            }


            text = source.getText();
            
            contentPane.remove(panel);
            panel.remove(number);
            panel.remove(sorted);
            panel.remove(name);
            panel.remove(resorted);
            panel.remove(rank);

            
            if (text.isEmpty()) {
                ProblemList = ProblemDBManager.findProblemToID(sort);
                table.updateProblemListToTable(ProblemList);
            } else {
            	ProblemList = ProblemDBManager.findProblemSearch(text,1);
            	table.updateProblemListToTable(ProblemList);
            	search1 = true;
            }
            
            
            
            MakeRadioButton();
            contentPane.applyFontAndBackgroundToAllComponents();
            contentPane.revalidate();
            contentPane.repaint();

        }
    }
};





