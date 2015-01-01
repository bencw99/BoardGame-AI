package game.move;

import game.board.Board;
import game.board.CheckersBoard;
import game.board.RectangularBoard;
import game.board.node.Location;
import game.board.node.Node;
import game.piece.Piece.Loyalty;

import java.util.ArrayList;

/**
 * A class representing a move of a piece in a checkers game 
 * 
 * @author Benjamin Cohen-Wang
 */
public class CheckersMove extends Move
{	
	/**
	 * Parameterized Constructor, initializes start and end nodes, board, interNodes, and loyalty
	 * 
	 * @param interNodes	the array list of nodes jumped through
	 * @param board	the board this move occurs in
	 * @param loyalty	the loyalty of this move
	 */
	public CheckersMove(ArrayList<Node> nodes, RectangularBoard board, Loyalty loyalty)
	{
		super(nodes, board, loyalty);
	}

	/**
	 * Adds jumped pieces to the jump array list
	 */
	public void loadJumped()
	{
		Node current;
		Node next;
		
		setJumped(new ArrayList<Node>());
		
		for(int i = 0; i < getNodes().size() - 1; i ++)
		{
			current = getNodes().get(i);
			next = getNodes().get(i + 1);
			
			if(Math.abs(next.getLoc().getRow() - current.getLoc().getRow()) == 2)
			{
				getJumped().add(getBoard().getNode(new Location((next.getLoc().getRow() + current.getLoc().getRow())/2, (next.getLoc().getCol() + current.getLoc().getCol())/2)));
			}
		}
	}
	
	/**
	 * Returns whether or not this move is simple
	 * 
	 * @return a boolean describing whether or not this move is simple
	 */
	public boolean isSimple()
	{
		if(getNodes().size() == 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
