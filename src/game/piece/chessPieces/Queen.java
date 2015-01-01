package game.piece.chessPieces;

import java.util.ArrayList;

import game.board.node.Location;
import game.board.node.Node;
import game.move.CheckersMove;
import game.piece.Piece;

/**
 * A class representing a chess queen
 * 
 * @author Benjamin Cohen-Wang
 */
public class Queen extends Piece
{
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 */
	public Queen(Loyalty loyalty)
	{
		super(loyalty);
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 */
	public Queen(Loyalty loyalty, Node node)
	{
		super(loyalty, node);
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 */
	public Queen(Piece piece, Node node)
	{
		super(piece.getLoyalty(), node);
	}

	/**
	 * Returns the possible move this piece can do
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public ArrayList<CheckersMove> getPossibleMoves()
	{
		ArrayList<CheckersMove> possibleMoves = new ArrayList<CheckersMove>();
		
		for(int i = -1; i <= 1; i ++)
		{
			for(int j = -1; j <= 1; j ++)
			{
				boolean openSpace = true;
				int count = 1;
				
				while(openSpace)
				{
					Location currentLoc = new Location(getNode().getLoc().getRow() + i*count, getNode().getLoc().getCol() + j*count);
					
					if(!getNode().getBoard().isValid(currentLoc) || getNode().getBoard().getNode(currentLoc).getPiece() != null)
					{
						openSpace = false;
						
						if(getNode().getBoard().isValid(currentLoc) && getNode().getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
						{
							ArrayList<Node> move = new ArrayList<Node>();
							
							move.add(getNode());
							move.add(getNode().getBoard().getNode(currentLoc));
							
							possibleMoves.add(new CheckersMove(move, getNode().getBoard(), getLoyalty()));
						}
					}
					else
					{
						ArrayList<Node> move = new ArrayList<Node>();
						
						move.add(getNode());
						move.add(getNode().getBoard().getNode(currentLoc));
						
						possibleMoves.add(new CheckersMove(move, getNode().getBoard(), getLoyalty()));
					}
					
					count ++;
				}
			}
		}
		
		return possibleMoves;
	}
}