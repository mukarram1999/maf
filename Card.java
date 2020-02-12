/**
 * Blackjack card game; this class defines a card object
 * @author
 * 	Mukarram Faridi
 *    James MacCarthy
 *    Yuantai Pan
 *		Sanchit Sethi
 *    Preet Shah
 *    Stanley Thomas
 * @version 02/09/2020
 */
public class Card {
		public int value;
		public String suit;
		
		public Card(int valueIn, String suitIn) {
			value = valueIn;
			suit = suitIn;
		}
		
		public int getValue() {
			return value;
		}
		
		public String getSuit() {
			return suit;
		}
      
      public String toString() {
         return value + suit;
      }
}