package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
	
	private static final PieceColor white = PieceColor.WHITE;
	private static final PieceColor black = PieceColor.BLACK;
	
	private static Piece[][] b = {{new Rook(0,0, white), new Knight(1,0, white), new Bishop(2,0, white), new Queen(3,0, white), new King(4,0, white), new Bishop(5,0, white), new Knight(6,0, white), new Rook(7,0, white)},
									  {new Pawn(0,1, white),new Pawn(1,1, white), new Pawn(2,1, white), new Pawn(3,1, white), new Pawn(4,1, white), new Pawn(5,1, white), new Pawn(6,1, white), new Pawn(7,1, white)},
									  {null, null, null, null, null, null, null, null},
									  {null, null, null, null, null, null, null, null},
									  {null, null, null, null, null, null, null, null},
									  {null, null, null, null, null, null, null, null},
									  {new Pawn(0,6, black),new Pawn(1,6, black), new Pawn(2,6, black), new Pawn(3,6, black), new Pawn(4,6, black), new Pawn(5,6, black), new Pawn(6,6, black), new Pawn(7,6, black)},
									  {new Rook(0,7, black), new Knight(1,7, black), new Bishop(2,7, black), new Queen(3,7, black), new King(4,7, black), new Bishop(5,7, black), new Knight(6,7, black), new Rook(7,7, black)}};
	
	private static Piece[][] board = {{null, null, null, new King(3, 0, white),null, null, null, new Rook(7, 0, PieceColor.BLACK)},
			                          {null, null, new Pawn(2, 1, white), new Pawn(3, 1, white), new Rook(4, 1, white), null, null, null}};
	
	private static List<Piece> allPieces = new ArrayList<>();

	public static void main(String[] args) {
		
		// initializes the list that has all of the available pieces.
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] != null) {
					allPieces.add(board[i][j]);
				}
			}
		}

		// ----------------------------
		simulateUpdate();
		simulateMove(allPieces.get(4), new Coordinate(4, 0));
		simulateUpdate();

		// set each piece's targeting methods BEFORE each move, so that
		// when clicked on a piece, it will show the specific targeting, so
		// the player can see where they can move
		
		// will then see if the king is in check, in which case, we will see
		// which piece(s) are attacking the king by retrieving the list from 
		// isCheck(). First check if list is empty, in which case => pass thru.
		// otherwise, player can only move to spaces in which is not being targeted
		// by any of the pieces in the list. 
		//
		// Can do this by:
		// 1) removing any coordinate that the king is targeting,
		// except for that which is not being targeted by another piece.
		// 2) removing any other piece's targeting that is not actively blocking
		// the check.
		//
		// if not possible in 1 move, checkmate
		
		// if the king can be put under check by a piece.
		
		// if pieceList.size() > 0 => updateMoveIfInCheck();
		
	}

	/**
	 * Simulates a move
	 * @param p piece to be moved
	 * @param l location to be moved
	 */
	public static void simulateMove(Piece p, Coordinate l) {
		System.out.println("Trying to move to " + l.getX() + ", " + l.getY());
		if(!move(p, l)) {
			System.out.println("Cannot move to position " + l.getX() + ", " + l.getY());
		}
		System.out.println(p.getX() + ", " + p.getY());
	}

	/**
	 * simulates an update
	 */
	public static void simulateUpdate() {
		if(!updateAllTargeted()) {
			checkMate();
		}
		
		for(Piece piece : allPieces) {
			System.out.println(piece.toString() + " is targeting: " + piece.getTargeting());
		}
	}
	
	/**
	 * removes the selected piece from the board
	 * @param piece => piece to be removed
	 * @return true if piece is removed, false if piece is null
	 */
	private static boolean killPiece(Piece piece) {
		if(piece == null)
			return false;
		
		board[piece.getY()][piece.getX()] = null;
		
		allPieces.remove(piece);
		return true;
	}
	
	/**
	 * Moves the selected piece into the desired x, y coordinate
	 * @param piece to be moved
	 * @param x => x position to be moved
	 * @param y => y position to be moved
	 * @return true if space can be moved to, false otherwise
	 */
	private static boolean move(Piece piece, Coordinate c) {
		
		if(piece == null) {
			System.out.println("Piece is null");
			return false;
		}
		
		Set<Coordinate> targeting = piece.getTargeting();	
		
		// if the value is not in the targeting array
		if(!targeting.contains(c)) {
			System.out.println("Does not contain that coordinate");			
			return false;
		}
		
		Piece piece2 = board[c.getY()][c.getX()];
		
		//if empty space, move into empty space
		if(piece2 == null) {
			return moveIntoEmpty(piece, c.getX(), c.getY());
		}
		
		// if the pieces' colors are the same
		if(piece.sameColor(piece2)) {
			
			return false;
		}
		
		// A piece exists there, so it must be killed
		
		if(!killPiece(board[c.getY()][c.getX()])) {
			System.out.println("Could not kill piece: " + board[c.getY()][c.getX()]);
			return false;
		}
				
		return moveIntoEmpty(piece, c.getX(), c.getY());
	}
	
	/**
	 * Moves the selected piece into the x and y spot
	 * @param piece to be moved
	 * @param x	column
	 * @param y	row
	 * @return true if piece was successfully moved; false otherwise
	 */
	private static boolean moveIntoEmpty(Piece piece, final int x, final int y) {
		if(piece == null)
		{
			return false;
		}
		
		if (board[y][x] != null) {
	        return false; // Ensures the target spot is empty before moving
	    }
			
		int origX = piece.getX();
		int origY = piece.getY();
		
		//swaps the pieces on the board
		board[origY][origX] = null;
		board[y][x] = piece;
		
		//now swaps the internal positions of the piece
		piece.setPos(x, y);
		
		// checks to see if the old spot is null
		// and that the new spot has the new coordinates		
		return board[origY][origX] == null &&
			   piece.getX() == x && piece.getY() == y &&
			   board[y][x] != null;

	}
	
	/**
	 * Will iterate over available pieces and get all by specified color
	 * @param color
	 * @return List<Piece> of the pieces that are the color
	 */
	private static List<Piece> getAllPieceByColor(PieceColor color) {
		List<Piece> listofColor = new ArrayList<>();
		for(Piece piece : allPieces) {
			if(piece.getColor() == color) {
				listofColor.add(piece);
			}
		}
		return listofColor;
	}
	
	/**
	 * Updates ALL piece's targeting lists.
	 * @return true if no mate, false if mate
	 */
	private static boolean updateAllTargeted() {
		
		Piece wKing = null;
		Piece bKing = null;
		List<Piece> pieceList = new ArrayList<>();
		boolean isMate = false;
		
		// updates targeting and finds kings
		for(Piece piece : allPieces) {
			piece.updateTargeting(board);
			if(piece.getType() == PieceType.KING) {
				if(piece.getColor() == PieceColor.BLACK) {
					bKing = piece;
				} else {
					wKing = piece;	
				}
			}
		}
		
		
		if(wKing != null) {
			pieceList = getAllPieceByColor(black);
			 if(!updateMoveIfInCheck(pieceList, wKing))
			 	isMate = true;
		}
		if(bKing != null) {
			pieceList = getAllPieceByColor(white);
			if(!updateMoveIfInCheck(pieceList, bKing))
				isMate = true;
		}
		
		return !isMate;
	}
	
	private static void checkMate() {
	System.out.println("CHECKMATE");
	}
	
	/**
	 * Goes through checklist and sees how the pieces can move if king
	 * is in check
	 * @param pieceList of pieces opposite color of king
	 * @param king
	 * @return true if there is a place to move; false if checkmate
	 */
	private static boolean updateMoveIfInCheck(List<Piece> pieceList, Piece king) {
		
		List<Piece> listOfKingColor = getAllPieceByColor(king.getColor());
		
		listOfKingColor.remove(king);
		// go through each piece that is currently targeting king
		// and update the list of pieces that can move out of the
		// way


		boolean pieceCanMove = true;
		
		for(Piece piece : pieceList) {

			Set<Coordinate> pieceTargeting = piece.getTargeting();

			Coordinate pieceLocation = new Coordinate(piece.getX(), piece.getY());
					
			// update the king's moves and see if it is in check
			if(updateKingMove(king, piece, listOfKingColor)) {
					System.out.println("King in check");
				// update piece's targeting to see if it can move to block
				pieceCanMove = updatePieceMoveIfInCheck(listOfKingColor, pieceTargeting, pieceLocation);
			}
		}
		return king.getTargeting().size() > 0 || pieceCanMove;
	}
	
	/**
	 * helper function for updateMoveIfInCheck
	 * 
	 * Updates the king's moves.
	 * Does this by getting places where it cannot move and removing them from list.
	 * 
	 * Will check to see if a piece is protected by simulating a move on the king
	 * with those coordinates. If it is in check the move will not be added
	 * @param entry
	 * @param kingTargeting
	 * @param piece => the piece targeting the king
	 * @return true if king is in check; false otherwise
	 */
	private static boolean updateKingMove(Piece king, Piece piece, List<Piece> sameColor) {
		
		boolean isCheck = false;
		Set<Coordinate> pieceTargeting = piece.getTargeting();
		Set<Coordinate> kingTargeting = king.getTargeting();
		
		removeUnwantedCoords(kingTargeting, pieceTargeting);

		// does this to stop killing protected pieces
		removeUnwantedCoords(kingTargeting, piece.getSameColorTargeting());
		// if the piece can target the king
		if(pieceTargeting.contains(new Coordinate(king.getX(), king.getY()))) {
			isCheck = true;
		}
		return isCheck;	
	}

	/**
	 * Removes an intersecting element from set1 that both set1 and set2 have
	 * 
	 * Effectively s1 = s1 - s2
	 * @param set1
	 * @param set2
	 */
	private static void removeUnwantedCoords(Set<Coordinate> set1, Set<Coordinate> set2) {
		for(Coordinate coord : set2) {
			if(set1.contains(coord)) {
				set1.remove(coord);
			}
		}
	}
	
	/**
	 * helper function for updateMoveIfInCheck
	 * 
	 * Updated all of the piece's moves to see
	 * if it can block a check.
	 * Does this by getting the intersection of
	 * the pieces of the same color and the
	 * map of the piece that is targeting the king
	 * @param sameColor => List of pieces of the same color
	 * @param targeting => Set of coordinates of the piece that is targeting it
	 * @param pieceCoord => coordinate of the piece that is targeting king
	 * @return true if there is a place to block the check; false otherwise
	 */
	private static boolean updatePieceMoveIfInCheck(List<Piece> sameColor, Set<Coordinate> targeting, Coordinate pieceCoord) {

		if(sameColor.size() <= 0)
			return true;

		boolean flag = false;

		for(Piece piece : sameColor) {

			boolean canTakePiece = false;
			
			
			Set<Coordinate> intersection = new HashSet<>();
			
			Set<Coordinate> pieceTargeting = piece.getTargeting();
			
			
			for(Coordinate coord : targeting) {
				if(pieceTargeting.contains(coord)) {
					intersection.add(coord);
				}	
			}
			
			//checks to see if the piece that is targeting king can be taken
			canTakePiece = pieceTargeting.contains(pieceCoord);

			// if so, then we can add it back to the targeting list
			if(canTakePiece) {
				intersection.add(pieceCoord);
			}
			
			piece.setTargeting(intersection);
			
			
			if(intersection.size() > 0) {
				flag = true;
			}
		}
		
		return flag;
	}
}
	
