package game;

import java.util.Set;

public class Rook extends Piece {

	public Rook(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.ROOK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		Set<Coordinate> targeting = super.getTargeting();
		Set<Coordinate> canTarget = super.getCanTarget();
		targeting.clear();
		canTarget.clear();

		canTarget.addAll(CheckLines.checkLines(board, super.getColor(), targeting, super.getX(), super.getY()));
	}
}
