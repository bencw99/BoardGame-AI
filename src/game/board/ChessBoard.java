package game.board;

import java.awt.Color;
import java.io.IOException;

import game.Game;
import game.board.node.Location;
import game.board.node.Node;
import game.move.Move;

/**
 * A class representing a chess board
 * 
 * @author Benjamin Cohen-Wang
 */
public class ChessBoard	extends RectangularBoard
{
	/** The default length of the board grid **/
	public static final int CHESS_GRID_LENGTH = 8;
	
	/** The default width of nodes of this board **/
	public static final int NODE_WIDTH =  80;
	
	/** The default height of nodes of this board **/
	public static final int NODE_HEIGHT =  80;
	
	/**
	 * Parameterized constructor, initializes grid to size 8 by 8, and game to given game
	 * 
	 * @param game	the game of this board
	 */
	public ChessBoard(Game game)
	{
		this(CHESS_GRID_LENGTH, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a square with given side length
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public ChessBoard(int length, Game game)
	{
		this(length, length, game);
	}
	
	/**
	 * Parameterized constructor, initializes board to copy of given Board but with no game
	 * 
	 * @param board	the board whose copy is made
	 */
	public ChessBoard(ChessBoard board, Game game)
	{
		super(board, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a rectangle with given length and width
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public ChessBoard(int length, int width, Game game)
	{
		super(length, width, game);
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
					color = Color.WHITE;
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
		
		move(move.getNodes().get(0).getLoc(), move.getNodes().get(move.getNodes().size() - 1).getLoc());
	}
}
