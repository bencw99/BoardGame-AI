package game.player;
import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.Move;
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
	
	/** The pieces of this player **/
	private ArrayList<Piece> pieces;
	
	/**The state of this player **/
	private State state;
	
	/** The default number of pieces on the checkers grid **/
	public final static int DEFAULT_PIECE_NUM = 12;
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param pieces	the pieces of this player
	 */
	public Player(String name, Loyalty loyalty, ArrayList<Piece> pieces)
	{
		this.name = name;
		this.loyalty = loyalty;
		this.pieces = pieces;
		this.state = State.PLAYING;
	}
	
	/**
	 * Returns the possible moves of this player
	 * 
	 * @return	the arrayList of possible moves of this player
	 */
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> jumpMoves = new ArrayList<Move>();
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		for(Piece piece : pieces)
		{
			for(Move possibleMove : piece.getPossibleMoves())
			{
				if(!possibleMove.getJumped().isEmpty())
				{
					jumpMoves.add(possibleMove);
				}
				
				possibleMoves.add(possibleMove);
			}
		}
		
		if(possibleMoves.isEmpty())
		{
			state = State.DEFEATED;
		}
		
		if(jumpMoves.isEmpty())
		{
			return possibleMoves;
		}
		else
		{
			return jumpMoves;
		}	
	}
	
	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 * @throws IOException 
	 */
	public abstract Move getThisTurnMove() throws IOException;
	
	/**
	 * Removes the given piece from pieces ArrayList
	 * 
	 * @param piece	the piece to be removed
	 */
	public void remove(Piece piece)
	{
		pieces.remove(piece);
	}
	
	/**
	 * Adds the given piece to pieces ArrayList
	 * 
	 * @param piece	the piece to be added
	 */
	public void add(Piece piece)
	{
		pieces.add(piece);
	}
	
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

	/**
	 * @return the pieces
	 */
	public ArrayList<Piece> getPieces()
	{
		return pieces;
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
	 * @param pieces the pieces to set
	 */
	public void setPieces(ArrayList<Piece> pieces)
	{
		this.pieces = pieces;
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
