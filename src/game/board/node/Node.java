package game.board.node;

import game.Game;
import game.board.CheckersBoard;
import game.board.RectangularBoard;
import game.piece.Piece;
import game.piece.checkersPieces.King;
import game.piece.checkersPieces.Soldier;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A class representing a checkers board node
 * 
 * @author Benjamin Cohen-Wang
 */
public class Node 
{
	/** The board containing this node **/
	private RectangularBoard board;
	
	/** The location of this node **/
	private Location loc;
	
	/** The piece currently contained in this node **/
	private Piece piece;
	
	/** The color of this node **/
	private Color color;
	
	/** The boolean determining whether or not this node is highlighted **/
	private boolean isHighlighted;
	
	private static final Color DEFAULT_HIGHLIGHT_COLOR = new Color(255, 255, 255);
	
	/**
	 * Parameterized constructor, initializes Node location
	 */
	public Node(Location loc, RectangularBoard board)
	{
		this.loc = loc;
		this.board = board;
	}
	
	/**
	 * Parameterized constructor, initializes color to given color, and location to given values
	 */
	public Node(Location loc, RectangularBoard board, Color color)
	{
		this(loc, board);
		this.color = color;
	}
	
	/**
	 * Parameterized constructor, initializes node to given node copy (has no board)
	 */
	public Node(Node node, RectangularBoard board)
	{
		this.color = node.color;
		this.loc = new Location(node.loc);
		this.board = board;
		
		if(node.getPiece() == null)
		{
			this.piece = null;
		}
		else
		{
			try
			{
				this.piece = node.getPiece().clone(this);
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
//			Constructor pieceConstructor = null;
//			try
//			{
//				pieceConstructor = node.getPiece().getClass().getConstructor(Piece.class, this.getClass());
//			} 
//			catch (SecurityException e)
//			{
//				e.printStackTrace();
//			} 
//			catch (NoSuchMethodException e)
//			{
//				e.printStackTrace();
//			}
//
//			try
//			{
//				this.piece = (Piece) pieceConstructor.newInstance(node.getPiece(), this);
//			} 
//			catch (IllegalArgumentException e)
//			{
//				e.printStackTrace();
//			} 
//			catch (InstantiationException e)
//			{
//				e.printStackTrace();
//			} 
//			catch (IllegalAccessException e)
//			{
//				e.printStackTrace();
//			} 
//			catch (InvocationTargetException e)
//			{
//				e.printStackTrace();
//			}
		}
	}
	
	/**
	 * Draws this node
	 * 
	 * @param graphics	 the graphics object to be drawn on
	 */
	public void draw(Graphics graphics)
	{
		graphics.setColor(color);
		graphics.fillRect(getLoc().getCol()*getBoard().getNodeHeight(), getLoc().getRow()*getBoard().getNodeWidth(), getBoard().getNodeHeight(), getBoard().getNodeWidth());
		
		if(isHighlighted)
		{
			graphics.setColor(DEFAULT_HIGHLIGHT_COLOR);
			graphics.drawRect(getLoc().getCol()*getBoard().getNodeHeight(), getLoc().getRow()*getBoard().getNodeWidth(), getBoard().getNodeHeight() - 1, getBoard().getNodeWidth() - 1);
			graphics.drawRect(getLoc().getCol()*getBoard().getNodeHeight() + 1, getLoc().getRow()*getBoard().getNodeWidth() + 1, getBoard().getNodeHeight() - 3, getBoard().getNodeWidth() - 3);
		}
		
		if(piece != null)
		{
			piece.draw(graphics);
		}
	}
	
	public String toString()
	{
		return ("(" + loc.getRow() + ", " + loc.getCol() + ")");
	}
	
	/**
	 * Adds the given piece to this node
	 * 
	 * @param piece	the piece added to this node
	 */ 
	public void add(Piece piece)
	{
		this.piece = piece;
	}
	
	/**
	 * Returns the piece contained in this node
	 * 
	 * @return the piece contained in this node
	 */ 
	public Piece getPiece()
	{
		return piece;
	}
	
	/**
	 * Returns the location of this node
	 * 
	 * @return the location of this node
	 */ 
	public Location getLoc()
	{
		return loc;
	}
	
	/**
	 * Returns the board of this node
	 * 
	 * @return the board of this node
	 */ 
	public RectangularBoard getBoard()
	{
		return board;
	}
	
	/**
	 * Returns the game of this node
	 * 
	 * @return the game of this node
	 */ 
	public Game getGame()
	{
		return board.getGame();
	}
	
	/**
	 * Returns the color of this node
	 * 
	 * @return the color of this node
	 */ 
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Sets the highlight of this node
	 * 
	 * @param isHighlighted	the highlight state to be state
	 */
	public void setHighlighted(boolean isHighlighted)
	{
		this.isHighlighted = isHighlighted;
	}
}
