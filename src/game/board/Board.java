package game.board;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.Move;
import game.piece.King;
import game.piece.Piece;
import game.piece.Piece.Loyalty;

/**
 * A superclass representing a general game board
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class Board
{
	/** The game this board belong to **/
	private Game game;
	
	/** The arraylist of nodes composing this grid **/
	private ArrayList<Node> nodes;
	 
	/**
	 * Parameterized constructor, initializes game to given game
	 */
	public Board(Game game)
	{
		this.game = game;
		initializeNodes();
	}
	
	/**
	 * Parameterized constructor, initializes game to given game
	 */
	public Board(Board board, Game game)
	{
		this.game = game;
	}
	
	/** 
	 * Draws this board
	 * 
	 * @param graphics
	 */
	public void draw(Graphics graphics)
	{
		for(Node node : nodes)
		{
			node.draw(graphics);
		}
	}
	
	/**
	 * Initializes the array list of nodes of this game
	 */
	public abstract void initializeNodes();
	
	/**
	 * Loads the initial posiition of this board
	 */
	public abstract void loadBoard();
	
	/**
	 * @return the game of this board
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * Sets this board to have the given game
	 * 
	 * @param game	the game to set this instance to
	 */
	public void setGame(Game game)
	{
		this.game = game;
	}
	
	/**
	 * @return the array list of nodes of this board
	 */
	public ArrayList<Node> getNodes()
	{
		return nodes;
	}
}
