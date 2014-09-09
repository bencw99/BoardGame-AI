package game.piece;

import game.Location;
import game.Node;

/**
 * A class describing a checkers soldier piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class Soldier extends Piece
{
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
					possibleMoves.add(new Move(getNode().getLoc(), currentLoc, new ArrayList<Node>(), getNode().getBoard()));
				}
				else
				{
					Location jumpLoc = new Location(currentLoc.getRow() + i, currentLog.getCol() + orientation);
					
					if(getNode().getBoard().isValid(jumpLoc) && getNode().getBoard().getPiece(jumpLoc) == null)
					{
						for(Node next : getNextJumps(jumpLoc))
						{
						
						}
					}
				}
			}
		}
	}
	
	/**
	 * Returns the possible nodes this piece can jump to
	 * 
	 * @return	the array list of possible nodes this piece can jump to
	 */
	protected ArrayList<Node> getNextJumps(Node current)
	{
		
	}
}
