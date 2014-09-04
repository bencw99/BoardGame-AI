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
	 * Adds the given piece to the grid at the given node
	 * 
	 * @param piece	the piece to be added
	 * @param loc	the location to be added to
	 */
	public void add(Piece piece, Location loc)
	{
		piece.add(this, loc);
		
		grid[loc.getRow()][loc.getCol()].add(piece);
	}
	
	/**
	 * Returns the piece at the given location
	 * 
	 * @return the piece at the given location
	 */ 
	public boolean isValid(Location loc)
	{
		if(loc.getRow() < grid.length && loc.getCol() < grid[0].length)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns the node at the given location
	 * 
	 * @return the node at the given location
	 */ 
	public Node getNode(Location loc)
	{
		return grid[loc.getRow()][loc.getCol()];
	}
	
	/**
	 * Returns the piece at the given location
	 * 
	 * @return the piece at the given location
	 */ 
	public Piece getPiece(Location loc)
	{
		return grid[loc.getRow()][loc.getCol()].getPiece();
	}
	
	/**
	 * Returns the piece at the given location and removes it from the board
	 * 
	 * @return the piece at the given location
	 */ 
	public Piece remove(Location loc)
	{
		Piece piece = getPiece(loc);
		
		add(null, loc);
		
		return piece;
	}
}
