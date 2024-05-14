package game;

import java.util.Map;

/**
 * Helper class so that Rook and Queen 
 * can use the same mathods to check lines
 */
public class CheckLines {
	
	public CheckLines() {}
	
	public static void checkLines(Piece[][] board,
								  PieceColor color,
								  Map<Integer, Integer> targeting,
								  int x,
								  int y) {
		
		// checking right side of the rank:
		checkRight(board, x, y, targeting, color, 1);
		
		// checking left side:
		checkLeft(board, x, y, targeting, color, 1);
		
		// checking up:
		checkUp(board, x, y, targeting, color, 1);
		
		// checking down:
		checkDown(board, x, y, targeting, color, 1);
	}
	
	/**
	 * Checks right side, will stop at any point
	 * it finds a piece.
	 * If it finds a piece of a different color,
	 * it will add it to targeting.
	 * 
	 * Recursively checks another time so it 
	 * adds the king to the possible moves.
	 * useful in avoiding check when moving
	 * a piece
	 * @param board
	 */
	private static void checkRight(Piece[][] board, int x, int y,  Map<Integer, Integer> targeting, PieceColor color, int iteration) {
		int tempX = x + 1;
		while(tempX < board[0].length) {
			Piece piece = board[y][tempX];
			
			// will break
			if(piece != null) {
				if(!sameColor(piece, color)) {
					targeting.put(tempX, y);
				}
				break;
			} 
			// so this can be put here
			targeting.put(tempX, y);
			tempX++;
		}
		
		if(iteration < 2) {
			iteration++;
			checkRight(board, tempX, y, targeting, color, iteration);
		}
		
	}
	
	/**
	 * Checks right side, will stop at any point
	 * it finds a piece.
	 * If it finds a piece of a different color,
	 * it will add it to targeting
	 * 
	 * Recursively checks another time so it 
	 * adds the king to the possible moves.
	 * useful in avoiding check when moving
	 * a piece
	 * @param board
	 */
	private static void checkLeft(Piece[][] board, int x, int y,  Map<Integer, Integer> targeting, PieceColor color, int iteration) {
		int tempX = x - 1;
		
		while(tempX >= 0) {
			Piece piece = board[y][tempX];
			
			// will break
			if(piece != null) {
				if(!sameColor(piece, color)) {
					targeting.put(tempX, y);
				}
				break;
			} 
			// so this can be put here
			targeting.put(tempX, y);
			tempX--;
		}
		
		if(iteration < 2) {
			iteration++;
			checkLeft(board, tempX, y, targeting, color, iteration);
		}
	}
	
	/**
	 * Checks right side, will stop at any point
	 * it finds a piece.
	 * If it finds a piece of a different color,
	 * it will add it to targeting
	 * 
	 * Recursively checks another time so it 
	 * adds the king to the possible moves.
	 * useful in avoiding check when moving
	 * a piece
	 * @param board
	 */
	private static void checkUp(Piece[][] board, int x, int y,  Map<Integer, Integer> targeting, PieceColor color, int iteration) {
		int tempY = y + 1;
		
		while(tempY < board.length) {
			Piece piece = board[tempY][x];
			
			// will break
			if(piece != null) {
				if(!sameColor(piece, color)) {
					targeting.put(x, tempY);
				}
				break;
			}
			// so this can be put here
			targeting.put(x, tempY);
			tempY++;
		}
		
		if(iteration < 2) {
			iteration++;
			checkUp(board, x, tempY, targeting, color, iteration);
		}
	}
	
	/**
	 * Checks right side, will stop at any point
	 * it finds a piece.
	 * If it finds a piece of a different color,
	 * it will add it to targeting
	 * 
	 * Recursively checks another time so it 
	 * adds the king to the possible moves.
	 * useful in avoiding check when moving
	 * a piece
	 * @param board
	 */
	private static void checkDown(Piece[][] board, int x, int y,  Map<Integer, Integer> targeting, PieceColor color, int iteration) {
		int tempY = y - 1;
		
		while(tempY >= 0) {
			Piece piece = board[tempY][x];
			
			// will break
			if(piece != null) {
				if(!sameColor(piece, color)) {
					targeting.put(x, tempY);
				}
				break;
			}
			// so this can be put here
			targeting.put(x, tempY);
			tempY--;
		}
		
		if(iteration < 2) {
			iteration++;
			checkDown(board, x, tempY, targeting, color, iteration);
		}

	}
	
	public static boolean sameColor(Piece piece, PieceColor color) {
		return color == piece.getColor();
	}
	

}
