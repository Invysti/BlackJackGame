
public class Card {
	
	private Value cardValue;
	private Suit cardSuit;
	
	public Card(Value v, Suit s) {
		cardValue = v;
		cardSuit = s;
	}
	
	public Value getValue() {
		return cardValue;
	}
	
	public Suit getSuit() {
		return cardSuit;
	}
	
	public String toString() {
		return getValue() + " of " + getSuit();
	}
}
