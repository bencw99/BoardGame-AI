package game.board;

import game.Game;
import game.board.node.Location;
import game.board.node.Node;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.checkersPieces.King;

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
			
			add(newKing, nodes.get(0).getLoc());
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
			
			add(newKing, nodes.get(0).getLoc());
		}
		
		move(nodes.get(0).getLoc(), nodes.get(nodes.size() - 1).getLoc());
	}

	/**
	 * Loads the board grid
	 */
	public void loadBoard()
	{
		int[] piecesLeft = new int[getGame().getPlayers().length];
		
		for(int i = 0; i < piecesLeft.length; i ++)
		{
			piecesLeft[i] = getGame().getPlayers()[i].getPieces().size();
		}

		for(int i = 0; i < getGrid().length; i ++)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{	
				if(getGrid()[i][j].getColor() == Color.BLACK)
				{
					if(piecesLeft[0] > 0)
					{
						put(getGame().getPlayers()[0].getPieces().get(piecesLeft[0] - 1), getGrid()[i][j].getLoc());
						getGame().getPlayers()[0].getPieces().get(piecesLeft[0] - 1).add(getGrid()[i][j]);
					}
						
					piecesLeft[0] --;
				}
			}
		}
		
		for(int i = getGrid().length - 1; i >= 0; i --)
		{
			for(int j = getGrid()[0].length - 1; j >= 0; j --)
			{	
				if(getGrid()[i][j].getColor() == Color.BLACK)
				{
					if(piecesLeft[1] > 0)
					{
						put(getGame().getPlayers()[1].getPieces().get(piecesLeft[1] - 1), getGrid()[i][j].getLoc());
						getGame().getPlayers()[1].getPieces().get(piecesLeft[1] - 1).add(getGrid()[i][j]);
					}
						
					piecesLeft[1] --;
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
