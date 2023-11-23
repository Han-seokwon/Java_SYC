package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import file.FileManager;

public class GuiJFrame extends JFrame{
	public GuiJFrame() {
		setLocationRelativeTo(null); // 창 가운데 위치
		String filepath = FileManager.getPackageRootDir() + "/images/bg.png";
        try {
            this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(filepath)))));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
