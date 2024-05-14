package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Game {
	
	private static final PieceColor white = PieceColor.WHITE;
	private static final PieceColor black = PieceColor.BLACK;
	
	private static Piece[][] board = {{new Rook(0,0, white), new Knight(1,0, white), new Bishop(2,0, white), new Queen(3,0, white), new King(4,0, white), new Bishop(5,0, white), new Knight(6,0, white), new Rook(7,0, white)},
									  {new Pawn(0,1, white),new Pawn(1,1, white), new Pawn(2,1, white), new Pawn(3,1, white), new Pawn(4,1, white), new Pawn(5,1, white), new Pawn(6,1, white), new Pawn(7,1, white)},
									  {null, null, null, null, null, null, null, null},
									  {null, null, null, null, null, null, null, null},
									  {null, null, null, null, null, null, null, null},
									  {null, null, null, null, null, null, null, null},
									  {new Pawn(0,6, black),new Pawn(1,6, black), new Pawn(2,6, black), new Pawn(3,6, black), new Pawn(4,6, black), new Pawn(5,6, black), new Pawn(6,6, black), new Pawn(7,6, black)},
									  {new Rook(0,7, black), new Knight(1,7, black), new Bishop(2,7, black), new Queen(3,7, black), new King(4,7, black), new Bishop(5,7, black), new Knight(6,7, black), new Rook(7,7, black)}};
	
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
		
		updateAllTargeted();
		
		for(Piece piece : allPieces) {
			System.out.println(piece.toString() + " is targeting: " + piece.getTargeting());
		}
		
		// - Move 1 ---------------------------------------------------------
		
		Piece piece1 = allPieces.get(13); // gets f2 pawn
		System.out.println(piece1);
		
		System.out.println("Trying to move to 5, 3");
		if(!move(piece1, new Coordinate(piece1.getX(), piece1.getY() + 2))) {
			System.out.println("Cannot move to position 5, 3");
		}
		System.out.println(piece1.getX() + ", " + piece1.getY());
		
		// ---------------------------------------------------------------
		
		updateAllTargeted();
		
		for(Piece piece : allPieces) {
			System.out.println(piece.toString() + " is targeting: " + piece.getTargeting());
		}
		
		// - Move 2 ---------------------------------------------------------
		
		Piece piece2 = allPieces.get(20);// e7 pawn
		System.out.println(piece2);
		
		System.out.println("Moving down 2 spaces");
		move(piece2, new Coordinate(piece2.getX(), piece2.getY() - 2));
		System.out.println(piece2.getX() + ", " + piece2.getY());
		
		System.out.println("trying to move 2 spaces down again:");
		if(!move(piece2, new Coordinate(piece2.getX(), piece2.getY() - 2))) {
			System.out.println("Cannot move down 2 again");
		}		
		System.out.println(piece2.getX() + ", " + piece2.getY());

		// ---------------------------------------------------------------

		updateAllTargeted();
		
		for(Piece piece : allPieces) {
			System.out.println(piece.toString() + " is targeting: " + piece.getTargeting());
		}
		
		// - Move 3 ---------------------------------------------------------
		
		Piece piece3 = allPieces.get(14); // get g2 pawn
		
		System.out.println("Trying to move to 6, 3");
		if(!move(piece3, new Coordinate(piece3.getX(), piece3.getY() + 2))) {
			System.out.println("Cannot move to position 6, 3");
		}
		System.out.println(piece3.getX() + ", " + piece3.getY());
		
		// ---------------------------------------------------------------

		updateAllTargeted();
		
		for(Piece piece : allPieces) {
			System.out.println(piece.toString() + " is targeting: " + piece.getTargeting());
		}
		
		// - Move 4 --- Black check mates ---------------------------------
		 Piece piece4 = allPieces.get(27);
		 
		System.out.println("Trying to move to 7, 3");
		if(!move(piece4, new Coordinate(7,3))) {
			System.out.println("Cannot move to position 7, 3");
		}
		System.out.println(piece4.getX() + ", " + piece4.getY());
		
		// ---------------------------------------------------------------

		updateAllTargeted();
		
		for(Piece piece : allPieces) {
			System.out.println(piece.toString() + " is targeting: " + piece.getTargeting());
		}
		
			
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
		
		// if checkList.size() > 0 => updateMoveIfInCheck();

		
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
			return false;
		}
		
		Set<Coordinate> targeting = piece.getTargeting();	
		
		// if the value is not in the targeting array
		if(!targeting.contains(c)) {
			return false;
		}
		
		System.out.println(c);
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
		
		if(!killPiece(board[c.getX()][c.getY()]))
			return false;
			
			
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
			System.out.println("Piece is null");
			return false;
		}
		
		if (board[y][x] != null) {
	        System.out.println("Target spot is not empty");
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
			   piece.getX() == x && piece.getY() == y;

	}
	
	/**
	 * Checks to see if the king is under check. Does this
	 * by iterating over each piece and checking if they're 
	 * targeting the king
	 * @param king
	 * @return a List<Piece> that is targeting the king
	 */
	private static List<Piece> getCheckList(Piece king) {
		
		List<Piece> checkList = new ArrayList<>();
		final Coordinate kingCoord = new Coordinate(king.getX(), king.getY());
		
		for(Piece piece : allPieces) {
			//makes sure that the pieces are not the same color
			if(!king.sameColor(piece)) {
				Set<Coordinate> targeting = piece.getTargeting();
				
				if(targeting.contains(kingCoord)) {
					checkList.add(piece);
				}
			}
		}
		
		return checkList;
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
	 */
	private static void updateAllTargeted() {
		
		Piece wKing = null;
		Piece bKing = null;
		List<Piece> checkList = new ArrayList<>();
		
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
			checkList = getCheckList( allPieces.get(allPieces.indexOf(wKing)));
			if(!updateMoveIfInCheck(checkList, wKing)) checkMate();
		}
		if(bKing != null) {
			checkList = getCheckList( allPieces.get(allPieces.indexOf(bKing)));
			if(!updateMoveIfInCheck(checkList, bKing)) checkMate();

		}
	}
	
	private static void checkMate() {
	System.out.println("CHECKMATE");
	}
	
	/**
	 * Goes through checklist and sees how the pieces can move if king
	 * is in check
	 * @param checkList
	 * @param king
	 * @return true if there is a place to move; false if checkmate
	 */
	private static boolean updateMoveIfInCheck(List<Piece> checkList, Piece king) {
		
		boolean flag = true;
		
		List<Piece> listOfKingColor = getAllPieceByColor(king.getColor());
		
		listOfKingColor.remove(king);
		// go through each piece that is currently targeting king
		// and update the list of pieces that can move out of the
		// way
		for(Piece piece : checkList) {
			Set<Coordinate> kingTargeting = king.getTargeting();
			Set<Coordinate> pieceTargeting = piece.getTargeting();
			
			// iterates through piece's targeting.
			// will see if it is in king's targeting. will then remove that spot
			// from the king's targeting.
				
				// check if king can move
				boolean kingCanMove = updateKingMoveIfInCheck(pieceTargeting, kingTargeting);
				
				// update piece's targeting to see if it can move to block
				boolean pieceCanMove = updatePieceMoveIfInCheck(listOfKingColor, pieceTargeting);
				
				if(!(kingCanMove && pieceCanMove)) {
					flag = false;
				} else {
					flag = true;
				}
		}
		return flag;
	}
	
	/**
	 * helper function for updateMoveIfInCheck
	 * 
	 * Updates the king's moves if it is in check.
	 * Does this by getting places where it cannot move
	 * @param entry
	 * @param kingTargeting
	 * @return true if king has place to go; false otherwise
	 */
	private static boolean updateKingMoveIfInCheck(Set<Coordinate> pieceTargeting, Set<Coordinate> kingTargeting) {
		
		for(Coordinate coord : pieceTargeting) {
			if(kingTargeting.contains(coord)) {
				kingTargeting.remove(coord);
			}		
		}
		return kingTargeting.size() > 0;	
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
	 * @param targeting => Map of targeting coordinates
	 * @return true if there is a place to block the check; false otherwise
	 */
	private static boolean updatePieceMoveIfInCheck(List<Piece> sameColor, Set<Coordinate> targeting) {
		boolean flag = true;
	
		for(Piece piece : sameColor) {
			Set<Coordinate> intersection = new HashSet<>();
			Set<Coordinate> pieceTargeting = piece.getTargeting();
			
			for(Coordinate coord : targeting) {
				if(pieceTargeting.contains(coord)) {
					intersection.add(coord);
				}	
			}
			
			piece.setTargeting(intersection);
			
			if(intersection.size() <= 0) {
				flag = false;
			} else {
				flag = true;
			}
		}
		
		System.out.println(flag);
		return flag;
	}
}
	
