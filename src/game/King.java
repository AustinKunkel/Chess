package game;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece{

	public King(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.KING);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		super.getTargeting().clear();
		super.getCanTarget().clear();
		int y = super.getY();
		
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
	 */
	private void checkRow(Piece[] board, int row) {

		Set<Coordinate> targeting = super.getTargeting();
		Set<Coordinate> canTarget = super.getCanTarget();

		int x = super.getX();
		int y = super.getY();
		
		// check forward/back
		if(row != y) {

			if(board[x] != null) {
				if(sameColor(board[x])) {
					canTarget.add(new Coordinate(x, row)); // middle
				} else {
					targeting.add(new Coordinate(x, row)); // middle
				}
			} else {
				targeting.add(new Coordinate(x, row)); // middle
			}
		}
		
		// check left/right to see if piece is there:
		if(!(x - 1 < 0)) {
			if(board[x - 1] != null) {
				if(sameColor(board[x - 1])) {
					canTarget.add(new Coordinate(x - 1, row)); // left
				} else {
					targeting.add(new Coordinate(x - 1, row)); // left
				}
			}else {
				targeting.add(new Coordinate(x - 1, row)); // left
			}
		}
		if(!(x + 1 == board.length)) {
			if(board[x + 1] != null) {
				if(sameColor(board[x + 1])) {
					canTarget.add(new Coordinate(x + 1, row)); // right
				} else {
					targeting.add(new Coordinate(x + 1, row)); // right
				}
			} else {
				targeting.add(new Coordinate(x + 1, row)); // right

			}
		}
		
	}
}
