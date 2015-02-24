package game.board;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import game.Game;
import game.board.node.Location;
import game.board.node.Node;
import game.move.ChessMove;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.chessPieces.Bishop;
import game.piece.chessPieces.King;
import game.piece.chessPieces.Knight;
import game.piece.chessPieces.Pawn;
import game.piece.chessPieces.Queen;
import game.piece.chessPieces.Rook;

/**
 * A class representing a chess board
 * 
 * @author Benjamin Cohen-Wang
 */
public class ChessBoard	extends RectangularBoard
{
	/** The default length of the board grid **/
	public static final int CHESS_GRID_LENGTH = 8;
	
	/** The default width of nodes of this board **/
	public static final int NODE_WIDTH =  80;
	
	/** The default height of nodes of this board **/
	public static final int NODE_HEIGHT =  80;
	
	/**
	 * Parameterized constructor, initializes grid to size 8 by 8, and game to given game
	 * 
	 * @param game	the game of this board
	 */
	public ChessBoard(Game game)
	{
		this(CHESS_GRID_LENGTH, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a square with given side length
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public ChessBoard(int length, Game game)
	{
		this(length, length, game);
	}
	
	/**
	 * Parameterized constructor, initializes board to copy of given Board but with no game
	 * 
	 * @param board	the board whose copy is made
	 */
	public ChessBoard(ChessBoard board, Game game)
	{
		super(board, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a rectangle with given length and width
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public ChessBoard(int length, int width, Game game)
	{
		super(length, width, game);
	}

	/**
	 * Initializes the array list of nodes of this game
	 */
	public void initializeNodes()
	{
		for(int i = 0; i < getGrid().length; i ++)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{
				Color color;
				
				if((i + j) % 2 == 0)
				{
					color = new Color(222, 185, 119);
				}
				else
				{
					color = new Color(255, 214, 140);
				}
				
				getGrid()[i][j] = new Node(new Location(i, j), this, color); 
			}
		}
	}

	/**
	 * Loads the board grid
	 */
	public void loadBoard()
	{
		ArrayList<Piece> p1Pieces = new ArrayList<Piece>();
		ArrayList<Piece> p2Pieces = new ArrayList<Piece>();
		
		try
		{
			for(int i = 0; i < 8; i ++)
			{
				p1Pieces.add(new Pawn(Loyalty.RED));
			}
			p1Pieces.add(new Rook(Loyalty.RED));
			p1Pieces.add(new Knight(Loyalty.RED));
			p1Pieces.add(new Bishop(Loyalty.RED));
			p1Pieces.add(new King(Loyalty.RED));
			p1Pieces.add(new Queen(Loyalty.RED));
			p1Pieces.add(new Bishop(Loyalty.RED));
			p1Pieces.add(new Knight(Loyalty.RED));
			p1Pieces.add(new Rook(Loyalty.RED));
	
			for(int i = 0; i < 8; i ++)
			{
				p2Pieces.add(new Pawn(Loyalty.BLACK));
			}
			p2Pieces.add(new Rook(Loyalty.BLACK));
			p2Pieces.add(new Knight(Loyalty.BLACK));
			p2Pieces.add(new Bishop(Loyalty.BLACK));
			p2Pieces.add(new King(Loyalty.BLACK));
			p2Pieces.add(new Queen(Loyalty.BLACK));
			p2Pieces.add(new Bishop(Loyalty.BLACK));
			p2Pieces.add(new Knight(Loyalty.BLACK));
			p2Pieces.add(new Rook(Loyalty.BLACK));
		}
		catch(IOException e)
		{
			
		}
		
		int p1PiecesLeft = p1Pieces.size();
		int p2PiecesLeft = p2Pieces.size();
		
		for(int i = 0; i < getGrid().length; i ++)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{	
				if(p1PiecesLeft > 0)
				{
					put(p1Pieces.get(p1PiecesLeft - 1), getGrid()[i][j].getLoc());
				}
						
				p1PiecesLeft --;
			}
		}
		
		for(int i = getGrid().length - 1; i >= 0; i --)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{	
				if(p2PiecesLeft > 0)
				{
					put(p2Pieces.get(p2PiecesLeft - 1), getGrid()[i][j].getLoc());
				}
						
				p2PiecesLeft --;
			}
		}
	}
	
	/**
	 * Executes the given move
	 * 
	 * @param move	the move to be executed
	 * @throws IOException 
	 */
	public void executeMove(Move move)
	{	
		ArrayList<Node> nodes = move.getNodes();
		
		for(Node jumped : move.getJumped())
		{
			remove(jumped.getLoc());
		}
		
		Node initialNode = nodes.get(0);
		Node terminalNode = nodes.get(nodes.size() - 1);
		Piece movedPiece = initialNode.getPiece();
		
		if(movedPiece instanceof King)
		{
			if(terminalNode.getLoc().getCol() - initialNode.getLoc().getCol() == 2)
			{
				Location locBeside = new Location(terminalNode.getLoc().getRow(), terminalNode.getLoc().getCol() + 1);
				Location otherLocBeside = new Location(terminalNode.getLoc().getRow(), terminalNode.getLoc().getCol() - 1);

				Piece pieceBeside = getNode(locBeside).getPiece();

				if(pieceBeside instanceof Rook && !pieceBeside.hasMoved())
				{
					move(locBeside, otherLocBeside);
				}
			}
			
			if(terminalNode.getLoc().getCol() - initialNode.getLoc().getCol() == -2)
			{
				Location locBeside = new Location(terminalNode.getLoc().getRow(), terminalNode.getLoc().getCol() - 2);
				Location otherLocBeside = new Location(terminalNode.getLoc().getRow(), terminalNode.getLoc().getCol() + 1);
				
				Piece pieceBeside = getNode(locBeside).getPiece();
						
				if(pieceBeside instanceof Rook && !pieceBeside.hasMoved())
				{
					move(locBeside, otherLocBeside);
				}
			}
		}
		
		if(movedPiece instanceof Pawn)
		{
			if(nodes.get(nodes.size() - 1).getLoc().getRow() == (1 - getPiece(nodes.get(0).getLoc()).getLoyalty().getVal()*(getGrid()).length - 1))
			{	
				Piece promoted = null;
				
				try
				{
					Constructor<? extends Piece> constructor = ((ChessMove) move).getPromotionType().getConstructor(Loyalty.class, Node.class);
					
					promoted = (Piece) constructor.newInstance(nodes.get(0).getPiece().getLoyalty(), nodes.get(0));
				} 
				catch (SecurityException e)
				{
					e.printStackTrace();
				} 
				catch (NoSuchMethodException e)
				{
					e.printStackTrace();
				} 
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				} 
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.printStackTrace();
				}
				
				remove(nodes.get(0).getLoc());
				
				put(promoted, nodes.get(0).getLoc());
			}
		}
		
		move(initialNode.getLoc(), terminalNode.getLoc());
	}

	/**
	 * Loads the types of pieces present in this board
	 */
	protected void pieceTypesInit()
	{
		PIECE_TYPES = new ArrayList<Class<? extends Piece>>();
		
		PIECE_TYPES.add(King.class);
		PIECE_TYPES.add(Queen.class);
		PIECE_TYPES.add(Rook.class);
		PIECE_TYPES.add(Bishop.class);
		PIECE_TYPES.add(Knight.class);
		PIECE_TYPES.add(Pawn.class);
	}
	
	/**
	 * Gets the possible moves of the given loyalty
	 * 
	 * @param loyalty	the loyalty to be tested
	 */
	public ArrayList<Move> getPossibleMoves(Loyalty loyalty)
	{
		ArrayList<Move> possibleMoves = super.getPossibleMoves(loyalty);
		ArrayList<Move> realPossibleMoves = new ArrayList<Move>();
		
		for(Move possibleMove : possibleMoves)
		{
			boolean possible = true;
			
			Game nextGame = new Game(getGame());
			
			Board nextBoard = nextGame.getBoard();
			
			nextBoard.executeMove(possibleMove);
			
			for(Node node : nextBoard.getNodes())
			{
				Piece checkCandidate = null;
				
				if(node.getPiece() != null && node.getPiece().getLoyalty() != loyalty)
				{
					checkCandidate = node.getPiece();
				}
				
				if(checkCandidate == null)
				{
					continue;
				}
				else
				{
					for(Move move : checkCandidate.getPossibleMoves())
					{
						for(Node jumped : move.getJumped())
						{
							if(jumped.getPiece() instanceof King)
							{
								possible = false;
							}
						}
					}
				}
			}
			
			if(possible)
			{
				realPossibleMoves.add(possibleMove);
			}
		}
		
		return realPossibleMoves;
	}
	
	/**
	 * @return the node width of this board
	 */
	public int getNodeWidth()
	{
		return NODE_WIDTH;
	}

	/**
	 * @return the node height of this board
	 */
	public int getNodeHeight()
	{
		return NODE_HEIGHT;
	}
	
	/**
	 * Returns a clone of this board with the given game
	 * 
	 * @param game	the game to be cloned with
	 * @return	the cloned board
	 * @throws IOException 
	 */
	public RectangularBoard clone(Game game)
	{
		return new ChessBoard(game);
	}
}
