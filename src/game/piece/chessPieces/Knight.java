package game.piece.chessPieces;

import java.util.ArrayList;

import game.board.node.Location;
import game.board.node.Node;
import game.move.CheckersMove;
import game.piece.Piece;

/**
 * A class representing a chess knight
 * 
 * @author Benjamin Cohen-Wang
 */
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
	public ArrayList<CheckersMove> getPossibleMoves()
	{
		ArrayList<CheckersMove> possibleMoves = new ArrayList<CheckersMove>();
		
		for(int i = -2; i <= 2; i += 4)
		{
			for(int j = -1; j <= 1; j += 2)
			{
				Location moveLoc = new Location(getNode().getLoc().getRow() + i, getNode().getLoc().getCol() + j);
				
				if(getNode().getBoard().isValid(moveLoc) && getNode().getBoard().getNode(moveLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getNode().getBoard().getNode(moveLoc));
					
					possibleMoves.add(new CheckersMove(move, getNode().getBoard(), getLoyalty()));
				}
			}
		}
		
		for(int i = -1; i <= 1; i += 2)
		{
			for(int j = -2; j <= 2; j += 4)
			{
				Location moveLoc = new Location(getNode().getLoc().getRow() + i, getNode().getLoc().getCol() + j);
				
				if(getNode().getBoard().isValid(moveLoc) && getNode().getBoard().getNode(moveLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getNode().getBoard().getNode(moveLoc));
					
					possibleMoves.add(new CheckersMove(move, getNode().getBoard(), getLoyalty()));
				}
			}
		}
		
		return possibleMoves;
	}
}
