package game;

import java.util.Set;

public abstract class Piece {
	
	private int x, y, move;
	private PieceColor color;
	private PieceType type;
	private Set<Coordinate> targeting;
	
	public Piece(int x, int y, PieceColor color, PieceType type, Set<Coordinate> targeting) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = type;
		this.targeting = targeting;
		this.move = 0;
	}

	/**
	 * 
	 * @returnx y position in the array
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	public int getMove() {
		return this.move;
	}
	
	/***
	 * 
	 * @return the rank (y + 1)
	 */
	public int getRank() {
		return this.y + 1;
	}
	
	public char getFile() {
		return switch(this.x) {
		case 0 -> 'a';
		case 1 -> 'b';
		case 2 -> 'c';
		case 3 -> 'd';
		case 4 -> 'e';
		case 5 -> 'f';
		case 6 -> 'g';
		case 7 -> 'h';
		default -> 'i';
		};
	}
	
	public Coordinate getCoord() {
		return new Coordinate(this.x, this.y);
	}

	/**
	 * 
	 * @return y position in the array
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Sets the position to the x and y values
	 * @param x
	 * @param y
	 */
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
		this.move++;
	}
	
	
	/**
	 * 
	 * @return type of the piece
	 */
	public PieceType getType() {
		return this.type;
	}
	
	/**
	 * 
	 * @return piece's color
	 */
	public PieceColor getColor() {
		return this.color;
	}
	
	/**
	 * 
	 * @return a Set<Coordinate> of the pieces being targeted
	 */
	public Set<Coordinate> getTargeting() {
		return this.targeting;
	}
	
	/**
	 * Sets the targeting map to the new one
	 * Clears old and adds new to not destroy the other pieces
	 * @param update set that targeting will be set to
	 */
	public void setTargeting(Set<Coordinate> update) {
		this.targeting.clear();
		this.targeting.addAll(update);
	}
	
	/**
	 * updates the piece's targeting list
	 * with the current spots its targeting.
	 * @param board game board
	 */
	abstract public void updateTargeting(Piece[][] board);
	
	/**
	 * @param piece
	 * @return true if the colors are the same, false otherwise
	 */
	public boolean sameColor(Piece piece) {
		return piece.getColor().equals(this.color);
	}
	
	/**
	 * 
	 * @param piece to be compared to
	 * @return true if the piece is the same piece;
	 * 		   false if the piece is a different one
	 */
	public boolean equals(Piece piece) {
		return this.x == piece.getX() &&
				   this.y == piece.getY() && 
				   this.type == piece.getType() &&
				   this.color == piece.getColor();
	}
	
	public String toString() {
		String string = type.toString() + getFile() + getRank();
		if(this.color.equals(PieceColor.BLACK)) {
			return string.toLowerCase() + " " + this.color;
		} else {
			return string + " " + this.color;
		}
	}

}
