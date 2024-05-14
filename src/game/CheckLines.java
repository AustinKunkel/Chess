package game;

import java.util.HashSet;
import java.util.Set;

/**
 * Helper class so that Rook and Queen 
 * can use the same methods to check lines
 */
public class CheckLines {
	
	private static Set<Coordinate> canTarget;
	
	private static Piece breakPiece = null;
	
	private static Piece currPiece = null;
	
	public CheckLines() {}
	
	public static Set<Coordinate> checkLines(Piece[][] board,
								  PieceColor color,
								  Set<Coordinate> targeting,
								  int x,
								  int y) {
		
		canTarget = new HashSet<>();
		
		// checking right side of the rank:
		checkRight(board, x, y, targeting, color, 1);
		
		// checking left side:
		checkLeft(board, x, y, targeting, color, 1);
		
		// checking up:
		checkUp(board, x, y, targeting, color, 1);
		
		// checking down:
		checkDown(board, x, y, targeting, color, 1);
		
		return canTarget;
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
	private static boolean checkRight(Piece[][] board, int x, int y, Set<Coordinate> targeting, PieceColor color, int iteration) {
		int tempX = x + 1;
		while(tempX < board[0].length) {
			currPiece = board[y][tempX];
			// will break
			if(currPiece != null) {
				if(!sameColor(currPiece, color)) {
					targeting.add(new Coordinate(tempX, y));

					//if king is in check
					if(currPiece.getType() == PieceType.KING) {
						return true;
					}
					
					//so we can keep track of the piece that we need to update
					if(iteration < 2) {
						breakPiece = currPiece;
					}
				}
				break;
			} 
			// so this can be put here
			targeting.add(new Coordinate(tempX, y));
			tempX++;
		}
		
		if(iteration < 2) {
			iteration++;
			if(checkRight(board, tempX, y, canTarget, color, iteration)) {// if king is in check
				if(breakPiece != null) {// and there was a piece before it
					// update that piece's targeting 
					updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
				}	
			}
		}
		
		breakPiece = null;
		
		return false;
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
	private static boolean checkLeft(Piece[][] board, int x, int y,  Set<Coordinate> targeting, PieceColor color, int iteration) {
		int tempX = x - 1;
		
		while(tempX >= 0) {
			currPiece = board[y][tempX];
			
			// will break
			if(currPiece != null) {
				if(!sameColor(currPiece, color)) {
					targeting.add(new Coordinate(tempX, y));

					//if king is in check
					if(currPiece.getType() == PieceType.KING) {
						return true;
					}
					
					//so we can keep track of the piece that we need to update
					if(iteration < 2) {
						breakPiece = currPiece;
					}
				}
				
				break;
			} 
			// so this can be put here
			targeting.add(new Coordinate(tempX, y));
			tempX--;
		}
		if(iteration < 2) {
			iteration++;
			if(checkLeft(board, tempX, y, canTarget, color, iteration)) {// if king is in check
				if(breakPiece != null) {// and there was a piece before it
					// update that piece's targeting 
					updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
				}	
			}
		}
		
		breakPiece = null;
		
		return false;
		
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
	private static boolean checkUp(Piece[][] board, int x, int y,  Set<Coordinate> targeting, PieceColor color, int iteration) {
		int tempY = y + 1;
		
		while(tempY < board.length) {
			currPiece = board[tempY][x];
			
			// will break
			if(currPiece != null) {
				if(!sameColor(currPiece, color)) {
					targeting.add(new Coordinate(x, tempY));

					//if king is in check
					if(currPiece.getType() == PieceType.KING) {
						return true;
					}
					
					//so we can keep track of the piece that we need to update
					if(iteration < 2) {
						breakPiece = currPiece;
					}
				}
				break;
			}
			// so this can be put here
			targeting.add(new Coordinate(x, tempY));
			tempY++;
		}
		
		if(iteration < 2) {
			iteration++;
			if(checkUp(board, x, tempY, canTarget, color, iteration)) {// if king is in check
				if(breakPiece != null) {// and there was a piece before it
					// update that piece's targeting 
					updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
				}	
			}
		}
		
		breakPiece = null;
		
		return false;
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
	private static boolean checkDown(Piece[][] board, int x, int y,  Set<Coordinate> targeting, PieceColor color, int iteration) {
		int tempY = y - 1;
		
		while(tempY >= 0) {
			currPiece = board[tempY][x];
			
			// will break
			if(currPiece != null) {
				if(!sameColor(currPiece, color)) {
					targeting.add(new Coordinate(x, tempY));
					
					//if king is in check
					if(currPiece.getType() == PieceType.KING) {
						return true;
					}
					
					//so we can keep track of the piece that we need to update
					if(iteration < 2) {
						breakPiece = currPiece;
					}
				}
				break;
			}
			// so this can be put here
			targeting.add(new Coordinate(x, tempY));
			tempY--;
		}
		
		if(iteration < 2) {
			iteration++;
			if(checkDown(board, x, tempY, canTarget, color, iteration)) {// if king is in check
				if(breakPiece != null) {// and there was a piece before it
					// update that piece's targeting 
					updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
				}	
			}
		}
		
		breakPiece = null;
		
		return false;

	}
	
	public static boolean sameColor(Piece piece, PieceColor color) {
		return color == piece.getColor();
	}
	
	/**
	 * keeps intersection of piece and targeting of piece
	 * that can check king
	 * @param piece
	 * @param targeting => map of the piece that is targeting the king
	 */
	private static void updatePieceTargetingIfCheck(Piece piece, Set<Coordinate> targeting) {
		Set<Coordinate> pieceTargeting = piece.getTargeting();
		Set<Coordinate> intersection = new HashSet<>();
		
		for(Coordinate coord : targeting) {
			if(pieceTargeting.contains(coord)) {
				intersection.add(coord);
			}	
		}
		
		piece.setTargeting(intersection);
	}

}
