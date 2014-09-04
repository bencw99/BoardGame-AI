package game;

import java.util.ArrayList;

/**
 * A class representing a checkers board
 * 
 * @author Benjamin Cohen-Wang
 */
public class Board 
{
	/** The array of nodes composing the board grid **/
	private Node[][] grid;
	
	/** The default length of the board grid **/
	public final static int DEFAULT_GRID_LENGTH = 8;
	
	/** The default number of pieces on the checkers grid **/
	public final static int DEFAULT_PIECE_NUM = 12;
	
	/**
	 * Default constructor, initializes grid to size 8 by 8
	 */
	public Board()
	{
		this(DEFAULT_GRID_LENGTH, DEFAULT_PIECE_NUM);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a square with given side length
	 * 
	 * @param length	the side length of the grid
	 * @param pieceNum	the number of pieces of each player
	 */
	public Board(int length, int pieceNum)
	{
		this(length, length, pieceNum);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a rectangle with given length and width
	 * 
	 * @param length	the side length of the grid
	 * @param pieceNum	the number of pieces of each player
	 */
	public Board(int length, int width, int pieceNum)
	{
		grid = new Node[length][width];
	}
	
	/**
	 * Adds the given piece to the grid at the default node
	 * 
	 * @param piece	the piece to be added
	 */
	public void add(Piece piece)
	{
		//Adds to default node
	}
	
	/**
	 * Adds the given piece to the grid at the given node
	 * 
	 * @param piece	the piece to be added
	 * @param node	the node to be
	 */
	public void add(Piece piece, Node node)
	{
		piece.add(this, node);
	}
	
	/**
	 * Returns the piece at the given location
	 * 
	 * @return the piece at the given location
	 */ 
	public boolean isValid(int row, int col)
	{
		if(row < grid.length && col < grid[0].length)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns the piece at the given location
	 * 
	 * @return the piece at the given location
	 */ 
	public Piece get (int row, int col)
	{
		return grid[row][col].getPiece();
	}
}
