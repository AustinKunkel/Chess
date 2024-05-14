package game;

import java.util.HashSet;
import java.util.Set;

public class King implements Piece{
	
	private int x,y;//position in the array
	
	private final PieceType type;
	
	private PieceColor color;//color of the piece
	
	private Set<Coordinate> targeting;// Rows and columns being targeted

	public King(int x, int y, PieceColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = PieceType.KING;
		this.targeting = new HashSet<>();
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		targeting.clear();
		targeting = new HashSet<>();
		
		if(y + 1 <= board.length - 1) { // if not on the upper edge,
									   // search the upper pieces
			checkRow(board[y+1], y+1);
		}
		
		checkRow(board[y], y);
		
		if(!(y - 1 < 0)) { // if not on lower edge, search lower pieces
			checkRow(board[y - 1], y - 1);
		}
		
	}
	
	/**
	 * checks the specified row in the targeting.
	 * Selects a row out of the board.
	 * @param board
	 */
	private void checkRow(Piece[] board, int row) {
		
		// check forward/back
		if(row != y) {
			
			if(board[x] != null) {
				if(!sameColor(board[x]))
					this.targeting.add(new Coordinate(x, row));
			} else {
				this.targeting.add(new Coordinate(x, row));
			}
		}
		
		// check left/right to see if piece is there:
		if(!(x - 1 < 0)) {
			if(board[x - 1] != null) {
				if(!sameColor(board[x - 1])) 
					this.targeting.add(new Coordinate(x - 1, row)); // left
			}else {
				this.targeting.add(new Coordinate(x - 1, row)); // left
			}
		}
		if(!(x + 1 == board.length)) {
			if(board[x + 1] != null) {
				if(!sameColor(board[x + 1]))
					targeting.add(new Coordinate(x + 1, row)); // right
			} else {
				targeting.add(new Coordinate(x + 1, row)); // right

			}
		}
		
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
