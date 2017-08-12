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
		
		System.out.println(deck.toString());
		dealCards();
		int playerTotal = playerTurn(keyboard);
		
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
	
	// Fills the deck with cards
	public static void fill() {
		for (Suit s : Suit.values()) {
			for (Value v : Value.values()) {
				deck.add(new Card(v,s));
			}
		}
	}
	
	// Shuffles and deals starting cards
	public static int dealCards() {
		Collections.shuffle(deck);
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
	
	// TODO adjust for ace possibility (1 or 11);
	// TODO setup computer's turn
	// TODO check winner
	
	
	// Player's turn
	public static int playerTurn(Scanner keyboard) {
		// Gets player's beginning total
		int playerTotal = 0;
		for (int i = 0; i < playerCards.size(); i++) {
			playerTotal += playerCards.get(i).getValue().getCardValue();	
		}
		// Prompts user if they would like to hit and checks whether they type hit and if they have a hand below 21
		String playerChoice = "";
		do {
			System.out.println("Player cards: " + playerCards.toString());
			System.out.println("Your total is: " + playerTotal);
			playerChoice = hitPrompt(keyboard);
			playerTotal += hit(keyboard, playerChoice);
		} while (playerChoice.equals("hit") && playerTotal <= 21);
		System.out.println("Player cards: " + playerCards.toString());
		System.out.println("Your final total is: " + playerTotal);
		return playerTotal;
	}
	
	// Add card if user chooses to hit
	public static int hit(Scanner keyboard, String playerChoice) {
		int newCard = 0;
		if (playerChoice.equalsIgnoreCase("hit")) {
			playerCards.add(deck.get(currentCard));
			newCard = deck.get(currentCard).getValue().getCardValue();
			currentCard++;
		}
		return newCard;
	}
	
	// Ask the user if they would like to hit
	public static String hitPrompt(Scanner keyboard) {
		String hit = "";
		while (!(hit.equalsIgnoreCase("hit") || hit.equalsIgnoreCase("hold"))) {
			System.out.print("Would you like to hit or hold?: ");
			hit = keyboard.next();
		}
		return hit;
	}
}
