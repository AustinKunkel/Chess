package game;
import java.util.HashSet;
import java.util.Set;

public class Pawn implements Piece{
	
	private int x,y, move;//position in the array
	
	private final PieceType type;
	
	private PieceColor color;//color of the piece
	
	private Set<Coordinate> targeting;

	public Pawn(int x, int y, PieceColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = PieceType.PAWN;
		this.move = 0;
		this.targeting = new HashSet<>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getX() {
		return this.x;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setX(int x) {
		this.x = x;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getY() {
		return this.y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPos(int x, int y) {
		this.setX(x);
		this.setY(y);
		//inherently moves forward when setting position
		this.move++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PieceType getType() {
		return this.type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PieceColor getColor() {
		return this.color;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Coordinate> getTargeting() {
		return this.targeting;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTargeting(Set<Coordinate> update) {
		this.targeting = update;
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
		if(this.move == 0) {
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean sameColor(Piece piece) {
		return color == piece.getColor();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Piece piece) {
		return this.x == piece.getX() &&
				   this.y == piece.getY() && 
				   this.type == piece.getType() &&
				   this.color == piece.getColor();
	}
	
	@Override
	public String toString() {
		return type.toString() + x + y;
	}

}
