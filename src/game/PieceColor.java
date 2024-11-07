package game;

public enum PieceColor {
	WHITE,
	BLACK;
	
	@Override
	public String toString() {
		return switch(this) {
		case WHITE -> "White";
		case BLACK -> "Black";
		default -> "NA";
		};
	}
}
