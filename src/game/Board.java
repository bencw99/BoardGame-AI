package game;

import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.Soldier;

import java.awt.Color;
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
		
		for(int i = 0; i < length; i ++)
		{
			for(int j = 0; j < width; j ++)
			{
				Color color;
				
				if(length*i + j == 0)
				{
					color = Color.RED;
				}
				else
				{
					color = Color.BLACK;
				}
				
				grid[i][j] = new Node(new Location(i, j), color); 
			}
		}
		
		int piecesLeft = pieceNum;

		for(int i = 0; i < length; i ++)
		{
			for(int j = 0; j < width; j ++)
			{
				if(grid[i][j].getColor() == Color.BLACK)
				{
					grid[i][j].add(new Soldier(Loyalty.RED, grid[i][j]));
					grid[length - 1 - i][width - 1 - j].add(new Soldier(Loyalty.BLACK, grid[length - 1 - i][width - 1 - j]));
					
					piecesLeft --;
					
					if(piecesLeft <= 0)
					{
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Adds the given piece to the grid at the given node
	 * 
	 * @param piece	the piece to be added
	 * @param loc	the location to be added to
	 */
	public void add(Piece piece, Location loc)
	{
		piece.add(getNode(loc));
		
		grid[loc.getRow()][loc.getCol()].add(piece);
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
}
