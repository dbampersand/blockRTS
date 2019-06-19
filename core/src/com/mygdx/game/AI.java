package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public class AI {
	public RTSPlayer AIPlayer;
	Base base;
	RTSMap map; 
	float takeActionAccumulator = 0f;
	float takeActionAfter = 5f; 
	public AI(RTSMap Map, RTSPlayer player)
	{
		base = player.base;
		map = Map;
		AIPlayer = player;
	}
	
	public void update()
	{
		takeActionAccumulator += Gdx.graphics.getDeltaTime();
		if (takeActionAccumulator >= takeActionAfter)
		{
			Random r = new Random();
			createUnit(0,0);//r.nextInt(3));
			takeActionAccumulator = 0; 
		}
	}
	public void createUnit(int unitType, int lane)
	{
		Unit u = new Unit();
		switch (unitType) 
		{
		case 0:
			u = Unit.createFootman(base.position,false, AIPlayer);
			break;
		
		case 1:
			u = Unit.createArcher(base.position,false, AIPlayer);
			break;
		}
		u.lane = lane;
		u.parent = AIPlayer;
		u.fixVerts(map);
		u.setUnitColor(Unit.footmanSpriteRed);
		map.units.add(u);
	}
	
}
