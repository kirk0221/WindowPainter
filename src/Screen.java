import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Screen extends Canvas implements MouseListener, MouseMotionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension dim;
	private Image offScreen = null;
	private Graphics bufferGraphics = null;
	
	private DrawObject drawMode;
	private DrawObject[] drawObjectList = new DrawObject[12];

	private Color color = new Color(0,0,0);
	private int dotSize = 10;
	private Point savePoint;
	public boolean clearall;
	public Screen() {
		addMouseListener(this);
		addMouseMotionListener(this);
		//그림객체들을 리스트로 묶어줌
		drawObjectList[0] = new Selection(this);
		drawObjectList[1] = new DrawPoint(this);
		drawObjectList[2] = new DrawLine(this);
		drawObjectList[3] = new DrawCircle(this);
		drawObjectList[4] = new DrawRect(this);
		drawObjectList[5] = new DrawFillCircle(this);
		drawObjectList[6] = new DrawFillRect(this);
		drawObjectList[7] = new DrawRoundRect(this);
		drawObjectList[8] = new DrawTriangle(this);
		drawObjectList[9] = new DrawRightTriangle(this);
		drawObjectList[10] = new Eraser(this);
		drawObjectList[11] = new Clear(this);
		
		this.clearall = false;
	}
	
	public void setDrawMode(int mode) { // 그림그리기 모드를 지정
		this.drawMode = drawObjectList[mode];
		if (mode == 11) {
			this.clearall = true;
			repaint();
		}
	}
	
	public void setColor(Color color) { // 색상을 가지고오는 함수
		this.color = color;
	}
	
	
	@Override
	public void paint(Graphics g) { //최소화 시켰다가 다시 화면을 띄우면 자동으로 paint가 호출되어 그려짐
		initBufferd(); // 가상버퍼 초기화
		for(DrawObject draw : drawObjectList) {
			if(this.clearall) {
				draw.eraser();
			}
			draw.paint(bufferGraphics); // 가상버퍼에 이미지 그리기
		}
		this.clearall = false;
		g.drawImage(this.offScreen, 0, 0, this);// 가상버퍼(이미지)를 원본 버퍼에 복사
	}
	
	@Override
	public void mouseDragged(MouseEvent e) { // 마우스를 드래그하는 동안
		// TODO Auto-generated method stub
		if(drawMode != null) {
			if (drawMode == drawObjectList[1]) {
				this.drawMode.getColor(this.color);
				this.drawMode.forDotSize(dotSize);
			}
			if (drawMode == drawObjectList[10]) {
				this.drawMode.getColor(this.color);
				this.drawMode.forDotSize(dotSize);
			}
			this.drawMode.mouseDragged(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (drawMode == drawObjectList[10]) { // 지우개선택시 마우스에 지우는 범위 따라가기
			Graphics g = this.getGraphics();
			Point point = e.getPoint();
			if (savePoint == null) { // savePoint의 초기화
				savePoint = new Point(1000000,1000000);
			}else {
			g.setXORMode(Color.WHITE);
			g.drawRect(savePoint.x-dotSize/2, savePoint.y-dotSize/2, dotSize, dotSize);
			g.drawRect(point.x-dotSize/2, point.y-dotSize/2, dotSize, dotSize);
			this.savePoint = point;
			g.setColor(Color.BLACK);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {//마우스를 누를때
		// TODO Auto-generated method stub
		if(drawMode != null) {
			this.drawMode.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {//마우스 클릭을 끝낼때
		// TODO Auto-generated method stub
		if(drawMode != null) {
			if (drawMode != drawObjectList[1]) {
				this.drawMode.getColor(this.color);
			}
			if (savePoint != null) {
				savePoint = null;
			}
			this.drawMode.mouseReleased(e);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {//마우스가 들어갔는지
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {//마우스가 나갔는지
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	public void selectedDotSize(int mode) { //점의 크기를 바꾼다
		if(mode == 0 && dotSize > 1) {
			this.dotSize-=1;
		}else if(mode == 1 && dotSize > 0) {
			this.dotSize+=1;
		}
	}
	public int returnDotSize() {
		return this.dotSize;
	}
	
	private void initBufferd() {
		this.dim = getSize();
		this.offScreen = createImage(dim.width, dim.height);
		this.bufferGraphics = this.offScreen.getGraphics();
	}
	
	public void save(String filename) { //저장
		File file = new File(filename);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(fos);
			for (DrawObject draw : drawObjectList) {
				draw.save(dos);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void open(String filename) { //열기
		File file = new File(filename);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			for (DrawObject draw : drawObjectList) {
				draw.open(dis);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}

}
