package game;

import java.util.Map;

public class CheckDiags {

	public CheckDiags() {}
	
	public static void checkDiags(Piece[][] board,
								  PieceColor color,
								  Map<Integer, Integer> targeting,
								  int x,
								  int y) {
		
		bL(board, x, y, targeting, color,1);
		
		tL(board, x, y, targeting, color,1);
		
		tR(board, x, y, targeting, color,1);
		
		bR(board, x, y, targeting, color,1);
	}
	
	
	/**
	 * Similar method to checkLines. starts from center and branches outward.
	 * will then call again so that it can see if the next piece is a king.
	 * helpful for determining moves in advance.
	 * @param board
	 * @param x
	 * @param y
	 * @param targeting
	 * @param color
	 * @param iteration
	 */
	private static void bL(Piece[][] board, int x, int y,  Map<Integer, Integer> targeting, PieceColor color, int iteration) {
		int tempX = x - 1;
		int tempY = y - 1;
		
		while(tempX >= 0 && tempY >= 0) {
			Piece piece = board[tempY][tempX];
			
			if(piece != null) {
				if(!sameColor(piece, color)) {
					targeting.put(tempX, tempY);
				}
				break;
			}
			
			targeting.put(tempX, tempY);
			tempX--;
			tempY--;
		}
		
		if(iteration < 2) {
			iteration++;
			bL(board, tempX, tempY, targeting, color, iteration);
		}
	}
	
	/**
	 * Similar method to checkLines. starts from center and branches outward.
	 * will then call again so that it can see if the next piece is a king.
	 * helpful for determining moves in advance.
	 * @param board
	 * @param x
	 * @param y
	 * @param targeting
	 * @param color
	 * @param iteration
	 */
	private static void tL(Piece[][] board, int x, int y,  Map<Integer, Integer> targeting, PieceColor color, int iteration) {
		int tempX = x - 1;
		int tempY = y + 1;
		
		while(tempX >= 0 && tempY < board.length) {
			Piece piece = board[tempY][tempX];
			
			if(piece != null) {
				if(!sameColor(piece, color)) {
					targeting.put(tempX, tempY);
				}
				break;
			}
			
			targeting.put(tempX, tempY);
			tempX--;
			tempY++;
		}
		
		if(iteration < 2) {
			iteration++;
			tL(board, tempX, tempY, targeting, color, iteration);
		}
	}
	
	/**
	 * Similar method to checkLines. starts from center and branches outward.
	 * will then call again so that it can see if the next piece is a king.
	 * helpful for determining moves in advance.
	 * @param board
	 * @param x
	 * @param y
	 * @param targeting
	 * @param color
	 * @param iteration
	 */
	private static void tR(Piece[][] board, int x, int y,  Map<Integer, Integer> targeting, PieceColor color, int iteration) {
		int tempX = x + 1;
		int tempY = y + 1;
		
		while(tempX < board[0].length && tempY < board.length) {
			Piece piece = board[tempY][tempX];
			
			if(piece != null) {
				if(!sameColor(piece, color)) {
					targeting.put(tempX, tempY);
				}
				break;
			}
			
			targeting.put(tempX, tempY);
			tempX++;
			tempY++;
		}
		
		if(iteration < 2) {
			iteration++;
			tR(board, tempX, tempY, targeting, color, iteration);
		}
	}
	
	/**
	 * Similar method to checkLines. starts from center and branches outward.
	 * will then call again so that it can see if the next piece is a king.
	 * helpful for determining moves in advance.
	 * @param board
	 * @param x
	 * @param y
	 * @param targeting
	 * @param color
	 * @param iteration
	 */
	private static void bR(Piece[][] board, int x, int y,  Map<Integer, Integer> targeting, PieceColor color, int iteration) {
		int tempX = x + 1;
		int tempY = y - 1;
		
		while(tempX < board[0].length && tempY >= 0) {
			Piece piece = board[tempY][tempX];
			
			if(piece != null) {
				if(!sameColor(piece, color)) {
					targeting.put(tempX, tempY);
				}
				break;
			}
			
			targeting.put(tempX, tempY);
			tempX++;
			tempY--;
		}
		
		if(iteration < 2) {
			iteration++;
			bR(board, tempX, tempY, targeting, color, iteration);
		}
	}
	public static boolean sameColor(Piece piece, PieceColor color) {
		return color == piece.getColor();
	}

}
