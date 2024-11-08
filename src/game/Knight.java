package game;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
	private final Set<Coordinate> targeting;// Rows and columns being targeted
	private final Set<Coordinate> canTarget;

	public Knight(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.KNIGHT);
		this.targeting = super.getTargeting();
		this.canTarget = super.getCanTarget();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		this.targeting.clear();
		this.canTarget.clear();
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
		int x = super.getX();
		int y = super.getY();
		
		if(x + 2 < board[0].length) {
			// upper
			if(y + 1 <= board.length - 1) {// if not out of bounds
				if(board[y + 1][x + 2] != null) {// if it's not an empty space
					if(sameColor(board[y + 1][x + 2])) {
						this.canTarget.add(new Coordinate(x + 2, y + 1));
					} else {
						this.targeting.add(new Coordinate(x + 2, y + 1));
					}
				} else {
					this.targeting.add(new Coordinate(x + 2, y + 1));
				}
			}
			
			// lower
			if(y - 1 >= 0) {// if not out of bounds
				if(board[y - 1][x +2] != null) {// if it's not an empty space
					if(sameColor(board[y - 1][x + 2])) {
						this.canTarget.add(new Coordinate(x + 2, y - 1));
					} else {
						this.targeting.add(new Coordinate(x + 2, y - 1));
					}
				} else {
					this.targeting.add(new Coordinate(x + 2, y - 1));
				}
			}
		}
	}
	
	private void checkLeft(Piece[][] board) {
		int x = super.getX();
		int y = super.getY();
		
		if(x - 2 >= 0) {
			// upper
			if(y + 1 <= board.length - 1) {
				if(board[y + 1][x - 2] != null) {
					if(sameColor(board[y + 1][x - 2])) {
						this.canTarget.add(new Coordinate(x - 2, y + 1));
					} else {
						this.targeting.add(new Coordinate(x - 2, y + 1));
					}
				} else {
					this.targeting.add(new Coordinate(x - 2, y + 1));
				}
			}
			
			// lower
			if(y - 1 >= 0) {// if not out of bounds
				if(board[y - 1][x - 2] != null) {// if it's not an empty space
					if(sameColor(board[y - 1][x - 2])) {
						this.canTarget.add(new Coordinate(x - 2, y - 1));
					} else {
						this.targeting.add(new Coordinate(x - 2, y - 1));
					}
				} else {
					this.targeting.add(new Coordinate(x - 2, y - 1));
				}
			}
		}
	}
	
	private void checkUp(Piece[][] board) {
		int x = super.getX();
		int y = super.getY();
		
		if(y + 2 < board.length) {
			// right
			if(x + 1 <= board[0].length - 1) {
				if(board[y + 2][x + 1] != null) {
					if(sameColor(board[y + 2][x + 1])) {
						this.canTarget.add(new Coordinate(x + 1, y + 2));
					} else {
						this.targeting.add(new Coordinate(x + 1, y + 2));
					}
				} else {
					this.targeting.add(new Coordinate(x + 1, y + 2));
				}
			}
			
			// left
			if(x - 1 >= 0) {// if not out of bounds
				if(board[y + 2][x - 1] != null) {// if it's not an empty space
					if(sameColor(board[y + 2][x - 1])) {
						this.canTarget.add(new Coordinate(x - 1, y + 2));
					} else {
						this.targeting.add(new Coordinate(x - 1, y + 2));
					}
				} else {
					this.targeting.add(new Coordinate(x - 1, y + 2));
				}
			}
		}
	}
	
	private void checkDown(Piece[][] board) {
		int x = super.getX();
		int y = super.getY();
		
		if(y - 2 >= 0) {
			// right
			if(x + 1 < board[0].length) {
				if(board[y - 2][x + 1] != null) {
					if(sameColor(board[y - 2][x + 1])) {
						this.canTarget.add(new Coordinate(x + 1, y - 2));
					} else {
						this.targeting.add(new Coordinate(x + 1, y - 2));
					}
				} else {
					this.targeting.add(new Coordinate(x + 1, y - 2));
				}
			}
			
			// left
			if(x - 1 >= 0) {// if not out of bounds
				if(board[y - 2][x - 1] != null) {// if it's not an empty space
					if(sameColor(board[y - 2][x - 1])) {
						this.canTarget.add(new Coordinate(x - 1, y - 2));
					} else {
						this.targeting.add(new Coordinate(x - 1, y - 2));
					}
				} else {
					this.targeting.add(new Coordinate(x - 1, y - 2));
				}
			}
		}
	}
}
