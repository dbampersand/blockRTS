package com.mygdx.game;

public class Point2 {
	public float x = 0; 
	public float y = 0;
	public Point2()
	{
		
	}
	public Point2(Point2 P)
	{
		x = P.x;
		y = P.y;
	}
	public Point2(float X,float Y)
	{
		x = X;
		y = Y;
	}
	public void add(Point2 p)
	{
		x += p.x;
		y += p.y;
	}
	public void add(float X, float Y)
	{
		x += X;
		y += Y;
	}
	public void mul(Point2 p)
	{
		x *= p.x;
		y *= p.y;
	}
	public void mul(float X, float Y)
	{
		x *= X;
		y *= Y;
	}
	public void mul(float i)
	{
		x *= i;
		y *= i;
	}

	public void sub(float i)
	{
		x -= i;
		y -= i;
	}
	public void sub(float X, float Y)
	{
		x -= X;
		y -= Y;
	}
	public void sub(Point2 P)
	{
		x -= P.x;
		y -= P.y;
	}
	public void div(float i)
	{
		x /= i;
		y /= i;
	}
	public void div(float X, float Y)
	{
		x /= X;
		y /= Y;
	}
	public void div(Point2 p)
	{
		x /= p.x;
		y /= p.y;
	}

	public float magnitude()
	{	
		return Math.abs(x) + Math.abs(y);
	}

}
