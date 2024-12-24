import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class DrawLine extends DrawObject{
	public DrawLine(Screen screen) {
		super(screen);
	}
	private Point startPoint = new Point();
	private Point endPoint = new Point();
	private LinkedList<Point> mouseStartLineList = new LinkedList<>();
	private LinkedList<Point> mouseEndLineList = new LinkedList<>();
	private Point oldEndPoint = new Point();
	private LinkedList<Color> mouseColorList = new LinkedList<>();
	private int eraserMode = 0;
	
	public void paint(Graphics g) {
		for(int i = 0; i< mouseStartLineList.size(); i++) {
			Point pointStart = mouseStartLineList.get(i);
			Point pointEnd = mouseEndLineList.get(i);
			Color color = mouseColorList.get(i);
			g.setColor(color);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
		}
	}
	public void getColor(Color color) {
		this.mouseColorList.add(color);
	}
	
	public void mouseDragged(MouseEvent e) {
		if (eraserMode == 0) {
			Graphics g = getScreen().getGraphics();
			this.endPoint = e.getPoint();
			g.setXORMode(Color.WHITE);
			g.drawLine(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
			g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
			this.oldEndPoint = endPoint;
			g.setColor(Color.BLACK);
		}else if (eraserMode == 1) {
			for(int i=0; i<mouseStartLineList.size(); i++) { // 수정 필요
				if(mouseStartLineList.get(i) == e.getPoint()) {
					mouseStartLineList.remove(i);
					mouseEndLineList.remove(i);
					mouseColorList.remove(i);
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		this.startPoint = e.getPoint();
		this.endPoint = e.getPoint();
		this.oldEndPoint = e.getPoint();
	}

	public void mouseReleased(MouseEvent e) {
		if (eraserMode == 0) {
			this.endPoint = e.getPoint();
			mouseStartLineList.add(startPoint);
			mouseEndLineList.add(endPoint);
		}
		getScreen().repaint();
	}
	public void save(DataOutputStream dos) {
		try {
			dos.writeInt(this.mouseStartLineList.size());
			for(int i = 0; i<mouseStartLineList.size(); i++) {
				Point startPoint = mouseStartLineList.get(i);
				Point endPoint = mouseEndLineList.get(i);
				Color color = mouseColorList.get(i);
				dos.writeInt(startPoint.x);
				dos.writeInt(startPoint.y);
				dos.writeInt(endPoint.x);
				dos.writeInt(endPoint.y);
				dos.writeInt(color.getRed());
				dos.writeInt(color.getGreen());
				dos.writeInt(color.getBlue());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void open(DataInputStream dis) {
		try {
			int pointSize = dis.readInt();
			this.mouseStartLineList.clear();
			this.mouseEndLineList.clear();
			for(int i=0; i<pointSize; i++) {
				int startx = dis.readInt();
				int starty = dis.readInt();
				int endx = dis.readInt();
				int endy = dis.readInt();
				int r = dis.readInt();
				int g = dis.readInt();
				int b = dis.readInt();
				this.mouseStartLineList.add(new Point(startx,starty));
				this.mouseEndLineList.add(new Point(endx,endy));
				this.mouseColorList.add(new Color(r,g,b));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		getScreen().repaint();
	}
	public void forDotSize(int dotSize) {
		
	}
	@Override
	public void eraser() {
		this.mouseStartLineList.clear();
		this.mouseEndLineList.clear();
		this.mouseColorList.clear();
	}
}
