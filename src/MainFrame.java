import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class MainFrame extends JFrame implements ActionListener, WindowListener {
	
	private static final long serialVersionUID = 1L;
	//file, view, tool에 쓰이는 단어
	private final String MENU_FILE_NEW = "새로만들기(N)";
	private final String MENU_FILE_OPEN = "열기(O)";
	private final String MENU_FILE_SAVE = "저장(S)";
	private final String MENU_FILE_RENAMESAVE = "다른 이름으로 저장(A)";
	private final String MENU_FILE_CLOSE = "끝내기(E)";
	private final String MENU_VIEW_STATUS = "상태바 보기(S)";
	private final String MENU_TOOL_DOT = "점(D)";
	private final String MENU_TOOL_LINE = "선(L)";
	private final String MENU_TOOL_CIRCLE = "원(C)";
	private final String MENU_TOOL_RECT = "네모(R)";
	private final String MENU_TOOL_FILLCIRCLE = "채워진 원(F)";
	private final String MENU_TOOL_FILLRECT = "채워진 네모(I)";
	private final String MENU_TOOL_ROUNDRECT = "둥근 네모(U)";
	private final String MENU_TOOL_TRIANGLE = "삼각형(T)";
	private final String MENU_TOOL_RIGHTTRIANGLE = "직각삼각형(G)";
	private final String MENU_TOOL_ERASER = "지우개(Y)";
	private final String MENU_TOOL_CLEAR = "전체지우기(H)";
	
	private JButton[] toolboxButtons;
	private JButton[] colorButtons;
	public Color[] colors;
	private ImageIcon[] drawButtonImage;
	private JButton selectedColorButton;
	private JButton selectedColorButton2;
	private JButton[] selectedColorButtons;
	private Screen screen;
	private JButton[] selectedDotSizeButtons;
	private ImageIcon[] selectedDotSizeButtonImage;
	private int screenDotSize = 10;
	private JTextField showDotSize;
	private int selectedColorButtonMode;
	private Color[] selectedColor;
	private JButton[] userChangeColorButtons;
	private Color[] userColors;
	private JFrame colorFrame;
	private int remember_i;
	private String labelString = "selection";
	private JLabel statusBar = null;
	
	
	public MainFrame() {
		
		this.setJMenuBar(createMenuBar()); //menubar 추가
		this.statusBar = createStatusBar();
		this.add(this.statusBar, BorderLayout.SOUTH); //statusbar 추가
		this.add(createToolBar(), BorderLayout.NORTH); //toolbar 추가
		this.screen = new Screen(); //스크린 생성
		this.add(this.screen); //스크린 추가
		setTitle("Window Painter"); //창 이름 지정
		setSize(800, 600); //화면 사이즈 지정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); //화면이 가운데 위치하게
		setVisible(true); //화면이 표시되게
	}
	
	//메뉴바 생성
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		//이미지 아이콘 생성
		this.toolboxButtons = new JButton[12];
		drawButtonImage = new ImageIcon[12];
		drawButtonImage[1] = new ImageIcon("src/resources/dot.png");
		drawButtonImage[2] = new ImageIcon("src/resources/line.png");
		drawButtonImage[3] = new ImageIcon("src/resources/circle.png");
		drawButtonImage[4] = new ImageIcon("src/resources/rect.png");
		drawButtonImage[5] = new ImageIcon("src/resources/fillcircle.png");
		drawButtonImage[6] = new ImageIcon("src/resources/fillrect.png");
		drawButtonImage[7] = new ImageIcon("src/resources/roundrect.png");
		drawButtonImage[8] = new ImageIcon("src/resources/triangle.png");
		drawButtonImage[9] = new ImageIcon("src/resources/righttriangle.png");
		drawButtonImage[10] = new ImageIcon("src/resources/eraser.png");
		drawButtonImage[11] = new ImageIcon("src/resources/clear.png");
		
		//File Menu
		ImageIcon iconNew = new ImageIcon("src/resources/new.png");
		ImageIcon iconOpen = new ImageIcon("src/resources/open.png");
		ImageIcon iconSave = new ImageIcon("src/resources/save.png");
		ImageIcon iconRenameSave = new ImageIcon("src/resources/renamesave.png");
		ImageIcon iconClose = new ImageIcon("src/resources/close.png");

		JMenu fileMenu = new JMenu("파일(F)");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem newMenuItem = new JMenuItem(this.MENU_FILE_NEW, iconNew);
		newMenuItem.setToolTipText("새 그림을 만듭니다."); //간단한 단축키(지정한 알파벳에 밑줄을 쳐준다)
		newMenuItem.setMnemonic(KeyEvent.VK_N); //단축키
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		newMenuItem.addActionListener(this);
		
		JMenuItem openMenuItem = new JMenuItem(this.MENU_FILE_OPEN, iconOpen);
		openMenuItem.setToolTipText("기존 그림을 엽니다.");
		openMenuItem.setMnemonic(KeyEvent.VK_O);
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		openMenuItem.addActionListener(this);
		
		JMenuItem saveMenuItem = new JMenuItem(this.MENU_FILE_SAVE, iconSave);
		saveMenuItem.setToolTipText("현재 그림을 저장합니다.");
		saveMenuItem.setMnemonic(KeyEvent.VK_S);
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		saveMenuItem.addActionListener(this);
		
		JMenuItem renameSaveMenuItem = new JMenuItem(this.MENU_FILE_RENAMESAVE, iconRenameSave);
		renameSaveMenuItem.setToolTipText("현재 그림을 새 파일로 저장합니다.");
		renameSaveMenuItem.setMnemonic(KeyEvent.VK_A);
		renameSaveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		renameSaveMenuItem.addActionListener(this);
				
		JMenuItem closeMenuItem = new JMenuItem(this.MENU_FILE_CLOSE, iconClose);
		closeMenuItem.setToolTipText("프로그램을 종료 합니다.");
		closeMenuItem.setMnemonic(KeyEvent.VK_E);
		closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		closeMenuItem.addActionListener(this);
		
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(renameSaveMenuItem);
		fileMenu.addSeparator(); //구분자
		fileMenu.add(closeMenuItem);
		
		//View Menu
		JMenu viewMenu = new JMenu("보기(V)");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		
		JCheckBoxMenuItem showStatusBarMenuItem = new JCheckBoxMenuItem(this.MENU_VIEW_STATUS);
        showStatusBarMenuItem.setMnemonic(KeyEvent.VK_S);
        showStatusBarMenuItem.setSelected(true);
        showStatusBarMenuItem.addItemListener(new ItemListener() {//statusbar 보이게 하기
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					statusBar.setVisible(true);
			    } else {
			    	statusBar.setVisible(false);
			    }
			}
		});
        viewMenu.add(showStatusBarMenuItem);
        
        //Tool Menu
     
        JMenu toolMenu = new JMenu("도구(T)");
        toolMenu.setMnemonic(KeyEvent.VK_T);
        
        JMenuItem dot = new JMenuItem(this.MENU_TOOL_DOT, drawButtonImage[1]);
		dot.setToolTipText("점을 그립니다.");
		dot.setMnemonic(KeyEvent.VK_D);
		dot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
		dot.addActionListener(this);
		
        JMenuItem line = new JMenuItem(this.MENU_TOOL_LINE, drawButtonImage[2]);
		line.setToolTipText("선을 그립니다.");
		line.setMnemonic(KeyEvent.VK_L);
		line.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
		line.addActionListener(this);
		
        JMenuItem circle = new JMenuItem(this.MENU_TOOL_CIRCLE, drawButtonImage[3]);
		circle.setToolTipText("원을 그립니다.");
		circle.setMnemonic(KeyEvent.VK_C);
		circle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		circle.addActionListener(this);
		
        JMenuItem rect = new JMenuItem(this.MENU_TOOL_RECT, drawButtonImage[4]);
		rect.setToolTipText("네모를 그립니다.");
		rect.setMnemonic(KeyEvent.VK_R);
		rect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		rect.addActionListener(this);
		
        JMenuItem fillcircle = new JMenuItem(this.MENU_TOOL_FILLCIRCLE, drawButtonImage[5]);
		fillcircle.setToolTipText("채워진 원을 그립니다.");
		fillcircle.setMnemonic(KeyEvent.VK_F);
		fillcircle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		fillcircle.addActionListener(this);
		
        JMenuItem fillrect = new JMenuItem(this.MENU_TOOL_FILLRECT, drawButtonImage[6]);
		fillrect.setToolTipText("채워진 네모를 그립니다.");
		fillrect.setMnemonic(KeyEvent.VK_I);
		fillrect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
		fillrect.addActionListener(this);
		
        JMenuItem roundrect = new JMenuItem(this.MENU_TOOL_ROUNDRECT, drawButtonImage[7]);
		roundrect.setToolTipText("모서리가 둥근 네모를 그립니다.");
		roundrect.setMnemonic(KeyEvent.VK_U);
		roundrect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
		roundrect.addActionListener(this);
		
        JMenuItem triangle = new JMenuItem(this.MENU_TOOL_TRIANGLE, drawButtonImage[8]);
        triangle.setToolTipText("세모를 그립니다.");
		triangle.setMnemonic(KeyEvent.VK_T);
		triangle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		triangle.addActionListener(this);
		
        JMenuItem rightTriangle = new JMenuItem(this.MENU_TOOL_RIGHTTRIANGLE, drawButtonImage[9]);
        rightTriangle.setToolTipText("직각삼각형을 그립니다.");
		rightTriangle.setMnemonic(KeyEvent.VK_G);
		rightTriangle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		rightTriangle.addActionListener(this);
		
        JMenuItem eraser = new JMenuItem(this.MENU_TOOL_ERASER, drawButtonImage[10]);
        eraser.setToolTipText("그림을 지웁니다.");
		eraser.setMnemonic(KeyEvent.VK_Y);
		eraser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
		eraser.addActionListener(this);
		
        JMenuItem clear = new JMenuItem(this.MENU_TOOL_CLEAR, drawButtonImage[11]);
        clear.setToolTipText("창을 지웁니다.");
		clear.setMnemonic(KeyEvent.VK_H);
		clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		clear.addActionListener(this);
        
		toolMenu.add(dot);
		toolMenu.add(line);
		toolMenu.add(circle);
		toolMenu.add(rect);
		toolMenu.add(fillcircle);
		toolMenu.add(fillrect);
		toolMenu.add(roundrect);
		toolMenu.add(triangle);
		toolMenu.add(rightTriangle);
		toolMenu.add(eraser);
		toolMenu.add(clear);
		
        //메뉴바 추가
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		menuBar.add(toolMenu);

		return menuBar;
	}
	//상태바 생성
	private JLabel createStatusBar() {
		JLabel statusBar = new JLabel(labelString);
		statusBar.setBorder(BorderFactory.createEtchedBorder());
		return statusBar;
	}
	//툴바 생성
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// drawMode 선택버튼
		JPanel groupPanelDrawBox = new JPanel();
		groupPanelDrawBox.setLayout(new GridLayout(3, 4));
		
		for(int i=0; i < 12; i++) {
			toolboxButtons[i] = new JButton(drawButtonImage[i]);
			JButton toolButton = toolboxButtons[i];
			toolButton.setPreferredSize(new Dimension(20, 20));
			toolButton.addActionListener(this);
			groupPanelDrawBox.add(toolButton);
		}
		
		//색상 표시버튼
		JPanel groupPanelSelectedColor = new JPanel();
		groupPanelSelectedColor.setLayout(new GridLayout(1, 2));
		selectedColor = new Color[2];
		selectedColorButtons = new JButton[2];
		selectedColorButton = new JButton();
		selectedColorButton.setBackground(new Color(0, 0, 0));
		selectedColorButton.setPreferredSize(new Dimension(60, 60));
		selectedColor[0] = new Color(0,0,0);
		selectedColorButton.addActionListener(this);
		
		selectedColorButton2 = new JButton();
		selectedColorButton2.setBackground(new Color(255, 255, 255));
		selectedColorButton2.setPreferredSize(new Dimension(60, 60));
		selectedColor[1] = new Color(255,255,255);
		selectedColorButton2.addActionListener(this);
		selectedColorButtons[0] = selectedColorButton;
		selectedColorButtons[1] = selectedColorButton2;
		groupPanelSelectedColor.add(selectedColorButton);
		groupPanelSelectedColor.add(selectedColorButton2);
		
		//색상 선택버튼
		JPanel groupPanelColor = new JPanel();
		groupPanelColor.setLayout(new GridLayout(3, 5));
		
		colorButtons = new JButton[15];
		colors = new Color[15];
		for(int i=0; i < colors.length; i++) {
			colors[i] = new Color(0, 0, 0);
		}
		//기본 색상
		colors[1] = new Color(255, 0, 0);
		colors[2] = new Color(0, 255, 0);
		colors[3] = new Color(0, 0, 255);
		colors[4] = new Color(127, 127, 127);
		colors[5] = new Color(136, 0, 21);
		colors[6] = new Color(255, 127, 39);
		colors[7] = new Color(255, 242, 0);
		colors[8] = new Color(34, 177, 76);
		colors[9] = new Color(0, 162, 232);
		colors[10] = new Color(153, 217, 234);
		colors[11] = new Color(163, 73, 164);
		colors[12] = new Color(255, 174, 201);
		colors[13] = new Color(181, 230, 29);
		colors[14] = new Color(255, 255, 255);
		
		for(int i=0; i < 15; i++) {
			colorButtons[i] = new JButton();
			JButton colorButton = colorButtons[i];
			colorButton.setBackground(colors[i]);
			colorButton.setPreferredSize(new Dimension(20, 20));
			colorButton.addActionListener(this);
			groupPanelColor.add(colorButton);
		}
		
		//사용자 지정 색상
		JPanel groupPanelUserColor = new JPanel();
		groupPanelUserColor.setLayout(new GridLayout(3,5));
		userChangeColorButtons = new JButton[15];
		userColors = new Color[15];
		for(int i=0; i < userColors.length; i++) {
			userColors[i] = new Color(0, 0, 0);
		}
		
		for(int i=0; i < 15; i++) {
			userChangeColorButtons[i] = new JButton();
			JButton userColorButton = userChangeColorButtons[i];
			userColorButton.setBackground(userColors[i]);
			userColorButton.setPreferredSize(new Dimension(20, 20));
			userColorButton.addActionListener(this);
			groupPanelUserColor.add(userColorButton);
		}
		
		//점 굵기 표시
		JPanel groupPanelShowDotSize = new JPanel();
		groupPanelShowDotSize.setLayout(new GridLayout(2,1));
		this.showDotSize = new JTextField(Integer.toString(this.screenDotSize));
		this.showDotSize.setEditable(false);
		JTextField showDotSizeIsIt = new JTextField("현재 점 크기");
		showDotSizeIsIt.setEditable(false);
		groupPanelShowDotSize.add(showDotSizeIsIt);
		groupPanelShowDotSize.add(this.showDotSize);
		
		//점굵기 선택
		JPanel groupPanelSelectedDotSize = new JPanel();
		groupPanelSelectedDotSize.setLayout(new GridLayout(2,1));
		this.selectedDotSizeButtons = new JButton[2];
		selectedDotSizeButtonImage = new ImageIcon[2];
		selectedDotSizeButtonImage[0] = new ImageIcon("src/resources/makethin.png");
		selectedDotSizeButtonImage[1] = new ImageIcon("src/resources/makethick.png");
		for (int i=0; i<2; i++) {
			selectedDotSizeButtons[i] = new JButton(selectedDotSizeButtonImage[i]);
			JButton selectedDotSizeButton = selectedDotSizeButtons[i];
			selectedDotSizeButton.setPreferredSize(new Dimension(20, 20));
			selectedDotSizeButton.addActionListener(this);
			groupPanelSelectedDotSize.add(selectedDotSizeButton);
		}
		
		toolBar.add(groupPanelDrawBox);
		toolBar.add(groupPanelSelectedColor);
		toolBar.add(groupPanelColor);
		toolBar.add(groupPanelUserColor);
		toolBar.add(groupPanelShowDotSize);
		toolBar.add(groupPanelSelectedDotSize);
		return toolBar;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(this.MENU_FILE_NEW)) { //새로만들기
			new MainFrame();
			this.dispose();
		}
		else if(e.getActionCommand().equals(this.MENU_FILE_CLOSE)) { //끝내기
			this.dispose();
			System.exit(0); 
		}else if(e.getActionCommand().equals(this.MENU_FILE_SAVE)) { //저장
			FileDialog fd = new FileDialog(this, "파일 저장", FileDialog.SAVE);
			fd.setVisible(true);
			if(fd.getFile() != null) {
				this.screen.save(fd.getDirectory() + fd.getFile());
			}
		}else if(e.getActionCommand().equals(this.MENU_FILE_RENAMESAVE)) { //다른 이름으로 저장
			FileDialog fd = new FileDialog(this, "파일 다른이름으로 저장", FileDialog.SAVE);
			fd.setVisible(true);
			this.screen.save(fd.getDirectory() + fd.getFile());
		}else if(e.getActionCommand().equals(this.MENU_FILE_OPEN)) { //파일 열기
			FileDialog fd = new FileDialog(this, "파일 열기", FileDialog.LOAD);
			fd.setFile("*.sav");
			fd.setVisible(true);
			if(fd.getFile() != null) {
				this.screen.open(fd.getDirectory() + fd.getFile());
				this.screen.repaint();
			}
		}
		//menubar의 도구
		else if(e.getActionCommand().equals(this.MENU_TOOL_DOT)) {
			this.screen.setDrawMode(1);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_LINE)) {
			this.screen.setDrawMode(2);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_CIRCLE)) {
			this.screen.setDrawMode(3);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_RECT)) {
			this.screen.setDrawMode(4);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_FILLCIRCLE)) {
			this.screen.setDrawMode(5);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_FILLRECT)) {
			this.screen.setDrawMode(6);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_ROUNDRECT)) {
			this.screen.setDrawMode(7);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_TRIANGLE)) {
			this.screen.setDrawMode(8);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_RIGHTTRIANGLE)) {
			this.screen.setDrawMode(9);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_ERASER)) {
			this.screen.setDrawMode(10);
		}else if(e.getActionCommand().equals(this.MENU_TOOL_CLEAR)) {
			this.screen.setDrawMode(11);
		}
		else {
			for(int i=0; i < this.toolboxButtons.length; i++) { //그리기 모드 설정
				if(toolboxButtons[i].equals(e.getSource())) {
					System.out.println("toolbox btn: "+i);
					this.screen.setDrawMode(i);
				}
			}
			for(int j=0; j<this.selectedColorButtons.length;j++) { //색상 설정
				if(selectedColorButtons[j].equals(e.getSource())) {
					System.out.println("selectedcolormode btn: "+j);
					this.selectedColorButtonMode = j;
					this.screen.setColor(selectedColor[j]);
				}
			}
			for(int i=0; i < this.colorButtons.length; i++) { //저장해둔 색상 선택
				if(colorButtons[i].equals(e.getSource())) {
					System.out.println("color btn: "+i);
					this.selectedColorButtons[selectedColorButtonMode].setBackground(this.colors[i]);
					this.screen.setColor(this.colors[i]);
					this.selectedColor[selectedColorButtonMode] = this.colors[i];
				}
			}
			for(int i=0; i < this.selectedDotSizeButtons.length; i++) { //점 크기 변경
				if(selectedDotSizeButtons[i].equals(e.getSource())) {
					System.out.println("selectedDotSize btn: "+i);
					this.screen.selectedDotSize(i);
					this.screenDotSize = this.screen.returnDotSize();
					this.showDotSize.setText(Integer.toString(this.screenDotSize));
				}
			}
			for(int i=0; i < this.userChangeColorButtons.length; i++) { //사용자 지정 색상만들기
				if(userChangeColorButtons[i].equals(e.getSource())) { //만들어지지 않은 사용자 지정 색상을 선택하였을 때
					if(userChangeColorButtons[i].getBackground().getRed() == 0 && userChangeColorButtons[i].getBackground().getGreen() == 0 && userChangeColorButtons[i].getBackground().getBlue() == 0) {
						this.colorFrame = new ColorFrame();
						this.colorFrame.addWindowListener(this);
						this.remember_i = i;
						
					}else { //이미 만들어진 사용자 지정 색상을 선택했을 때
						System.out.println("usercolor btn: "+i);
						this.selectedColorButtons[selectedColorButtonMode].setBackground(this.userColors[i]);
						this.screen.setColor(this.userColors[i]);
						this.selectedColor[selectedColorButtonMode] = this.userColors[i];
					}
				}
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) { //사용자 지정 색상 창이 닫혔을 때
		// TODO Auto-generated method stub
		userChangeColorButtons[remember_i].setBackground(colorFrame.getBackground());
		userColors[remember_i] = colorFrame.getBackground();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
