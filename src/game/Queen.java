package game;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {

	public Queen(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.QUEEN, new HashSet<Coordinate>());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		Set<Coordinate> targeting = super.getTargeting();
		targeting.clear();
		
		int x = super.getX();
		int y = super.getY();
		
		CheckLines.checkLines(board, super.getColor(), targeting, x, y);
		
		CheckDiags.checkDiags(board, super.getColor(), targeting, x, y);
	}
}
