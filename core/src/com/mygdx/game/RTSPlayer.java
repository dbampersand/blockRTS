package com.mygdx.game;

public class RTSPlayer {
	public int gold = 10;
	public Base base; 
	public int currentCombo = 1; 
	public void comboAdd()
	{
		currentCombo++;
	}
	public void resetCombo()
	{
		currentCombo = 1;
	}
	public void addGold(int linesCleared, Tetromino piece)
	{
		gold += linesCleared * currentCombo; 
	}
}
