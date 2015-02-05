package game.piece;

import game.Game;
import game.board.RectangularBoard;
import game.board.node.Location;
import game.board.node.Node;
import game.move.Move;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A class representing a checkers piece
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class Piece implements ImageObserver 
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
	
	/** The boolean representing whether or not this piece has moved **/
	private boolean hasMoved;
	
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
	public static BufferedImage BLACK_CHECKER_KING;
	
	/** The image for a red king checker **/
	public static BufferedImage RED_CHECKER_KING;
	
	/** The image for a white pawn **/
	public static BufferedImage WHITE_PAWN;
	
	/** The image for a white rook **/
	public static BufferedImage WHITE_ROOK;
	
	/** The image for a white knight **/
	public static BufferedImage WHITE_KNIGHT;
	
	/** The image for a white bishop **/
	public static BufferedImage WHITE_BISHOP;
	
	/** The image for a white king **/
	public static BufferedImage WHITE_KING;
	
	/** The image for a white queen **/
	public static BufferedImage WHITE_QUEEN;
	
	/** The image for a black pawn **/
	public static BufferedImage BLACK_PAWN;
	
	/** The image for a black rook **/
	public static BufferedImage BLACK_ROOK;
	
	/** The image for a black knight **/
	public static BufferedImage BLACK_KNIGHT;
	
	/** The image for a black bishop **/
	public static BufferedImage BLACK_BISHOP;
	
	/** The image for a black king **/
	public static BufferedImage BLACK_KING;
	
	/** The image for a black queen **/
	public static BufferedImage BLACK_QUEEN;
	
	/**
	 * Default constructor
	 * 
	 * @param loyalty	the value the loyalty of this piece
	 * @throws IOException 
	 */
	public Piece(Loyalty loyalty) throws IOException
	{
		this.loyalty = loyalty;
		this.hasMoved = false;
		
		if(!imagesInitialized)
		{
			imagesInit();
		}
	}
	
	/**
	 * Parameterized constructor, initializes loyalty and node to given variables
	 * 
	 * @param node	the node of this instance on the board
	 * @param loyalty	the value the loyalty is set to
	 * @throws IOException 
	 */
	public Piece(Loyalty loyalty, Node node) throws IOException
	{
		this(loyalty);
		this.node = node;
		this.hasMoved = false;
		
		if(!imagesInitialized)
		{
			imagesInit();
		}
	}
	
	/**
	 * Parameterized constructor, initializes this to a copy of the given piece
	 * 
	 * @param piece	the piece to be copied
	 * @param node	the node to be added to
	 * @throws IOException 
	 */
	public Piece(Piece piece, Node node) throws IOException
	{
		this(piece.loyalty, node);
		this.hasMoved = false;
		
		if(!imagesInitialized)
		{
			imagesInit();
		}
	}
	
	/**
	 * Draws this piece
	 * 
	 * @param graphics	the graphics object to be drawn on
	 */
	public void draw(Graphics graphics) 
	{
		int widthOffset = (getBoard().getNodeWidth() - image.getWidth())/2;
		int heightOffset = (getBoard().getNodeHeight() - image.getHeight())/2;
		
		graphics.drawImage(image, getLoc().getCol()*getBoard().getNodeHeight() + widthOffset, getLoc().getRow()*getBoard().getNodeWidth() + heightOffset, this);
	}
	
	/**
	 * Returns the possible move this piece can do
	 * 
	 * @return	the array list of possible moves this piece execute
	 */
	public abstract ArrayList<Move> getPossibleMoves();
	
	/**
	 * Returns a clone of this piece at the given node
	 * 
	 * @param node	the node to be cloned to
	 * @return	the cloned piece
	 */
	public abstract Piece clone(Node node) throws IOException;
	
	/**
	 * @return a string representation of this piece
	 */
	public String toString()
	{
		return loyalty.toString() + this.getClass().toString();
	}
	
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
	 * @return the board of this piece
	 */
	public RectangularBoard getBoard()
	{
		return getNode().getBoard();
	}
	
	/**
	 * @return the board of this piece
	 */
	public Game getGame()
	{
		return getNode().getBoard().getGame();
	}
	
	/**
	 * @return the location of this piece
	 */
	public Location getLoc()
	{
		return getNode().getLoc();
	}

	/**
	 * @return the worth
	 */
	public int getWorth() 
	{
		return worth;
	}

	/**
	 * @return	has moved
	 */
	public boolean hasMoved()
	{
		return hasMoved;
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
	 * @param hasMoved	the moved boolean to be set
	 */
	public void setHasMoved(boolean hasMoved)
	{
		this.hasMoved = hasMoved;
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
	{
		return false;
	}

	/**
	 * Initializes images
	 * 
	 * @throws IOException
	 */
	public static void imagesInit() throws IOException
	{
		BLACK_CHECKER = ImageIO.read(new File("CheckersPieces/black-checker.png"));
		RED_CHECKER = ImageIO.read(new File("CheckersPieces/red-checker.png"));
		BLACK_CHECKER_KING = ImageIO.read(new File("CheckersPieces/black-king.png"));
		RED_CHECKER_KING = ImageIO.read(new File("CheckersPieces/red-king.png"));
		
		WHITE_PAWN = ImageIO.read(new File("ChessPieces/WhitePawn.png"));
		BLACK_PAWN = ImageIO.read(new File("ChessPieces/BlackPawn.png"));
		WHITE_ROOK = ImageIO.read(new File("ChessPieces/WhiteRook.png"));
		BLACK_ROOK = ImageIO.read(new File("ChessPieces/BlackRook.png"));
		WHITE_KNIGHT = ImageIO.read(new File("ChessPieces/WhiteKnight.png"));
		BLACK_KNIGHT = ImageIO.read(new File("ChessPieces/BlackKnight.png"));
		WHITE_BISHOP = ImageIO.read(new File("ChessPieces/WhiteBishop.png"));
		BLACK_BISHOP = ImageIO.read(new File("ChessPieces/BlackBishop.png"));
		WHITE_KING = ImageIO.read(new File("ChessPieces/WhiteKing.png"));
		BLACK_KING = ImageIO.read(new File("ChessPieces/BlackKing.png"));
		WHITE_QUEEN = ImageIO.read(new File("ChessPieces/WhiteQueen.png"));
		BLACK_QUEEN = ImageIO.read(new File("ChessPieces/BlackQueen.png"));
		
		imagesInitialized = true;
	}
}
