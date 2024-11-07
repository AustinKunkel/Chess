package game;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {
	private PieceColor color;//color of the piece
	
	private Set<Coordinate> targeting;// Rows and columns being targeted

	public Queen(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.QUEEN, new HashSet<Coordinate>());
		this.color = color;
		this.targeting = super.getTargeting();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		targeting.clear();
		
		int x = super.getX();
		int y = super.getY();
		
		CheckLines.checkLines(board, color, targeting, x, y);
		
		CheckDiags.checkDiags(board, color, targeting, x, y);

	}
}
