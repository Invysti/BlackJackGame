import java.util.*;
import java.lang.Math;

public class BlackJackGame {

	public static final int CARDS = 52;
	private static Card[] cards = new Card[CARDS];

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		intro(keyboard);
		getRounds(keyboard);
		fill();
		for(int i = 0; i < CARDS; i++) {
			System.out.println(cards[i].toString());
		}
		System.out.println("Program done");
	}

	// Prints out the introduction to the game.
	public static void intro(Scanner keyboard) {
		System.out.print("Welcome to BlackJack! Please enter your name: ");
		String playerName = keyboard.nextLine();
		System.out.print("Hello " + playerName + ". Let's begin!");
	}
	
	// Gets the number of rounds the user would like to play.
	public static int getRounds(Scanner keyboard) {
		int rounds;
		System.out.println("How many rounds from 1 to 10 would you like to play?");
		do {
			System.out.print("Please enter an amount between 1 and 10: ");
			while (!keyboard.hasNextInt()) {
				System.out.print("Please enter an integer: ");
				keyboard.next();
			}
			rounds = keyboard.nextInt();
		} while (rounds < 1 || rounds > 10);
		return rounds;
	}
	
	public static void fill() {
		int i = 0;
		for (Suit s : Suit.values()) {
			for (Value v : Value.values()) {
				cards[i] = new Card(v,s);
				i++;
			}
		}
	}

}
