package game.player;
import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.board.Board;
import game.board.RectangularBoard;
import game.move.Move;
import game.piece.*;
import game.piece.Piece.Loyalty;

/**
 * A class representing a Player associated with a checkers game
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class Player
{
	/** The enum representing the state of a player **/
	public static enum State
	{
		PLAYING,
		DEFEATED
	}
	
	/** The name of this player **/
	private String name;
	
	/** The loyalty of this player **/
	private Loyalty loyalty;
	
	/** The game this player belongs to **/
	private Game game;
	
	/**The state of this player **/
	private State state;
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param pieces	the pieces of this player
	 */
	public Player(String name, Loyalty loyalty, Game game)
	{
		this.name = name;
		this.loyalty = loyalty;
		this.state = State.PLAYING;
		this.game = game;
	}
	
	/**
	 * Returns the possible moves of this player
	 * 
	 * @return	the arrayList of possible moves of this player
	 */
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> possibleMoves = game.getBoard().getPossibleMoves(loyalty);
		
		if(possibleMoves.isEmpty())
		{
			setState(State.DEFEATED);
		}
		
		return possibleMoves;
	}
	
	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 * @throws IOException 
	 */
	public abstract Move getThisTurnMove() throws IOException;
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return the state
	 */
	public State getState()
	{
		return state;
	}

	/**
	 * @return the loyalty
	 */
	public Loyalty getLoyalty()
	{
		return loyalty;
	}

//	/**
//	 * @return the pieces
//	 */
//	public ArrayList<Piece> getPieces()
//	{
//		return pieces;
//	}
	
	/**
	 * @return the game
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * @return the board
	 */
	public RectangularBoard getBoard()
	{
		return (RectangularBoard) game.getBoard();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(State state)
	{
		this.state = state;
	}

	/**
	 * @param loyalty the loyalty to set
	 */
	public void setLoyalty(Loyalty loyalty)
	{
		this.loyalty = loyalty;
	}
	
	/**
	 * @return	whether or not this player is defeated
	 */
	public boolean isDefeated()
	{
		if(state == State.DEFEATED)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
