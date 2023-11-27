package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import users.User;

//평균값을 어떤식으로 저장하고 불러올지 생각하지 못 함
public class CodeEficiency extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField RunTime_textField;
	private JTextField Memory_textField;
	private JLabel ResultGuide;
	private int enteredRunTime;
    private int enteredMemory;
    private int averageRunTime = 168;
    private int averageMemory = 2048;
	
	public CodeEficiency(/*MainFrame mainFrame, User user*/) {
		//this.mainFrame = mainFrame;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel ProblemName = new JLabel("문제 이름");
		ProblemName.setFont(new Font("굴림", Font.PLAIN, 30));
		ProblemName.setHorizontalAlignment(SwingConstants.CENTER);
		ProblemName.setBounds(112, 10, 620, 100);
		ProblemName.setBorder(new LineBorder(UIManager.getColor("Black") ,2));
		contentPane.add(ProblemName);
		
		JLabel Ave_Runtime = new JLabel("평균 런타임 : " + "165ms");
		Ave_Runtime.setHorizontalAlignment(SwingConstants.CENTER);
		Ave_Runtime.setFont(new Font("굴림", Font.PLAIN, 30));
		Ave_Runtime.setBorder(new LineBorder(UIManager.getColor("Black") ,2));
		Ave_Runtime.setBounds(112, 116, 305, 60);
		contentPane.add(Ave_Runtime);
		
		JLabel Ave_Memory = new JLabel("평균 메모리 : " + "2048kb");
		Ave_Memory.setHorizontalAlignment(SwingConstants.CENTER);
		Ave_Memory.setFont(new Font("굴림", Font.PLAIN, 30));
		Ave_Memory.setBorder(new LineBorder(UIManager.getColor("Black") ,2));
		Ave_Memory.setBounds(429, 116, 305, 60);
		contentPane.add(Ave_Memory);
		
		////
		JPanel panel = new JPanel();
		panel.setBounds(112, 196, 620, 186);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel Guide = new JLabel("해결한 코드의 런타임, 메모리를 입력해주세요");
		Guide.setBounds(12, 10, 596, 39);
		Guide.setHorizontalAlignment(SwingConstants.CENTER);
		Guide.setFont(new Font("굴림", Font.PLAIN, 25));
		Guide.setBorder(new LineBorder(UIManager.getColor("Black") ,2));
		panel.add(Guide);
		
		JLabel RunTime = new JLabel("런타임");
		RunTime.setHorizontalAlignment(SwingConstants.CENTER);
		RunTime.setFont(new Font("굴림", Font.PLAIN, 30));
		RunTime.setBounds(127, 59, 146, 39);
		panel.add(RunTime);
		
		JLabel Memory = new JLabel("메모리");
		Memory.setHorizontalAlignment(SwingConstants.CENTER);
		Memory.setFont(new Font("굴림", Font.PLAIN, 30));
		Memory.setBounds(127, 108, 146, 39);
		panel.add(Memory);
		////
		
		// 런타임 입력란
        RunTime_textField = new JTextField();
        RunTime_textField.setBounds(270, 59, 213, 39);
        panel.add(RunTime_textField);
        RunTime_textField.setColumns(10);

        // 메모리 입력란
        Memory_textField = new JTextField();
        Memory_textField.setColumns(10);
        Memory_textField.setBounds(270, 108, 213, 39);
        panel.add(Memory_textField);

        // 비교하는 버튼
        JButton CmpButton = new JButton("비교하기");
        CmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 사용자가 입력한 런타임과 메모리 값을 가져와서 변수에 저장
                try {
                    enteredRunTime = Integer.parseInt(RunTime_textField.getText());
                    enteredMemory = Integer.parseInt(Memory_textField.getText());

                    // 평균과 비교하여 결과 계산
                    double runtimePercentage = ((double) enteredRunTime / averageRunTime) * 100;
                    double memoryPercentage = ((double) enteredMemory / averageMemory) * 100;

                    String runtimeComparison = (enteredRunTime == averageRunTime) ? "같습" : (enteredRunTime < averageRunTime) ? "빠르" : "느립니다";

                    String memoryComparison = (enteredMemory == averageMemory) ? "같습" : (enteredMemory < averageMemory) ? "적" : "많습니다";

                    String resultMessage = String.format("<html>런타임이 평균보다 %.2f%% %s.<br/>메모리 사용량이 평균보다 %.2f%% %s.</html>", runtimePercentage, runtimeComparison, memoryPercentage, memoryComparison);

                    ResultGuide.setText(resultMessage);

                } catch (NumberFormatException ex) {
                    // 사용자가 숫자가 아닌 값을 입력한 경우에 대한 예외 처리
                    ResultGuide.setText("숫자를 입력해주세요.");
                }
            }
        });
        CmpButton.setBounds(511, 153, 97, 23);
        panel.add(CmpButton);
		
		
		//비교 결과 출력 안내
		JLabel ResultGuide = new JLabel();
		ResultGuide.setHorizontalAlignment(SwingConstants.CENTER);
		ResultGuide.setFont(new Font("굴림", Font.PLAIN, 20));
		ResultGuide.setBorder(new LineBorder(UIManager.getColor("Black") ,2));
		ResultGuide.setBounds(112, 392, 620, 100);
		contentPane.add(ResultGuide);
		
		JButton btnNewButton = new JButton("메인 화면");
//	    btnNewButton.addActionListener(new ButtonActionListener(mainFrame));
		btnNewButton.setBounds(735, 502, 97, 23);
		contentPane.add(btnNewButton);
	}

}
