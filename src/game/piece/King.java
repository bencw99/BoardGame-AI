package game.piece;

import java.util.ArrayList;

import game.Move;
import game.Node;
import game.piece.Piece.Loyalty;

/**
 * A class describing a checkers king piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class King extends Piece
{
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 */
	public King(Loyalty loyalty)
	{
		super(loyalty);
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 */
	public King(Loyalty loyalty, Node node)
	{
		super(loyalty, node);
	}

	public ArrayList<Move> getPossibleMoves() 
	{
		return null;
	}

	protected ArrayList<Node> getNextJumps(Node current) 
	{
		return null;
	}
}
