package game.piece.chessPieces;
import java.io.IOException;
import java.util.ArrayList;

import game.board.node.Location;
import game.board.node.Node;
import game.move.ChessMove;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.checkersPieces.King;

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
		setWorth(PAWN_WORTH);
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
		setWorth(PAWN_WORTH);
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
		setWorth(PAWN_WORTH);
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
		
		Location inFront = new Location(getLoc().getRow() + orientation, getLoc().getCol());
		
		if(getBoard().isValid(inFront))
		{
			if(getBoard().getNode(inFront).getPiece() == null)
			{
				ArrayList<Node> move = new ArrayList<Node>();
				
				move.add(getNode());
				move.add(getBoard().getNode(inFront));
				
				if(inFront.getRow() == (orientation + 1)*(getBoard().getGrid().length - 1)/2)
				{
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty(), Knight.class));
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty(), Rook.class));
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty(), Bishop.class));
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty(), Queen.class));
				}
				else
				{
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
				}
				
				if(!hasMoved())
				{
					Location twoInFront = new Location(getLoc().getRow() + 2*orientation, getLoc().getCol());
					
					if(getBoard().isValid(twoInFront))
					{
						if(getBoard().getNode(twoInFront).getPiece() == null)
						{
							ArrayList<Node> firstMove = new ArrayList<Node>();
							
							firstMove.add(getNode());
							firstMove.add(getBoard().getNode(twoInFront));
							
							possibleMoves.add(new ChessMove(firstMove, getBoard(), getLoyalty()));
						}
					}
				}
			}
		}
		
		for(int i = -1; i <= 1; i += 2)
		{
			Location jumpLoc = new Location(getLoc().getRow() + orientation, getLoc().getCol() + i);
			
			if(getBoard().isValid(jumpLoc))
			{
				if(getBoard().getNode(jumpLoc).getPiece() != null && getBoard().getNode(jumpLoc).getPiece().getLoyalty() != this.getLoyalty())
				{
					ArrayList<Node> jumpMove = new ArrayList<Node>();
					
					jumpMove.add(getNode());
					jumpMove.add(getBoard().getNode(jumpLoc));
					
					if(inFront.getRow() == (orientation + 1)*(getBoard().getGrid().length - 1)/2)
					{
						possibleMoves.add(new ChessMove(jumpMove, getBoard(), getLoyalty(), Knight.class));
						possibleMoves.add(new ChessMove(jumpMove, getBoard(), getLoyalty(), Rook.class));
						possibleMoves.add(new ChessMove(jumpMove, getBoard(), getLoyalty(), Bishop.class));
						possibleMoves.add(new ChessMove(jumpMove, getBoard(), getLoyalty(), Queen.class));
					}
					else
					{
						possibleMoves.add(new ChessMove(jumpMove, getBoard(), getLoyalty()));
					}
				}
			}
		}
		
		return possibleMoves;
	}
	
	/**
	 * Returns a clone of this piece at the given node
	 * 
	 * @param node	the node to be cloned to
	 * @return	the cloned piece
	 * @throws IOException 
	 */
	public Piece clone(Node node) throws IOException
	{
		return new Pawn(this, node);
	}
}
