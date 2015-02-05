package game;

import java.awt.Graphics;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game.board.*;
import game.move.Move;
import game.piece.Piece.Loyalty;
import game.player.*;
import game.player.ai.AI;

/**
 * A class representing a checkers game
 * 
 * @author Benjamin Cohen-Wang
 */
public class Game
{	
	/**
	 * An enum representing the game type
	 * 
	 * @author Benjamin Cohen-Wang
	 */
	public static enum GameType
	{
		CHECKERS,
		CHESS
	}
	
	/**
	 * An enum representing whose turn it is
	 * 
	 * @author Benjamin Cohen-Wang
	 */
//	public static enum Turn
//	{
//		PLAYER1(0),
//		PLAYER2(1);
//		
//		private int val;
//		
//		Turn(int val)
//		{
//			this.val = val;
//		}
//		
//		public int getVal()
//		{
//			return val;
//		}
//		
//		public Loyalty getLoyalty()
//		{
//			return val == 0 ? Loyalty.RED: Loyalty.BLACK;
//		}
//		
//		public Turn getOther()
//		{
//			if(this == PLAYER1)
//			{
//				return PLAYER2;
//			}
//			else
//			{
//				return PLAYER1;
//			}
//		}
//		
//		public static Turn getRandom()
//		{
//			int random = (int)(2*Math.random());
//			
//			return random == 0 ? PLAYER1 : PLAYER2;
//		}
//	}
	
	/** The board this game takes place on **/
	private Board board;
	
	/** The array of players participating in this game **/
	private Player[] players;
	
	/** The loyalty describing whose turn it is **/
	private Loyalty turn;
	
	/** The state of this game **/
	private boolean completed;
	
	/**
	 * Parameterized constructor, sets this game to the given game type
	 * 
	 * @throws IOException 
	 */
	public Game(GameType type) throws IOException
	{	
		completed = false;
		
		if(type == GameType.CHECKERS)
		{
			turn = Loyalty.getRandom();
			
			players = new Player[2];
			players[0] = new AI("AI", Loyalty.RED, this);
			players[1] = new Human("Human", Loyalty.BLACK, this);
			
			board = new CheckersBoard(this);
		}
		if(type == GameType.CHESS)
		{
			turn = Loyalty.RED;
			
			players = new Player[2];
			players[0] = new AI("Player 1", Loyalty.RED, this, 6);
			players[1] = new Human("Player 2", Loyalty.BLACK, this);
			
			board = new ChessBoard(this);
		}
	}
	
	/**
	 * Parameterized constructor, makes this game a copy of the given game
	 * 
	 * @param game	the game whose copy is made
	 */
//	public Game(Game game)
//	{
//		completed = false;
//		
//		this.players = new Player[game.getPlayers().length];
//		
//		for(int i = 0; i < players.length; i ++)
//		{
//			players[i] = new AI(null, game.getPlayers()[i].getLoyalty(), this);
//		}
//		
////		this.board = game.getBoard().clone(this);
//		
//		Constructor constructor = null;
//		
//		try
//		{
//			 constructor = game.getBoard().getClass().getConstructor(game.getBoard().getClass(), this.getClass());
//		} 
//		catch (SecurityException e)
//		{
//			e.printStackTrace();
//		} 
//		catch (NoSuchMethodException e)
//		{
//			e.printStackTrace();
//		}
//		
//		try
//		{
//			this.board = (Board) constructor.newInstance(game.getBoard(), this);
//		} 
//		catch (IllegalArgumentException e)
//		{
//			e.printStackTrace();
//		} 
//		catch (InstantiationException e)
//		{
//			e.printStackTrace();
//		} 
//		catch (IllegalAccessException e)
//		{
//			e.printStackTrace();
//		} 
//		catch (InvocationTargetException e)
//		{
//			e.printStackTrace();
//		}
//		
//		this.turn = game.turn;
//	}
	
	/**
	 * Parameterized constructor, initializes fields to given parameters 
	 * 
	 * @param board	the board that the board of this game is set to
	 * @param players	the players of this game
	 */
	public Game(Board board, Player[] players)
	{
		this(board, players, Loyalty.getRandom());
	}
	
	/**
	 * Parameterized constructor, initializes fields to given parameters 
	 * 
	 * @param board	the board that the board of this game is set to
	 * @param players	the players of this game
	 */
	public Game(Board board, Player[] players, Loyalty turn)
	{
		this.completed = false;
		this.board = board;
		this.players = players;
		this.turn = turn;
	}
	
	/**
	 * Executes the next turn
	 * @throws IOException 
	 */
	public void executeTurn() throws IOException
	{	
		Player thisPlayer = players[turn.getVal()];
		
		Move move = thisPlayer.getThisTurnMove();
		
//		Move move = thisPlayer instanceof AI ? ((AI)thisPlayer).getThisTurnMove(1000) : thisPlayer.getThisTurnMove();
		
		if(thisPlayer.isDefeated())
		{	
			return;
		}
		
		int aliveCount = 0;
		
		for(Player player : players)
		{
			if(!player.isDefeated())
			{
				aliveCount ++;
			}
		}
		
		if(aliveCount <= 1)
		{
			this.completed = true;
		}
		
		while(move == null)
		{
			move = thisPlayer.getThisTurnMove();
		}
	
		board.executeMove(move);
				
		turn = turn.getOther();
	}
	
	/**
	 * Draws this game on the given graphics object
	 * 
	 * @param graphics	the graphics object to be drawn on
	 */
	public void draw(Graphics graphics)
	{
		board.draw(graphics);
	}

	/**
	 * @return the board
	 */
	public Board getBoard() 
	{
		return board;
	}

	/**
	 * @return the players
	 */
	public Player[] getPlayers() 
	{
		return players;
	}

	/**
	 * @return the turn
	 */
	public Loyalty getTurn() 
	{
		return turn;
	}

	/**
	 * @return is completed
	 */
	public boolean isCompleted()
	{
		return completed;
	}
	
	/**
	 * @param board the board to set
	 */
	public void setBoard(CheckersBoard board) 
	{
		this.board = board;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(Player[] players) 
	{
		this.players = players;
	}

	/**
	 * @param turn the turn to set
	 */
	public void setTurn(Loyalty turn) 
	{
		this.turn = turn;
	}
}
