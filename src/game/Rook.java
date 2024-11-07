package game;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {

	public Rook(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.ROOK, new HashSet<Coordinate>());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		Set<Coordinate> targeting = super.getTargeting();
		targeting.clear();
		CheckLines.checkLines(board, super.getColor(), targeting, super.getX(), super.getY());
	}
}
