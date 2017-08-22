import java.util.*;

// TODO work on abstraction/redundancy for playerTurn and computerTurn

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
		// Plays blackjack rounds number of times
		for (int i = 0; i < rounds; i++) {
			System.out.println("Round: " + (i+1));
			// resets the player and dealer's hand
			playerCards.clear();
			computerCards.clear();
			dealCards();
			// returns the player and dealer's total to 0 at the beginning of each round
			int playerTotal = 0;
			int computerTotal = 0;
			playerTotal = playerTurn(keyboard);
			computerTotal = computerTurn();
			checkWin(playerTotal, computerTotal);
		}
		System.out.println("Thanks for playing!");
	}

	// Prints out the introduction to the game.
	public static void intro(Scanner keyboard) {
		System.out.print("Welcome to BlackJack! Please enter your name: ");
		String playerName = keyboard.nextLine();
		System.out.println("Hello " + playerName + ". Let's begin!");
	}
	
	// Gets the number of rounds the user would like to play.
	// Currently not used
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
	public static void dealCards() {
		Collections.shuffle(deck);
		// System.out.println(deck.toString());
		for (int i = 0; i < 2; i++) { 
			playerCards.add(deck.get(currentCard));
			currentCard++;
			computerCards.add(deck.get(currentCard));
			currentCard++;
		}
	}
	
	// Player's turn
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
		System.out.println("The Dealer has a " + computerCards.get(1).getValue() + " showing.");
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
		String Name = "Player";
		System.out.println(Name + "'s cards: " + playerCards.toString());
		return printTotal(Name, playerTotal, playerTotalAces);
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
	
	// Computer's turn, computer hits as long as it has less than 17
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
			System.out.println("Dealer's cards: " + computerCards.toString());
			if (computerTotal != computerTotalAces) {
				System.out.println("Dealer's total is: " + computerTotal + " or " + computerTotalAces);;
			} else {
				System.out.println("Dealer's total is: " + computerTotal);
			}
			System.out.println("Dealer hits.");
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
		String Name = "Dealer";
		System.out.println(Name + "'s cards: " + computerCards.toString());
		return printTotal(Name, computerTotal, computerTotalAces);
	}
	
	// Print's the total of the hand to standard output.
	static int printTotal(String Name, int total, int totalAces) {
		if (totalAces < 21 && totalAces > total) {
			System.out.println(Name + "'s final total is: " + totalAces);
			return totalAces;
		} else {
			System.out.println(Name + "'s final total is: " + total);
		}
		return total;
	}
	
	public static void checkWin(int playerTotal, int computerTotal) {
		if (playerTotal > 21 && computerTotal > 21) {
			System.out.println("You and the Dealer busted! It's a draw.");
		} else if (playerTotal == computerTotal) {
			System.out.println("Your " + playerTotal + " is equal to the Dealer's " + computerTotal + ". It's a draw.");
		} else if (playerTotal > 21 && computerTotal <= 21) {
			System.out.println("You busted! The Dealer wins.");
		} else if (playerTotal <= 21 && computerTotal > 21) {
			System.out.println("The Dealer busted! You win!!");
		} else if (playerTotal <= 21 && computerTotal <= 21 && playerTotal > computerTotal) {
			System.out.println("Your " + playerTotal + " is greater than the Dealer's " + computerTotal + ". You win!!");
		} else {
			System.out.println("Your " + playerTotal + " is less than the Dealer's " + computerTotal + ". You lose.");
		}
	}
}
