package graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Miccio
 */
public class GraphBar extends JPanel
{
	private ArrayList<Integer> data;
	private ArrayList<String> labels;
	private static final int MARGIN = 1;
	private final int MAXX;
	private final int MINX = -MARGIN;
	private final int MAXY;
	private final int MINY;
	private final int MAXDATA;
	private final int MINDATA;
	private static final int STEP = 10;
	private static final int MARK = 5;
	private static final int SPACE = 10;

    /**
     *
     * @param data
     * @param labels
     */
    public GraphBar(ArrayList<Integer> data, ArrayList<String> labels) 
	{
		this.data = data;
		this.labels = labels;
		MAXX = data.size()+MARGIN;
		int max = 0; 
		int min = 0; 
		int minData = data.get(0);

		for (int i : data)
		{
			if (i < min) min = i;
			if (i > max) max = i;
			if (i < minData) minData = i;
		}
		MAXDATA = max;
		MINDATA = minData;
		MAXY = max+MARGIN;
		MINY = min-MARGIN;
	}
	
	private void drawAxes(Graphics g, Origo o)
	{
		g.setColor(Color.RED);
		// Draw axes
		g.drawLine(0,o.y,o.w-1,o.y);
		g.drawLine(o.x,0,o.x,o.h-1);
		// Draw markers on y-axis
		for (int i=(MINY/STEP)*STEP; i<=MAXY; i+=STEP)
		{
			g.setColor(Color.RED);
			int y = o.getY(i);
			g.drawLine(o.x-MARK/2,y,o.x+MARK/2,y);
			
			if(i!=MAXY && i!=(MINY/STEP)*STEP)
			{
				g.setColor(Color.BLACK);
				g.drawString(i+"",o.x-g.getFontMetrics().stringWidth(i+"")-MARK,y+g.getFontMetrics().getAscent()/2);
			}
			
		}
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Origo o = new Origo(getHeight(),getWidth(),MINX,MINY,MAXX,MAXY);
		drawAxes(g,o);
		g.setFont(new Font("default", Font.BOLD, 12));
		int x = o.x;
		for (int i=0; i<data.size(); i++)
		{
			Point p = o.transform(new Point(i+1,data.get(i)));
			
			g.setColor(new Color(map(data.get(i),MINDATA,MAXDATA,255,0),map(data.get(i),MINDATA,MAXDATA,0,230),map(i,0,data.size(),0,50)));
			g.fillRect(x+SPACE,p.y,p.x-x-SPACE,o.y-p.y);
			//g.setColor(Color.BLACK);
			//g.drawString(labels.get(i),(x+SPACE+p.x)/2-g.getFontMetrics().stringWidth(labels.get(i))/2,o.y+g.getFontMetrics().getHeight());
			
			x = p.x;
		}
	}
	
	int map(int x, int in_min, int in_max, int out_min, int out_max)
	{
		try{
			return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
		} catch (ArithmeticException ex) {
			return 0;
		}
	}
}