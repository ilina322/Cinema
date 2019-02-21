

public class Person {
	public static final char POSITION_ABOVE = 'A';
	public static final char POSITION_BELOW = 'B';
	public static final char POSITION_RIGHT = 'R';
	public static final char POSITION_LEFT = 'L';

	private char name;
	private char relevantPosition;
	private char nearbyPersonName;
	private boolean isCentral;
	private int cinemaRow;
	private int cinemaCol;
	
	public Person(char name, char relevantPosition, char nearbyPerson) {
		super();
		this.name = name;
		this.relevantPosition = relevantPosition;
		this.nearbyPersonName = nearbyPerson;
		this.isCentral = false;
	}
	
	public int getCinemaRow() {
		return cinemaRow;
	}

	public void setCinemaRow(int cinemaRow) {
		this.cinemaRow = cinemaRow;
	}

	public int getCinemaCol() {
		return cinemaCol;
	}

	public void setCinemaCol(int cinemaCol) {
		this.cinemaCol = cinemaCol;
	}

	public Person(char name) {
		super();
		this.name = name;
		this.isCentral = true;
	}
	
	public void setCentral(boolean isCentral) {
		this.isCentral = isCentral;
	}
	
	public boolean isCentral() {
		return isCentral;
	}
	
	public char getName() {
		return name;
	}
	public void setName(char name) {
		this.name = name;
	}
	public char getRelevantPosition() {
		return relevantPosition;
	}
	public void setRelevantPosition(char relevantPosition) {
		this.relevantPosition = relevantPosition;
	}
	public char getNearbyPersonName() {
		return nearbyPersonName;
	}
	public void setNearbyPersonName(char nearbyPersonName) {
		this.nearbyPersonName = nearbyPersonName;
	}
	
	
}
