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
		this("��ư", defalutWidth, defalutHeight, COLOR.MEDIUM_SLATE_BLUE);
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
        setForeground(fontColor.getColor()); // �ؽ�Ʈ�� ����
        Color backgroundColor = btnColor.getColor();
        setBackground(backgroundColor);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // ��ư ������ ��
            	setBackground(backgroundColor.brighter());
            }
            @Override
            public void mouseEntered(MouseEvent e) { // ���콺�� ��ư ���� ���� ��
            	setBackground(backgroundColor.darker());                
            }
            
            @Override
            public void mouseExited(MouseEvent e) { // ���콺�� ��ư ������ ���� ��
                setBackground(backgroundColor);
            }
        });                
	}
	

	@Override 
	protected void paintComponent(Graphics g) {		
		Graphics2D graphics = (Graphics2D) g; 
		// ������ �ε巴�� ����
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							
		
		graphics.setColor(getBackground());  // �⺻ ����
		int width = getWidth(); 
		int height = getHeight(); 
		graphics.fillRoundRect(0, 0, width, height, 70, 70); // ��ư �𼭸� �ձ۰�
		
		
		graphics.setColor(getForeground()); // �ؽ�Ʈ�� ����
		try { // ��Ʈ ���� 
			graphics.setFont(FileManager.createFontFromFile("contentFont").deriveFont(25f)); 
		} catch (IOException e) { 
			System.out.println(e.getMessage());				
		}
		// ��ư �ؽ�Ʈ ��ġ ����
		FontMetrics fontMetrics = graphics.getFontMetrics(); // ���� ��Ʈ ���� ��������
		Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); // �ؽ�Ʈ�� ��谪�� ������
		int textX = (width - stringBounds.width) / 2; // �ؽ�Ʈ x��ġ
		int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent() + 2; // �ؽ�Ʈ y��ġ + ��Ʈ �⺻ ����
		graphics.drawString(getText(), textX, textY); // ��ư �ؽ�Ʈ ������ ��ġ�� �׸���
		
		graphics.dispose(); // �ݱ� 
		super.paintComponent(g); 
	}
}


