package game.piece.chessPieces;

import java.util.ArrayList;

import game.Move;
import game.board.node.Node;
import game.piece.Piece;
import game.piece.Piece.Loyalty;

public class Knight extends Piece
{
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 */
	public Knight(Loyalty loyalty)
	{
		super(loyalty);
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 */
	public Knight(Loyalty loyalty, Node node)
	{
		super(loyalty, node);
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 */
	public Knight(Piece piece, Node node)
	{
		super(piece.getLoyalty(), node);
	}

	/**
	 * Returns the possible move this piece can do
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public ArrayList<Move> getPossibleMoves()
	{
		return null;
	}
	
}
