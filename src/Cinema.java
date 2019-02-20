import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Cinema {

	public static void main(String[] args) {
		// input/setup elements
		Scanner scanner = new Scanner(System.in);
		 System.out.println("cinema dimentions:");
		String dimensions = scanner.nextLine();

		String dimentionsRaw[] = dimensions.split(" ");
		int rows = Integer.parseInt(dimentionsRaw[0]);
		int cols = Integer.parseInt(dimentionsRaw[1]);

		 System.out.println("cinema:");
		Seat[][] cinema = setupCinema(scanner, rows, cols);

		System.out.println("How many people?");
		int peopleCount = Integer.parseInt(scanner.nextLine());

		System.out.println("People:");
		ArrayList<Person> people = setupPeople(scanner, peopleCount);
		Person[] peopleUnaltered = new Person[peopleCount];
		for(int i = 0; i < people.size(); i++) {
			peopleUnaltered[i] = people.get(i);
		}

		// logic

		int startRow = 0;
		int startCol = 0;
		while (people.size() != 0) {
			people.clear();
			for(int i = 0; i < peopleCount; i++) {
				people.add(peopleUnaltered[i]);
			}
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < cols; j++) {
					if(cinema[i][j].getPerson() != null) {
						cinema[i][j].setOccupied(false);
					}
				}
			}
			placeAll(cinema, people, startRow, startCol);
			startCol++;
			if(startCol == cols) {
				startCol = 0;
				startRow++;
			}
		}

		System.out.println();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {

				System.out.print(cinema[row][col].getSymbol());
			}
			System.out.println();
		}
	}

	private static void placeAll(Seat[][] cinema, ArrayList<Person> people, int startRow, int startCol) {
		for (int row = startRow; row < cinema.length; row++) {
			for (int col = startCol; col < cinema[0].length; col++) {
				Seat currentSeat = cinema[row][col];

				if (!currentSeat.isOccupied()) {
					for (int k = 0; k < people.size(); k++) {
						Person person = people.get(0);

						// TODO: break and delete array if something is wrong
						if (person.isCentral()) {
							currentSeat.setPerson(person);
							people.remove(person);
							row = 0;
							col = 0;
						} else {
							boolean isPositionOk = checkPosition(person, cinema, row, col);
							if (isPositionOk) {
								currentSeat.setPerson(person);
								people.remove(person);
								row = 0;
								col = 0;
							}
						}
					}
				}
			}
		}
	}

	private static boolean checkPosition(Person person, Seat[][] cinema, int row, int col) {
		if (person.getRelevantPosition() == Person.POSITION_ABOVE) {
			if (row + 1 < cinema.length) {
				Person personBelow = cinema[row + 1][col].getPerson();
				if (personBelow != null && personBelow.getName() == person.getNearbyPersonName()) {
					return true;
				}
			}
		}

		if (person.getRelevantPosition() == Person.POSITION_BELOW) {
			if (row - 1 >= 0) {
				Person personAbove = cinema[row - 1][col].getPerson();
				if (personAbove != null && personAbove.getName() == person.getNearbyPersonName()) {
					return true;
				}
			}
		}

		if (person.getRelevantPosition() == Person.POSITION_LEFT) {
			if (col + 1 < cinema[0].length) {
				Person personRight = cinema[row][col + 1].getPerson();
				if (personRight != null && personRight.getName() == person.getNearbyPersonName()) {
					return true;
				}
			}
		}

		if (person.getRelevantPosition() == Person.POSITION_RIGHT) {
			if (col - 1 >= 0) {
				Person personLeft = cinema[row][col - 1].getPerson();
				if (personLeft != null && personLeft.getName() == person.getNearbyPersonName()) {
					return true;
				}
			}
		}
		return false;
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
