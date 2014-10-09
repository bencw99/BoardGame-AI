package game.player;
import java.util.ArrayList;

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
	/** The name of this player **/
	private String name;
	
	/** The loyalty of this player **/
	private Loyalty loyalty;
	
	/** The pieces of this player **/
	ArrayList<Piece> pieces;
	
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
	 */
	public abstract Move getThisTurnMove();
	
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
}
