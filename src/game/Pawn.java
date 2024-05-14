package game;

import java.util.Map;

public class Pawn implements Piece{
	
	private int x,y;//position in the array
	
	private final PieceType type;
	
	private PieceColor color;//color of the piece
	
	private Map<Integer, Integer> targeting;

	public Pawn(int x, int y, PieceColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = PieceType.PAWN;
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
	public Map<Integer, Integer> getTargeting() {
		return this.targeting;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTargeting(Map<Integer, Integer> update) {
		this.targeting = update;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTargeting(Piece[][] board) {
		targeting.clear();
		
		if(!(y + 1 == board.length)) {
			
			// check forward to see if a piece isnt there:
			if(board[y + 1][x] == null) {
				this.targeting.put(x, y + 1);
			}
			
			// check diagonal to see if piece is there:
			if(!(x - 1 < 0)) {
				if(board[y + 1][x - 1] != null) {
					if(!sameColor(board[y + 1][x - 1]))
						targeting.put(x - 1, y + 1); // left
				}
			}
			if(!(x + 1 == board[0].length)) {
				if(board[y + 1][x + 1] != null) {
					if(!sameColor(board[y + 1][x + 1]))
						targeting.put(x + 1, y + 1); // right
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
