package game.piece.chessPieces;
import java.io.IOException;
import java.util.ArrayList;

import game.board.node.Location;
import game.board.node.Node;
import game.move.ChessMove;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;

/**
 * A class representing a chess pawn
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
	 * @throws IOException 
	 */
	public Pawn(Loyalty loyalty) throws IOException
	{
		super(loyalty);
		
		if(!imagesInitialized)
		{
			imagesInit();
		}
		
		image = (getLoyalty() == Loyalty.RED) ? WHITE_PAWN: BLACK_PAWN;
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 * @throws IOException 
	 */
	public Pawn(Loyalty loyalty, Node node) throws IOException
	{
		super(loyalty, node);
		
		if(!imagesInitialized)
		{
			imagesInit();
		}
		
		image = (getLoyalty() == Loyalty.RED) ? WHITE_PAWN: BLACK_PAWN;
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 * @throws IOException 
	 */
	public Pawn(Piece piece, Node node) throws IOException
	{
		super(piece.getLoyalty(), node);
		
		if(!imagesInitialized)
		{
			imagesInit();
		}
		
		image = (getLoyalty() == Loyalty.RED) ? WHITE_PAWN: BLACK_PAWN;
	}

	/**
	 * Returns the possible move this piece can do
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
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
				
				possibleMoves.add(new ChessMove(move, getNode().getBoard(), getLoyalty()));
				
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
							
							possibleMoves.add(new ChessMove(firstMove, getNode().getBoard(), getLoyalty()));
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
					
					possibleMoves.add(new ChessMove(jumpMove, getNode().getBoard(), getLoyalty()));
				}
			}
		}
		
		return possibleMoves;
	}
}
