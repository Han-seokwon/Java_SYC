package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import file.FileManager;

public class DesignedButton extends JButton{
	
	private static int defalutWidth = 200;
	private static int defalutHeight = 50;
	
	public DesignedButton() {
		this("버튼", defalutWidth, defalutHeight, COLOR.MEDIUM_SLATE_BLUE);
	}
	public DesignedButton(String text){
		this(text, defalutWidth, defalutHeight, COLOR.MEDIUM_SLATE_BLUE, COLOR.WHITE);
	}
	public DesignedButton(String text, COLOR btnColor){
		this(text, defalutWidth, defalutHeight, btnColor, COLOR.WHITE);
	}
	public DesignedButton(String text, int width, int height, COLOR btnColor){
		this(text, width, height, btnColor, COLOR.WHITE);
	}
	public DesignedButton(String text, int width, int height, COLOR btnColor, COLOR fontColor){
		super(text);
		setOpaque(false);        
        setPreferredSize(new Dimension(width, height));        
        setForeground(fontColor.getColor()); // 텍스트색 설정
        Color backgroundColor = btnColor.getColor();
        setBackground(backgroundColor);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // 버튼 눌렸을 때
            	setBackground(backgroundColor.brighter());
            }
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위에 있을 때
            	setBackground(backgroundColor.darker());                
            }
            
            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 버튼 위에서 나갈 때
                setBackground(backgroundColor);
            }
        });                
	}
	

	@Override 
	protected void paintComponent(Graphics g) {		
		Graphics2D graphics = (Graphics2D) g; 
		// 렌더링 부드럽게 조정
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							
		
		graphics.setColor(getBackground());  // 기본 색상
		int width = getWidth(); 
		int height = getHeight(); 
		graphics.fillRoundRect(0, 0, width, height, 70, 70); // 버튼 모서리 둥글게
		
		
		graphics.setColor(getForeground()); // 텍스트색 적용
		try { // 폰트 설정 
			graphics.setFont(FileManager.createFontFromFile("contentFont").deriveFont(25f)); 
		} catch (IOException e) { 
			System.out.println(e.getMessage());				
		}
		// 버튼 텍스트 위치 조정
		FontMetrics fontMetrics = graphics.getFontMetrics(); // 현재 폰트 구조 가져오기
		Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); // 텍스트의 경계값을 가져옴
		int textX = (width - stringBounds.width) / 2; // 텍스트 x위치
		int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent() + 2; // 텍스트 y위치 + 폰트 기본 높이
		graphics.drawString(getText(), textX, textY); // 버튼 텍스트 지정된 위치에 그리기
		
		graphics.dispose(); // 닫기 
		super.paintComponent(g); 
	}
}


