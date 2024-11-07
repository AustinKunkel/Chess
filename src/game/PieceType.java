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
		return switch(this) {
		case PAWN -> "";
		case ROOK -> "R";
		case KNIGHT -> "N";
		case BISHOP -> "B";
		case QUEEN -> "Q";
		case KING -> "K";
		default -> "Unknown Piece";
		};
	}
}