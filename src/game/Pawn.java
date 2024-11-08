package game;
import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece{

	public Pawn(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.PAWN);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		super.getTargeting().clear();
		super.getCanTarget().clear();
		
		int factor = 1;
		int border = board.length;
		
		if(super.getColor() == PieceColor.BLACK) {
			factor = -1;
			border = -1;
		}
		
		// checks the spots forward
		// and diagonal when applicable
		boolean spotIsClear = checkForward(factor, border, board);
		
		// now we check for 2 spaces in front when its the first move
		if(super.getMove() == 0 && spotIsClear) {
			factor = factor * 2;
			checkForward(factor, border, board);
		}	
	}
	
	/**
	 * Helper function for updateTargeting.
	 * 
	 * Will check the forward (or reverse if black)
	 * and will add the spaces to targeting if able
	 * @param factor
	 * @param border
	 * @param board
	 * @return false if there is a piece in front of it
	 */
	private boolean checkForward(int factor, int border, Piece[][] board) {
		Set<Coordinate> targeting = super.getTargeting();
		Set<Coordinate> canTarget = super.getCanTarget();
		int x = super.getX();
		int y = super.getY();
		boolean flag = true; // flag to see if a piece is in front of it
		if(!(y + factor == border)) {
			// check forward to see if a piece isn't there:
			if(board[y + factor][x] == null) {
				targeting.add(new Coordinate(x, y + factor));
			} else {
				flag = false;
			}
			
			// if we're only moving forward 1 space
			if(Math.abs(factor) <= 1) {
				// check diagonal to see if piece is there:
				if(!(x - 1 < 0)) {
					if(board[y + factor][x - 1] != null) {
						if(sameColor(board[y + factor][x - 1])) {
							canTarget.add(new Coordinate(x - 1, y + factor)); // left
						} else {
							targeting.add(new Coordinate(x - 1, y + factor)); // left
						}
					}
				}
				if(!(x + 1 == board[0].length)) {
					if(board[y + factor][x + 1] != null) {
						if(sameColor(board[y + factor][x + 1])) {
							canTarget.add(new Coordinate(x + 1, y + factor)); // right
						} else {
							targeting.add(new Coordinate(x + 1, y + factor)); // right
						}
					}
				}			
			}
			
		}
		return flag;
	}
}
