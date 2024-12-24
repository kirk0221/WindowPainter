import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DrawTriangle extends DrawObject {
   private LinkedList<Point> mouseStartPointList = new LinkedList<>();
   private LinkedList<Point> mouseEndPointList = new LinkedList<>();
   private LinkedList<Color> mouseColorList = new LinkedList<>();
   private Point startPoint = new Point();
   private Point endPoint = new Point();
   private Point oldEndPoint = new Point();
   private int X[] = new int[3];
   private int Y[] = new int[3];
   private int x[] = new int[3];
   private int y[] = new int[3];
   private int a[] = new int[3];
   private int b[] = new int[3];
   
   public DrawTriangle(Screen screen) {
      super(screen);
   }
   
   @Override
   public void paint(Graphics g) {
      int count = mouseStartPointList.size();
      for(int i = 0; i < count; i++) {
         Point start = mouseStartPointList.get(i);
         Point end = mouseEndPointList.get(i);
         Color color = mouseColorList.get(i);
         a[1] = start.x;
         b[0] = start.y;
         a[2] = end.x;
         b[2] = end.y;
         b[1] = end.y;
         a[0] = (a[2]+a[1]) / 2;
         g.setColor(color);
         g.drawPolygon(a, b, 3);
      }
   }
   
   public void getColor(Color color) {
      this.mouseColorList.add(color);
   }
   @Override
   public void mouseDragged(MouseEvent e) {
      Graphics g = getScreen().getGraphics();
       this.endPoint = e.getPoint();
       g.setXORMode(Color.WHITE);
       x[2] = endPoint.x;
       y[2] = endPoint.y;
       y[1] = endPoint.y;
       x[0] = (x[2] + x[1]) / 2;
       X[2] = oldEndPoint.x;
       Y[2] = oldEndPoint.y;
       Y[1] = oldEndPoint.y;
       X[0] = (X[2] + X[1]) / 2;
       g.setColor(Color.BLACK);
       g.drawPolygon(X, Y, 3);
       g.drawPolygon(x, y, 3);
       this.oldEndPoint = endPoint;
   }

   @Override
   public void mousePressed(MouseEvent e) {
      this.startPoint = e.getPoint();
       this.endPoint = e.getPoint();
       this.oldEndPoint = e.getPoint();
       x[1] = startPoint.x;
       y[0] = startPoint.y;
       X[1] = startPoint.x;
       Y[0] = startPoint.y;
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      mouseStartPointList.add(startPoint);
      mouseEndPointList.add(endPoint);
      getScreen().repaint();
   }
   
   @Override
   public void save(DataOutputStream dos) {
      try {
         dos.writeInt(this.mouseStartPointList.size());
         for(int i = 0; i<mouseStartPointList.size(); i++) {
        	Point triangleStartPoint = mouseStartPointList.get(i);
        	Point triangleEndPoint = mouseEndPointList.get(i);
        	Color color = mouseColorList.get(i);
            dos.writeInt(triangleStartPoint.x);
            dos.writeInt(triangleStartPoint.y);
            dos.writeInt(triangleEndPoint.x);
            dos.writeInt(triangleEndPoint.y);
			dos.writeInt(color.getRed());
			dos.writeInt(color.getGreen());
			dos.writeInt(color.getBlue());
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void open(DataInputStream dis) {
      try {
         this.mouseStartPointList.clear();
         int length = dis.readInt();
         for(int i=0; i < length; i++) {
            int start_x = dis.readInt();
            int start_y = dis.readInt();
            int end_x = dis.readInt();
            int end_y = dis.readInt();
            int red = dis.readInt();
            int green = dis.readInt();
            int blue = dis.readInt();
            this.mouseStartPointList.add(new Point(start_x, start_y));
            this.mouseEndPointList.add(new Point(end_x, end_y));
            this.mouseColorList.add(new Color(red, green, blue));
         }
         
      } catch (IOException e) {
         e.printStackTrace();
      }
      getScreen().repaint();
   }

@Override
public void forDotSize(int dotSize) {
	// TODO Auto-generated method stub
	
	}

@Override
public void eraser() {
	// TODO Auto-generated method stub
	this.mouseStartPointList.clear();
	this.mouseEndPointList.clear();
	this.mouseColorList.clear();
	}
}