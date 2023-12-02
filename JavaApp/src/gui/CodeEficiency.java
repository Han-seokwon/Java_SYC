package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import file.FileManager;
import problems.Problem;

public class CodeEficiency extends DesignedJFrame {

    private static final long serialVersionUID = 1L;
    private DesignedContentPane contentPane;
    private JTextField RunTime_textField;
    private JTextField Memory_textField;
    private JLabel ResultGuide;
    
    private JLabel Ave_Runtime;
    private JLabel Ave_Memory;

    private int enteredRunTime;
    private int enteredMemory;
    private int averageRunTime = 168;
    private int averageMemory = 2048;
    private Problem problem = new Problem();

    public CodeEficiency(Problem problem) {
        super();
        this.problem = problem;
        setBounds(100, 100, 1300, 900);
        contentPane = new DesignedContentPane();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 문제 이름 패널
        ProblemNamePanel();

        // 코드 메인 패널
        CodeMainPanel();

        // 결과 패널
        ResultPanel();
        
        contentPane.applyFontAndBackgroundToAllComponents();
    }

    // 문제 이름 패널
    private void ProblemNamePanel() {
        try
        {
        	Font font = FileManager.createFontFromFile("contentFont");
        	JLabel ProblemName = new JLabel(problem.getProblemName());
            ProblemName.setFont(font.deriveFont(30f));
            ProblemName.setHorizontalAlignment(SwingConstants.CENTER);
            ProblemName.setBounds(251, 59, 821, 100);
            ProblemName.setBorder(new LineBorder(UIManager.getColor("Black"), 2));
            contentPane.add(ProblemName);
        }
        catch (IOException e) { 
            System.out.println(e.getMessage());             
        }
    }

    // 코드 메인 패널
    private void CodeMainPanel() {
        JPanel Mainpanel = new JPanel();
        Mainpanel.setBackground(new Color(255, 255, 255));
        Mainpanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        Mainpanel.setBounds(251, 156, 821, 467);
        contentPane.add(Mainpanel);
        Mainpanel.setLayout(null);

        // 안내 문구
        try
        {
        	Font font = FileManager.createFontFromFile("contentFont");
        	JLabel Guide = new JLabel("해결한 코드의 런타임, 메모리를 입력해주세요");
            Guide.setBounds(96, 21, 622, 39);
            Guide.setHorizontalAlignment(SwingConstants.CENTER);
            Guide.setFont(font.deriveFont(25f));
            //Guide.setBorder(new LineBorder(UIManager.getColor("Black"), 2));
            Mainpanel.add(Guide);
         // 런타임 입력 레이블
            JLabel RunTime = new JLabel("런타임");
            RunTime.setHorizontalAlignment(SwingConstants.CENTER);
            RunTime.setFont(font.deriveFont(30f));
            RunTime.setBounds(181, 208, 146, 39);
            Mainpanel.add(RunTime);

            // 메모리 입력 레이블
            JLabel Memory = new JLabel("메모리");
            Memory.setHorizontalAlignment(SwingConstants.CENTER);
            Memory.setFont(font.deriveFont(30f));
            Memory.setBounds(181, 278, 146, 39);
            Mainpanel.add(Memory);
        }
        catch (IOException e) { 
            System.out.println(e.getMessage());             
        }

        

        // 런타임 입력 필드
        RunTime_textField = new JTextField();
        RunTime_textField.setBounds(335, 208, 213, 39);
        Mainpanel.add(RunTime_textField);
        RunTime_textField.setColumns(10);

        // 메모리 입력 필드
        Memory_textField = new JTextField();
        Memory_textField.setColumns(10);
        Memory_textField.setBounds(335, 278, 213, 39);
        Mainpanel.add(Memory_textField);

        // 비교하기 버튼
        JButton CmpButton = new DesignedButton("비교하기");
        CmpButton.addActionListener(new CompareButtonActionListener());
        CmpButton.setBounds(640, 410, 169, 47);
        Mainpanel.add(CmpButton);

        // 평균 런타임 레이블
        Ave_Runtime = new JLabel("런타임 평균 : " + String.valueOf(Math.round(problem.getProblemAvgRunTime()))+ "ms");
        Ave_Runtime.setHorizontalAlignment(SwingConstants.CENTER);
        Ave_Runtime.setBorder(new LineBorder(UIManager.getColor("Black"), 2));
        Ave_Runtime.setBounds(96, 70, 305, 60);
        Mainpanel.add(Ave_Runtime);

        // 평균 메모리 레이블
        Ave_Memory = new JLabel("메모리 평균 : " + String.valueOf(Math.round(problem.getProblemAvgMemory())) + "KB");
        Ave_Memory.setHorizontalAlignment(SwingConstants.CENTER);
        Ave_Memory.setBorder(new LineBorder(UIManager.getColor("Black"), 2));
        Ave_Memory.setBounds(413, 70, 305, 60);
        Mainpanel.add(Ave_Memory);

        // 메인화면 버튼
        JButton MainMenuButton = new DesignedButton("메인화면", COLOR.AQUA_ISLAND);
        MainMenuButton.addActionListener(new MainMenuButtonActionListener());
        MainMenuButton.setBounds(12, 410, 169, 47);
        Mainpanel.add(MainMenuButton);
        
    }

    // 결과 패널
    private void ResultPanel() {
        ResultGuide = new JLabel();
        ResultGuide.setHorizontalAlignment(SwingConstants.CENTER);
        ResultGuide.setFont(new Font("굴림", Font.PLAIN, 20));
        ResultGuide.setBorder(new LineBorder(UIManager.getColor("Black"), 2));
        ResultGuide.setBackground(Color.WHITE);
        ResultGuide.setBounds(251, 619, 821, 199);
        contentPane.add(ResultGuide);
    }

    // 비교하기 버튼 액션 리스너
    class CompareButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int runTime = Integer.parseInt(RunTime_textField.getText());
                int memory = Integer.parseInt(Memory_textField.getText());
                
                //	입력된 값 문제 데이터에 추가
                problem.addProblemefficiency(runTime, memory); 
                
                double runTimeTopPercentage = getRunTimePercentile(runTime);
                double memoryTopPercentage = getMemoryPercentile(memory);

                System.out.println("runTimeTopPercentage: " + runTimeTopPercentage);
                System.out.println("memoryTopPercentage: " + memoryTopPercentage);
                String resultMessage = String.format(
                    "런타임 상위 %.2f%%, 메모리 상위 %.2f%%",
                    runTimeTopPercentage, memoryTopPercentage
                );
                ResultGuide.setText(resultMessage);
                
            } catch (NumberFormatException ex) {
                ResultGuide.setText("숫자를 입력해주세요.");
            }
        }
    }

    // 메인화면 버튼 액션 리스너
    class MainMenuButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
    
 // 런타임 백분위 및 상위 퍼센트 계산
    public double getRunTimePercentile(int myRunTime){
        double meanRunTime = problem.getProblemAvgRunTime();
        Ave_Runtime.setText("런타임 평균 : " + String.valueOf(Math.round(meanRunTime))+ "ms"); // 평균값 최신화
        
        double stdDevRunTime = problem.getProblemStdDevRunTime();
        if(stdDevRunTime == 0) { // 데이터값이 모두 동일한 경우
        	return 0.0; // 백분위 0% -> 상위 100% 
        }
        double zScoreRunTime = (myRunTime - meanRunTime) / stdDevRunTime;
        
        System.out.println("meanRunTime : " + meanRunTime);
        System.out.println("stdDevRunTime : " + stdDevRunTime);
        System.out.println("zScoreRunTime : " + zScoreRunTime);        
        
        return calculatePercentileFromZScore(zScoreRunTime);
    }


    // 메모리 사용량 백분위 및 상위 퍼센트 계산
    public double getMemoryPercentile(int myMemory){
        double meanMemory = problem.getProblemAvgMemory();
        Ave_Memory.setText("메모리 평균 : " + String.valueOf(Math.round(meanMemory))+ "KB"); // 평균값 최신화
        
        double stdDevMemory = problem.getProblemStdDevMemory();
        if(stdDevMemory == 0) { // 데이터값이 모두 동일한 경우
        	return 0.0; // 백분위 0% -> 상위 100% 
        }
        
        double zScoreMemory = (myMemory - meanMemory) / stdDevMemory;
        return calculatePercentileFromZScore(zScoreMemory);
    }

   
    
    // Z-점수를 이용하여 백분위 계산
    private static double calculatePercentileFromZScore(double zScore) {
        // 표준 정규 분포의 누적 분포 함수 (CDF)를 사용하여 백분위 계산
        return (1 - 0.5 * (1 + erf(zScore / Math.sqrt(2)))) * 100;
    }

    // 오차 함수 (Error Function)를 계산하는 함수
    private static double erf(double x) {
        // (erf)의 근사값을 사용
        double t = 1.0 / (1.0 + 0.5 * Math.abs(x));

        // 계수 및 상수
        double tau = t * Math.exp(-x * x - 1.26551223 + 1.00002368 * t + 0.37409196 * Math.pow(t, 2) +
                0.09678418 * Math.pow(t, 3) - 0.18628806 * Math.pow(t, 4) + 0.27886807 * Math.pow(t, 5) -
                1.13520398 * Math.pow(t, 6) + 1.48851587 * Math.pow(t, 7) - 0.82215223 * Math.pow(t, 8) +
                0.17087277 * Math.pow(t, 9));

        return (x >= 0) ? 1 - tau : tau - 1;
    }
    
    
}
