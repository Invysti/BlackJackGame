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
		dealCards();
		int playerTotal = playerTurn(keyboard);
		int computerTotal = computerTurn();
		
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
		/*Test Code
		deck.add(new Card(Value.TWO, Suit.CLUBS));
		deck.add(new Card(Value.TWO, Suit.HEARTS));
		deck.add(new Card(Value.TWO, Suit.DIAMONDS));
		deck.add(new Card(Value.TWO, Suit.SPADES));
		deck.add(new Card(Value.ACE, Suit.CLUBS));
		deck.add(new Card(Value.ACE, Suit.DIAMONDS));
		deck.add(new Card(Value.KING, Suit.DIAMONDS));*/
	}
	
	// Shuffles and deals starting cards
	public static int dealCards() {
		Collections.shuffle(deck);
		System.out.println(deck.toString());
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
	
	// TODO setup computer's turn
	// TODO check winner
	
	
	// Player's turn
	// TODO work on abstraction here
	public static int playerTurn(Scanner keyboard) {
		// Gets player's beginning total
		// Two separate variables to take into account aces
		// playerTotal considers aces as 1 while playerTotalAces considers Aces as 11
		int playerTotal = 0;
		int playerTotalAces = 0;
		for (int i = 0; i < playerCards.size(); i++) {
			if (playerCards.get(i).getValue().getCardValue() == 1) {
				playerTotalAces += 11;
			} else {
				playerTotalAces += playerCards.get(i).getValue().getCardValue();
			}
			playerTotal += playerCards.get(i).getValue().getCardValue();
		}
		// Prompts user if they would like to hit and checks whether they type hit and if they have a hand below 21
		String playerChoice = "";
		do {
			System.out.println("Player cards: " + playerCards.toString());
			if (playerTotal != playerTotalAces) {
				System.out.println("Your total is: " + playerTotal + " or " + playerTotalAces);;
			} else {
				System.out.println("Your total is: " + playerTotal);
			}
			playerChoice = hitPrompt(keyboard);
			int newCard = hit(keyboard, playerChoice);
			if (newCard == 1 && playerTotal == playerTotalAces) {
				playerTotal += 1;
				playerTotalAces += 11;
			} else {
				playerTotal += newCard;
				playerTotalAces += newCard;
			}
			
		} while (playerChoice.equals("hit") && playerTotal <= 21);
		if (playerTotalAces < 21 && playerTotalAces > playerTotal) {
			System.out.println("Player cards: " + playerCards.toString());
			System.out.println("Your final total is: " + playerTotalAces);
			return playerTotalAces;
		} else {
			System.out.println("Player cards: " + playerCards.toString());
			System.out.println("Your final total is: " + playerTotal);
		}
		return playerTotal;
	}
	
	// Add card if user chooses to hit
	// TODO Add card if computer needs to hit
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
	
	public static int computerTurn() {
		int computerTotal = 0;
		int computerTotalAces = 0;
		for (int i = 0; i < computerCards.size(); i++) {
			if (computerCards.get(i).getValue().getCardValue() == 1) {
				computerTotalAces += 11;
			} else {
				computerTotalAces += computerCards.get(i).getValue().getCardValue();
			}
			computerTotal += computerCards.get(i).getValue().getCardValue();
		}
		do {
			System.out.println("Computer cards: " + computerCards.toString());
			if (computerTotal != computerTotalAces) {
				System.out.println("Computer's total is: " + computerTotal + " or " + computerTotalAces);;
			} else {
				System.out.println("Computer's total is: " + computerTotal);
			}
			System.out.println("Computer hits.");
			computerCards.add(deck.get(currentCard));
			int newCard = deck.get(currentCard).getValue().getCardValue();
			currentCard++;
			if (newCard == 1 && computerTotal == computerTotalAces) {
				computerTotal += 1;
				computerTotalAces += 11;
			} else {
				computerTotal += newCard;
				computerTotalAces += newCard;
			}
		} while (computerTotal <= 17 || computerTotalAces <= 17);
		
		if (computerTotalAces < 21 && computerTotalAces > computerTotal) {
			System.out.println("Computer's cards: " + computerCards.toString());
			System.out.println("Computer's final total is: " + computerTotalAces);
			return computerTotalAces;
		} else {
			System.out.println("Computer's cards: " + computerCards.toString());
			System.out.println("Computer's final total is: " + computerTotal);
		}
		return computerTotal;
	}
}
