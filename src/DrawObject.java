import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class DrawObject {
	private Screen screen;
	public DrawObject(Screen screen) {
		this.screen = screen;
	}
	public Screen getScreen() {
		return this.screen;
	}
	public abstract void getColor(Color color);
	public abstract void paint(Graphics g);
	public abstract void mouseDragged(MouseEvent e);
	public abstract void mousePressed(MouseEvent e);
	public abstract void mouseReleased(MouseEvent e);
	public abstract void save(DataOutputStream dos);
	public abstract void open(DataInputStream dis);
	public abstract void forDotSize(int dotSize);
	public abstract void eraser();
}
