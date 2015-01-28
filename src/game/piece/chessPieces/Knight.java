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
 * A class representing a chess knight
 * 
 * @author Benjamin Cohen-Wang
 */
public class Knight extends Piece
{
	/** The worth of a knight **/
	public static final int KNIGHT_WORTH = 3;
	
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 * @throws IOException 
	 */
	public Knight(Loyalty loyalty) throws IOException
	{
		super(loyalty);
		setWorth(KNIGHT_WORTH);
		image = (getLoyalty() == Loyalty.RED) ? WHITE_KNIGHT: BLACK_KNIGHT;
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 * @throws IOException 
	 */
	public Knight(Loyalty loyalty, Node node) throws IOException
	{
		super(loyalty, node);
		setWorth(KNIGHT_WORTH);
		image = (getLoyalty() == Loyalty.RED) ? WHITE_KNIGHT: BLACK_KNIGHT;
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 * @throws IOException 
	 */
	public Knight(Piece piece, Node node) throws IOException
	{
		super(piece.getLoyalty(), node);
		setWorth(KNIGHT_WORTH);
		image = (getLoyalty() == Loyalty.RED) ? WHITE_KNIGHT: BLACK_KNIGHT;
	}

	/**
	 * Returns the possible move this piece can do
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		for(int i = -2; i <= 2; i += 4)
		{
			for(int j = -1; j <= 1; j += 2)
			{
				Location moveLoc = new Location(getLoc().getRow() + i, getLoc().getCol() + j);
				
				if(getBoard().isValid(moveLoc) && (getBoard().getNode(moveLoc).getPiece() == null || getBoard().getNode(moveLoc).getPiece().getLoyalty() != this.getLoyalty()))
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getBoard().getNode(moveLoc));
					
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
				}
			}
		}
		
		for(int i = -1; i <= 1; i += 2)
		{
			for(int j = -2; j <= 2; j += 4)
			{
				Location moveLoc = new Location(getLoc().getRow() + i, getLoc().getCol() + j);
				
				if(getBoard().isValid(moveLoc) && (getBoard().getNode(moveLoc).getPiece() == null || getBoard().getNode(moveLoc).getPiece().getLoyalty() != this.getLoyalty()))
				{
					ArrayList<Node> move = new ArrayList<Node>();
					
					move.add(getNode());
					move.add(getBoard().getNode(moveLoc));
					
					possibleMoves.add(new ChessMove(move, getBoard(), getLoyalty()));
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
		return new Knight(this, node);
	}
}
