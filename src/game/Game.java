package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
	
	private static final PieceColor white = PieceColor.WHITE;
	private static final PieceColor black = PieceColor.BLACK;
	
	private static Piece[][] board = {{new Rook(0,7, black), },{null}};
	
	private static List<Piece> allPieces = new ArrayList<>();

	public static void main(String[] args) {
		System.out.print("Hello World!");
		
		// initializes the list that has all of the available pieces.
		for(int i = 0; i < board.length - 1; i++) {
			for(int j = 0; j < board[0].length - 1; j++) {
				if(board[i][j] != null) {
					allPieces.add(board[i][j]);
				}
			}
		}
		
		// set each piece's targeting methods BEFORE each move, so that
		// when clicked on a piece, it will show the specific targeting, so
		// the player can see where they can move
		
		// will then see if the king is in check, in which case, we will see
		// which piece(s) are attacking the king by retreiving the list from 
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
	private boolean killPiece(Piece piece) {
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
	private boolean move(Piece piece, int x, int y) {
		
		Map<Integer, Integer> targeting = piece.getTargeting();		
		
		if(!targeting.containsKey(x)) {
			return false;
		}
		
		Piece piece2 = board[y][x];
		
		//if empty space, move into empty space
		if(piece2 == null) {
			
			return moveIntoEmpty(piece, x, y);
			
		}
		
		// if the 
		if(piece.getColor() == piece2.getColor()) {
			
			return false;
		}
		
		// A piece exists there, so it must be killed
		
			killPiece(board[y][x]);
			
			return moveIntoEmpty(piece, x, y);
		}
	
	/**
	 * Moves the selected piece into the x and y spot
	 * @param piece to be moved
	 * @param x	column
	 * @param y	row
	 * @return true if piece was successfully moved; false otherwise
	 */
	private boolean moveIntoEmpty(Piece piece, int x, int y) {
		if(piece == null)
		{
			return false;
		}
			
		Piece temp = board[piece.getY()][piece.getX()];	
		
		//swaps the pieces on the board
		board[piece.getY()][piece.getX()] = board[y][x];
		board[y][x] = temp;
		
		//now swaps the internal positions of the pieces
		piece.setPos(x, y);
		
		// checks to see if the new spot is null
		// and that the new spot has the new coordinates
		return board[temp.getY()][temp.getX()] == null &&
			   piece.getX() == x && piece.getY() == y;

	}
	
	/**
	 * Checks to see if the king is under check. Does this
	 * by iterating over each piece and checking if they're 
	 * targeting the king
	 * @param king
	 * @return a ArrayList<Piece> that is targeting the king
	 */
	private List<Piece> getCheckList(Piece king) {
		
		List<Piece> checkList = new ArrayList<>();
		
		for(Piece piece : allPieces) {
			//makes sure that the pieces are not the same color
			if(!king.sameColor(piece)) {
				Map<Integer, Integer> targeting = piece.getTargeting();
						
					if(targeting.containsKey(king.getX())) {
						if(targeting.get(king.getX()) == king.getY()) {
							checkList.add(piece);
						}
					}
			}
		}
		
		return checkList;
	}
	
	/**
	 * Will iterate over available pieces and get all by specified color
	 * @param color
	 * @return List<Piece> of the piece's that are the color
	 */
	private List<Piece> getAllPieceByColor(PieceColor color) {
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
	private void updateAllTargeted() {
		for(Piece piece : allPieces) {
			piece.updateTargeting(board);
		}
		
	}
	
	/**
	 * Goes through checklist and sees how the pieces can move if king
	 * is in check
	 * @param checkList
	 * @param king
	 * @return true if there is a place to move; false otherwise
	 */
	private boolean updateMoveIfInCheck(List<Piece> checkList, Piece king) {
		
		boolean flag = true;
		
		List<Piece> listOfKingColor = getAllPieceByColor(king.getColor());
		
		listOfKingColor.remove(king);
		// go through each piece that is currently targeting king
		// and update the list of pieces that can move out of the
		// way
		for(Piece piece : checkList) {
			Map<Integer, Integer> kingTargeting = king.getTargeting();
			Map<Integer, Integer> pieceTargeting = piece.getTargeting();
			
			// iterates through piece's targeting.
			// will see if it is in king's targeting. will then remove that spot
			// from the king's targeting.
				
				// check if king can move
				boolean kingCanMove = updateKingMoveIfInCheck(pieceTargeting, kingTargeting);
				
				//update piece's targeting to see if it can move to block
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
	 * @return true if king has place to go
	 */
	private boolean updateKingMoveIfInCheck(Map<Integer, Integer> pieceTargeting, Map<Integer, Integer> kingTargeting) {
		
		for(Map.Entry<Integer, Integer> entry : pieceTargeting.entrySet()) {
			if(kingTargeting.containsKey(entry.getKey())) {
				if(kingTargeting.get(entry.getKey()) == entry.getValue()) {
					kingTargeting.remove(entry.getKey());
				}
				
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
	 * the entries and pieces
	 * @param sameColor
	 * @param entry
	 * @return
	 */
	private boolean updatePieceMoveIfInCheck(List<Piece> sameColor, Map<Integer, Integer> targeting) {
		boolean flag = true;
	
		for(Piece piece : sameColor) {
			Map<Integer, Integer> intersection = new HashMap<>();
			Map<Integer, Integer> pieceTargeting = piece.getTargeting();
			
			for(Map.Entry<Integer, Integer> entry : targeting.entrySet()) {
				if(pieceTargeting.containsKey(entry.getKey())) {
					if(pieceTargeting.get(entry.getKey()) == entry.getValue()) {
						intersection.put(entry.getKey(), entry.getValue());
					}
				}		
			}
			
			piece.setTargeting(intersection);
			
			if(intersection.size() <= 0) {
				flag = false;
			} else {
				flag = true;
			}
		}
		
		return flag;
	}
}
	
