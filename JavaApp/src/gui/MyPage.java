package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Dimension;
import java.util.HashSet;
import javax.swing.JButton;

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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import file.FileManager;
import problems.Problem;
import users.User;
import users.UserDBManager;

public class MyPage extends DesignedJFrame {

    private static final long serialVersionUID = 1L;
    private DesignedContentPane contentPane;
    private JTextField searchField;
    private MainFrame mainFrame;
    private User user = new User();
    

    public MyPage(MainFrame mainFrame, User user, Boolean savevisible) {
        super();
        this.user = user;
        this.mainFrame = mainFrame;
        
        // ����Ʈ �� ����
        contentPane = new DesignedContentPane();
        contentPane.setBackground(UIManager.getColor("Button.darkShadow"));
        contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // "���� ȭ��" ��ư ����
        JButton btnNewButton = new DesignedButton("���� ȭ��", COLOR.AQUA_ISLAND);
        //btnNewButton.setBackground(UIManager.getColor("Button.highlight"));
        btnNewButton.addActionListener(new MyPageButtonActionListener(mainFrame));
        btnNewButton.setBounds(161, 35, 143, 43);
        contentPane.add(btnNewButton);
        
        // �˻� �ʵ� ����
        searchField = new JTextField();
        searchField.setBounds(879, 35, 251, 43);
        contentPane.add(searchField);
        searchField.setColumns(10);
        JButton searchButton = new DesignedButton("�˻�", COLOR.AQUA_ISLAND);
        searchButton.setBounds(1020, 92, 110, 32);
        contentPane.add(searchButton);
        SearchButtonActionListener searchListener = new SearchButtonActionListener(searchField, user);
        searchButton.addActionListener(searchListener);
        
        // ���̺� ����
        initializeTable();
        
        // ����� ���� �г� ����
        initializeUserInfoPanel();
        
        // ����� �Ұ� ����
        initializeUserIntroduction(savevisible);
        
        // ���� �⼮�� �� ����
        initializeConsecutiveActivityDateLabel();
        // ����ڰ� �ذ��� ���� ��
        UserSolvedPromlemCnt();
        
        
    }
    private void UserSolvedPromlemCnt() {
        int SolvedCnt = user.getSolvedProblemSet().size();

        
		try { 
            Font font = FileManager.createFontFromFile("contentFont");
            JLabel UserSolvedCnt = new JLabel(String.valueOf(SolvedCnt));
            UserSolvedCnt.setBounds(1034, 421, 192, 160);
            UserSolvedCnt.setFont(font.deriveFont(55f));
            UserSolvedCnt.setHorizontalAlignment(SwingConstants.CENTER);
    		contentPane.add(UserSolvedCnt);
    		
    		JLabel UserSolvedCntHeader = new JLabel("�ذ��� ���� ��");
    		UserSolvedCntHeader.setHorizontalAlignment(SwingConstants.CENTER);
    		UserSolvedCntHeader.setFont(font.deriveFont(17f));
    		UserSolvedCntHeader.setBounds(1034, 384, 192, 43);
    		contentPane.add(UserSolvedCntHeader);
      
        } catch (IOException e) { 
            System.out.println(e.getMessage());             
        }
    }
    private void initializeTable() {
        String[] columnNames = {"���� ��ȣ", "���� ����", "���̵�"}; 

        HashSet<Problem> solvedProblemSet = user.getSolvedProblemSet(); 
        Object[][] data = new Object[solvedProblemSet.size()][3]; 

        int i = 0;
        for (Problem solvedProblem : solvedProblemSet) {
            data[i][0] = solvedProblem.getProblemID(); 
            data[i][1] = solvedProblem.getProblemName();
            data[i][2] = solvedProblem.getProblemRank(); 
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames); 
        JTable table = new JTable(model);
        table.setBounds(147, 38, 455, 196);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(40);

        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.getTableHeader().setBackground(Color.CYAN);

        try { 
            Font font = FileManager.createFontFromFile("contentFont");
            table.getTableHeader().setFont(font.deriveFont(20f));
            table.setFont(font.deriveFont(17f));
        } catch (IOException e) { 
            System.out.println(e.getMessage());             
        }

        contentPane.add(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(161, 383, 861, 450);
        contentPane.add(scrollPane);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int j = 0; j < table.getColumnCount(); j++) {
            table.getColumnModel().getColumn(j).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(j).setPreferredWidth(260);
        }
        contentPane.applyFontAndBackgroundToAllComponents();
    }

    private void initializeUserInfoPanel() {	//����� �̸�, ��ũ, ��ũ ����Ʈ
        JPanel panel = new JPanel();
        panel.setBounds(161, 200, 380, 46);
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBackground(new Color(183, 173, 246));
        contentPane.add(panel);
        panel.setLayout(null);
       
        try { 
            Font font = FileManager.createFontFromFile("contentFont").deriveFont(20f);

            JLabel UserName = new JLabel(user.getUsername());
            UserName.setHorizontalAlignment(SwingConstants.CENTER);
            UserName.setFont(font);
            UserName.setBounds(12, 10, 160, 30);
            panel.add(UserName);
            
            JLabel UserRank = new JLabel(user.getRank().toString());
            UserRank.setFont(font);
            UserRank.setHorizontalAlignment(SwingConstants.CENTER);
            UserRank.setBounds(183, 10, 90, 30);
            panel.add(UserRank);
            
            JLabel UserRankPoint = new JLabel(String.valueOf(user.getRankPoint()));
            UserRankPoint.setHorizontalAlignment(SwingConstants.CENTER);
            UserRankPoint.setBounds(285, 10, 90, 30);
            UserRankPoint.setFont(font);
            panel.add(UserRankPoint);
        } catch (IOException e) { 
            System.out.println(e.getMessage());             
        }
    }

    private void initializeUserIntroduction(boolean savevisible) {	//����ڼҰ�
        JTextArea textArea = new JTextArea(user.getSelfIntroduction());
        textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
        textArea.setBounds(161, 267, 969, 93);
        contentPane.add(textArea);

        // ����ڰ� �˻��� �ϸ� ��ư�� �Ⱥ��̰���
        if (savevisible) {
            JButton Save = new DesignedButton("����");
            Save.setBounds(1020, 220, 110, 32);
            contentPane.add(Save);
            Save.addActionListener(new SaveButtonActionListener(textArea));
        }
    }

    private void initializeConsecutiveActivityDateLabel() {	//�⼮��
        try { 
            Font font = FileManager.createFontFromFile("contentFont").deriveFont(20f);
            JLabel lblNewLabel = new JLabel(String.valueOf(user.getConsecutiveActivityDate()) + "��");
            lblNewLabel.setFont(font.deriveFont(55f));
            lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lblNewLabel.setBounds(1034, 633, 192, 200);
            contentPane.add(lblNewLabel);
        } catch (IOException e) { 
            System.out.println(e.getMessage());             
        }
    }

    class SaveButtonActionListener implements ActionListener {
        private JTextArea textArea;

        public SaveButtonActionListener(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String selfIntroduction = textArea.getText();
            user.setSelfIntroduction(selfIntroduction);
        }
    }

    class MyPageButtonActionListener implements ActionListener {
        private MainFrame mainFrame;

        public MyPageButtonActionListener(MainFrame mainFrame) {
            this.mainFrame = mainFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            if (mainFrame != null) { 
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
            String searchName = searchField.getText();

            try {
                User searchedUser = UserDBManager.findUserByUsername(searchName);
                MyPage newPage = new MyPage(mainFrame, searchedUser, false);
                newPage.setVisible(true);

            } catch (NullPointerException ex) {
            	Dialog.showAlertDialog("�˻� ����", "��ϵ��� �ʴ� ������Դϴ�.");
                ex.printStackTrace();
            }
        }
    }
}