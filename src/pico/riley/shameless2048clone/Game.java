package pico.riley.shameless2048clone;

import java.util.Random;

import android.util.Log;

public class Game {
	private int[][] board;
	private Random rand;
	private int score;
	enum Direction {UP, DOWN, LEFT, RIGHT};
	
	public Game()
	{
		score = 0;
		rand = new Random();
		board = new int[4][4];
		addRandomTile();
		addRandomTile();
		Log.i("GAME", "Welcome to 2048!");
		Log.i("GAME", "----------------------------");
		print();
	}
	
	public void newGame()
	{
		score = 0;
		rand = new Random();
		board = new int[4][4];
		addRandomTile();
		addRandomTile();
	}
	
	public int[][] getGrid()
	{
		return board;
	}
	
	@Override public String toString() 
	{
		//Write out the board as a string that encodes the 16 tiles.
		//I use this to save the state of the game board when the app is closed.
		String encodedBoard = "";
		for(int[] y : board)
		{
			for(int x : y)
			{
				encodedBoard += x + "_";
			}
		}	
		return encodedBoard;
	}
	
	public void parseBoard(String encodedBoard)
	{
		if (encodedBoard != "")
		{
			String[] parts = encodedBoard.split("_");
			int counter = 0;
			for(int y = 0; y < 4; y++)
			{
				for(int x = 0; x < 4; x++)
				{
					board[y][x] = Integer.parseInt(parts[counter]);
					counter++;
				}
			}	
		}
	}
	
	public int getTile(int tileNumber)
	{
		int x = tileNumber % 4;
		int y = tileNumber / 4;
		return board[y][x];
	}
	
	public void print()
	{
		//This is for log statement debugging.
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
		if(shift(dir)) //If this move is significant (makes a change in game state)
		{
			addRandomTile();
		}
		if(isNotFull())
			return true;
		else
			return moveAvailable(); //The move wasn't significant, let the player move again.
	}
	
	private boolean moveAvailable() //Check each of the tiles against one another to see if any moves can be made 
	{
		for(int y = 0; y < 4; y++)
		{
			for(int x = 0; x < 4; x++)
			{
				//Because of the way we iterate, we only need to check the tiles below and to the right of us
				int thisTile = board[y][x];
				int rightTile = (x + 1 < 4) ? board[y][x + 1] : -1;
				int downTile = (y + 1 < 4) ? board[y + 1][x] : -1;
				if((thisTile == rightTile) || (thisTile == downTile))
					return true;
			}
		}
		return false;
	}

	public boolean shift(Direction dir)
	{
		boolean moveMade = false;
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
							moveMade = true;
						}
						if (nextTile == thisTile)
						{
							board[y][currX - 1] = thisTile + nextTile; //Combine the two tiles
							score += thisTile + nextTile;
							board[y][currX] = 0;
							moveMade = true;
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
							moveMade = true;
						}
						
						if (nextTile == thisTile)
						{
							board[y][currX + 1] = thisTile + nextTile; //Combine the two tiles
							score += thisTile + nextTile;
							board[y][currX] = 0;
							moveMade = true;
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
							moveMade = true;
						}
						
						if (nextTile == thisTile)
						{
							board[currY - 1][x] = thisTile + nextTile; //Combine the two tiles
							score += thisTile + nextTile;
							board[currY][x] = 0;
							moveMade = true;
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
							moveMade = true;
						}
						
						if (nextTile == thisTile)
						{
							board[currY + 1][x] = thisTile + nextTile; //Combine the two tiles
							score += thisTile + nextTile;
							board[currY][x] = 0;
							moveMade = true;
						}
					}
				}
			}
		}
		
		return moveMade;
	}
	
	public boolean addRandomTile()
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
			board[y][x] = rand.nextInt(10) < 9 ? 2 : 4;
			return true; //Placed a new tile
		}
		else
		{
			return false; //Couldn't place a new tile, game might be over.
		}
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
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
}
