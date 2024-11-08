package game;

import java.util.Set;

public class Queen extends Piece {

	public Queen(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.QUEEN);
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
		
		int x = super.getX();
		int y = super.getY();
		
		canTarget.addAll(CheckLines.checkLines(board, super.getColor(), targeting, x, y));
		
		canTarget.addAll(CheckDiags.checkDiags(board, super.getColor(), targeting, x, y));
	}
}
