import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Eraser extends DrawObject {
	private LinkedList<Point> mousePointList = new LinkedList<>();
	private LinkedList<Integer> mouseSizeList = new LinkedList<>();
	public int pointSize;
	
	public Eraser(Screen screen) {
		super(screen);
	}
	public void forDotSize(int dotSize) {
		this.pointSize = dotSize;
	}
	public void paint(Graphics g) {
		for(int i = 0; i<mousePointList.size(); i++) {
			Point point = mousePointList.get(i);
			int size = mouseSizeList.get(i);
			g.setColor(getScreen().getBackground());
			g.fillRect(point.x-size/2, point.y-size/2, size, size);
		}
	}
	public void getColor(Color color) {
	}
	
	public void mouseDragged(MouseEvent e) {
		Graphics g = getScreen().getGraphics();
		Point point = e.getPoint();
		g.fillRect(point.x-pointSize/2, point.y-pointSize/2, pointSize, pointSize);
		this.mouseSizeList.add(pointSize);
		this.mousePointList.add(e.getPoint());
		getScreen().repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
	
	public void eraser() {
		this.mousePointList.clear();
		this.mouseSizeList.clear();
	}
	
	public void save(DataOutputStream dos) {
		try {
			dos.writeInt(this.mousePointList.size());
			for(int i = 0; i<mousePointList.size(); i++) {
				Point point = mousePointList.get(i);
				int size = mouseSizeList.get(i);
				dos.writeInt(point.x);
				dos.writeInt(point.y);
				dos.writeInt(size);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void open(DataInputStream dis) {
		try {
			int PointSize = dis.readInt();
			this.mousePointList.clear();
			this.mouseSizeList.clear();
			for(int i=0; i<PointSize; i++) {
				int x = dis.readInt();
				int y = dis.readInt();
				int size = dis.readInt();
				this.mousePointList.add(new Point(x,y));
				this.mouseSizeList.add(size);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		getScreen().repaint();
	}
}
