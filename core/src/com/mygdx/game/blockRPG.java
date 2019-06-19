package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class blockRPG extends ApplicationAdapter {
	
	SpriteBatch batch;
    private BitmapFont font;
    Boolean end = false;
    
    
    
    private Texture texture;
    
    private Pixmap tileBorderTex;
    private Sprite tileBorderSprite;
    
    private Pixmap pixmap;
    private Sprite blueTile;
    
    Tetromino[] bag = new Tetromino[14]; 
    int bagIndex =0 ;
    
    public Tile[][] tileBlocks; 
    
    static Tetromino zPiece;
    
    public float keyPressAccumulator =0f;
    public float keyPressRepeatRate = 0.2f; //key will repeat after .1s
	Boolean movementKeyIsPressed = false;
	Boolean goingRight = false;
	Boolean goingLeft = false;
	public float moveHoriz;
	public float movementAccumulator = 0f;
	
    public static int tileSize = 16;
    public int boardStartPositionX = 100;
    public int boardStartPositionY = 100;
    
    public int boardSizeX = 10;
    public int boardSizeY = 20;
    
    public float lockTimer = 0;
    public float lockTime = 4;
   
    Tetromino holdPiece = null; 
    Tetromino activePiece; 
    Tetromino ghostPiece;
    
    public float gravity = 1;
    
    public float gravityStandard = 1;
    public float gravityFastSubtract = .95f;
    
    RTSMap rts;
    RTSPlayer player = new RTSPlayer();
    RTSPlayer opponent = new RTSPlayer();
    
    boolean isCreatingUnit = false; 
    boolean isOnUnitMenu = false;
    Unit unitToCreate;
    
    AssetManager manager;
    
    float accumulator = 0f;
    
    AI ai;
    
    BitmapFont roboto;
	public void createRTS()
	{
		rts = new RTSMap(325,100,250,250);
		Point2 p1 = new Point2(5,5); 
		Point2 p2 = new Point2(95,95); 
		Point2 p3 = new Point2();
		Point2[] middleLane = new Point2[] {p1,p2};
		
		
		p1 = new Point2(5,5);
		p2 = new Point2(5,95);
		p3 = new Point2(95,95);
		Point2[] leftLane = new Point2[] {p1,p2,p3};
		
		p1 = new Point2(5,5);
		p2 = new Point2(95,5);
		p3 = new Point2(95,95);
		Point2[] rightLane = new Point2[] {p1,p2,p3};

		Lane l = new Lane(leftLane);
		Lane l2 = new Lane(middleLane);
		Lane l3 = new Lane(rightLane);
		
		rts.playerBase = new Base(5,5);
		rts.opponentBase = new Base(95,95);
		
		player.base = rts.playerBase;
		opponent.base = rts.opponentBase;
		
        ai = new AI(rts,opponent);

		
		rts.lanes = new Lane[] {l,l2,l3};
	}
    
    @Override
	public void create () {
    	unitToCreate = new Unit();
    	
		/*manager = new AssetManager();
		manager.load("triangle.png", Texture.class);
		Texture footman = null;
		System.out.println(Gdx.files.internal("").path());

		while (!manager.update())
		{		
		}
		 footman = (Texture)manager.get(Gdx.files.internal("triangle.png").path(), Texture.class);
		manager.finishLoading();
		
		Unit.footmanSpriteBlue = new Sprite(footman);
		
		 */

    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 12;
    	roboto = generator.generateFont(parameter); // font size 12 pixels
    	generator.dispose();
    	
		Gdx.graphics.setTitle("blocks");
		
        tileBorderTex = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
        
        tileBorderTex.setColor(Color.WHITE);
        tileBorderTex.fill();
        tileBorderTex.setBlending(Pixmap.Blending.None);
        tileBorderTex.setColor(0,0,0,0);
        tileBorderTex.fillRectangle(1, 1, tileSize-2, tileSize-2);
        
        
        texture = new Texture(tileBorderTex);
        tileBorderTex.dispose();
        tileBorderSprite = new Sprite(texture);
		
        
        pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();
        Color c = new Color();
        c.b = 1;
        c.r = .5f;
        c.g = .5f;
        c.a = 1;
        pixmap.setColor(c);
        pixmap.drawRectangle(0, 0, tileSize, tileSize);
        
        texture = new Texture(pixmap);
        pixmap.dispose();
        blueTile = new Sprite(texture);
        
        Tetromino[] bagOne = Tetromino.genRandomBag(boardSizeX, boardSizeY);
        Tetromino[] bagTwo = Tetromino.genRandomBag(boardSizeX, boardSizeY);
        for (int i = 0; i < 7; i++)
        {
        	bag[i] = bagOne[i];
        	bag[i+7] = bagTwo[i];
        }
        
		batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        
        //initialise array 
        tileBlocks = new Tile[boardSizeX][boardSizeY];
        for (int x = 0; x < boardSizeX; x++)
        {
        	for (int y = 0; y < boardSizeY; y++)
        	{
        		tileBlocks[x][y] = new Tile();
        		
        	}
        }
        
        activePiece = bag[0];
        ghostPiece = new Tetromino();
        createRTS();
        loop();
	}
	public void nextTetromino()
	{
		bagIndex++;
		if (bagIndex == 6)
		{
			bagIndex = 0; 
			Tetromino[] newBag = Tetromino.genRandomBag(boardSizeX, boardSizeY);

			for (int i = 0; i < 7; i++)
			{
				bag[i] = bag[i+6];
				bag[i+6] = newBag[i];
			}
			
		}
		activePiece = bag[bagIndex];
	}
	public void loop() {
	}
	public Unit createUnit(int unit)
	{
		Unit u = new Unit();
		switch (unit) 
		{
		case 0:
			u = Unit.createFootman(rts.playerBase.position,true,player);
			break;
		case 1:
			u = Unit.createArcher(rts.playerBase.position,true,player);
			break;
		}
		return u;
	}
	public void input() {
		 boolean instantMove = false;
		 int moveHoriz = 0;
		
		 if (keyPressAccumulator == 0)
		 {
			 instantMove = true;
		 }
		 if (Gdx.input.isKeyPressed(Keys.G))
		 {
			 player.gold += 999;
		 }
		 if (Gdx.input.isButtonPressed(Keys.B))
		 {
			 isOnUnitMenu = true;
		 }	
		 if (Gdx.input.isKeyJustPressed(Keys.Q))
		{
			 nextTetromino();
		}
		 if (Gdx.input.isKeyJustPressed(Keys.NUM_1))
		{				 
			 boolean openMenu = true; 
			 if (isCreatingUnit == true)
			 {

				 if (unitToCreate != null)
				 {
					 if (player.gold >= unitToCreate.goldCost)
					 {
						 unitToCreate.lane = 0;
						 unitToCreate.nextVertice = rts.lanes[0].vertices[1];
						 rts.units.add(unitToCreate);
						 player.gold -= unitToCreate.goldCost;

						 unitToCreate = null;
						 isOnUnitMenu = false;
						 openMenu = false;
					 }
					}
			 }
			 if (openMenu)
			 {
				 isCreatingUnit = true;
				 unitToCreate = createUnit(0);
				 System.out.println("0)");
			 }
		}
		 
		 if (Gdx.input.isKeyJustPressed(Keys.NUM_2))
		{			
			 boolean openMenu = true; 

			 if (isCreatingUnit == true)
			 {
				 if (unitToCreate != null)
				 {
					 if (player.gold >= unitToCreate.goldCost)
					 {
						 unitToCreate.lane = 1;
						 unitToCreate.nextVertice = rts.lanes[1].vertices[1];
						 rts.units.add(unitToCreate);
						 player.gold -= unitToCreate.goldCost;
	
						 unitToCreate = null;
						 isOnUnitMenu = false;
						 openMenu = false;
					 }
				 }
			 }
		 if (openMenu)
		 {
			 isCreatingUnit = true;
			 unitToCreate = createUnit(1);
		 }

		}

		 if (Gdx.input.isKeyJustPressed(Keys.NUM_3))
		{
			 boolean openMenu = true; 

			 if (isCreatingUnit == true)
			 {
				 if (unitToCreate != null)
				 {
					 if (player.gold >= unitToCreate.goldCost)
					 {
						 unitToCreate.lane = 2;
						 unitToCreate.nextVertice = rts.lanes[2].vertices[1];
						 rts.units.add(unitToCreate);
						 player.gold -= unitToCreate.goldCost;
	
						 unitToCreate = null;
						 isOnUnitMenu = false;
						 openMenu = false;
					 }
				 }
			 }
		 if (openMenu)
		 {
			 isCreatingUnit = true;
			 unitToCreate = createUnit(1);
		 }

		}

		 if (Gdx.input.isKeyJustPressed(Keys.Z))
		 {
			 activePiece.rotateAntiClockwise(boardSizeX,boardSizeY,tileBlocks);
		 }
		 if (Gdx.input.isKeyJustPressed(Keys.X))
		 {
			 activePiece.rotateClockwise(boardSizeX,boardSizeY,tileBlocks);
		 }
		 if (Gdx.input.isKeyJustPressed(Keys.SPACE))
		 {

			 while (activePiece.y >= 0)
			 {			
				 activePiece.y--;

				 if (checkCollision(activePiece))
				 {
					 activePiece.y++;
					 break;
				 }			

			}
			 
			lockTimer = 0;

			 lockPiece();
			 checkLines();
			 nextTetromino();
			 }
		 if (Gdx.input.isKeyJustPressed(Keys.C))
		 {
			 holdPiece();
		 }
		 if (Gdx.input.isKeyJustPressed(Keys.LEFT))
		 {
				moveHoriz -= 1;
				activePiece.x += moveHoriz;
				Boolean coll = checkCollision(activePiece);
				if (coll)
				{
					activePiece.x -= moveHoriz;
				}

		 }
		 if (Gdx.input.isKeyJustPressed(Keys.RIGHT))
		 {
				moveHoriz += 1;
				activePiece.x += moveHoriz;
				Boolean coll = checkCollision(activePiece);
				if (coll)
				{
					activePiece.x -= moveHoriz;
				}
		 }

		if (Gdx.input.isKeyPressed(Keys.RIGHT))
		{				
			keyPressAccumulator += Gdx.graphics.getDeltaTime();
			moveHoriz += 1;
		}
		else if (Gdx.input.isKeyPressed(Keys.LEFT))
		{		
			keyPressAccumulator += Gdx.graphics.getDeltaTime();
			moveHoriz -= 1;
		}
		else
		{
			moveHoriz = 0;
			keyPressAccumulator = 0;
		}

		if (keyPressAccumulator >= keyPressRepeatRate)
		{
			boolean isMoveable = true;
			for (int x = 0; x < activePiece.getCurrentConfiguration()[0].length; x++)
			{
				for (int y = 0; y < activePiece.getCurrentConfiguration().length; y++)
				{
					if (activePiece.getCurrentConfiguration()[y][x] == true)
					{						

						if (moveHoriz == 1)
						{					
							if (x + activePiece.x >= boardSizeX - 1)
							{
								isMoveable = false;
							}

						}
						if (moveHoriz == -1)
						{
							if (x + activePiece.x <= 0)
							{
								isMoveable = false;
							}

						}
					}
				}
			}
		//	isMoveable = checkCollision(activePiece);
			if (isMoveable)
			{
				activePiece.x += moveHoriz;
				Boolean coll = checkCollision(activePiece);
				if (coll)
				{
					activePiece.x -= moveHoriz;
				}

			}
			
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN))
		{
			gravity = gravityStandard - gravityFastSubtract;
			
		}
		else
		{
			gravity = gravityStandard;
		}
	}
	public int getOffset()
	{
		 int highestPoint=9999;
		 
			for (int y = 0; y < activePiece.getCurrentConfiguration().length; y++)
			{
				for (int x = 0; x < activePiece.getCurrentConfiguration().length; x++)
				{
					if (activePiece.getCurrentConfiguration()[y][x] == true)
					{
						if (y + activePiece.y > 0)
						{
							if (tileBlocks[x+activePiece.x][(y+activePiece.y)].tileExists)
							{
								return y; 
						
								
							}
						}
						else
						{
							return y;
						}
						
					}
				}
			}
			return 0;
	}
	public void holdPiece()
	{
		activePiece = activePiece.regeneratePiece(boardSizeX, boardSizeY);
		Tetromino temp = activePiece;
		if (holdPiece != null)
		{
			activePiece = holdPiece; 
			holdPiece = temp; 
		}
		else {
			holdPiece = activePiece; 
			nextTetromino();
			
		}
		
	}
	public void lockPiece()
	{
		for (int x = 0; x < activePiece.getCurrentConfiguration()[0].length; x++)
		{
			for (int y = 0; y < activePiece.getCurrentConfiguration().length; y++)
			{
				if (activePiece.getCurrentConfiguration()[y][x] == true)
				{
					if (x+activePiece.x >= 0 && y+ activePiece.y >= 0)
					{
						tileBlocks[x+activePiece.x][y+activePiece.y].tileExists = true;
						tileBlocks[x+activePiece.x][y+activePiece.y].sprite = activePiece.sprite;
					}					
				}			
			}
		}

	}
	public Boolean checkCollision(Tetromino t)
	{
		Boolean hasCollided = false;
		for (int x = 0; x < t.getCurrentConfiguration()[0].length; x++)
		{
			for (int y = 0; y < t.getCurrentConfiguration().length; y++)
			{
				if (t.getCurrentConfiguration()[y][x] == true)
				{
					if (x + activePiece.x >= boardSizeX )
					{
						return true;
					}
					if (x + activePiece.x < 0)
					{
						return true;
					}
					if (y + t.y >= 0)
					{
						if (tileBlocks[x+t.x][(y+t.y)].tileExists)
						{
							return  true;
						}
					}
					else
					{
						return  true;
					}
				}
			}
		}
		return hasCollided;

	}
	public void checkLines()
	{
		int totalLinesCleared = 0; 
		Tile[][] tileBlocksCpy = tileBlocks.clone();

		for (int i = 0; i < 4; i++)
		{
			for (int y = 0; y < tileBlocks[0].length; y++)
			{
				int counter = 0;
				for (int x = 0; x <tileBlocks.length; x++)
				{
					if (tileBlocks[x][y].tileExists)
					{
						counter++;
					}
				}
				if (counter == tileBlocks.length)
				{
					totalLinesCleared++;
					for (int x = 0; x < tileBlocks.length; x++)
					{
						tileBlocksCpy[x][y].tileExists = false;
						for (int y2 = y; y2 < tileBlocks[0].length; y2++)
						{
								if (tileBlocks[x][y2].tileExists)
								{
									tileBlocksCpy[x][y2].tileExists = false;
									if (y2-1 >= 0)
									{
										tileBlocksCpy[x][y2-1].tileExists = true;
										tileBlocksCpy[x][y2-1].sprite = tileBlocksCpy[x][y2].sprite;
									}
								}
						}
						
					}
				}
			}
		}
			player.addGold(totalLinesCleared, activePiece);
			if (totalLinesCleared > 0)
			{
				player.comboAdd();
			}
			else if (totalLinesCleared ==0)
			{
				player.resetCombo();
			}

		tileBlocks = tileBlocksCpy;
	}
	public void moveGhostPiece()
	{
		ghostPiece.configList = activePiece.configList;
		ghostPiece.currentConfigIndex = activePiece.currentConfigIndex;
		ghostPiece.sprite = activePiece.sprite;
		ghostPiece.x = activePiece.x;
		ghostPiece.y = activePiece.y;
		
		if (activePiece.type == "I")
		{
			ghostPiece.sprite = Tetromino.ghostTeal;
		}
		if (activePiece.type == "J")
		{
			ghostPiece.sprite = Tetromino.ghostBlue;
		}
		if (activePiece.type == "L")
		{
			ghostPiece.sprite = Tetromino.ghostOrange;
		}
		if (activePiece.type == "O")
		{
			ghostPiece.sprite = Tetromino.ghostYellow;
		}
		if (activePiece.type == "S")
		{
			ghostPiece.sprite = Tetromino.ghostGreen;
		}
		if (activePiece.type == "T")
		{
			ghostPiece.sprite = Tetromino.ghostPurple;
		}
		if (activePiece.type == "Z")
		{
			ghostPiece.sprite = Tetromino.ghostRed;
		}
		
		 while (ghostPiece.y >= 0)
		 {			
			 ghostPiece.y--;

			 if (checkCollision(ghostPiece))
			 {
				 ghostPiece.y++;
				 break;
			 }		
		 }

		
		
		
	}
	
	public void checkForTimer()
	{			
		activePiece.y -= 1; 
		Boolean lockPiece = false; 

		for (int x = 0; x < activePiece.getCurrentConfiguration()[0].length; x++)
		{
			for (int y = 0; y < activePiece.getCurrentConfiguration().length; y++)
			{
				if (activePiece.getCurrentConfiguration()[y][x] == true)
				{
					if (y + activePiece.y >= 0)
					{
						if (tileBlocks[x+activePiece.x][(y+activePiece.y)].tileExists)
						{

							lockTimer += Gdx.graphics.getDeltaTime();
							lockPiece = true;
						}
					}
					else
					{
						lockTimer += Gdx.graphics.getDeltaTime();

						lockPiece = true;
					}
				}
			}
		}
		activePiece.y++;

	}
	public void moveUnits()
	{
		for (Unit u : rts.units)
		{
			u.move(rts);
		}
	}
	public void updateUnits()
	{
		for (Unit u : rts.units) {
			u.update();
		}
	}
	public void checkUnitsDead()
	{
		for (int i = 0; i < rts.units.size(); i++)
		{
			if (rts.units.get(i).checkIfUnitIsDead())
			{
				rts.units.remove(i);
				i--;
			}
		}
	}
	public void RTSUpdate()
	{
		updateUnits();
		moveUnits();
		ai.update();
		checkUnitsDead();
		
	}
	public void update () {
		
		RTSUpdate();
		input();
		accumulator += Gdx.graphics.getDeltaTime();
		checkForTimer();
		if (accumulator >= gravity)
		{
			activePiece.y -= 1; 
			Boolean lockPiece = false; 
			
			for (int x = 0; x < activePiece.getCurrentConfiguration()[0].length; x++)
			{
				for (int y = 0; y < activePiece.getCurrentConfiguration().length; y++)
				{
					if (activePiece.getCurrentConfiguration()[y][x] == true)
					{
						if (y + activePiece.y >= 0)
						{
							if (tileBlocks[x+activePiece.x][(y+activePiece.y)].tileExists)
							{

								lockPiece = true;
							}
						}
						else
						{

							lockPiece = true;
						}
					}
				}
			}
			accumulator=0;
			if (lockPiece)
			{
				activePiece.y += 1;
			}
			if (lockPiece && lockTimer > lockTime)
			{			
				lockTimer = 0;

				lockPiece();
				checkLines();
		        nextTetromino();
			}
		}
		moveGhostPiece();
	}
	@Override
	public void render () {
		update();

        Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();        

		//batch.draw(sprite, 0, 0)
		drawBorders();
		drawPieces();
		drawNextPieces();
    	drawUnits();
    	CharSequence str = Integer.toString(player.gold);
    	roboto.setColor(Color.GOLD);
    	roboto.draw(batch, str, 350, 400);
    	batch.end();
		
		batch.begin();
	
	    	drawRTSMap();
	    	
        batch.end();

	}
	public void drawUnits()
	{		

		for (Unit u : rts.units)
		{

			Point2 position = new Point2();
			position.x = rts.rectStart.x + (u.position.x / 100) * rts.size.x;
			position.x += u.randomSpriteOffset.x;
			position.y = rts.rectStart.y + (u.position.y / 100) * rts.size.y;
			position.y += u.randomSpriteOffset.y;
		//	batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
			batch.draw(u.sprite, position.x, position.y,
					position.x + u.sprite.getWidth()/2,
					position.y + u.sprite.getHeight()/2,
					u.sprite.getWidth(),
					u.sprite.getHeight(), 1, 1, u.rotation);
			batch.draw(u.sprite,position.x,position.y);

		}
	}
	public void drawRTSMap()
	{
		
		ShapeRenderer sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		
		sr.begin();
		sr.line((float)rts.rectStart.x, (float)rts.rectStart.y, (float)rts.rectStart.x, rts.rectStart.y + rts.size.y);
		sr.line((float)rts.rectStart.x, (float)rts.rectStart.y, (float)rts.rectStart.x + rts.size.x, rts.rectStart.y);
		sr.line((float)rts.rectStart.x, (float)rts.rectStart.y + rts.size.y, (float)rts.rectStart.x + rts.size.x, rts.rectStart.y + rts.size.y);
		sr.line((float)rts.rectStart.x + rts.size.x, (float)rts.rectStart.y, (float)rts.rectStart.x + rts.size.x, rts.rectStart.y + rts.size.y);
		
		//draw bases
		sr.circle(rts.rectStart.x + (((float)rts.playerBase.position.x / 100) * rts.size.x), rts.rectStart.y + (((float)rts.playerBase.position.y / 100) * rts.size.y),5);
		sr.circle(rts.rectStart.x + (((float)rts.opponentBase.position.x / 100) * rts.size.x), rts.rectStart.y + (((float)rts.opponentBase.position.y / 100) * rts.size.y),5);

		//draw lanes
		for (Lane l : rts.lanes)
		{
			for (int i = 0; i < l.vertices.length - 1; i++)
			{
				sr.line(rts.rectStart.x + (((float)l.vertices[i].x / 100) * rts.size.x),rts.rectStart.y + (((float)l.vertices[i].y / 100) * rts.size.y),
						rts.rectStart.x + (((float)l.vertices[i+1].x / 100) * rts.size.x), rts.rectStart.y + (((float)l.vertices[i+1].y / 100) * rts.size.y));
			}
		}
		
		sr.end();
	}
	public void drawNextPieces()
	{
		int piecesToDraw = 3;
		int index = 0;
		for (int i = bagIndex + 1; i < bagIndex + piecesToDraw + 1; i++) {
			for (int x = 0; x < bag[i].getCurrentConfiguration()[0].length; x++) {
				for (int y = 0; y < bag[i].getCurrentConfiguration().length; y++) {
					if (bag[i].getCurrentConfiguration()[y][x] == true) {
						float startPosX = boardStartPositionX + (tileSize * tileBlocks.length);
						float startPosY = boardStartPositionY + (tileSize * tileBlocks[0].length);
						
						float xPos =  startPosX + (x * tileSize); 
						float yPos = startPosY + (index * -70) + (y * tileSize); 
						batch.draw(bag[i].sprite, xPos, yPos);
					}
				}
			}
			index++;

		}
		//draw hold pieces
		if (holdPiece != null)
		{
			float startPosX = boardStartPositionX - 30;
			float startPosY = boardStartPositionY + (tileSize * tileBlocks[0].length);
			for (int x = 0; x < holdPiece.getCurrentConfiguration()[0].length; x++) {
				for (int y = 0; y < holdPiece.getCurrentConfiguration().length; y++) {
					if (holdPiece.getCurrentConfiguration()[y][x] == true) {

						float xPos =  startPosX + (x * tileSize); 
						float yPos = startPosY +  + (y * tileSize); 
						batch.draw(holdPiece.sprite, xPos, yPos);
					}
				}
		}
		}
	}
	public void drawPieces() {
		for (int x = 0; x < tileBlocks.length; x++)
		{
			for (int y = 0; y < tileBlocks[0].length; y++)
			{
					if (tileBlocks[x][y].tileExists == true)
					{
						if (tileBlocks[x][y].sprite != null)
						{
							float xPos = boardStartPositionX + (x * tileSize);
							float yPos = boardStartPositionY + (y * tileSize);
							batch.draw(tileBlocks[x][y].sprite,xPos,yPos);
						}
					}
			}
		}
		//draw active piece
		for (int y = 0; y < activePiece.getCurrentConfiguration().length; y++)
		{
			for (int x = 0; x < activePiece.getCurrentConfiguration()[0].length; x++)
			{
				if (activePiece.getCurrentConfiguration()[y][x] == true)
				{
					float xPos = boardStartPositionX + ((activePiece.x + x) * tileSize);
					float yPos = boardStartPositionY + ((activePiece.y + y)* tileSize);

					batch.draw(activePiece.sprite,xPos,yPos);
					
					float xPosGhostPiece = boardStartPositionX + ((ghostPiece.x + x) * tileSize);
					float yPosGhostPiece = boardStartPositionY + ((ghostPiece.y + y)* tileSize);
					batch.draw(ghostPiece.sprite,xPosGhostPiece,yPosGhostPiece);
				}
			}
		}

		
	}
	public void drawBorders()
	{
		batch.draw(tileBorderSprite,5,5);
		for (int x = boardStartPositionX; x < boardStartPositionX + ( boardSizeX*tileSize); x += tileSize)
		{
			for (int y = boardStartPositionY; y < boardStartPositionY + (boardSizeY * tileSize); y += tileSize)
			{
				batch.draw(tileBorderSprite,x,y);
			}
		}
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}
