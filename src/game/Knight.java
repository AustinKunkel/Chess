package game;

import java.util.Map;

public class Knight implements Piece {
	
	private int x,y;//position in the array
	
	private final PieceType type;
	
	private PieceColor color;//color of the piece
	
	private Map<Integer, Integer> targeting;// Rows and columns being targeted

	public Knight(int x, int y, PieceColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = PieceType.KNIGHT;
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
	public void updateTargeting(Piece[][] board) {
		// check right
		checkRight(board);
		
		// check left
		checkLeft(board);
		
		// check up:
		checkUp(board);
		
		// check down:
		checkDown(board);
	}
	
	private void checkRight(Piece[][] board) {
		
		if(this.x + 3 < board[0].length) {
			// upper
			if(this.y + 1 <= board.length - 1) {// if not out of bounds
				if(board[y + 1][x + 3] != null) {// if its not an empty space
					if(!sameColor(board[y + 1][x + 3]))
						this.targeting.put(x + 3, y + 1);
				} else {
					this.targeting.put(x + 3, y + 1);
				}
			}
			
			// lower
			if(this.y - 1 >= 0) {// if not out of bounds
				if(board[y - 1][x +3] != null) {// if its not an empty space
					if(!sameColor(board[y - 1][x + 3]))
						this.targeting.put(x + 3, y - 1);
				} else {
					this.targeting.put(x + 3, y - 1);
				}
			}
		}
	}
	
	private void checkLeft(Piece[][] board) {
		if(this.x - 3 >= 0) {
			// upper
			if(this.y + 1 <= board.length - 1) {
				if(board[y + 1][x - 3] != null) {
					if(!sameColor(board[y + 1][x - 3]))
						this.targeting.put(x - 3, y + 1);
				} else {
					this.targeting.put(x - 3, y + 1);
				}
			}
			
			// lower
			if(this.y - 1 >= 0) {// if not out of bounds
				if(board[y - 1][x - 3] != null) {// if its not an empty space
					if(!sameColor(board[y - 1][x - 3]))
						this.targeting.put(x - 3, y - 1);
				} else {
					this.targeting.put(x - 3, y - 1);
				}
			}
		}
	}
	
	private void checkUp(Piece[][] board) {
		if(this.y + 3 < board.length) {
			// right
			if(this.x + 1 <= board[0].length - 1) {
				if(board[y + 3][x + 1] != null) {
					if(!sameColor(board[y + 3][x + 1]))
						this.targeting.put(x + 1, y + 3);
				} else {
					this.targeting.put(x + 1, y + 3);
				}
			}
			
			// left
			if(this.x - 1 >= 0) {// if not out of bounds
				if(board[y + 3][x - 1] != null) {// if its not an empty space
					if(!sameColor(board[y + 3][x - 1]))
						this.targeting.put(x - 1, y + 3);
				} else {
					this.targeting.put(x - 1, y + 3);
				}
			}
		}
	}
	
	private void checkDown(Piece[][] board) {
		if(this.y - 3 < board.length) {
			// right
			if(this.x + 1 <= board[0].length - 1) {
				if(board[y - 3][x + 1] != null) {
					if(!sameColor(board[y - 3][x + 1]))
						this.targeting.put(x + 1, y - 3);
				} else {
					this.targeting.put(x + 1, y - 3);
				}
			}
			
			// left
			if(this.x - 1 >= 0) {// if not out of bounds
				if(board[y - 3][x - 1] != null) {// if its not an empty space
					if(!sameColor(board[y - 3][x - 1]))
						this.targeting.put(x - 1, y - 3);
				} else {
					this.targeting.put(x - 1, y - 3);
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

}
