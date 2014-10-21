package game.player;

import game.Move;
import game.board.Board;
import game.board.Location;
import game.board.Node;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import input.HumanMoveInput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * A class representing a Human player associated with a checkers game
 * 
 * @author Benjamin Cohen-Wang
 */
public class Human extends Player implements MouseMotionListener, MouseListener, KeyListener
{
	/** The array list of locations representing the current move **/
	private ArrayList<Location> moveLocs;
	
	/** The boolean determining whether or not the move is registered **/
	private boolean moveRegistered = false;
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param pieces	the pieces of this player
	 */
	public Human(String name, Loyalty loyalty, ArrayList<Piece> pieces)
	{
		super(name, loyalty, pieces);
	}

	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 */
	public Move getThisTurnMove()
	{
		if(isDefeated())
		{
			return null;
		}
		
		while(!moveRegistered)
		{
			try
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		moveRegistered = false;
		
		ArrayList<Move> possibleMoves = getPossibleMoves();

//		String moveString = JOptionPane.showInputDialog("Enter your move in the form (row1, col1), (row2, col2), ... ");
//		
//		StringBuffer bufferedMoveString = new StringBuffer(moveString);
//		
//		while(bufferedMoveString.indexOf(" ") != -1)
//		{
//			bufferedMoveString.deleteCharAt(bufferedMoveString.indexOf(" "));
//		}
//		
//		ArrayList<Location> moveLocs = new ArrayList<Location>();
//		
//		while(bufferedMoveString.indexOf(")") != -1)
//		{
//			String moveLocString = bufferedMoveString.substring(1, bufferedMoveString.indexOf(")"));
//			
//			int row = Integer.parseInt(moveLocString.substring(0, moveLocString.indexOf(",")));
//			
//			int col = Integer.parseInt(moveLocString.substring(moveLocString.indexOf(",") + 1));
//			
//			moveLocs.add(new Location(row, col));
//			
//			bufferedMoveString.delete(0, Math.min(bufferedMoveString.indexOf(")") + 2, bufferedMoveString.length()));
//		}
		
		boolean isPossible = false;
		
		for(Move possibleMove : possibleMoves)
		{
			ArrayList<Node> thisMoveNodes = possibleMove.getNodes();
			
			ArrayList<Location> thisMoveLocs = new ArrayList<Location>();
			
			for(Node moveNode : thisMoveNodes)
			{
				thisMoveLocs.add(moveNode.getLoc());
			}
			
			boolean isPossiblyPossible = true;
			
			if(thisMoveLocs.size() == moveLocs.size())
			{
				for(int i = 0; i < moveLocs.size(); i ++)
				{
					if(moveLocs.get(i).getRow() != thisMoveLocs.get(i).getRow() || moveLocs.get(i).getCol() != thisMoveLocs.get(i).getCol())
					{
						isPossiblyPossible = false;
					}
				}
			}
			
			if(isPossiblyPossible)
			{
				isPossible = true;
			}
		}
		
		if(!isPossible)
		{
			System.out.println("IMPOSSIBLE MOVE");
			return null;
		}
		
		ArrayList<Node> moveNodes = new ArrayList<Node>();
		
		for(Location moveLoc : moveLocs)
		{
			moveNodes.add(getPieces().get(0).getNode().getBoard().getNode(moveLoc));
		}
		
		Move thisTurnMove = new Move(moveNodes, getPieces().get(0).getNode().getBoard(), getLoyalty());
		
		return thisTurnMove;
	}
	
	/**
	 * Registers the mouse input move
	 * 
	 * @return the registered move for this turn
	 */
	public Move registerMouseMove()
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		for(Location loc : moveLocs)
		{
			nodes.add(getPieces().get(0).getNode().getBoard().getNode(loc));
		}
		
		return new Move(nodes, getPieces().get(0).getNode().getBoard(), getLoyalty());
	}

	public void mouseDragged(MouseEvent event)
	{
		if(getPieces().get(0).getNode().getBoard().getGame().getTurn().getVal() == getLoyalty().getVal())
		{
//			int x = event.getX();
//			int y = event.getY();
//			
//			int row = (x - x % Board.NODE_WIDTH)/Board.NODE_WIDTH;
//			int col = (y - y % Board.NODE_HEIGHT)/Board.NODE_HEIGHT;
//			
//			Location loc = new Location(row, col);
//			
//			System.out.println("(" + row + ", " + col + ")");
//			
//			boolean isAlreadyAdded = false;
//			
//			for(Location currentMoveLoc : moveLocs)
//			{
//				if(loc.getRow() == currentMoveLoc.getRow() && loc.getCol() == currentMoveLoc.getCol())
//				{
//					isAlreadyAdded = true;
//				}
//			}
//			
//			if(!isAlreadyAdded)
//			{
//				moveLocs.add(loc);
//			}
		}
	}

	public void mouseMoved(MouseEvent event)
	{
		
	}

	public void mouseClicked(MouseEvent event)
	{
		if(getPieces().get(0).getNode().getBoard().getGame().getTurn().getVal() == getLoyalty().getVal())
		{
			if(moveLocs == null)
			{
				moveLocs = new ArrayList<Location>();
			}
			
			int x = event.getX();
			int y = event.getY();
			
			int row = (x - x % Board.NODE_WIDTH)/Board.NODE_WIDTH;
			int col = (y - y % Board.NODE_HEIGHT)/Board.NODE_HEIGHT;
			
			Location loc = new Location(row, col);
			
			System.out.println("(" + row + ", " + col + ")");
			
			boolean isAlreadyAdded = false;
			
			for(Location currentMoveLoc : moveLocs)
			{
				if(loc.getRow() == currentMoveLoc.getRow() && loc.getCol() == currentMoveLoc.getCol())
				{
					isAlreadyAdded = true;
				}
			}
			
			if(!isAlreadyAdded)
			{
				moveLocs.add(loc);
			}
		}
	}

	public void mouseEntered(MouseEvent e)
	{
		
	}

	public void mouseExited(MouseEvent e)
	{
		
	}

	public void mousePressed(MouseEvent e)
	{
		if(getPieces().get(0).getNode().getBoard().getGame().getTurn().getVal() == getLoyalty().getVal())
		{
			moveLocs = new ArrayList<Location>();
		}
	}

	public void mouseReleased(MouseEvent e)
	{

	}

	public void keyPressed(KeyEvent arg0)
	{
		if(getPieces().get(0).getNode().getBoard().getGame().getTurn().getVal() == getLoyalty().getVal())
		{
			moveRegistered = true;
		}
	}

	public void keyReleased(KeyEvent arg0)
	{
		
	}

	public void keyTyped(KeyEvent arg0)
	{
		
	}
}
