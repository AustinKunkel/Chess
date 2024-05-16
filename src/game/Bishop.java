package game;

import java.util.HashSet;
import java.util.Set;

public class Bishop implements Piece {
	
	private int x,y;//position in the array
	
	private final PieceType type;
	
	private PieceColor color;//color of the piece
	
	private Set<Coordinate> targeting;// Rows and columns being targeted
	private Set<Coordinate> sameTargeting;

	public Bishop(int x, int y, PieceColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = PieceType.BISHOP;
		this.targeting = new HashSet<>();
		this.sameTargeting = new HashSet<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getX() {
		return this.x;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getY() {
		return this.y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPos(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PieceType getType() {
		return this.type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PieceColor getColor() {
		return this.color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Coordinate> getTargeting() {
		return this.targeting;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTargeting(Set<Coordinate> update) {
		this.targeting = update;
	}
	
	@Override
	public Set<Coordinate> getSameColorTargeting() {
		return this.sameTargeting;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		
		CheckLines lineChecker = new CheckLines(board, color, targeting, new Coordinate(this.x, this.y), x, y);
		
		targeting.clear();
		sameTargeting.clear();
		
		sameTargeting.addAll(lineChecker.checkDiags(false, null));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean sameColor(Piece piece) {
		return color == piece.getColor();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Piece piece) {
		return this.x == piece.getX() &&
				   this.y == piece.getY() && 
				   this.type == piece.getType() &&
				   this.color == piece.getColor();
	}
	
	@Override
	public String toString() {
		return type.toString() + x + y;
	}

}
