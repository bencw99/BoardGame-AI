package game.piece.chessPieces;
import java.util.ArrayList;

import game.Move;
import game.board.node.Location;
import game.board.node.Node;
import game.piece.Piece;
import game.piece.Piece.Loyalty;

/**
 * A class representing a checkers piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class Pawn extends Piece
{
	/** The worth of a pawn **/
	public static final int PAWN_WORTH = 1;
	
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
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		int orientation = getLoyalty() == Loyalty.RED ? 1 : -1;
		
		Location inFront = new Location(getNode().getLoc().getRow() + orientation, getNode().getLoc().getCol());
		
		if(getNode().getBoard().isValid(inFront))
		{
			if(getNode().getBoard().getNode(inFront).getPiece() == null)
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getNode().getBoard().getNode(inFront));
				
				possibleMoves.add(new Move(move, getNode().getBoard(), getLoyalty()));
				
				boolean hasNotMoved = true;
				
				if(getLoyalty() == Loyalty.RED && getNode().getLoc().getRow() != 1)
				{
					hasNotMoved = false;
				}
				
				if(getLoyalty() == Loyalty.RED && getNode().getLoc().getRow() != getNode().getBoard().getGrid().length - 2)
				{
					hasNotMoved = false;
				}
				
				if(hasNotMoved)
				{
					Location twoInFront = new Location(getNode().getLoc().getRow() + 2*orientation, getNode().getLoc().getCol());
					
					if(getNode().getBoard().isValid(twoInFront))
					{
						if(getNode().getBoard().getNode(twoInFront).getPiece() == null)
						{
							ArrayList<Node> firstMove = new ArrayList<Node>();
							
							firstMove.add(getNode());
							firstMove.add(getNode().getBoard().getNode(twoInFront));
							
							possibleMoves.add(new Move(firstMove, getNode().getBoard(), getLoyalty()));
						}
					}
				}
			}
		}
		
		for(int i = -1; i <= 1; i += 2)
		{
			Location jumpLoc = new Location(getNode().getLoc().getRow() + orientation, getNode().getLoc().getCol() + i);
			
			if(getNode().getBoard().isValid(jumpLoc))
			{
				if(getNode().getBoard().getNode(jumpLoc).getPiece() != null && getNode().getBoard().getNode(jumpLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> jumpMove = new ArrayList<Node>();
					
					jumpMove.add(getNode());
					jumpMove.add(getNode().getBoard().getNode(jumpLoc));
					
					possibleMoves.add(new Move(jumpMove, getNode().getBoard(), getLoyalty()));
				}
			}
		}
		
		return possibleMoves;
	}
}
