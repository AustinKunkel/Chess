package game;

import java.util.HashSet;
import java.util.Set;

public class Knight implements Piece {
	
	private int x,y;//position in the array
	
	private final PieceType type;
	
	private PieceColor color;//color of the piece
	
	private Set<Coordinate> targeting;// Rows and columns being targeted
	private Set<Coordinate> sameTargeting;

	public Knight(int x, int y, PieceColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = PieceType.KNIGHT;
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
		
		this.targeting.clear();
		this.sameTargeting.clear();
		// check right
		checkRight(board);
		
		// check left
		checkLeft(board);
		
		// check up:
		checkUp(board);
		
		// check down:
		checkDown(board);
	}
	
	private void checkRight(Piece[][] board) {
		
		if(this.x + 2 < board[0].length) {
			// upper
			if(this.y + 1 <= board.length - 1) {// if not out of bounds
				if(board[y + 1][x + 2] != null) {// if its not an empty space
					if(!sameColor(board[y + 1][x + 2]))
						this.targeting.add(new Coordinate(x + 2, y + 1));
					else
						this.sameTargeting.add(new Coordinate(x + 2, y + 1));
				} else {
					this.targeting.add(new Coordinate(x + 2, y + 1));
				}
			}
			
			// lower
			if(this.y - 1 >= 0) {// if not out of bounds
				if(board[y - 1][x +2] != null) {// if its not an empty space
					if(!sameColor(board[y - 1][x + 2]))
						this.targeting.add(new Coordinate(x + 2, y - 1));
					else
						this.sameTargeting.add(new Coordinate(x + 2, y - 1));
				} else {
					this.targeting.add(new Coordinate(x + 2, y - 1));
				}
			}
		}
	}
	
	private void checkLeft(Piece[][] board) {
		if(this.x - 2 >= 0) {
			// upper
			if(this.y + 1 <= board.length - 1) {
				if(board[y + 1][x - 2] != null) {
					if(!sameColor(board[y + 1][x - 2]))
						this.targeting.add(new Coordinate(x - 2, y + 1));
					else
						this.sameTargeting.add(new Coordinate(x - 2, y + 1));
				} else {
					this.targeting.add(new Coordinate(x - 2, y + 1));
				}
			}
			
			// lower
			if(this.y - 1 >= 0) {// if not out of bounds
				if(board[y - 1][x - 2] != null) {// if its not an empty space
					if(!sameColor(board[y - 1][x - 2]))
						this.targeting.add(new Coordinate(x - 2, y - 1));
					else
						this.sameTargeting.add(new Coordinate(x - 2, y - 1));
				} else {
					this.targeting.add(new Coordinate(x - 2, y - 1));
				}
			}
		}
	}
	
	private void checkUp(Piece[][] board) {
		if(this.y + 2 < board.length) {
			// right
			if(this.x + 1 <= board[0].length - 1) {
				if(board[y + 2][x + 1] != null) {
					if(!sameColor(board[y + 2][x + 1]))
						this.targeting.add(new Coordinate(x + 1, y + 2));
					else
						this.sameTargeting.add(new Coordinate(x + 1, y + 2));
				} else {
					this.targeting.add(new Coordinate(x + 1, y + 2));
				}
			}
			
			// left
			if(this.x - 1 >= 0) {// if not out of bounds
				if(board[y + 2][x - 1] != null) {// if its not an empty space
					if(!sameColor(board[y + 2][x - 1]))
						this.targeting.add(new Coordinate(x - 1, y + 2));
					else
						this.sameTargeting.add(new Coordinate(x - 1, y + 2));
				} else {
					this.targeting.add(new Coordinate(x - 1, y + 2));
				}
			}
		}
	}
	
	private void checkDown(Piece[][] board) {
		if(this.y - 2 >= 0) {
			// right
			if(this.x + 1 < board[0].length) {
				if(board[y - 2][x + 1] != null) {
					if(!sameColor(board[y - 2][x + 1]))
						this.targeting.add(new Coordinate(x + 1, y - 2));
					else
						this.sameTargeting.add(new Coordinate(x + 1, y - 2));
				} else {
					this.targeting.add(new Coordinate(x + 1, y - 2));
				}
			}
			
			// left
			if(this.x - 1 >= 0) {// if not out of bounds
				if(board[y - 2][x - 1] != null) {// if its not an empty space
					if(!sameColor(board[y - 2][x - 1]))
						this.targeting.add(new Coordinate(x - 1, y - 2));
					else
						this.sameTargeting.add(new Coordinate(x - 1, y - 2));
				} else {
					this.targeting.add(new Coordinate(x - 1, y - 2));
				}
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
