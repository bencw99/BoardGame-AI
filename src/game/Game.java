package game;

import java.util.ArrayList;

import game.board.*;
import game.piece.Piece;
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
		
		public int getVal()
		{
			return val;
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
	 * @param players	the players of this game
	 */
	public Game(Board board, Player[] players)
	{
		this(board, players, Turn.getRandom());
	}
	
	/**
	 * Parameterized constructor, initializes fields to given parameters 
	 * 
	 * @param board	the board that the board of this game is set to
	 * @param player1	the players of this game
	 */
	public Game(Board board, Player[] players, Turn turn)
	{
		this.board = board;
		this.players = players;
		this.turn = turn;
	}
	
	/**
	 * Executes the next turn
	 */
	public void executeTurn()
	{
		Player thisPlayer = players[turn.getVal()];
		
		Move move = thisPlayer.getThisTurnMove();
	}
}
