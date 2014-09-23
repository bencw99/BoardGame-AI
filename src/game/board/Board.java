package game.board;

import game.Game;
import game.Move;
import game.piece.King;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.Soldier;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
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
	
	/** The game this board belong to **/
	private Game game;
	
	/** The default length of the board grid **/
	public static final int DEFAULT_GRID_LENGTH = 8;
	
	/** The default width of nodes of this board **/
	public static final int NODE_WIDTH =  80;
	
	/** The default height of nodes of this board **/
	public static final int NODE_HEIGHT =  80;
			
	/**
	 * Parameterized constructor, initializes grid to size 8 by 8, and game to given game
	 * 
	 * @param game	the game of this board
	 */
	public Board(Game game)
	{
		this(DEFAULT_GRID_LENGTH, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a square with given side length
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public Board(int length, Game game)
	{
		this(length, length, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a rectangle with given length and width
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public Board(int length, int width, Game game)
	{
		this.grid = new Node[length][width];
		this.game = game;
		
		for(int i = 0; i < length; i ++)
		{
			for(int j = 0; j < width; j ++)
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
				
				grid[i][j] = new Node(new Location(i, j), this, color); 
			}
		}
		
		loadBoard();
	}
	
	/**
	 * Loads the board grid
	 */
	public void loadBoard()
	{
		int[] piecesLeft = new int[game.getPlayers().length];
		
		for(int i = 0; i < piecesLeft.length; i ++)
		{
			piecesLeft[i] = game.getPlayers()[i].getPieces().size();
		}

		for(int i = 0; i < grid.length; i ++)
		{
			for(int j = 0; j < grid[0].length; j ++)
			{	
				if(grid[i][j].getColor() == Color.BLACK)
				{
					if(piecesLeft[0] > 0)
					{
						add(game.getPlayers()[0].getPieces().get(piecesLeft[0] - 1), grid[i][j].getLoc());
						game.getPlayers()[0].getPieces().get(piecesLeft[0] - 1).add(grid[i][j]);
					}
						
					piecesLeft[0] --;
				}
			}
		}
		
		for(int i = grid.length - 1; i >= 0; i --)
		{
			for(int j = grid[0].length - 1; j >= 0; j --)
			{	
				if(grid[i][j].getColor() == Color.BLACK)
				{
					if(piecesLeft[1] > 0)
					{
						add(game.getPlayers()[1].getPieces().get(piecesLeft[1] - 1), grid[i][j].getLoc());
						game.getPlayers()[1].getPieces().get(piecesLeft[1] - 1).add(grid[i][j]);
					}
						
					piecesLeft[1] --;
				}
			}
		}
	}
	
	/**
	 * Executes the given move
	 * 
	 * @param move	the move to be executed
	 * @throws IOException 
	 */
	public void executeMove(Move move) throws IOException
	{
		for(Node jumped : move.getJumped())
		{
			remove(jumped.getLoc());
		}
		
		ArrayList<Node> nodes = move.getNodes();
		
		if(nodes.get(nodes.size() - 1).getLoc().getRow() == grid.length - 1 && getPiece(nodes.get(0).getLoc()).getLoyalty() == Loyalty.RED)
		{
			add(new King(Loyalty.RED, nodes.get(0)), nodes.get(0).getLoc());
		}
		
		if(nodes.get(nodes.size() - 1).getLoc().getRow() == 0 && getPiece(nodes.get(0).getLoc()).getLoyalty() == Loyalty.BLACK)
		{
			add(new King(Loyalty.BLACK, nodes.get(0)), nodes.get(0).getLoc());
		}
		
		move(nodes.get(0).getLoc(), nodes.get(nodes.size() - 1).getLoc());
	}
	
	/** 
	 * Draws this board
	 * 
	 * @param graphics
	 */
	public void draw(Graphics graphics)
	{
		for(Node[] row : grid)
		{
			for(Node node: row)
			{
				node.draw(graphics);
			}
		}
	}
	
	//ERROR: Add method causes the black checkers player to have its Players ArrayList point to red pieces when red pieces are added to previous black location
	
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
	 * Moves the piece at the start location to the end location
	 * 
	 * @return the piece moved
	 */ 
	public Piece move(Location start, Location end)
	{
		Piece piece = remove(start);
		
		add(piece, end);
		
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
