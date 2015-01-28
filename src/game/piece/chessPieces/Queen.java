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
 * A class representing a chess queen
 * 
 * @author Benjamin Cohen-Wang
 */
public class Queen extends Piece
{
	/** The worth of a queen **/
	public static final int QUEEN_WORTH = 9;
	
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 * @throws IOException 
	 */
	public Queen(Loyalty loyalty) throws IOException
	{
		super(loyalty);
		setWorth(QUEEN_WORTH);
		image = (getLoyalty() == Loyalty.RED) ? WHITE_QUEEN: BLACK_QUEEN;
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 * @throws IOException 
	 */
	public Queen(Loyalty loyalty, Node node) throws IOException
	{
		super(loyalty, node);
		setWorth(QUEEN_WORTH);
		image = (getLoyalty() == Loyalty.RED) ? WHITE_QUEEN: BLACK_QUEEN;
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 * @throws IOException 
	 */
	public Queen(Piece piece, Node node) throws IOException
	{
		super(piece.getLoyalty(), node);
		setWorth(QUEEN_WORTH);
		image = (getLoyalty() == Loyalty.RED) ? WHITE_QUEEN: BLACK_QUEEN;
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
			for(int j = -1; j <= 1; j ++)
			{
				boolean openSpace = true;
				int count = 1;
				
				while(openSpace)
				{
					Location currentLoc = new Location(getLoc().getRow() + i*count, getLoc().getCol() + j*count);
					
					if(!getBoard().isValid(currentLoc) || getBoard().getNode(currentLoc).getPiece() != null)
					{
						openSpace = false;
						
						if(getBoard().isValid(currentLoc) && getBoard().getNode(currentLoc).getPiece().getLoyalty() != this.getLoyalty())
						{
							ArrayList<Node> move = new ArrayList<Node>();
							
							move.add(getNode());
							move.add(getBoard().getNode(currentLoc));
							
							possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
						}
					}
					else
					{
						ArrayList<Node> move = new ArrayList<Node>();
						
						move.add(getNode());
						move.add(getBoard().getNode(currentLoc));
						
						possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
					}
					
					count ++;
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
		return new Queen(this, node);
	}
}