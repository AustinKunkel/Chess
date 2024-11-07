package game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private PieceColor color;
	private List<Piece> currentPieces;
	private List<Piece> takenPieces;
	private int moveNum;

	public Player(PieceColor color, List<Piece> currentPieces) {
		this.color = color;
		this.currentPieces = currentPieces;
		this.takenPieces = new ArrayList<>();
		this.moveNum = 0;
	}
	
	public PieceColor getColor() {
		return this.color;
	}
	
	public List<Piece> getCurrentPieces() {
		return this.currentPieces;
	}
	
	public void addToCurrentPieces(Piece piece) {
		this.currentPieces.add(piece);
	}
	
	public void removeFromCurrentPieces(Piece piece) {
		this.currentPieces.remove(piece);
	}
	
	public void removeFromCurrentPieces(int index) {
		this.currentPieces.remove(index);
	}
	
	public List<Piece> getTakenPieces() {
		return this.takenPieces;
	}
	
	public void addToTakenPieces(Piece piece) {
		this.takenPieces.add(piece);
	}
	
	public void removeFromTakenPieces(Piece piece) {
		this.takenPieces.remove(piece);
	}
	
	public void removeFromTakenPieces(int index) {
		this.takenPieces.remove(index);
	}
	
	public int getMoveNum() {
		return this.moveNum;
	}
	
	public void incrementMove() {
		this.moveNum++;
	}
	
	public void decrementMoveNum() {
		this.moveNum--;
	}

}
