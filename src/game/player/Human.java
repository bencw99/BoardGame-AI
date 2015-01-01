package game.player;

import game.board.CheckersBoard;
import game.board.node.Location;
import game.board.node.Node;
import game.move.CheckersMove;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import gui.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

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
	public CheckersMove getThisTurnMove()
	{
		if(isDefeated())
		{
			return null;
		}
		
		moveLocs = new ArrayList<Location>();
		
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
		
		for(Location moveLoc : moveLocs)
		{
			getPieces().get(0).getNode().getBoard().getNode(moveLoc).setHighlighted(false);
		}
		
		moveRegistered = false;
		
		ArrayList<CheckersMove> possibleMoves = getPossibleMoves();
		
		boolean isPossible = false;
		
		for(CheckersMove possibleMove : possibleMoves)
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
			else
			{
				isPossiblyPossible = false;
			}
			
			if(isPossiblyPossible)
			{
				isPossible = true;
			}
		}
		
		if(!isPossible)
		{
			for(Location moveLoc : moveLocs)
			{
				getPieces().get(0).getNode().getBoard().getNode(moveLoc).setHighlighted(false);
			}
			
			System.out.println("Impossible Move");
			
			GamePanel.frame.repaint();
			
			return null;
		}
		
		return registerMouseMove();
	}
	
	/**
	 * Registers the mouse input move
	 * 
	 * @return the registered move for this turn
	 */
	public CheckersMove registerMouseMove()
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		for(Location loc : moveLocs)
		{
			nodes.add(getPieces().get(0).getNode().getBoard().getNode(loc));
		}
		
		return new CheckersMove(nodes, getPieces().get(0).getNode().getBoard(), getLoyalty());
	}

	public void mouseDragged(MouseEvent event)
	{

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
			
			int row = (y - y % CheckersBoard.NODE_HEIGHT)/CheckersBoard.NODE_HEIGHT;
			int col = (x - x % CheckersBoard.NODE_WIDTH)/CheckersBoard.NODE_WIDTH;
			
			Location loc = new Location(row, col);
			
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
			
			Node node = getPieces().get(0).getNode().getBoard().getNode(loc);
			node.setHighlighted(true);

			GamePanel.frame.repaint();
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

	}

	public void mouseReleased(MouseEvent e)
	{

	}

	public void keyPressed(KeyEvent event)
	{
		if(getPieces().get(0).getNode().getBoard().getGame().getTurn().getVal() == getLoyalty().getVal())
		{
			if(event.getKeyCode() == KeyEvent.VK_R)
			{
				moveRegistered = true;
			}
			if(event.getKeyCode() == KeyEvent.VK_U)
			{
				for(Location moveLoc : moveLocs)
				{
					getPieces().get(0).getNode().getBoard().getNode(moveLoc).setHighlighted(false);
				}
				
				moveLocs = new ArrayList<Location>();
			}
			
			GamePanel.frame.repaint();
		}
	}

	public void keyReleased(KeyEvent arg0)
	{
		
	}

	public void keyTyped(KeyEvent arg0)
	{
		
	}
}
