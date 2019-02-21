
public class Seat {
	public static final int SYMBOL_OCCUPIED = '*';
	public static final int SYMBOL_FREE = '.';
	private boolean isOccupied;
	private boolean isUsedByCentral;
	private char symbol;
	private Person person;

	public Seat(boolean isOccupied) {
		super();
		this.isOccupied = isOccupied;
		if (isOccupied) {
			this.symbol = SYMBOL_OCCUPIED;
		} else {
			this.symbol = SYMBOL_FREE;
		}
	}

	public void setPerson(Person person) {
		if (!isOccupied) {
			this.person = person;
			this.symbol = person.getName();
			this.isOccupied = true;
		}
	}

	public Person getPerson() {
		return person;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
		if (!isOccupied) {
			this.person = null;
			this.symbol = '.';
		}
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public boolean isUsedByCentral() {
		return isUsedByCentral;
	}

	public void setUsedByCentral(boolean centralPersonHasSat) {
		this.isUsedByCentral = centralPersonHasSat;
	}

}
