package game.piece;

import game.Move;
import game.board.Location;
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
	public static enum Loyalty {BLACK, RED};
	
	/** The node that this piece belongs to **/
	private Node node;
	
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
	 * Returns the possible nodes this piece can jump to
	 * 
	 * @return	the array list of possible nodes this piece can jump to
	 */
	protected abstract ArrayList<ArrayList<Node>> getNextJumps(Location loc);
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
	 * @return the loyalty of this piece
	 */
	public Loyalty getLoyalty()
	{
		return loyalty;
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
