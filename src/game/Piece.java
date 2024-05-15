package game;

import java.util.List;
import java.util.Set;

public interface Piece {

	/**
	 * 
	 * @returnx y position in the array
	 */
	public int getX();
	
	/**
	 * @param x
	 */
	public void setX(int x);

	/**
	 * 
	 * @return y position in the array
	 */
	public int getY();
	
	/**
	 * 
	 * @param y
	 */
	public void setY(int y);
	
	/**
	 * Sets the position to the x and y values
	 * @param x
	 * @param y
	 */
	public void setPos(int x, int y);
	
	
	/**
	 * 
	 * @return type of the piece
	 */
	public PieceType getType();
	
	/**
	 * 
	 * @return piece's color
	 */
	public PieceColor getColor();
	
	@Override
	public String toString();
	
	/**
	 * 
	 * @return a Set<Coordinate> of the pieces being targeted
	 */
	public Set<Coordinate> getTargeting();
	
	/**
	 * Sets the targeting map to the new one
	 * @param update
	 */
	public void setTargeting(Set<Coordinate> update);
	
	/**
	 * 
	 * @return a Set<Coordinate> of the pieces of the same color being targeted
	 */
	public Set<Coordinate> getSameColorTargeting();
	
	/**
	 * updates the piece's targeting list
	 * with the current spots its targeting.
	 * @param board
	 */
	public void updateTargeting(Piece[][] board);
	
	/**
	 * @param piece2
	 * @return true if the colors are the same, false otherwise
	 */
	public boolean sameColor(Piece piece);
	
	/**
	 * 
	 * @param piece to be compared to
	 * @return true if the piece is the same piece;
	 * 		   false if the piece is a different one
	 */
	public boolean equals(Piece piece);

}
