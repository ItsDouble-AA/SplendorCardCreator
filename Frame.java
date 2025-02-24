import java.awt.*;
import javax.swing.*;
public class Frame extends JFrame{
	private static final int WIDTH = 40*8+16;
	private static final int HEIGHT = 56*8+38;
	public Frame(String frameName) {
		super(frameName);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH,HEIGHT);
		getContentPane().setBackground(new Color(0, 0, 0, 0));
		add(new Panel());
		setVisible(true);
	}
}
