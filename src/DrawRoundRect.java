import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class DrawRoundRect extends DrawObject {
	public DrawRoundRect(Screen screen) {
		super(screen);
	}
	private Point startPoint = new Point();
	private Point endPoint = new Point();
	private LinkedList<Point> mouseStartLineList = new LinkedList<>();
	private LinkedList<Point> mouseEndLineList = new LinkedList<>();
	private LinkedList<Color> mouseColorList = new LinkedList<>();
	private Point oldEndPoint = new Point();
	
	public void paint(Graphics g) {
		for(int i = 0; i< mouseStartLineList.size(); i++) {
			Point pointStart = mouseStartLineList.get(i);
			Point pointEnd = mouseEndLineList.get(i);
			Color color = mouseColorList.get(i);
			g.setColor(color);
			g.drawRoundRect(pointStart.x, pointStart.y, pointEnd.x - pointStart.x, pointEnd.y - pointStart.y, 20, 20);
			g.drawRoundRect(pointStart.x, pointEnd.y, pointEnd.x - pointStart.x, pointStart.y - pointEnd.y, 20, 20);
			g.drawRoundRect(pointEnd.x, pointStart.y, pointStart.x - pointEnd.x, pointEnd.y - pointStart.y, 20, 20);
			g.drawRoundRect(pointEnd.x, pointEnd.y, pointStart.x - pointEnd.x, pointStart.y - pointEnd.y, 20, 20);
		}
	}
	public void getColor(Color color) {
		this.mouseColorList.add(color);
	}
	
	public void mouseDragged(MouseEvent e) {
		Graphics g = getScreen().getGraphics();
		this.endPoint = e.getPoint();
		g.setXORMode(Color.WHITE);
		g.drawRoundRect(startPoint.x, startPoint.y, oldEndPoint.x - startPoint.x, oldEndPoint.y - startPoint.y, 20, 20);
		g.drawRoundRect(startPoint.x, oldEndPoint.y, oldEndPoint.x - startPoint.x, startPoint.y - oldEndPoint.y, 20, 20);
		g.drawRoundRect(oldEndPoint.x, startPoint.y, startPoint.x - oldEndPoint.x, oldEndPoint.y - startPoint.y, 20, 20);
		g.drawRoundRect(oldEndPoint.x, oldEndPoint.y, startPoint.x - oldEndPoint.x, startPoint.y - oldEndPoint.y, 20, 20);
		g.drawRoundRect(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y, 20, 20);
		g.drawRoundRect(startPoint.x, endPoint.y, endPoint.x - startPoint.x, startPoint.y - endPoint.y, 20, 20);
		g.drawRoundRect(endPoint.x, startPoint.y, startPoint.x - endPoint.x, endPoint.y - startPoint.y, 20, 20);
		g.drawRoundRect(endPoint.x, endPoint.y, startPoint.x - endPoint.x, startPoint.y - endPoint.y, 20, 20);
		this.oldEndPoint = endPoint;
		g.setColor(Color.BLACK);
	}
	
	public void mousePressed(MouseEvent e) {
		this.startPoint = e.getPoint();
		this.endPoint = e.getPoint();
		this.oldEndPoint = e.getPoint();
	}

	public void mouseReleased(MouseEvent e) {
		this.endPoint = e.getPoint();
		mouseStartLineList.add(startPoint);
		mouseEndLineList.add(endPoint);
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
