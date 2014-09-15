package game;

import game.board.*;
import game.player.*;

/**
 * A class representing a checkers game
 * 
 * @author Benjamin Cohen-Wang
 */
public class Game
{
	/**
	 * An enum representing whose turn it is
	 * 
	 * @author Benjamin Cohen-Wang
	 */
	public enum Turn
	{
		PLAYER1(0),
		PLAYER2(1);
		
		private int val;
		
		Turn(int val)
		{
			this.val = val;
		}
		
		public Turn getOther()
		{
			if(this == PLAYER1)
			{
				return PLAYER2;
			}
			else
			{
				return PLAYER1;
			}
		}
		
		public static Turn getRandom()
		{
			int random = (int)(2*Math.random());
			
			if(random == 0)
			{
				return PLAYER1;
			}
			else
			{
				return PLAYER2;
			}
		}
	}
	
	/** The board this game takes place on **/
	private Board board;
	
	/** The array of players participating in this game **/
	private Player[] players;
	
	/** The Turn describing whose turn it is **/
	private Turn turn;
	
	/**
	 * Default constructor, creates this as a default game
	 */
	public Game()
	{
		
	}
	
	/**
	 * Parameterized constructor, initializes fields to given parameters 
	 * 
	 * @param board	the board that the board of this game is set to
	 * @param player1	the first player of this game
	 * @param player2	the second player of this game
	 */
	public Game(Board board, Player player1, Player player2)
	{
		this(board, player1, player2, Turn.getRandom());
	}
	
	/**
	 * Parameterized constructor, initializes fields to given parameters 
	 * 
	 * @param board	the board that the board of this game is set to
	 * @param player1	the first player of this game
	 * @param player2	the second player of this game
	 */
	public Game(Board board, Player player1, Player player2, Turn turn)
	{
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
		this.turn = turn;
	}
	
	/**
	 * Executes the next turn
	 */
	public void executeTurn()
	{
		
	}
}
