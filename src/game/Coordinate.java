package game;

public class Coordinate {

	private int x, y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	@Override
	public boolean equals(Object c) {
		if( this == c ) return true; 
		
		if(c == null || getClass() != c.getClass()) return false;
		
		Coordinate coord = (Coordinate)c;
		
		return this.x == coord.getX() && this.y == coord.getY();
	}
	
	private char getFile() {
		return switch(this.x) {
		case 0 -> 'a';
		case 1 -> 'b';
		case 2 -> 'c';
		case 3 -> 'd';
		case 4 -> 'e';
		case 5 -> 'f';
		case 6 -> 'g';
		case 7 -> 'h';
		default -> 'i';
		};
	}
	
	@Override
	public String toString() {
		return getFile() + "" + (y + 1);
	}
	
	@Override
	public int hashCode() {
        int result = 17;  // Non-zero constant
        int prime = 31;   // A prime number used as a multiplier
        result = prime * result + x;
        result = prime * result + y;
        return result;
	}
}
	
