import java.util.*;

public class BlackJackGame {

	public static final int CARDS = 52;
	private static ArrayList<Card> deck = new ArrayList<Card>();
	private static ArrayList<Card> playerCards = new ArrayList<Card>();
	private static ArrayList<Card> computerCards = new ArrayList<Card>();
	static int currentCard = 0;

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		intro(keyboard);
		int rounds = getRounds(keyboard);
		fill();
		// play game
		Collections.shuffle(deck);
		System.out.println(deck.toString());
		dealCards();
		System.out.println("Player cards: " + playerCards.toString());
		playerTurn(keyboard);
		System.out.println("Player cards: " + playerCards.toString());
		
		
		// while player is <= 21 && hit it is player turn
		
		for (int i = 0; i < rounds; i++) {
			// TODO play game
				// deal cards 1 to player, 1 to dealer, etc
				// let player decide what to do
				// hit is another card
				// check total
				// computer hits until >= 17
		}
		
		
	}

	// Prints out the introduction to the game.
	public static void intro(Scanner keyboard) {
		System.out.print("Welcome to BlackJack! Please enter your name: ");
		String playerName = keyboard.nextLine();
		System.out.println("Hello " + playerName + ". Let's begin!");
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
		for (Suit s : Suit.values()) {
			for (Value v : Value.values()) {
				deck.add(new Card(v,s));
			}
		}
	}
	
	public static int dealCards() {
		// TODO deal with redundancy
		playerCards.add(deck.get(currentCard));
		currentCard++;
		computerCards.add(deck.get(currentCard));
		currentCard++;
		playerCards.add(deck.get(currentCard));
		currentCard++;
		computerCards.add(deck.get(currentCard));
		currentCard++;
		return currentCard;
	}
	
	public static void playerTurn(Scanner keyboard) {
		hit(keyboard);
	}
	
	// Ask user if they would like to hit or hold
	public static void hit(Scanner keyboard) {
		String hit = "";
		while (!(hit.equalsIgnoreCase("hit") || hit.equalsIgnoreCase("hold"))) {
			System.out.print("Would you like to hit or hold?: ");
			hit = keyboard.next();
		}
		if (hit.equalsIgnoreCase("hit")) {
			playerCards.add(deck.get(currentCard));
			currentCard++;
		}
	}
}
