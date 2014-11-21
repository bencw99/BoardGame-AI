package game.board;

import game.Game;
import game.Move;
import game.piece.King;
import game.piece.Piece;
import game.piece.Piece.Loyalty;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class representing a checkers board
 * 
 * @author Benjamin Cohen-Wang
 */
public class CheckersBoard extends Board
{
	/** The array of nodes composing the board grid **/
	private Node[][] grid;
	
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
		this.grid = new Node[board.grid.length][board.grid[0].length];
		
		for(int i = 0; i < grid.length; i ++)
		{
			for(int j = 0; j < grid[0].length; j ++)
			{
				this.grid[i][j] = new Node(board.grid[i][j], this);
			}
		}
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a rectangle with given length and width
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public CheckersBoard(int length, int width, Game game)
	{
		super(game);
		this.grid = new Node[length][width];
		initializeNodes();
		loadBoard();
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

		for(int i = 0; i < grid.length; i ++)
		{
			for(int j = 0; j < grid[0].length; j ++)
			{	
				if(grid[i][j].getColor() == Color.BLACK)
				{
					if(piecesLeft[0] > 0)
					{
						put(getGame().getPlayers()[0].getPieces().get(piecesLeft[0] - 1), grid[i][j].getLoc());
						getGame().getPlayers()[0].getPieces().get(piecesLeft[0] - 1).add(grid[i][j]);
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
						put(getGame().getPlayers()[1].getPieces().get(piecesLeft[1] - 1), grid[i][j].getLoc());
						getGame().getPlayers()[1].getPieces().get(piecesLeft[1] - 1).add(grid[i][j]);
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
			remove(nodes.get(0).getLoc());
			
			Piece newKing = new King(Loyalty.RED, nodes.get(0));
			
			add(newKing, nodes.get(0).getLoc());
		}
		
		if(nodes.get(nodes.size() - 1).getLoc().getRow() == 0 && getPiece(nodes.get(0).getLoc()).getLoyalty() == Loyalty.BLACK)
		{
			remove(nodes.get(0).getLoc());
			
			Piece newKing = new King(Loyalty.BLACK, nodes.get(0));
			
			add(newKing, nodes.get(0).getLoc());
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
	
	/**
	 * @return the grid of this board
	 */
	public Node[][] getGrid()
	{
		return grid;
	}

	/**
	 * Initializes the array list of nodes of this game
	 */
	public void initializeNodes()
	{
		for(int i = 0; i < grid.length; i ++)
		{
			for(int j = 0; j < grid[0].length; j ++)
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
	}
}
