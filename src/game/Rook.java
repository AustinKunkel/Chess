package game;

import java.util.Map;

public class Rook implements Piece {
	
	private int x,y;//position in the array
	
	private final PieceType type;
	
	private PieceColor color;//color of the piece
	
	private Map<Integer, Integer> targeting;// Rows and columns being targeted

	public Rook(int x, int y, PieceColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = PieceType.ROOK;
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
	public Map<Integer, Integer> getTargeting() {
		return this.targeting;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTargeting(Map<Integer, Integer> update) {
		this.targeting = update;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		
		targeting.clear();
		
		CheckLines.checkLines(board, color, targeting, x, y);
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
}
