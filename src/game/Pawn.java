package game;
import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece{
	
	private PieceColor color;//color of the piece
	
	private Set<Coordinate> targeting;

	public Pawn(int x, int y, PieceColor color) {
		super(x, y, color, PieceType.PAWN, new HashSet<Coordinate>());
		this.color = color;
		this.targeting = super.getTargeting();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		targeting.clear();
		
		int factor = 1;
		int border = board.length;
		
		if(this.color == PieceColor.BLACK) {
			factor = -1;
			border = -1;
		}
		
		// checks the spots forward
		// and diagonal when applicable
		checkForward(factor, border, board);
		
		// now we check for 2 spaces in front when its the first move
		if(super.getMove() == 0) {
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
	 */
	private void checkForward(int factor, int border, Piece[][] board) {
		int x = super.getX();
		int y = super.getY();
		if(!(y + factor == border)) {
			// check forward to see if a piece isnt there:
			if(board[y + factor][x] == null) {
				this.targeting.add(new Coordinate(x, y + factor));
			}
			
			// if we're only moving forward 1 space
			if(Math.abs(factor) <= 1) {
				// check diagonal to see if piece is there:
				if(!(x - 1 < 0)) {
					if(board[y + factor][x - 1] != null) {
						if(!sameColor(board[y + factor][x - 1]))
							targeting.add(new Coordinate(x - 1, y + factor)); // left
					}
				}
				if(!(x + 1 == board[0].length)) {
					if(board[y + factor][x + 1] != null) {
						if(!sameColor(board[y + factor][x + 1]))
							targeting.add(new Coordinate(x + 1, y + factor)); // right
					}
				}			
			}
			
		}
	}
}
