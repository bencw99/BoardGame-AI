package game.player;

import game.Game;
import game.board.node.Location;
import game.board.node.Node;
import game.move.ChessMove;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.chessPieces.*;
import game.player.ai.MinimaxNode;
import gui.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class representing a Human player associated with a board game
 * 
 * @author Benjamin Cohen-Wang
 */
public class Human extends Player implements MouseMotionListener, MouseListener, KeyListener
{
	/** The array list of locations representing the current move **/
	private ArrayList<Location> moveLocs;
	
	/** The type to be promoted to **/
	private Class<? extends Piece> promotionType;
	
	/** The boolean determining whether or not the move is registered **/
	private boolean moveRegistered = false;
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 */
	public Human(String name, Loyalty loyalty, Game game)
	{
		super(name, loyalty, game);
	}

	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 * @throws IOException 
	 */
	public Move getThisTurnMove() throws IOException
	{
		if(isDefeated())
		{
			return null;
		}
		
		moveLocs = new ArrayList<Location>();
		
		promotionType = null;
		
		while(!moveRegistered)
		{	
			try
			{
				Thread.sleep(50);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		for(Location moveLoc : moveLocs)
		{
			getBoard().getNode(moveLoc).setHighlighted(false);
		}
		
		moveRegistered = false;
		
		ArrayList<Move> possibleMoves = getPossibleMoves();
		
		Move selectedMove = null;
		
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
					
					if(possibleMove instanceof ChessMove)
					{
						if(promotionType == null)
						{
							if(((ChessMove) possibleMove).getPromotionType() != null)
							{
								isPossiblyPossible = false;	
							}
						}
						else
						{
							if(((ChessMove) possibleMove).getPromotionType() == null)
							{
								isPossiblyPossible = false;	
							}
							else if(((ChessMove) possibleMove).getPromotionType() != promotionType)
							{
								isPossiblyPossible = false;	
							}
						}
						
					}
				}
			}
			else
			{
				isPossiblyPossible = false;
			}
			
			if(isPossiblyPossible)
			{
				selectedMove = possibleMove;
				break;
			}
		}
		
		if(selectedMove == null)
		{
			for(Location moveLoc : moveLocs)
			{
				getBoard().getNode(moveLoc).setHighlighted(false);
			}
			
			System.out.println("Impossible Move");
			
			GamePanel.frame.repaint();
			
			return null;
		}
		else
		{
			return selectedMove;
	
		}
	}

	public void mouseDragged(MouseEvent event)
	{

	}

	public void mouseMoved(MouseEvent event)
	{
		
	}

	public void mouseClicked(MouseEvent event)
	{
		if(getGame().getTurn().getVal() == getLoyalty().getVal())
		{
			if(moveLocs == null)
			{
				moveLocs = new ArrayList<Location>();
			}
			
			int x = event.getX();
			int y = event.getY();
			
			int row = (y - y % getBoard().getNodeHeight())/getBoard().getNodeHeight();
			int col = (x - x % getBoard().getNodeWidth())/getBoard().getNodeWidth();
			
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
			
			Node node = getBoard().getNode(loc);
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
		if(getGame().getTurn().getVal() == getLoyalty().getVal())
		{
			if(event.getKeyCode() == KeyEvent.VK_ENTER)
			{
				moveRegistered = true;
			}
			if(event.getKeyCode() == KeyEvent.VK_U)
			{
				for(Location moveLoc : moveLocs)
				{
					getBoard().getNode(moveLoc).setHighlighted(false);
				}
				
				moveLocs = new ArrayList<Location>();
			}
			if(event.getKeyCode() == KeyEvent.VK_R)
			{
				promotionType = Rook.class;
			}
			if(event.getKeyCode() == KeyEvent.VK_B)
			{
				promotionType = Bishop.class;
			}
			if(event.getKeyCode() == KeyEvent.VK_K)
			{
				promotionType = Knight.class;
			}
			if(event.getKeyCode() == KeyEvent.VK_Q)
			{
				promotionType = Queen.class;
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
