package game.piece.chessPieces;
import java.util.ArrayList;

import game.Move;
import game.board.node.Node;
import game.piece.Piece;

/**
 * A class representing a checkers piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class Pawn extends Piece
{
	/** The worth of a pawn **/
	public static final int PAWN_WORTH = 3;
	
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 */
	public Pawn(Loyalty loyalty)
	{
		super(loyalty);
		setWorth(PAWN_WORTH);
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 */
	public Pawn(Loyalty loyalty, Node node)
	{
		super(loyalty, node);
		setWorth(PAWN_WORTH);
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 */
	public Pawn(Piece piece, Node node)
	{
		super(piece.getLoyalty(), node);
		setWorth(PAWN_WORTH);
	}

	public ArrayList<Move> getPossibleMoves()
	{
		return null;
	}
}
