/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.*;

/**
 *
 * @author Miccio
 */
public class Origo
{
	private double scaleX = 0.0;
	private double scaleY = 0.0;

    /**
     *
     */
    public final int h;

    /**
     *
     */
    public final int w;

    /**
     *
     */
    public final int x;

    /**
     *
     */
    public final int y;

    /**
     *
     * @param h
     * @param w
     * @param minX
     * @param minY
     * @param maxX
     * @param maxY
     */
    public Origo(int h, int w, int minX, int minY, int maxX, int maxY)
	{
		this.h = h;
		this.w = w;
		scaleX = (double)w / (double)(maxX-minX);
		scaleY = (double)h / (double)(maxY-minY);
		x = -(int)((double)minX*scaleX);
		y = (int)(h+(double)(minY*scaleY));
	}

    /**
     *
     * @param i
     * @return
     */
    public int getX(int i) {
		return x+(int)((double)i*scaleX);
	}

    /**
     *
     * @param i
     * @return
     */
    public int getY(int i) {
		return y-(int)((double)i*scaleY);
	}

    /**
     *
     * @param p
     * @return
     */
    public Point transform(Point p) {
		return new Point(getX(p.x),getY(p.y));
	}
}
