package game;

public enum PieceType {
	PAWN,
	ROOK,
	KNIGHT,
	BISHOP,
	QUEEN,
	KING;
	
	/**
	 * @return the FEN string for the type
	 */
	@Override
	public String toString() {
		switch(this) {
		case PAWN:
			return "P";
		case ROOK:
			return "R";
		case KNIGHT:
			return "K";
		case BISHOP:
			return "B";
		case QUEEN:
			return "Q";
		case KING:
			return "K";
		default:
			return "Unknown Piece";
		}
	}
}