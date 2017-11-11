/*Eric Pickup
Validate a credit card number given by the user as a String. First, remove all the
spaces or dashes between the number. Then, do the following steps to validate the number:
• Form the sum of all the digits of the credit card number.
• Add to that sum every second digit, starting with the second digit from the right.
• Add the number of digits in the second step that are greater than 4.
• The result should be divisible by 10
*/


public class Card {
	
	/**
	 * Credit Card Checker, 03-60-212 Assignment #3
	 * Has functions to create credit card object, retrieve the card number, and verify its validity
	 * @author Eric Pickup
	 *
	 */
		
		//Instance variables
		String cardNumber;
		
		/**
		 * Constructor for credit card, removes any non-digit characters to make it into format xxxxxxxxxxxxxxxx)
		 * @param preNumber - can be any format as long as all 16 digits exist
		 */
		Card(String preNumber) {
			String postNumber = "";
			int index = 0;
			char indexChar;
			while(index < preNumber.length()) {
				indexChar = preNumber.charAt(index);
				if (Character.isDigit(indexChar)) {
					postNumber+=indexChar;
				}
				index++;
			}
			if (postNumber.length() != 16) {
				throw new IllegalArgumentException("ERROR: Credit Card should contain 16 digits!");
			}
			this.cardNumber = postNumber;
		}
		
		/**
		 * Converts and returns credit card number in a spaced format (xxxx xxxx xxxx xxxx)
		 * @return cardNumber converted to format
		 */
		public String getNumber() {
			return cardNumber.substring(0, 4) + " " + cardNumber.substring(4,8) + " " + cardNumber.substring(8,12) + " " + cardNumber.substring(12,16);
		}
		
		/**
		 * Verifies whether the credit card is valid (sum of all digits, every other digit starting from 1st, and previous step numbers > 4)
		 * @return 1 if valid, 0 if not
		 */
		public int isValid() {
			int sumOfNumbers = 0;
			int greaterThanFourCount = 0;
			int sumOfColoredNumbers = 0;
			for (int i = 0; i < 16; i++) {	//For each digit in card number
				sumOfNumbers += Character.getNumericValue(this.cardNumber.charAt(i));
				if (i % 2 == 0) {	//If the index is even (colored numbers)
					sumOfColoredNumbers += Character.getNumericValue(this.cardNumber.charAt(i));
					if (Character.getNumericValue(this.cardNumber.charAt(i)) > 4) {	//If colored number > 4
						greaterThanFourCount++;
					}
				}
			}
			int totalSum = sumOfNumbers + greaterThanFourCount + sumOfColoredNumbers;
			if ((totalSum % 10) == 0) {	//If number is divisible by 10
				return 1;
			}
			return 0;
		}
		
		
		
	}
	

