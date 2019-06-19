package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

public class RTSMap {
	public Base playerBase;
	public Base opponentBase;
	
	public Lane[] lanes;
		
	Point2 rectStart;
	Point2 size;
	
	List<Unit> units = new ArrayList<Unit>();
	RTSMap(int startX, int startY, int width,int height)
	{
		rectStart = new Point2(startX,startY);
		size = new Point2(width,height);
		
		playerBase = new Base();
		opponentBase = new Base();
	}
}
