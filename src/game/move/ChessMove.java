package game.move;

import game.board.RectangularBoard;
import game.board.node.Node;
import game.piece.Piece;
import game.piece.Piece.Loyalty;

import java.util.ArrayList;

/**
 * A class representing a move of a piece in a chess game 
 * 
 * @author Benjamin Cohen-Wang
 */
public class ChessMove extends Move
{
	Class promotionType;
	
	/**
	 * Parameterized Constructor, initializes start and end nodes, board, interNodes, and loyalty
	 * 
	 * @param interNodes	the array list of nodes jumped through
	 * @param board	the board this move occurs in
	 * @param loyalty	the loyalty of this move
	 */
	public ChessMove(ArrayList<Node> nodes, RectangularBoard board, Loyalty loyalty)
	{
		super(nodes, board, loyalty);
	}
	
	/**
	 * Parameterized Constructor, initializes start and end nodes, board, interNodes, and loyalty
	 * 
	 * @param interNodes	the array list of nodes jumped through
	 * @param board	the board this move occurs in
	 * @param loyalty	the loyalty of this move
	 * @param promotionType the promotionType to be set to
	 */
	public ChessMove(ArrayList<Node> nodes, RectangularBoard board, Loyalty loyalty, Class promotionType)
	{
		super(nodes, board, loyalty);
		this.promotionType = promotionType;
	}
	
	/**
	 * Adds jumped pieces to the jump array list
	 */
	public void loadJumped()
	{
		setJumped(new ArrayList<Node>());
		
		Node terminal = getNodes().get(getNodes().size() - 1);
		
		if(terminal.getPiece() != null)
		{
			getJumped().add(terminal);
		}
	}
	
	/**
	 * @return the promotion type
	 */
	public Class getPromotionType()
	{
		return promotionType;
	}
}
