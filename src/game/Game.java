package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
	
	private static final PieceColor white = PieceColor.WHITE;
	private static final PieceColor black = PieceColor.BLACK;
	private static boolean isCheckMate = false;
	
	private static Player player1;
	
	private static Player player2;
	
	private static Piece[][] board = {{new Rook(0,0, white), new Knight(1,0, white), new Bishop(2,0, white), new Queen(3,0, white), new King(4,0, white), new Bishop(5,0, white), new Knight(6,0, white), new Rook(7,0, white)},
									  {new Pawn(0,1, white),new Pawn(1,1, white), new Pawn(2,1, white), new Pawn(3,1, white), new Pawn(4,1, white), new Pawn(5,1, white), new Pawn(6,1, white), new Pawn(7,1, white)},
									  {null, null, null, null, null, null, null, null},
									  {null, null, null, null, null, null, null, null},
									  {null, null, null, null, null, null, null, null},
									  {null, null, null, null, null, null, null, null},
									  {new Pawn(0,6, black),new Pawn(1,6, black), new Pawn(2,6, black), new Pawn(3,6, black), new Pawn(4,6, black), new Pawn(5,6, black), new Pawn(6,6, black), new Pawn(7,6, black)},
									  {new Rook(0,7, black), new Knight(1,7, black), new Bishop(2,7, black), new Queen(3,7, black), new King(4,7, black), new Bishop(5,7, black), new Knight(6,7, black), new Rook(7,7, black)}};
	
	private static final List<Piece> allPieces = new ArrayList<>();

	public static void main(String[] args) {
		
		// initializes the list that has all of the available pieces.
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] != null) {
					allPieces.add(board[i][j]);
				}
			}
		}
		
		player1 = new Player(white, getAllPieceByColor(white));
		player2 = new Player(black, getAllPieceByColor(black));
		
		updateAllTargeted();
		
		
		System.out.println("Player 1: ");
		System.out.println(player1.getCurrentPieces());
		System.out.println("Player 2: ");
		System.out.println(player2.getCurrentPieces());
		
		Scanner sc = new Scanner(System.in);
		String line = "";
		
		boolean isPlayer2 = false;
		PieceColor playerColor = player1.getColor();
		
		// Main loop
		while(!isCheckMate) {
			updateAllTargeted();
			for(Piece piece : allPieces) {
				System.out.println(piece.toString() + ", is targeting: " + piece.getTargeting());
				//System.out.println(piece.toString() + ", can target: " + piece.getCanTarget());
			}
			
			System.out.println(playerColor + "'s move");
			
			
			if(sc.hasNextLine()) {
				line = sc.nextLine().trim();
			}
			while(invalidInput(line)) {
				System.out.println("Invalid input, please enter a valid input in chess notation <piece letter><file letter><rank number>");
				if(sc.hasNextLine()) {
					line = sc.nextLine().trim();
				}
			}
			
			Double<Piece, Coordinate> input = parseUserInput(line, playerColor);
			input = parseUserInput(line, playerColor);
			while(input.getFirst() == null) {
				System.out.println("Could not move there, re enter where to move");
				if(sc.hasNextLine()) {
					line = sc.nextLine().trim();
				}
				while(invalidInput(line)) {
					System.out.println("Invalid input, please enter a valid input in chess notation <piece letter><file letter><rank number>");
					if(sc.hasNextLine()) {
						line = sc.nextLine().trim();
					}
				}
				input = parseUserInput(line, playerColor);
			}
			
			Piece inputPiece = input.getFirst();
			Coordinate coordinate = input.getSecond();
			
			if(!move(inputPiece, coordinate)) {
				System.out.println("Could not move " + inputPiece + " to " + coordinate);
				continue;
			}
			if(!isPlayer2) {
				isPlayer2 = true;
				playerColor = player2.getColor();
			} else {
				isPlayer2 = false;
				playerColor = player1.getColor();
			}
			
		} //main loop
		sc.close();
		
			
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
	
	private static boolean invalidInput(String input) {
		Pattern pattern = Pattern.compile("(?:(?<piece>[KQRBNkqrbn])?(?<file>[a-h])(?<rank>[1-8]))");
	    Matcher matcher = pattern.matcher(input);
	    return !matcher.matches();
	}
	
	private static Double<Piece, Coordinate> parseUserInput(String input, PieceColor color) {
		char firstChar = input.charAt(0);
		PieceType pieceType = getPieceTypeFromLetter(firstChar);
		int fileIndex, rankIndex;
		
		if(pieceType != null) { // if the piece is not a pawn
			fileIndex = input.charAt(1) - 'a';
			rankIndex = Character.getNumericValue(input.charAt(2)) - 1;
		} else {
			pieceType = PieceType.PAWN;
			fileIndex = input.charAt(0) - 'a';
			rankIndex = Character.getNumericValue(input.charAt(1)) - 1;
		}
		Coordinate coordinate = new Coordinate(fileIndex, rankIndex);
		
		List<Piece> pieces = getAllPieceByColor(color);
		
		for(Piece piece : pieces) {
			if(piece.getType() != pieceType) {
				continue;
			}
			if(piece.getTargeting().contains(coordinate)) {
				return new Double<>(piece, coordinate);
			}
		}
		return new Double<>(null, null);
	}
	
	/**
	 * Pawns should be handled before (letter of nothing)
	 * @param letter
	 * @return
	 */
	private static PieceType getPieceTypeFromLetter(char letter) {
		letter = Character.toLowerCase(letter);
		return switch(letter) {
		case 'b' -> PieceType.BISHOP;
		case 'k' -> PieceType.KING;
		case 'q' -> PieceType.QUEEN;
		case 'r' -> PieceType.ROOK;
		case 'n' -> PieceType.KNIGHT;
		default -> null;
		};
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
		
		if(piece.getColor().equals(player1.getColor())) {
			player1.removeFromCurrentPieces(piece);
			player2.addToTakenPieces(piece);
		} else {
			player2.removeFromCurrentPieces(piece);
			player1.addToTakenPieces(piece);
		}
		return true;
	}
	
	/**
	 * Moves the selected piece into the desired x, y coordinate
	 * @param piece to be moved
	 * @param c coordinate to be moved to
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
		
		if(!killPiece(piece2)) return false;
			
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
			System.out.println("Spot is not null");
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
			if(!updateMoveIfInCheck(getAllPieceByColor(PieceColor.BLACK), wKing)) { checkMate(); }
//			checkList = getCheckList( allPieces.get(allPieces.indexOf(wKing)));
//			if(!checkList.isEmpty()) {
//				if(!updateMoveIfInCheck(getAllPieceByColor(PieceColor.BLACK), wKing)) checkMate();
//			}
		}else {
			checkMate();
		}
		if(bKing != null) {
			if(!updateMoveIfInCheck(getAllPieceByColor(PieceColor.WHITE), bKing)) { checkMate(); }
//			checkList = getCheckList( allPieces.get(allPieces.indexOf(bKing)));
//			if(!checkList.isEmpty()) {
//				if(!updateMoveIfInCheck(checkList, bKing)) checkMate();
//			}
		} else {
			checkMate();
		}
	}
	
	private static void checkMate() {
	isCheckMate = true;
	System.out.println("CHECKMATE");
	}
	
	/**
	 * Goes through checklist and sees how the pieces can move if king
	 * is in check
	 * @param oppositePieces all pieces of the opposite color of the king
	 * @param king
	 * @return true if there is a place to move; false if checkmate
	 */
	private static boolean updateMoveIfInCheck(List<Piece> oppositePieces, Piece king) {

		// go through each piece that is currently targeting king
		// and update the list of pieces that can move out of the
		// way
		Set<Coordinate> kingTargeting = king.getTargeting();

			Double<Set<Piece>, Set<Coordinate>> updatedMove = updateKingMoveIfInCheck(oppositePieces, king);
			Set<Piece> checkList = updatedMove.getFirst();
			boolean isKingInCheck = !checkList.isEmpty();
			Set<Coordinate> kingTargeted = updatedMove.getSecond();

			if (isKingInCheck) {
				System.out.println("King in check!!");
				List<Piece> listOfKingColor = getAllPieceByColor(king.getColor());

				return updatePieceMoveIfInCheck(listOfKingColor, checkList, kingTargeted) || !kingTargeting.isEmpty();
			}
		return true;
	}
	
	/**
	 * <b>helper function for updateMoveIfInCheck<br>
	 * Updates the king's moves to avoid check.<br>
	 * Does this by getting places where it cannot move and removing that from the king's targeting
	 * @param oppositePieces List of pieces opposite that of the king color
	 * @param king
	 * @return Double => Set of Pieces that are targeting the king, and a set of spots the king cannot move to due to check
	 */
	private static Double<Set<Piece>, Set<Coordinate>> updateKingMoveIfInCheck(List<Piece> oppositePieces, Piece king) {
		Set<Piece> checkList = new HashSet<>();
		Set<Coordinate> targeted = new HashSet<>();

		for(Piece piece : oppositePieces) {
			//boolean for if the piece is a pawn, but it cannot attack the king (is right in front of it)
			boolean isPawnClear = piece.getType().equals(PieceType.PAWN) && (piece.getX() == king.getX());
			Set<Coordinate> kingTargeting = king.getTargeting();

			for(Piece otherPiece : oppositePieces) { // checks to see if a piece is protected by another one
				if(otherPiece.canTarget(piece.getCoord())) {
					kingTargeting.remove(piece.getCoord()); // if so, remove that from the king's targeting
				}
			}

			for (Coordinate coord : piece.getTargeting()) {
				if (kingTargeting.contains(coord) && !isPawnClear) {
					kingTargeting.remove(coord);
					targeted.add(coord);
				}
				if (coord.equals(king.getCoord())) {
					checkList.add(piece);
				}
			}
		}
		return new Double<>(checkList, targeted);
	}
	
	/**
	 * helper function for updateMoveIfInCheck
	 * Updates all the piece's moves to see if it can block a check.
	 * Does this by getting the intersection of
	 * the pieces of the same color and the
	 * map of the piece that is targeting the king
	 * @param sameColor => List of pieces of the same color
	 * @param checkList => Map of pieces that are checking the kings
	 * @param kingTargeted => Set of Coordinates that the king could not go
	 * @return true if there is a place to block the check; false otherwise
	 */
	private static boolean updatePieceMoveIfInCheck(List<Piece> sameColor, Set<Piece> checkList, Set<Coordinate> kingTargeted) {
		boolean flag = false;
	
		for(Piece piece : sameColor) {
			Set<Coordinate> intersection = new HashSet<>();
			Set<Coordinate> pieceTargeting = piece.getTargeting();

			for (Coordinate coord : kingTargeted) {
				if (pieceTargeting.contains(coord)) {
					intersection.add(coord);
				}
			}

			for(Piece targeting : checkList) { // if the piece of the same color can take the checking piece
				for(Coordinate coord : targeting.getTargeting()) { // grabs the intersection of the pieces, to see if it can block a check
					if(pieceTargeting.contains(coord)) { // if it can move to block
						intersection.add(coord);
					}
				}
				if(pieceTargeting.contains(targeting.getCoord())) {
					intersection.add(targeting.getCoord());
				}
			}

			piece.setTargeting(intersection);

			if (!intersection.isEmpty()) {
				flag = true;
			}
		}
		return flag;
	}
}
	
