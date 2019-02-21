import java.util.ArrayList;
import java.util.Scanner;

public class Cinema {

	public static void main(String[] args) {
		// input/setup elements
		Scanner scanner = new Scanner(System.in);
		// System.out.println("cinema dimentions:");
		String dimensions = scanner.nextLine();

		String dimentionsRaw[] = dimensions.split(" ");
		int rows = Integer.parseInt(dimentionsRaw[0]);
		int cols = Integer.parseInt(dimentionsRaw[1]);

		// System.out.println("cinema:");
		Seat[][] cinema = setupCinema(scanner, rows, cols);

		// System.out.println("How many people?");
		int peopleCount = Integer.parseInt(scanner.nextLine());

		// System.out.println("People:");
		ArrayList<Person> people = setupPeople(scanner, peopleCount);
		Person[] peopleUnaltered = new Person[peopleCount];
		for (int i = 0; i < people.size(); i++) {
			peopleUnaltered[i] = people.get(i);
		}

		// logic
		int startRow = 0;
		int startCol = 0;

		while (startRow*startCol != cinema.length*cinema[0].length) {
			if (placeAll(cinema, people, startRow, startCol)) {
				System.out.println();
				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < cols; col++) {
						System.out.print(cinema[row][col].getSymbol());
					}
					System.out.println();
				}
				startCol = people.get(0).getCinemaCol() + 1;
				if (startCol == cinema.length) {
					startRow = people.get(0).getCinemaRow() + 1;
					startCol = 0;
				} else {
					startRow = people.get(0).getCinemaRow();
				}
			}
			restartCinema(cinema);
		}
	}

	private static boolean placeAll(Seat[][] cinema, ArrayList<Person> people, int startRow, int startCol) {

		for (int row = startRow; row < cinema.length; row++) {
			for (int col = startCol; col < cinema[0].length; col++) {
				int seatedPeopleCount = 0;

				outer: for (int i = 0; i < people.size(); i++) {
					Person currentPerson = people.get(i);

					if (currentPerson.isCentral()) {
						Seat currentSeat = cinema[row][col];
						if (!currentSeat.isOccupied()) {
							currentSeat.setPerson(currentPerson);
							currentPerson.setCinemaCol(col);
							currentPerson.setCinemaRow(row);
							seatedPeopleCount++;
						}
					}
					for (int j = 0; j < people.size(); j++) {
						Person nextPerson = people.get(j);
						if (nextPerson.getNearbyPersonName() == currentPerson.getName()) {

							char position = nextPerson.getRelevantPosition();
							if (putNear(cinema, position, currentPerson, nextPerson)) {
								seatedPeopleCount++;
							} else {
								restartCinema(cinema);
								seatedPeopleCount = 0;
								break outer;
							}
						}
					}
				}
				if (seatedPeopleCount == people.size()) {
					return true;
				}
				if (col == cinema[0].length - 1) {
					startCol = 0;
				}
			}
		}
		return false;
	}

	private static boolean putNear(Seat[][] cinema, char position, Person currentPerson, Person nextPerson) {
		if (position == Person.POSITION_ABOVE) {
			return putAbove(cinema, currentPerson, nextPerson);
		} else if (position == Person.POSITION_BELOW) {
			return putBelow(cinema, currentPerson, nextPerson);
		} else if (position == Person.POSITION_RIGHT) {
			return putRight(cinema, currentPerson, nextPerson);
		} else if (position == Person.POSITION_LEFT) {
			return putLeft(cinema, currentPerson, nextPerson);
		}
		return false;
	}

	private static boolean putRight(Seat[][] cinema, Person currentPerson, Person nextPerson) {
		// TODO: maybe extract method
		int row = currentPerson.getCinemaRow();
		int col = currentPerson.getCinemaCol() + 1;
		if (col < cinema[0].length && !cinema[row][col].isOccupied()) {
			cinema[row][col].setPerson(nextPerson);
			nextPerson.setCinemaCol(col);
			nextPerson.setCinemaRow(row);
			return true;
		}
		return false;
	}

	private static boolean putLeft(Seat[][] cinema, Person currentPerson, Person nextPerson) {
		int row = currentPerson.getCinemaRow();
		int col = currentPerson.getCinemaCol() - 1;
		if (col >= 0 && !cinema[row][col].isOccupied()) {
			cinema[row][col].setPerson(nextPerson);
			nextPerson.setCinemaCol(col);
			nextPerson.setCinemaRow(row);
			return true;
		}
		return false;
	}

	private static boolean putBelow(Seat[][] cinema, Person currentPerson, Person nextPerson) {
		int row = currentPerson.getCinemaRow() + 1;
		int col = currentPerson.getCinemaCol();
		if (row < cinema.length && !cinema[row][col].isOccupied()) {
			cinema[row][col].setPerson(nextPerson);
			nextPerson.setCinemaCol(col);
			nextPerson.setCinemaRow(row);
			return true;
		}
		return false;
	}

	private static boolean putAbove(Seat[][] cinema, Person currentPerson, Person nextPerson) {
		int row = currentPerson.getCinemaRow() - 1;
		int col = currentPerson.getCinemaCol();
		if (row >= 0 && !cinema[row][col].isOccupied()) {
			cinema[row][col].setPerson(nextPerson);
			nextPerson.setCinemaCol(col);
			nextPerson.setCinemaRow(row);
			return true;
		}
		return false;
	}

	private static void restartCinema(Seat[][] cinema) {
		for (int i = 0; i < cinema.length; i++) {
			for (int j = 0; j < cinema[0].length; j++) {
				Person person = cinema[i][j].getPerson();
				if (person != null) {
					cinema[i][j].setOccupied(false);
				}
			}
		}
	}

	private static ArrayList<Person> setupPeople(Scanner scanner, int peopleCount) {
		ArrayList<Person> people = new ArrayList<Person>();
		for (int i = 0; i < peopleCount; i++) {

			char[] personCharacteristics = (scanner.nextLine()).toCharArray();
			Person person;

			if (personCharacteristics.length == 1) {
				char name = personCharacteristics[0];
				person = new Person(name);
			} else {
				char name = personCharacteristics[0];
				char relevantPosition = personCharacteristics[1];
				char nearbyPerson = personCharacteristics[2];
				person = new Person(name, relevantPosition, nearbyPerson);
			}
			people.add(person);
		}
		return people;
	}

	private static Seat[][] setupCinema(Scanner scanner, int rows, int cols) {
		Seat[][] cinema = new Seat[rows][cols];

		for (int i = 0; i < rows; i++) {
			Seat seat;
			String row = scanner.nextLine();
			char seats[] = row.toCharArray();
			for (int j = 0; j < cols; j++) {
				if (seats[j] == Seat.SYMBOL_OCCUPIED) {
					seat = new Seat(true);
				} else {
					seat = new Seat(false);
				}
				cinema[i][j] = seat;
			}
		}
		return cinema;
	}

}