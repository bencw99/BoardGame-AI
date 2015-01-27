package game.board;

import game.Game;
import game.board.node.Location;
import game.board.node.Node;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.checkersPieces.King;
import game.piece.checkersPieces.Soldier;
import game.piece.chessPieces.Bishop;
import game.piece.chessPieces.Knight;
import game.piece.chessPieces.Pawn;
import game.piece.chessPieces.Queen;
import game.piece.chessPieces.Rook;
import game.player.Player;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class representing a checkers board
 * 
 * @author Benjamin Cohen-Wang
 */
public class CheckersBoard extends RectangularBoard
{	
	/** The default length of the board grid **/
	public static final int CHECKERS_GRID_LENGTH = 8;
	
	/** The default width of nodes of this board **/
	public static final int NODE_WIDTH =  80;
	
	/** The default height of nodes of this board **/
	public static final int NODE_HEIGHT =  80;
	
	/** **/
	public static final int CHECKERS_PIECE_NUM = 12;
	
	/**
	 * Parameterized constructor, initializes grid to size 8 by 8, and game to given game
	 * 
	 * @param game	the game of this board
	 */
	public CheckersBoard(Game game)
	{
		this(CHECKERS_GRID_LENGTH, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a square with given side length
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public CheckersBoard(int length, Game game)
	{
		this(length, length, game);
	}
	
	/**
	 * Parameterized constructor, initializes board to copy of given Board but with no game
	 * 
	 * @param board	the board whose copy is made
	 */
	public CheckersBoard(CheckersBoard board, Game game)
	{
		super(board, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a rectangle with given length and width
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public CheckersBoard(int length, int width, Game game)
	{
		super(length, width, game);
	}
	
	/**
	 * Executes the given move
	 * 
	 * @param move	the move to be executed
	 * @throws IOException 
	 */
	public void executeMove(Move move)
	{
		for(Node jumped : move.getJumped())
		{
			remove(jumped.getLoc());
		}
		
		ArrayList<Node> nodes = move.getNodes();
		
		if(nodes.get(nodes.size() - 1).getLoc().getRow() == getGrid().length - 1 && getPiece(nodes.get(0).getLoc()).getLoyalty() == Loyalty.RED)
		{
			remove(nodes.get(0).getLoc());
			
			Piece newKing = null;
			try
			{
				newKing = new King(Loyalty.RED, nodes.get(0));
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			put(newKing, nodes.get(0).getLoc());
		}
		
		if(nodes.get(nodes.size() - 1).getLoc().getRow() == 0 && getPiece(nodes.get(0).getLoc()).getLoyalty() == Loyalty.BLACK)
		{
			remove(nodes.get(0).getLoc());
			
			Piece newKing = null;
			try
			{
				newKing = new King(Loyalty.BLACK, nodes.get(0));
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			put(newKing, nodes.get(0).getLoc());
		}
		
		move(nodes.get(0).getLoc(), nodes.get(nodes.size() - 1).getLoc());
	}

	/**
	 * Loads the board grid
	 */
	public void loadBoard()
	{
		ArrayList<Piece> p1Pieces = new ArrayList<Piece>();
		ArrayList<Piece> p2Pieces = new ArrayList<Piece>();
		
		try
		{
			for(int i = 0; i < CHECKERS_PIECE_NUM; i ++)
			{
				p1Pieces.add(new Soldier(Loyalty.RED));
				p2Pieces.add(new Soldier(Loyalty.BLACK));
			}
		}
		catch(IOException e)
		{
			
		}
		
		int p1PiecesLeft = p1Pieces.size();
		int p2PiecesLeft = p2Pieces.size();
		
		for(int i = 0; i < getGrid().length; i ++)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{	
				if(getGrid()[i][j].getColor() == Color.BLACK)
				{
					if(p1PiecesLeft > 0)
					{
						put(p1Pieces.get(p1PiecesLeft - 1), getGrid()[i][j].getLoc());
					}
							
					p1PiecesLeft --;
				}
			}
		}
		
		for(int i = getGrid().length - 1; i >= 0; i --)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{	
				if(getGrid()[i][j].getColor() == Color.BLACK)
				{
					if(p2PiecesLeft > 0)
					{
						put(p2Pieces.get(p2PiecesLeft - 1), getGrid()[i][j].getLoc());
					}
							
					p2PiecesLeft --;
				}
			}
		}
	}
	
	/**
	 * Initializes the array list of nodes of this game
	 */
	public void initializeNodes()
	{
		for(int i = 0; i < getGrid().length; i ++)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{
				Color color;
				
				if((i + j) % 2 == 0)
				{
					color = Color.RED;
				}
				else
				{
					color = Color.BLACK;
				}
				
				getGrid()[i][j] = new Node(new Location(i, j), this, color); 
			}
		}
	}
	
	/**
	 * Gets the possible moves of the given loyalty
	 * 
	 * @param loyalty	the loyalty to be tested
	 */
	public ArrayList<Move> getPossibleMoves(Loyalty loyalty)
	{
		ArrayList<Move> possibleMoves = super.getPossibleMoves(loyalty);
		ArrayList<Move> jumpMoves = new ArrayList<Move>();
		
		for(Move possibleMove : possibleMoves)
		{
			if(!possibleMove.getJumped().isEmpty())
			{
				jumpMoves.add(possibleMove);
			}
		}
		
		if(jumpMoves.isEmpty())
		{
			return possibleMoves;
		}
		else
		{
			return jumpMoves;
		}	
	}
	
	/**
	 * Returns a clone of this board with the given game
	 * 
	 * @param game	the game to be cloned with
	 * @return	the cloned board
	 * @throws IOException 
	 */
	public RectangularBoard clone(Game game)
	{
		return new CheckersBoard(game);
	}
	
	/**
	 * @return the node width of this board
	 */
	public int getNodeWidth()
	{
		return NODE_WIDTH;
	}

	/**
	 * @return the node height of this board
	 */
	public int getNodeHeight()
	{
		return NODE_HEIGHT;
	}
}
