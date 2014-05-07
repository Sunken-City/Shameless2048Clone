package pico.riley.shameless2048clone;

import java.util.Random;

import android.util.Log;

public class Game {
	private int[][] board;
	private Random rand;
	enum Direction {UP, DOWN, LEFT, RIGHT};
	
	public Game()
	{
		rand = new Random();
		board = new int[4][4];
		addTwo();
		Log.i("GAME", "Welcome to 2048!");
		Log.i("GAME", "----------------------------");
		print();
	}
	
	public int[][] getGrid()
	{
		return board;
	}
	
	public int getTile(int tileNumber)
	{
		int x = tileNumber % 4;
		int y = tileNumber / 4;
		return board[y][x];
	}
	
	public void print()
	{
		for(int[] y : board)
		{
			String s = "";
			for(int x : y)
			{
				s += "[" + x + "]";
			}
			Log.i("GAME", s);
		}	
		Log.i("GAME", "----------------------------");
	}
	
	public boolean makeMove(Direction dir)
	{
		shift(dir);
		addTwo();
		print();
		return true;
	}
	
	public void shift(Direction dir)
	{
		if (dir == Direction.LEFT)
		{
			for(int y = 0; y < 4; y++)
			{
				for(int x = 1; x < 4; x++)
				{
					int thisTile = board[y][x];
					int nextTile = board[y][x-1];
					if (thisTile != 0)
					{
						int currX = x;
						while (nextTile == 0)
						{
							board[y][currX] = 0; //Set this tile to 0
							currX--;
							board[y][currX] = thisTile; //Move the tile to the left one
							nextTile = (currX - 1 > -1) ? board[y][currX - 1] : -1; //Set the next tile to the next tile left
						}
						
						if (nextTile == thisTile)
						{
							board[y][currX - 1] = thisTile + nextTile;
							board[y][currX] = 0;
						}
					}
				}
			}
		}
		else if (dir == Direction.RIGHT)
		{
			for(int y = 0; y < 4; y++)
			{
				for(int x = 2; x > -1; x--)
				{
					int thisTile = board[y][x];
					int nextTile = board[y][x+1];
					if (thisTile != 0)
					{
						int currX = x;
						while (nextTile == 0 && currX < 4)
						{
							board[y][currX] = 0; //Set this tile to 0
							currX++;
							board[y][currX] = thisTile; //Move the tile to the right one
							nextTile = (currX + 1 < 4) ? board[y][currX + 1] : -1; //Set the next tile to the next tile right
						}
						
						if (nextTile == thisTile)
						{
							board[y][currX + 1] = thisTile + nextTile;
							board[y][currX] = 0;
						}
					}
				}
			}
		}
		else if (dir == Direction.UP)
		{
			for(int y = 1; y < 4; y++)
			{
				for(int x = 0; x < 4; x++)
				{
					int thisTile = board[y][x];
					int nextTile = board[y-1][x];
					if (thisTile != 0)
					{
						int currY = y;
						while (nextTile == 0)
						{
							board[currY][x] = 0; //Set this tile to 0
							currY--;
							board[currY][x] = thisTile; //Move the tile to the upper one
							nextTile = (currY - 1 > -1) ? board[currY - 1][x] : -1; //Set the next tile to the next tile up
						}
						
						if (nextTile == thisTile)
						{
							board[currY - 1][x] = thisTile + nextTile;
							board[currY][x] = 0;
						}
					}
				}
			}
		}
		else if (dir == Direction.DOWN)
		{
			for(int y = 2; y > -1; y--)
			{
				for(int x = 0; x < 4; x++)
				{
					int thisTile = board[y][x];
					int nextTile = board[y+1][x];
					if (thisTile != 0)
					{
						int currY = y;
						while (nextTile == 0)
						{
							board[currY][x] = 0; //Set this tile to 0
							currY++;
							board[currY][x] = thisTile; //Move the tile to the upper one
							nextTile = (currY + 1 < 4) ? board[currY + 1][x] : -1; //Set the next tile to the next tile up
						}
						
						if (nextTile == thisTile)
						{
							board[currY + 1][x] = thisTile + nextTile;
							board[currY][x] = 0;
						}
					}
				}
			}
		}
	}
	
	public boolean addTwo()
	{
		if (isNotFull())
		{
			int x = rand.nextInt(4);
			int y = rand.nextInt(4);
			while(board[y][x] != 0)
			{
				x = rand.nextInt(4);
				y = rand.nextInt(4);
			}
			board[y][x] = 2;
			return true; //Placed a 2
		}
		return false; //Couldn't place a 2, game is over.
	}
	
	public boolean isNotFull()
	{
		for(int[] y : board)
		{
			for(int x : y)
			{
				if (x == 0)
					return true;
			}
		}
		return false;
	}
	
	public boolean hasWon()
	{
		for(int[] y : board)
		{
			for(int x : y)
			{
				if (x == 2048)
					return true;
			}
		}
		return false;
	}
}
