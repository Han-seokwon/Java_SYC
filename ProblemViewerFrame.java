package gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
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

public class ProblemViewerFrame extends JFrame implements ItemListener {

    private JPanel panel; // 검색 관련 컴포넌트를 담을 패널
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

        panel = new JPanel(new GridLayout(3, 2));
        panel.setBounds(50, 51, 228, 95);
        
        //라디오버튼 생성
        number = new JRadioButton("번호");
        name = new JRadioButton("제목");
        rank = new JRadioButton("난이도");
        sorted = new JRadioButton("오름차순");
        resorted = new JRadioButton("내림차순");
        
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
        
        getContentPane().setLayout(null);
        getContentPane().add(panel); // 패널을 프레임의 상단에 추가

        sort = true;
        id1 = true;
        search1 = false;

		//문제 테이블 생성
        table = new ProblemTable(user);
        ProblemList = ProblemDBManager.findProblemToID(sort);
        table.updateProblemListToTable(ProblemList);
		scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 169, 786, 350);
        getContentPane().add(scrollPane);
        
        
        
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
        lblNewLabel.setBounds(114, 10, 68, 43);
        getContentPane().add(lblNewLabel);
        
        
        ActionListener actionListener = new MyActionListener();
        //검색 버튼 생성
        JButton btnNewButton = new JButton("");
        btnNewButton.setBounds(654, 84, 50, 35);
        getContentPane().add(btnNewButton);

        //검색 텍스트 필드 생성
        searchField = new JTextField();
        searchField.setBounds(491, 85, 165, 34);
        getContentPane().add(searchField);
        searchField.setColumns(10);
        
        //
        btnNewButton.addActionListener(actionListener);
        searchField.addActionListener(actionListener);
        
        setSize(800, 600); // 프레임 크기 설정
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임이 닫힐 때 프로그램 종료
        setVisible(true); // 프레임 표시
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
            	new ProblemInfoFrame(ProblemDBManager.findProblem(Integer.parseInt(String.valueOf(selectedID))));
            	} 
            

    		}
    	}
    }
    
    private class MyActionListener implements ActionListener { // 텍스트 필드, 검색 버튼 이벤트 제어
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField source = (JTextField) e.getSource();
            text = source.getText();
            
            if (text.isEmpty()) {
                ProblemList = ProblemDBManager.findProblemToID(sort);
                table.updateProblemListToTable(ProblemList);
            } else {
            	ProblemList = ProblemDBManager.findProblemSearch(text,1);
            	table.updateProblemListToTable(ProblemList);
            	search1 = true;
            }

        }
    }
    
};





