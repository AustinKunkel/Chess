package game;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {

	public Bishop(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.BISHOP, new HashSet<Coordinate>());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		Set<Coordinate> targeting = super.getTargeting();
		targeting.clear();
		CheckDiags.checkDiags(board, super.getColor(), targeting, super.getX(), super.getY());
	}
}
