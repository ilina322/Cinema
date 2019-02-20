
public class Seat {
	public static final int SYMBOL_OCCUPIED = '*';
	public static final int SYMBOL_FREE = '.';
	private boolean isOccupied;
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
		this.person = person;
		this.symbol = person.getName();
		this.isOccupied = true;
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

}
