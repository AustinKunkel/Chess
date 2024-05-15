package game;

import java.util.HashSet;
import java.util.Set;

/**
 * Helper class so that Rook and Queen 
 * can use the same methods to check lines
 */
public class CheckLines {
	
	private Piece[][] board;
	private int x,y;
	private PieceColor color;
	
	private Set<Coordinate> targeting;
	private Set<Coordinate> sameTarget;
	private Set<Coordinate> setToKeep;
	
	private Coordinate origPieceCoord;
	private Piece king;
	private Piece breakPiece;
	private Piece currPiece;
	
	private boolean kingInCheck;
	
	public CheckLines(Piece[][] board, PieceColor color, Set<Coordinate> targeting, Coordinate origPieceCoord, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
		this.color = color;
		this.origPieceCoord = origPieceCoord;
		this.targeting = targeting;
		
		this.setToKeep = new HashSet<>();
		this.sameTarget = new HashSet<>();
		this.breakPiece = null;
		this.currPiece = null;
		this.king = null;
		this.kingInCheck = false;
	}
	
	public Set<Coordinate> checkAll() {
		
		clearAll();
		
		Set<Coordinate> set = new HashSet<>();
		//adds all lines
		set.addAll(checkLines());
		set.addAll(checkDiags());
		
		return set;
	}
	
	/**
	 * Iterates through each line to check.
	 * @param board
	 * @param color
	 * @param targeting
	 * @param pieceCoord => Coordinates of the piece
	 * @param x
	 * @param y
	 * @return a Set<Coordinate> of pieces that aren't directly 
	 * targeted, but can be if behind a piece
	 */
	public Set<Coordinate> checkLines() {
		
		clearAll();
		
		// if king was put in check
		if(check(x, y, 1, -1, 0)) {// check left line
			this.kingInCheck = true;
		}
		
		if(!kingInCheck) {
			setToKeep.clear();
		}
		if(check(x, y, 1, 1, 0)) {// check right line
			this.kingInCheck = true;
		}
		
		if(!kingInCheck) {
			setToKeep.clear();
		}
		if(check(x, y, 1, 0, 1)) { // check up
			this.kingInCheck = true;
		}
		
		if(!kingInCheck) {
			setToKeep.clear();
		}
		if(check(x, y, 1, 0, -1)) { // check down
			this.kingInCheck = true;
		}
		
		if(this.kingInCheck)
			kingIsInCheck();
		
		return sameTarget;

	}
	
	public Set<Coordinate> checkDiags() {

		clearAll();
		
		boolean kingInCheck = false;

		//if king was put in check
		if(check(x, y, 1, -1, -1)) {// check bL
			kingInCheck = true;
		}
		
		if(!kingInCheck) {
			setToKeep.clear();
		}
		if(check(x, y, 1, -1, 1)) {// check tL
			kingInCheck = true;
		}
		
		if(!kingInCheck) {
			setToKeep.clear();

		}
		if(check(x, y, 1, 1, 1)) {// check tR
			kingInCheck = true;
		}
		
		if(!kingInCheck) {
			setToKeep.clear();
		}
		if(check(x, y, 1, 1, -1)) {// check bR
			kingInCheck = true;
		}
		
		System.out.println("set to keep: " + setToKeep);
		if(kingInCheck)
			kingIsInCheck();
			
		return sameTarget;
	
	}
	
	/**
	 * Helper function for when the king is in check
	 * will find the intersection of the piece and 
	 * the king
	 */
	private void kingIsInCheck() {
		System.out.println("King in check");
		if(king == null) {
			return;
		}
		Set<Coordinate> intersection = new HashSet<>();
		Set<Coordinate> kingTargeting = king.getTargeting();
		
		for(Coordinate c : kingTargeting) {
			if(targeting.contains(c)) {
				intersection.add(c);
			}
				
		}
		
		intersection.addAll(setToKeep);
		
		System.out.println(targeting);
	}
	

	/**
	 * Similar method to checkLines. starts from center and branches outward.
	 * will then call again so that it can see if the next piece is a king.
	 * helpful for determining moves in advance.
	 * @param board
	 * @param x
	 * @param y
	 * @param color
	 * @param iteration
	 * @param xFactor
	 * @param yFactor
	 * @return true if king is in check; false otherwise
	 */
	private boolean check(int x, int y, int iteration, int xFactor, int yFactor) {
		int tempX = x + xFactor;
		int tempY = y + yFactor;
		
		while((tempX <= board[0].length - 1) && (tempY <= board.length - 1) && tempX >= 0 && tempY >= 0) {
			currPiece = board[tempY][tempX];
			
			if(currPiece != null) {
				if(!sameColor(currPiece, color)) {
					// add piece to targeting
					if(iteration < 2) {
						addCoordinate(tempX, tempY);
					}
					
					//if king is in check
					if(currPiece.getType() == PieceType.KING) {
						king = currPiece;
						System.out.println(king);
						return true;
					}
					
					//so we can keep track of the piece that we need to update
					// need to do this after so the logic underneath works
					if(iteration < 2) {
						breakPiece = currPiece;
					}
				} else {
					sameTarget.add(new Coordinate(tempX, tempY));
				}
				break;
			}
			if(iteration < 2) {
				addCoordinate(tempX, tempY);
			}
			
			tempX += xFactor;
			tempY += yFactor;
		}
		
		if(iteration < 2) {
			iteration++;
			if(check(tempX, tempY, iteration, xFactor, yFactor)) {// if king is in check
				if(breakPiece != null) {// and there was a piece before it
					// update that piece's targeting 
					updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
				}
			}
		}
		
		breakPiece = null;
		
		return false;
	}
	
	private void addCoordinate(int x, int y) {
		Coordinate coord = new Coordinate(x, y);
		targeting.add(coord);
		if(!kingInCheck) {
			setToKeep.add(coord);
		}
	}
	
	public static boolean sameColor(Piece piece, PieceColor color) {
		return color == piece.getColor();
	}
	
	/**
	 * keeps intersection of piece and targeting of piece
	 * that can check king
	 * @param piece
	 * @param targeting => Set of the piece that is targeting the king
	 */
	private void updatePieceTargetingIfCheck(Piece piece, Set<Coordinate> currTargeting) {
		Set<Coordinate> pieceTargeting = piece.getTargeting();
		Set<Coordinate> intersection = new HashSet<>();
		
		// if the piece can attack
		if(pieceTargeting.contains(origPieceCoord)) {
			intersection.add(origPieceCoord);
		}
		
		for(Coordinate coord : currTargeting) {
			if(pieceTargeting.contains(coord)) {
				intersection.add(coord);
			}	
		}
		
		piece.setTargeting(intersection);
	}
	
	private void clearAll() {
		this.setToKeep.clear();
		this.sameTarget.clear();
		
		this.breakPiece = null;
		this.currPiece = null;
		this.king = null;
	}
}
