package game.piece;

import java.util.ArrayList;

import game.Location;
import game.Move;
import game.Node;
import game.piece.Piece.Loyalty;

/**
 * A class describing a checkers soldier piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class Soldier extends Piece
{
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 */
	public Soldier(Loyalty loyalty)
	{
		super(loyalty);
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 */
	public Soldier(Loyalty loyalty, Node node)
	{
		super(loyalty, node);
	}
	
	/**
	 * Returns the possible nodes this piece can go to
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		int orientation = getLoyalty() == Loyalty.RED ? -1 : 1;
		
		for(int i = - 1; i < + 1; i += 2)
		{
			Location currentLoc = new Location(getNode().getLoc().getRow() + i, getNode().getLoc().getCol() + orientation);
			
			if(getNode().getBoard().isValid(currentLoc))
			{
				if(getNode().getBoard().getPiece(currentLoc) == null)
				{
					possibleMoves.add(new Move(getNode(), getNode().getBoard().getNode(currentLoc), new ArrayList<Node>(), getNode().getBoard()));
				}
				else if(getNode().getBoard().getPiece(currentLoc).getLoyalty() != this.getLoyalty())
				{
					
				}
			}
		}
		
		return possibleMoves;
	}
	
	/**
	 * Returns the possible nodes this piece can jump to
	 * 
	 * @return	the array list of possible nodes this piece can jump to
	 */
	protected ArrayList<Node> getNextJumps(ArrayList<Node> prevSteps)
	{	
		int orientation = getLoyalty() == Loyalty.RED ? -1 : 1;
		
		Node current = prevSteps.get(prevSteps.size() - 1);
		
		for(int i = - 1; i < + 1; i += 2)
		{
			Location jumpLoc = new Location(current.getLoc().getRow() + i, current.getLoc().getCol() + orientation);
						
			if(getNode().getBoard().isValid(jumpLoc) && getNode().getBoard().getPiece(jumpLoc) == null)
			{
				prevSteps.add(getNode().getBoard().getNode(jumpLoc));
				
				for(Node next : getNextJumps(prevSteps))
				{
					
				}
			}
		}
		
		return null;
	}
}
