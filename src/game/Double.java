package game;

public class Double<E, V> {
	
	private E first;
	private V second;

	public Double(E first, V second) {
		this.first = first;
		this.second = second;
	}
	
	public E getFirst() {
		return this.first;
	}

	public V getSecond() {
		return this.second;
	}
}
