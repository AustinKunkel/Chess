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

		this.kingInCheck = false;
		
		clearAll();
		
		Set<Coordinate> set = new HashSet<>();
		//adds all lines
		set.addAll(checkLines(this.kingInCheck, this.king));
		set.addAll(checkDiags(this.kingInCheck, this.king));
		
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
	public Set<Coordinate> checkLines(boolean checkFlag, Piece newKing) {

		this.kingInCheck = checkFlag;
		this.king = newKing;
		clearAll();
		
		// if king was put in check
		if(check(x, y, 1, -1, 0, false)) {// check left line
			checkFlag = true;
		}
		
		kingInCheck = checkFlag;
		if(!checkFlag) {
			setToKeep.clear();
		}
		if(check(x, y, 1, 1, 0, false)) {// check right line
			checkFlag = true;
		}
		
		kingInCheck = checkFlag;
		if(!checkFlag) {
			setToKeep.clear();
		}
		if(check(x, y, 1, 0, 1, false)) { // check up
			checkFlag = true;
		}
		
		kingInCheck = checkFlag;
		if(!checkFlag) {
			setToKeep.clear();
		}
		if(check(x, y, 1, 0, -1, false)) { // check down
			checkFlag = true;
		}
		
		kingInCheck = checkFlag;
		if(checkFlag) {
			kingIsInCheck();
		}

		return sameTarget;

	}
	
	public Set<Coordinate> checkDiags(boolean checkFlag, Piece newKing) {

		this.king = newKing;
		this.kingInCheck = checkFlag;
		clearAll();

		//if king was put in check
		if(check(x, y, 1, -1, -1, false)) {// check bL
			checkFlag = true;
		}
		
		kingInCheck = checkFlag;
		if(!checkFlag) {
			setToKeep.clear();
		}
		if(check(x, y, 1, -1, 1, false)) {// check tL
			checkFlag = true;
		}
		
		kingInCheck = checkFlag;
		if(!checkFlag) {
			setToKeep.clear();

		}
		if(check(x, y, 1, 1, 1, false)) {// check tR
			checkFlag = true;
		}
		
		kingInCheck = checkFlag;
		if(!checkFlag) {
			setToKeep.clear();
		}
		if(check(x, y, 1, 1, -1, false)) {// check bR
			checkFlag = true;
		}

		
		if(checkFlag) {
			this.kingInCheck = true;
			kingIsInCheck();
		}
			

			
		return sameTarget;
	
	}
	
	/**
	 * Helper function for when the king is in check
	 * will find the intersection of the piece and 
	 * the king
	 */
	private void kingIsInCheck() {
		if(this.king == null)
		 {
			System.out.println("king null");
			return;
		 }
		Set<Coordinate> intersection = new HashSet<>();
		Set<Coordinate> kingTargeting = king.getTargeting();

		for(Coordinate c : kingTargeting) {
			if(targeting.contains(c))
				intersection.add(c);
		}
		intersection.addAll(setToKeep);

		targeting.clear();
		targeting.addAll(intersection);

	}
	

	/**
	 * Similar method to checkLines. starts from center and branches outward.
	 * will then call again so that it can see if the next piece is a king.
	 * helpful for determining moves in advance.
	 * @param board
	 * @param x
	 * @param y
	 * @param color
	 * @param iteration => iteration number (used internally to determine path past king)
	 * @param xFactor	=> factor by which x is added
	 * @param yFactor => factor by which y is added
	 * @param flag => flag if king is in check
	 * @return true if king is in check; false otherwise
	 */
	private boolean check(int x, int y, int iteration, int xFactor, int yFactor, boolean flag) {
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
						flag = true;
					}
					
					//so we can keep track of the piece that we need to update
					// need to do this after so the logic underneath works
					if(iteration >= 2){
						if(!sameColor(currPiece, breakPiece.getColor())) {
							breakPiece = null;
						}
					} else {
						breakPiece = currPiece;
					}
						

				} else {// if(!sameColor(currPiece, color))
					sameTarget.add(new Coordinate(tempX, tempY));
				}
				break;
			} else { // if(currPiece != null)
				if(iteration >= 2)
					sameTarget.add(new Coordinate(tempX, tempY));
			}
			if(iteration < 2) {
				addCoordinate(tempX, tempY);
			}
			
			tempX += xFactor;
			tempY += yFactor;
		}
		
		if(iteration < 2) {
			iteration++;
			if(check(tempX, tempY, iteration, xFactor, yFactor, flag)) {// if king is in check
				if(breakPiece != null) {// and there was a piece before it
					// update that piece's targeting 
					updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
				}
			}
		}
		
		breakPiece = null;
		
		return flag;
	}
	
	private void addCoordinate(int x, int y) {
		Coordinate coord = new Coordinate(x, y);
		targeting.add(coord);
		if(!this.kingInCheck) {
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
		if(!this.kingInCheck)
			this.setToKeep.clear();
		this.sameTarget.clear();
		
		this.breakPiece = null;
		this.currPiece = null;
	}
}
