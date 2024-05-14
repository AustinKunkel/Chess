package game;

import java.util.ArrayList;
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
		Map<Integer, Integer> targeting = piece.getTargeting();
				
			if(targeting.containsKey(king.getX())) {
				if(targeting.get(king.getX()) == king.getY()) {
					checkList.add(piece);
				}
			}
		}
		
		return checkList;
	}
	
	/**
	 * Updates ALL piece's targeting lists.
	 * @return
	 */
	private void updateAllTargeted() {
		for(Piece piece : allPieces) {
			piece.updateTargeting(board);
		}
	}
}
	
