package game.piece.chessPieces;

import java.util.ArrayList;

import game.Move;
import game.board.node.Location;
import game.board.node.Node;
import game.piece.Piece;

/**
 * A class representing a chess king
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
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 */
	public King(Piece piece, Node node)
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
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		for(int i = -1; i <= 1; i ++)
		{
			for (int j = -1; j <= 1; j ++)
			{
				Location moveLoc = new Location(getNode().getLoc().getRow() + i, getNode().getLoc().getCol() + j);
				
				if(!moveLoc.equals(getNode().getLoc()))
				{
					if(getNode().getBoard().isValid(moveLoc))
					{
						ArrayList<Node> move = new ArrayList<Node>();
						
						move.add(getNode());
						move.add(getNode().getBoard().getNode(moveLoc));
						
						possibleMoves.add(new Move(move, getNode().getBoard(), getLoyalty()));
					}
				}
			}
		}
		
		return possibleMoves;
	}
}
