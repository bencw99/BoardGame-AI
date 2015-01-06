package game.board;

import java.awt.Color;

import game.Game;
import game.board.node.Location;
import game.board.node.Node;
import game.piece.Piece;

/**
 * A class representing a rectangular board
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class RectangularBoard extends Board
{
	/** The rectangular grid making up this rectangular board **/
	private Node[][] grid;
	
	/**
	 * Parameterized constructor, initializes board to given length, width, and games
	 * @param length the length of this rectangular board
	 * @param width the width of this rectangular board
	 * @param game the game of this rectangular board
	 */
	public RectangularBoard(int length, int width, Game game)
	{
		super(game);
		this.grid = new Node[length][width];
		initializeNodes();
		loadBoard();
		
		for(Node[] row : grid)
		{
			for(Node node : row)
			{
				getNodes().add(node);
			}
		}
	}
	
	/**
	 * Parameterized constructor, initializes board to copy of given Board but with no game
	 * 
	 * @param board	the board whose copy is made
	 */
	public RectangularBoard(RectangularBoard board, Game game)
	{
		super(game);
		this.grid = new Node[board.grid.length][board.grid[0].length];
		
		for(int i = 0; i < grid.length; i ++)
		{
			for(int j = 0; j < grid[0].length; j ++)
			{
				this.grid[i][j] = new Node(board.grid[i][j], this);
			}
		}
		
		for(Node[] row : grid)
		{
			for(Node node : row)
			{
				getNodes().add(node);
			}
		}
	}
	
	/**
	 * Loads the board grid
	 */
	public abstract void loadBoard();
	
	/**
	 * Adds the given piece to the grid at the given node
	 * 
	 * @param piece	the piece to be added
	 * @param loc	the location to be added to
	 */
	public void add(Piece piece, Location loc)
	{
		if(piece != null)
		{
			piece.add(getNode(loc));
			getGame().getPlayers()[piece.getLoyalty().getVal()].add(piece);
		}
		
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
	 * Puts the given piece to the grid at the given node
	 * 
	 * @param piece	the piece to be put
	 * @param loc	the location to be put in
	 */
	public void put(Piece piece, Location loc)
	{
		if(piece != null)
		{
			piece.add(getNode(loc));
		}
		
		grid[loc.getRow()][loc.getCol()].add(piece);
	}
	
	/**
	 * Moves the piece at the start location to the end location
	 * 
	 * @return the piece moved
	 */ 
	public Piece move(Location start, Location end)
	{
		Piece piece = getPiece(start);
		
		put(null, start);
		
		put(piece, end);
		
		return piece;
	}
	
	/**
	 * Returns the piece at the given location and removes it from the board
	 * 
	 * @return the piece at the given location
	 */ 
	public Piece remove(Location loc)
	{
		Piece piece = getPiece(loc);
		
		if(piece != null)
		{
			getGame().getPlayers()[piece.getLoyalty().getVal()].remove(piece);
			
			put(null, loc);
		}
		
		return piece;
	}
	
	/**
	 * @return the grid of this board
	 */
	public Node[][] getGrid()
	{
		return grid;
	}
	
	/**
	 * Returns the piece at the given location
	 * 
	 * @return the piece at the given location
	 */ 
	public boolean isValid(Location loc)
	{
		if(loc.getRow() >= 0 && loc.getRow() < grid.length && loc.getCol() >= 0 && loc.getCol() < grid[0].length)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
