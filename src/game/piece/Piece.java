package game.piece;

import game.Move;
import game.board.Node;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A class representing a checkers piece
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class Piece 
{
	/** The enum describing the loyalty of this piece **/
	public static enum Loyalty 
	{
		BLACK(1), 
		RED(0);
		
		private int val;
		
		Loyalty(int val)
		{
			this.val = val;
		}
		
		public int getVal()
		{
			return val;
		}
	};
	
	/** The node that this piece belongs to **/
	private Node node;
	
	/** The worth of this piece **/
	private int worth;
	
	/** The loyalty of this piece **/
	private Loyalty loyalty;
	
	/** The image of this piece **/
	protected BufferedImage image;
	
	/**The boolean describing the initialization state of the checkers images **/
	protected static boolean imagesInitialized = false;
	
	/** The image for a black checker **/
	public static BufferedImage BLACK_CHECKER;
	
	/** The image for a red checker **/
	public static BufferedImage RED_CHECKER;
	
	/** The image for a black king checker **/
	public static BufferedImage BLACK_KING;
	
	/** The image for a red king checker **/
	public static BufferedImage RED_KING;
	
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 */
	public Piece(Loyalty loyalty)
	{
		this.loyalty = loyalty;
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 */
	public Piece(Loyalty loyalty, Node node)
	{
		this(loyalty);
		this.node = node;
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 */
	public Piece(Piece piece, Node node)
	{
		this(piece.loyalty, node);
	}
	
	/**
	 * Returns the possible move this piece can do
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public abstract ArrayList<Move> getPossibleMoves();
	
	/**
	 * Draws this piece
	 * 
	 * @param graphics	the grapics object to be drawn on
	 */
	public abstract void draw(Graphics graphics);
	
	/**
	 * Adds this instance to the given node
	 * 
	 * @param node	the node this instance is added to
	 */
	public void add(Node node)
	{
		this.node = node;
	}
	
	/**
	 * @return the node of this piece
	 */
	public Node getNode()
	{
		return node;
	}

	/**
	 * @return the worth
	 */
	public int getWorth() 
	{
		return worth;
	}

	/**
	 * @return the loyalty of this piece
	 */
	public Loyalty getLoyalty()
	{
		return loyalty;
	}
	
	/**
	 * @param worth the worth to set
	 */
	public void setWorth(int worth) 
	{
		this.worth = worth;
	}

	/**
	 * Initializes checkers images
	 * 
	 * @throws IOException
	 */
	public static void checkerImagesInit() throws IOException
	{
		BLACK_CHECKER = ImageIO.read(new File("black-checker.png"));
		RED_CHECKER = ImageIO.read(new File("red-checker.png"));
		BLACK_KING = ImageIO.read(new File("black-king.png"));
		RED_KING = ImageIO.read(new File("red-king.png"));
		
		imagesInitialized = true;
	}
}
