import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ColorFrame extends JFrame implements KeyListener, ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JTextField colorRedInput;
	private JTextField colorBlueInput;
	private JTextField colorGreenInput;
	public Color setColor;
	public JButton colorButton;
	
	public ColorFrame(){
		setTitle("Color Selection");
		setSize(400, 300);
		this.add(colorPanel(), BorderLayout.CENTER);
		this.add(buttonPanel(), BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
	private JPanel colorPanel() {
		JPanel colorPanel = new JPanel();
		colorButton = new JButton();
		colorButton.setBackground(new Color(0,0,0));
		colorButton.setPreferredSize(new Dimension(400,250));
		colorButton.addActionListener(this);
		colorPanel.add(colorButton);
		return colorPanel;
	}
	
	private JPanel buttonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,3));
		JTextField colorRed = new JTextField("Red");
		colorRedInput = new JTextField("0");
		JTextField colorGreen = new JTextField("Green");
		colorGreenInput = new JTextField("0");
		JTextField colorBlue = new JTextField("Blue");
		colorBlueInput = new JTextField("0");
		colorRedInput.addKeyListener(this);
		colorGreenInput.addKeyListener(this);
		colorBlueInput.addKeyListener(this);
		colorRed.setEditable(false);
		colorGreen.setEditable(false);
		colorBlue.setEditable(false);
		buttonPanel.add(colorRed);
		buttonPanel.add(colorGreen);
		buttonPanel.add(colorBlue);
		buttonPanel.add(colorRedInput);
		buttonPanel.add(colorGreenInput);
		buttonPanel.add(colorBlueInput);
		return buttonPanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(Integer.parseInt(colorRedInput.getText())>=0 && Integer.parseInt(colorRedInput.getText())<=255 && Integer.parseInt(colorGreenInput.getText())>=0 && Integer.parseInt(colorGreenInput.getText())<=255 && Integer.parseInt(colorBlueInput.getText())>=0 && Integer.parseInt(colorBlueInput.getText())<=255){
			setColor = new Color(Integer.parseInt(colorRedInput.getText()), Integer.parseInt(colorGreenInput.getText()), Integer.parseInt(colorBlueInput.getText()));
			colorButton.setBackground(setColor);
			this.setBackground(setColor);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.colorButton) {
			System.out.println("colorButton");
			this.dispose();
		}
	}
	
}
