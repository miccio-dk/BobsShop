/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Miccio
 */
public class Graph extends JPanel
{
	private int[][] data;
	private static final int MARGINX = 1;
	private static final int MARGINY = 5;
	private final int MAXX;
	private final int MINX;
	private final int MAXY;
	private final int MINY;
	private static final int STEP = 5;
	private static final int MARK = 5;

    /**
     *
     * @param data
     */
    public Graph(int[][] data) 
	{
		this.data = data;
		int maxX = 0; int maxY = 0;
		int minX = 0; int minY = 0;
		for (int[] d : data)
		{
			if (d[0] > maxX) maxX = d[0];
			if (d[0] < minX) minX = d[0];
			if (d[1] > maxY) maxY = d[1];
			if (d[1] < minY) minY = d[1];
		}
		MAXX = maxX + MARGINX; MINX = minX - MARGINX;
		MAXY = maxY + MARGINY; MINY = minY - MARGINY;
	}
	
	private void drawAxes(Graphics g, Origo o)
	{
		g.setColor(Color.RED);
		g.drawLine(0,o.y,o.w-1,o.y); // Draw x axis
		g.drawLine(o.x,0,o.x,o.h-1); // Draw y axis
		// Draw markers on x-axis
		for (int i=(MINX/STEP)*STEP; i<=MAXX; i+=STEP)
		{
			int x = o.getX(i);
			g.drawLine(x,o.y-MARK,x,o.y+MARK);
		}
		// Draw markers on y-axis
		for (int i=(MINY/STEP)*STEP; i<=MAXY; i+=STEP)
		{
			int y = o.getY(i);
			g.drawLine(o.x-MARK,y,o.x+MARK,y);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Origo o = new Origo(getHeight(),getWidth(),MINX,MINY,MAXX,MAXY);
		drawAxes(g,o);
		g.setColor(Color.BLUE);
		Point p = o.transform(new Point(data[0][0],data[0][1]));
		for (int i=1; i<data.length; i++)
		{
			Point q= o.transform(new Point(data[i][0],data[i][1]));
			g.drawLine(p.x,p.y,q.x,q.y);
			p = q;
		}
	}
}