package com.mygdx.game;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tetromino {
	public int currentConfigIndex = 0;
	
	public Boolean[][][] configList;
	public int x; public int y;
	
	public Sprite sprite; 
	
	static Sprite blue;
	static Sprite teal;
	static Sprite orange;
	static Sprite yellow;
	static Sprite green;
	static Sprite purple;
	static Sprite red;
	
	static Sprite ghostBlue;
	static Sprite ghostTeal;
	static Sprite ghostOrange;
	static Sprite ghostYellow;
	static Sprite ghostGreen;
	static Sprite ghostPurple;
	static Sprite ghostRed;
	
	static float alpha = 0.45f;
	
	public String type = "";
	
	static {
		int tileSize = blockRPG.tileSize;
        Pixmap pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();
        Color c = new Color();
        c.b = 1;
        c.r = .5f;
        c.g = .5f;
        c.a = 1;
        pixmap.setColor(c);
        pixmap.drawRectangle(0, 0, tileSize, tileSize);
        
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        blue = new Sprite(texture);
        
         pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.TEAL);
        pixmap.fill();
        c = new Color();
        c.b = 1;
        c.r = .5f;
        c.g = .5f;
        c.a = 1;
        pixmap.setColor(c);
        pixmap.drawRectangle(0, 0, tileSize, tileSize);
        
         texture = new Texture(pixmap);
        pixmap.dispose();
        teal = new Sprite(texture);

        pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
       pixmap.setColor(Color.ORANGE);
       pixmap.fill();
       c = new Color();
       c.b = 1;
       c.r = .5f;
       c.g = .5f;
       c.a = 1;
       pixmap.setColor(c);
       pixmap.drawRectangle(0, 0, tileSize, tileSize);
       
        texture = new Texture(pixmap);
       pixmap.dispose();
       orange = new Sprite(texture);
       
       pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
      pixmap.setColor(Color.YELLOW);
      pixmap.fill();
      c = new Color();
      c.b = 1;
      c.r = .5f;
      c.g = .5f;
      c.a = 1;
      pixmap.setColor(c);
      pixmap.drawRectangle(0, 0, tileSize, tileSize);
      
       texture = new Texture(pixmap);
      pixmap.dispose();
      yellow = new Sprite(texture);
      
      pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
     pixmap.setColor(Color.GREEN);
     pixmap.fill();
     c = new Color();
     c.b = 1;
     c.r = .5f;
     c.g = .5f;
     c.a = 1;
     pixmap.setColor(c);
     pixmap.drawRectangle(0, 0, tileSize, tileSize);
     
      texture = new Texture(pixmap);
     pixmap.dispose();
     green = new Sprite(texture);

     pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.PURPLE);
    pixmap.fill();
    c = new Color();
    c.b = 1;
    c.r = .5f;
    c.g = .5f;
    c.a = 1;
    pixmap.setColor(c);
    pixmap.drawRectangle(0, 0, tileSize, tileSize);
    
     texture = new Texture(pixmap); 
    pixmap.dispose();
    purple = new Sprite(texture);

    pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
   pixmap.setColor(Color.RED);
   pixmap.fill();
   c = new Color();
   c.b = 1;
   c.r = .5f;
   c.g = .5f;
   c.a = 1;
   pixmap.setColor(c);
   pixmap.drawRectangle(0, 0, tileSize, tileSize);
   
    texture = new Texture(pixmap);
   pixmap.dispose();
   red = new Sprite(texture);

	}
	static {
		int tileSize = blockRPG.tileSize;
        Pixmap pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
        Color col = Color.BLUE;
        col.a = alpha;
        pixmap.setColor(col);
        pixmap.fill();
        Color c = new Color();
        c.b = 1;
        c.r = .5f;
        c.g = .5f;
        c.a = alpha;
        pixmap.setColor(c);
        pixmap.drawRectangle(0, 0, tileSize, tileSize);
        
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        ghostBlue = new Sprite(texture);
        
         pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
         col = Color.TEAL;
         col.a = alpha;
        pixmap.setColor(col);
        pixmap.fill();
        c = new Color();
        c.b = 1;
        c.r = .5f;
        c.g = .5f;
        c.a = alpha;
        pixmap.setColor(c);
        pixmap.drawRectangle(0, 0, tileSize, tileSize);
        
         texture = new Texture(pixmap);
        pixmap.dispose();
        ghostTeal = new Sprite(texture);

        pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
        col = Color.ORANGE;
        col.a = alpha;
       pixmap.setColor(col);
       pixmap.fill();
       c = new Color();
       c.b = 1;
       c.r = .5f;
       c.g = .5f;
       c.a = alpha;
       pixmap.setColor(c);
       pixmap.drawRectangle(0, 0, tileSize, tileSize);
       
        texture = new Texture(pixmap);
       pixmap.dispose();
       ghostOrange = new Sprite(texture);
       
       pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
       col = Color.YELLOW;
       col.a = alpha;
      pixmap.setColor(col);
      pixmap.fill();
      c = new Color();
      c.b = 1;
      c.r = .5f;
      c.g = .5f;
      c.a = alpha;
      pixmap.setColor(c);
      pixmap.drawRectangle(0, 0, tileSize, tileSize);
      
       texture = new Texture(pixmap);
      pixmap.dispose();
      ghostYellow = new Sprite(texture);
      
      pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
      col = Color.GREEN;
      col.a = alpha;
     pixmap.setColor(col);
     pixmap.fill();
     c = new Color();
     c.b = 1;
     c.r = .5f;
     c.g = .5f;
     c.a = alpha;
     pixmap.setColor(c);
     pixmap.drawRectangle(0, 0, tileSize, tileSize);
     
      texture = new Texture(pixmap);
     pixmap.dispose();
     ghostGreen = new Sprite(texture);

     pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
     col = Color.PURPLE;
     col.a = alpha;
    pixmap.setColor(col);
    pixmap.fill();
    c = new Color();
    c.b = 1;
    c.r = .5f;
    c.g = .5f;
    c.a = alpha;
    pixmap.setColor(c);
    pixmap.drawRectangle(0, 0, tileSize, tileSize);
    
     texture = new Texture(pixmap); 
    pixmap.dispose();
    ghostPurple = new Sprite(texture);

    pixmap = new Pixmap(tileSize,tileSize, Pixmap.Format.RGBA8888);
    col = Color.RED;
    col.a = alpha;
   pixmap.setColor(col);
   pixmap.fill();
   c = new Color();
   c.b = 1;
   c.r = .5f;
   c.g = .5f;
   c.a = alpha;
   pixmap.setColor(c);
   pixmap.drawRectangle(0, 0, tileSize, tileSize);
   
    texture = new Texture(pixmap);
   pixmap.dispose();
   ghostRed = new Sprite(texture);

		
	}

	public static Tetromino[] genRandomBag(int boardSizeX,int boardSizeY)
	{
		Tetromino[] bag = new Tetromino[7];
		bag[0] = Tetromino.createPiece_I(boardSizeX, boardSizeY);
		bag[1] = Tetromino.createPiece_J(boardSizeX, boardSizeY);
		bag[2] = Tetromino.createPiece_L(boardSizeX, boardSizeY);
		bag[3] = Tetromino.createPiece_O(boardSizeX, boardSizeY);
		bag[4] = Tetromino.createPiece_S(boardSizeX, boardSizeY);
		bag[5] = Tetromino.createPiece_T(boardSizeX, boardSizeY);
		bag[6] = Tetromino.createPiece_Z(boardSizeX, boardSizeY);
		
		//shuffle
		int currentIndex = bag.length;
		while (currentIndex != 0)
		{
			int randomIndex = (int)Math.floor(Math.random() * currentIndex);
			currentIndex--;
			
			Tetromino temp = bag[currentIndex];
			bag[currentIndex] = bag[randomIndex];
			bag[randomIndex] = temp;
		}
		
		return bag;
	}
	public Point2 getLeftMostSegmentIndex()
	{
		Point2 index = new Point2();
		index.x = 99999;
		for (int yConfig = 0; yConfig < configList.length; yConfig++)
		{
			for (int xConfig = 0; xConfig < configList[0].length; xConfig++)
			{
				if (getCurrentConfiguration()[yConfig][xConfig] ==  true)
				{
					if (xConfig < index.x) {
						index.x = xConfig;
						index.y = yConfig;;
					}
				}
			}
		}
		return index;
	}
	public void sideWallKick(int boardSizeX, int boardSizeY, Tile[][] tiles, boolean goingClockwise)
	{
		int offsetX = 0; 
		boolean exitLoop = false;
		for (int yConfig = 0; yConfig < configList.length; yConfig++)
		{
			for (int xConfig = 0; xConfig < configList[0].length; xConfig++)
			{
				if (getCurrentConfiguration()[yConfig][xConfig] == true)
				{
					if (x+xConfig > 0 && x+xConfig < tiles.length && y + yConfig > 0 && y + yConfig < tiles[0].length)
					{
						if (tiles[x+xConfig][y+yConfig].tileExists)
						{
							//y++;
							if (goingClockwise)
								rotateClockwise(boardSizeX, boardSizeY, tiles);
							else
								rotateAntiClockwise(boardSizeX, boardSizeY, tiles);

							yConfig=0;
							break;
						}
					}
					if (y + yConfig < 0) 
					{
						y++;
						yConfig=0;
						break;
					}
					if (y + 4 - yConfig >= boardSizeY)
					{
						y--; 
						yConfig=0;
						break;
					}
					if (x + xConfig < 0)
					{
						x++;
						yConfig=0;
						break;
					}
					if (x + xConfig >= boardSizeX)
					{	

						x--; 
						yConfig = 0; 
						break;
					}

				}
					
			}
		}
		x += offsetX;

	}
	
	public void rotateAntiClockwise(int boardSizeX, int boardSizeY, Tile[][] tiles)
	{		
		/*if (x < 0)
	{
		x=0;
	}
	if (x > boardSizeX)
	{
		x = boardSizeX;
	}*/

		currentConfigIndex--;
		if (currentConfigIndex < 0)
		{
			currentConfigIndex = configList.length - 1; 
		}
		sideWallKick(boardSizeX, boardSizeY,tiles,false);

	}
	public void rotateClockwise(int boardSizeX, int boardSizeY, Tile[][] tiles)
	{
		/*if (x < 0)
		{
			x=0;
		}
		if (x > boardSizeX)
		{
			x = boardSizeX;
		}*/
		currentConfigIndex++;
		if (currentConfigIndex >= configList.length)
		{
			currentConfigIndex = 0; 
		}
		sideWallKick(boardSizeX,boardSizeY,tiles,true);
	}
	public Boolean[][] getCurrentConfiguration()
	{
		
		Boolean[][] conf = configList[currentConfigIndex];
		
		return conf;
	}
	public Tetromino regeneratePiece(int boardSizeX, int boardSizeY)
	{
		Tetromino t = null; 
		if (type == "I")
		{
			t = Tetromino.createPiece_I(boardSizeX, boardSizeY);
		}
		if (type == "J")
		{
			t = Tetromino.createPiece_J(boardSizeX, boardSizeY);
		}
		if (type == "L")
		{
			t = Tetromino.createPiece_L(boardSizeX, boardSizeY);
		}
		if (type == "O")
		{
			t = Tetromino.createPiece_O(boardSizeX, boardSizeY);
		}
		if (type == "S")
		{
			t = Tetromino.createPiece_S(boardSizeX, boardSizeY);
		}
		if (type == "T")
		{
			t = Tetromino.createPiece_T(boardSizeX, boardSizeY);
		}
		if (type == "Z")
		{
			t = Tetromino.createPiece_Z(boardSizeX, boardSizeY);
		}
		return t; 
	}
	public static Tetromino random(int boardSizeX, int boardSizeY)
	{
		Random r = new Random();
		int rand = r.nextInt(6);
		switch (rand) {
			case 0:
				return Tetromino.createPiece_I(boardSizeX, boardSizeY);
			case 1:
				return Tetromino.createPiece_J(boardSizeX, boardSizeY);
			case 2: 
				return Tetromino.createPiece_L(boardSizeX, boardSizeY);
			case 3:
				return Tetromino.createPiece_O(boardSizeX, boardSizeY);
			case 4:
				return Tetromino.createPiece_S(boardSizeX, boardSizeY);
			case 5:
				return Tetromino.createPiece_T(boardSizeX, boardSizeY);
			case 6:
				return Tetromino.createPiece_Z(boardSizeX, boardSizeY);
		}
		return null;
	}
	static Tetromino createPiece_I(int boardSizeX, int boardSizeY)
	{
		Tetromino t = new Tetromino();
		Boolean[][][] arr = {
				{
				{false, false, false, false},
				{true, true, true, true},
				{false, false, false, false},
				{false, false, false, false},
				
				},
				{
					
				{false, false, true, false},
				{false, false, true, false},
				{false, false, true, false},
				{false, false, true, false},
					

				}
		};
		t.x = (boardSizeX/2) - 2;
		t.y = boardSizeY-2;
		t.configList = arr;
		t.sprite = Tetromino.teal;
		t.type = "I";
		return t;
	}
	static Tetromino createPiece_O(int boardSizeX, int boardSizeY)
	{
		Tetromino t = new Tetromino();
		Boolean[][][] arr = {
				{
				{false, false, false, false},
				{false, true, true, false},
				{false, true, true, false},
				{false, false, false, false},
				
				},
		};
		t.x = (boardSizeX/2) - 2;
		t.y = boardSizeY-3;
		
		t.configList = arr;
		t.sprite = Tetromino.yellow;
		t.type = "O";

		return t;

	}
	static Tetromino createPiece_T(int boardSizeX, int boardSizeY)
	{
		Tetromino t = new Tetromino();
		Boolean[][][] arr = {
				{
					{false, true, false, false},
					{true, true, true, false},
					{false, false, false, false},
					{false, false, false, false},
				},
				{
					{false, true, false, false},
					{true, true, false, false},
					{false, true, false , false},
					{false, false, false, false},
				},
				{
					{false, false, false, false},
					{true, true, true, false},
					{false, true, false, false},
					{false, false, false, false},
				},
				{
					{false, true, false, false},
					{false, true, true, false},
					{false, true, false, false},
					{false, false, false, false},
				},


		};
		t.x = (boardSizeX/2) - 1;
		t.y = boardSizeY-2;
		t.configList = arr;
		t.sprite = Tetromino.purple;
		t.type = "T";

		return t;

	}
	static Tetromino createPiece_J(int boardSizeX, int boardSizeY)
	{
		Tetromino t = new Tetromino();
		Boolean[][][] arr = {
				{
					{true, true, true, false},
					{true, false, false, false},
					{false, false, false, false},
					{false, false, false, false},
				},
				{
					{false, true, false, false},
					{false, true, false, false},
					{false, true, true, false},
					{false, false, false, false},
				},
				{
					{false, false, true, false},
					{true, true, true, false},
					{false, false, false, false},
					{false, false, false, false},
				},
				{
					{true, true, false, false},
					{false, true, false, false},
					{false, true, false, false},
					{false, false, false, false},
				}


		};
		t.x = (boardSizeX/2) - 1;
		t.y = boardSizeY-2;
		
		t.configList = arr;
		t.sprite = Tetromino.blue;
		t.type = "J";


		return t;

	}
	static Tetromino createPiece_L(int boardSizeX, int boardSizeY)
	{
		Tetromino t = new Tetromino();
		Boolean[][][] arr = {
				{
					{false, false, false, false},
					{true, true, true, false},
					{false, false, true, false},
					{false, false, false, false},
				},
				{
					{false, false, false,false },
					{false, true, true, false},
					{false, true, false, false},
					{false, true, false, false},
				},
				{
					{false, false, false, false},
					{true, false, false, false},
					{true, true, true, false},
					{false, false, false, false},
				},
				{
					{false, false, false, false},
					{false, true, false, false},
					{false, true, false, false},
					{true, true, false, false},
				}


		};
		t.x = boardSizeX/2 - 1;
		t.y = boardSizeY-3;
		
		t.configList = arr;
		t.sprite = Tetromino.orange;
		t.type = "L";

		
		return t;

	}
	static Tetromino createPiece_S(int boardSizeX, int boardSizeY)
	{
		Tetromino t = new Tetromino();
		Boolean[][][] arr = {
				{
					{false, true, true, false},
					{true, true, false, false},
					{false, false, false, false},
					{false, false, false, false},
				},
				{
					{false, true, false,false },
					{false, true, true, false},
					{false, false, true, false},
					{false, false, false, false},
				}

		};
		t.x = (boardSizeX/2) - 1;
		t.y = boardSizeY-2;
		
		t.configList = arr;
		t.sprite = Tetromino.green;
		t.type = "S";

		return t;

	}
	static Tetromino createPiece_Z(int boardSizeX, int boardSizeY)
	{
		Tetromino t = new Tetromino();
		Boolean[][][] arr = {
				{
					{true, true, false, false},
					{false, true, true, false},
					{false, false, false, false},
					{false, false, false, false },
				},
				{
					{false, true, false,false },
					{true, true, false, false},
					{true, false, false, false},
					{false, false, false, false},
				}

		};
		t.x = (boardSizeX/2) - 1;
		t.y = boardSizeY-2;
		
		t.configList = arr;
		t.sprite = Tetromino.red;
		t.type = "Z";

		return t;

	}
	
}
