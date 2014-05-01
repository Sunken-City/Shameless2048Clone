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
			for(int y = 1; y < 4; y++)
			{
				for(int x = 1; x < 4; x++)
				{
					int thisTile = board[y][x];
					int prevTile = board[y][x-1];
					if(prevTile == 0)
					{
						board[y][x-1] = thisTile;
						board[y][x] = 0;
					}
					else if (prevTile == thisTile)
					{
						board[y][x-1] = thisTile + prevTile;
						board[y][x] = 0;
					}
				}
			}
		}
		else if (dir == Direction.RIGHT)
		{
			for(int y = 1; y < 4; y++)
			{
				for(int x = 2; x > -1; x--)
				{
					int thisTile = board[y][x];
					int prevTile = board[y][x+1];
					if(prevTile == 0)
					{
						board[y][x+1] = thisTile;
						board[y][x] = 0;
					}
					else if (prevTile == thisTile)
					{
						board[y][x+1] = thisTile + prevTile;
						board[y][x] = 0;
					}
				}
			}
		}
		else if (dir == Direction.UP)
		{
			for(int y = 1; y < 4; y++)
			{
				for(int x = 1; x < 4; x++)
				{
					int thisTile = board[y][x];
					int prevTile = board[y-1][x];
					if(prevTile == 0)
					{
						board[y-1][x] = thisTile;
						board[y][x] = 0;
					}
					else if (prevTile == thisTile)
					{
						board[y-1][x] = thisTile + prevTile;
						board[y][x] = 0;
					}
				}
			}
		}
		else if (dir == Direction.DOWN)
		{
			for(int y = 2; y > -1; y--)
			{
				for(int x = 1; x < 4; x++)
				{
					int thisTile = board[y][x];
					int prevTile = board[y+1][x];
					if(prevTile == 0)
					{
						board[y+1][x] = thisTile;
						board[y][x] = 0;
					}
					else if (prevTile == thisTile)
					{
						board[y+1][x] = thisTile + prevTile;
						board[y][x] = 0;
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
