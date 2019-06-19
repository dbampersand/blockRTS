package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Unit {
	public RTSPlayer parent;
	int range = 1;
	public int lane = 0;
	static Sprite footmanSpriteBlue;
	static Sprite footmanSpriteRed;
	static String footmanFileName = "footman.png";
	
	public static AssetManager manager;
	public Point2 position = new Point2(10,10); 
	public Point2 randomSpriteOffset = new Point2();
	
	static float spriteOffsetMax = 5f;
	
	Point2 nextVertice; 
	int nextVerticeIndex = 1; 
	float rotation =0;
	Sprite sprite; 
	float speed = 10; 
	float health = 100; 
	
	float attackTimer = 1f;
	float attackCD = 1f;
	
	float damage = 25f;
	public int goldCost = 0;
	public boolean goingRight; 
	
	public Animation<TextureRegion> runningAnimation;
	static int FRAME_ROWS = 1;
	static int FRAME_COLS = 6;
	static TextureRegion[] walkFrames;
	
	static {
		manager = new AssetManager();
		manager.load(footmanFileName, Texture.class);
		
		while (!manager.update())
		{		
		}

		Texture footman  = manager.get(footmanFileName, Texture.class);
		TextureData textureData = footman.getTextureData();
		    textureData.prepare();
		Pixmap pixmap = textureData.consumePixmap();
		pixmap.setColor(Color.BLUE);
		for (int x = 0; x < pixmap.getWidth(); x++)
		{
			for (int y = 0; y < pixmap.getHeight(); y++)
			{
				Color c = new Color(pixmap.getPixel(x, y));
				float total = c.r * c.g * c.b * c.a;
				if (total >= 0.99f)
				{
					pixmap.drawPixel(x, y);
				}
			}
		}
		Texture t = new Texture(pixmap);
		footmanSpriteBlue = new Sprite(t);
		
		textureData = footman.getTextureData();
	    textureData.prepare();
		pixmap = textureData.consumePixmap();
		pixmap.setColor(Color.RED);
		for (int x = 0; x < pixmap.getWidth(); x++)
		{
			for (int y = 0; y < pixmap.getHeight(); y++)
			{
				Color c = new Color(pixmap.getPixel(x, y));
				float total = c.r * c.g * c.b * c.a;
				if (total >= 0.99f)
				{
					pixmap.drawPixel(x, y);
				}
			}
		}
		t = new Texture(pixmap);
		footmanSpriteRed = new Sprite(t);
		
		TextureRegion[][] tmp = TextureRegion.split(t, 
				t.getWidth() / FRAME_COLS,
				t.getHeight() / FRAME_ROWS);
		int index = 0; 
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}



		
	}
	public void nextVertice(RTSMap map)
	{
		if (goingRight)
		{
			nextVerticeIndex++;
			if (nextVerticeIndex >= map.lanes[lane].vertices.length)
			{
				int count = map.lanes[lane].vertices.length;
				nextVertice = map.lanes[lane].vertices[count-1];
			}	
			else
			{
				nextVertice = map.lanes[lane].vertices[nextVerticeIndex];
			}
		}
		else
		{
			nextVerticeIndex--;
			
			if (nextVerticeIndex < 0)
			{
				nextVertice = map.lanes[lane].vertices[0];
			}	
			else
			{
				nextVertice = map.lanes[lane].vertices[nextVerticeIndex];

			}

		}
	}
	public void fixVerts(RTSMap map)
	{
		if (nextVerticeIndex >= map.lanes[lane].vertices.length)
		{
			nextVerticeIndex = map.lanes[lane].vertices.length-1;
			nextVerticeIndex--;
		}
		if (nextVerticeIndex < 0)
		{
			nextVerticeIndex = 0;
			nextVerticeIndex++;
		}
		nextVertice = map.lanes[lane].vertices[nextVerticeIndex];

	}
	public Unit closestUnit(List<Unit> unitsInRange)
	{
		if (unitsInRange.size() == 0)
		{
			return null;
		}
		Unit closestUnit = unitsInRange.get(0);
		float closestDistance = Float.MAX_VALUE;
		for (Unit u : unitsInRange)
		{
			Point2 p = new Point2(u.position);
			p.sub(position);
			if (p.magnitude() < closestDistance)
			{
				closestDistance = p.magnitude();
				closestUnit = u;
			}
		}
		
		return closestUnit;
	}
	public void setUnitColor(Sprite sp)
	{
		sprite = sp;
	}
	public Unit checkInRange(RTSMap map)
	{
		Unit uR = new Unit();
		List<Unit> allUnitsInRange = new ArrayList();
		for (Unit u : map.units)
		{
			if (u.parent != this.parent)
			{
				Point2 p = new Point2(u.position);
				p.sub(position);
				if (p.magnitude() < range)
				{
					allUnitsInRange.add(u);
				}
			}
		}
		
		return closestUnit(allUnitsInRange);
	}
	public void attack(Unit u)
	{
		if (attackTimer > attackCD)
		{
			u.health -= damage;
			attackTimer = 0;
		}
	}
	public boolean checkIfUnitIsDead()
	{
		if (health <= 0)
		{
			return true;
			
		}
		 return false;
	}
	public void update()
	{
		attackTimer += Gdx.graphics.getDeltaTime();
	}
	public void move(RTSMap map)
	{
		Point2 movement = new Point2(nextVertice);
		movement.sub(position);
		movement.div(movement.magnitude());
		movement.mul(Gdx.graphics.getDeltaTime());

		Point2 distance = new Point2(nextVertice);
		distance.sub(position);
		
		boolean addMovement = true;
		Unit u = checkInRange(map);
		if (u != null)
		{
			addMovement = false;
			attack(u);
		}
		Point2 distCheck = new Point2(movement);
		distCheck.mul(speed);
		float check = (distance.magnitude() - distCheck.x) + (distance.magnitude() - distCheck.y);
		if (check <= speed)
		{
			if (nextVerticeIndex < map.lanes[lane].vertices.length && nextVerticeIndex > 0)
			{			
				nextVertice(map);

			}
			else 
			{
				addMovement = false; 
			}

		}
		if (addMovement)
		{		
			movement.mul(speed);

			position.add(movement);
		}
	}
	public void setNextPosition(int verticeIndex)
	{
		nextVerticeIndex = verticeIndex; 
	}
	public static Unit createFootman(Point2 startPos, boolean isGoingRight, RTSPlayer parent)
	{
		Unit u = new Unit();
		u.position = new Point2(startPos);
		u.sprite = footmanSpriteBlue;
		u.goingRight = isGoingRight;
		u.parent = parent;
		u.goldCost = 5;
		Random r = new Random();
		u.randomSpriteOffset.x = r.nextFloat() * spriteOffsetMax;
		u.randomSpriteOffset.y = r.nextFloat() * spriteOffsetMax; 
		u.range = 15;
		if (!isGoingRight)
		{
			u.nextVerticeIndex = Integer.MAX_VALUE - 1;
		}

		return u;
	}
	public static Unit createArcher(Point2 startPos, boolean isGoingRight,RTSPlayer parent)
	{
		Unit u = new Unit();
		u.position = new Point2(startPos);
		u.range = 30;
		u.sprite = footmanSpriteBlue;
		u.goingRight = isGoingRight;
		u.parent = parent; 
		u.goldCost = 10;
		if (!isGoingRight)
		{
			u.nextVerticeIndex = Integer.MAX_VALUE - 1;
		}
		
		return u;

	}
	public void flipVerts()
	{
		
	}
	public Unit()
	{
		
	}
}
