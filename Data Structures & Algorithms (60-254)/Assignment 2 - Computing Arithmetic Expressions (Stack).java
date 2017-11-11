/*Eric Pickup
Create a program to evaluate arithmetic expressions represented as text
For example, the string "1 + 2 + (3 * 4)" would evaluate to 15. Your program should:
1. Read arithmetic expressions in infix format from input text file
2. Validate the infix expression
3. Using Stack as ADT convert the infix expression into a postfix expression. 
4. Using Stack as ADT evaluate the postfix expression from step 3​ 
5. Display the data in the stack before and after each pop and push operation
*/

package assign260256;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class assign2 {
	
	public static void main (String[] args) throws IOException {
		
		BufferedReader input = new BufferedReader(new FileReader("input.txt"));
		String line;
		int lineCount = 0;
		
		ArrayList<String> list = new ArrayList<String>();
		while((line = input.readLine()) != null){
			if (!(line.isEmpty() || line.trim().equals("") || line.trim().equals("\n"))) {
				list.add(line);
				lineCount++;
			}
		}
		input.close();

		String[] stringArr = list.toArray(new String[0]);
		for (int i = 0; i < lineCount; i++) {
			System.out.println("==================\nConverting " + stringArr[i]);
			if (isValid(stringArr[i]) == 1) {
				stringArr[i] = convertToPostfix(stringArr[i]);
				System.out.println(stringArr[i] + " = " + postfixEvaluate(stringArr[i]));
			} else {
				System.out.println("Error: Invalid input");
			}
			System.out.println("=================");
		}
		
	}
	
	public static String convertToPostfix(String expression) {
		
		Stack<Character> stack = new Stack<Character>();
		String postfix = "";
		char temp;
		for (int i = 0; i < expression.length(); i++) {
			
			printStack(stack);
	
			temp = expression.charAt(i);
			if (Character.isDigit(temp)) {	//If it's a digit
				postfix+=temp;
				if (i == expression.length()-1 || !Character.isDigit(expression.charAt(i+1))) {	//If the next character isn't a digit (eg. numbers 0-9)
					postfix+=" ";	//Add a space
				}
			} else if (temp != ' ') {	//If it's not a space or digit (operator)
				if (stack.isEmpty()) {
					stack.push(temp);
				} else if (temp == '(') {
					stack.push(temp);
				} else if (temp == ')') {
					while (stack.peek() != '(') {
						postfix = postfix + stack.pop() + " ";				
					}
					stack.pop();
					
				} else if (checkPrecedence(temp) > checkPrecedence(stack.peek())) {	//If current operator precedence > last in stack
					stack.push(temp);	//Add to stack
				} else {	//If current operator precedence <= precedence of last in stack
					postfix = postfix + stack.pop() + " ";	//Otherwise pop last in stack to postfix
					stack.push(temp);	//Push current operator into stack
					
					while (!stack.isEmpty() && stack.size() > 1 && checkPrecedence(stack.peek()) <= checkPrecedence(stack.get(stack.size()-2))) {	//After popping one and adding another, we need to check the precedence with the new char
						postfix += stack.elementAt(stack.size()-2) + " ";	//Add preceding operator to postfix
						stack.removeElementAt(stack.size()-2);	//Remove preceding operator
					}
				}
			}
		}
		while (!stack.isEmpty()) {	//Empty out remaining operations in stack
			postfix = postfix + stack.pop() + " ";
		}
		
		return postfix;
		
	}
	
	public static int postfixEvaluate (String postfix) {
		
		Stack<Integer> stack = new Stack<Integer>();
		int index = 0;
		int value;
		
		while (index < postfix.length()) {
			printIntStack(stack);
			if (Character.isDigit(postfix.charAt(index))) {	//If it's a digit (not operator)
				stack.push(Integer.parseInt(postfix.substring(index, postfix.indexOf(" ",index))));
				index = postfix.indexOf(" ",index) + 1;
			} else {	//If it's an operator
				char operator = postfix.charAt(index);
				int operand1 = stack.pop();
				int operand2 = stack.pop();
				stack.push(evaluate(operand1,operand2,operator));
				index+=2; //skip operator and space after op
			}
		}
		return stack.pop();
	}
	
	public static int checkPrecedence(char A) {
		
		switch(A) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		case '^':
			return 3;
		}
		
		return -1;
		
	}
	
	public static int evaluate(int operand1, int operand2, char operator) {
		
		if (operator == '+') {
			return operand2 + operand1;
		} else if (operator == '-') {
			return operand2 - operand1;
		} else if (operator == '*') {
			return operand2 * operand1;
		} else if (operator == '/') {
			return operand2 / operand1;
		} else if (operator == '^') {
			return (int) Math.pow(operand2, operand1);
		}
		return -1;
	}
	
	public static int isValid (String expression) {	//check for double spaces, double operators
		
		int numOpenBracket = 0;
		int numCloseBracket = 0;
		char temp;
		for (int i = 0; i < expression.length(); i++) {
			temp = expression.charAt(i);	//Temp character to check
			if (temp == '(') numOpenBracket++;	//Increase number of open brackets
			if (temp == ')') {
				numCloseBracket++;	//Increase number of close brackets
				if (numOpenBracket < numCloseBracket) return 0; //Number of close brackets should never exceed number of open at any point
			}
			
			if (temp == ' ' && i < expression.length()-1 && expression.charAt(i+1) == ' ') return 0;	//If there is a space, and a space next to the space (double space)
			if (!Character.isDigit(temp) && temp != '+' && temp != '+' && temp != '-' && temp != '/' && temp != '*' && temp != '^' && temp != '(' && temp != ')' && temp != ' ') return 0;	//Checking for legal characters
		}
		if (numOpenBracket != numCloseBracket) return 0;	//Parantheses should be equal in amount
		return 1;	//Otherwise return no errors
	}
	
	public static void printStack (Stack<Character> stack) {
		System.out.print("STACK = ");
		for (int i = 0; i < stack.size(); i++) {
			System.out.print(stack.elementAt(i));
		}
		System.out.println("");
	}
	
	public static void printIntStack (Stack<Integer> stack) {
		System.out.print("INT STACK = ");
		for (int i = 0; i < stack.size(); i++) {
			System.out.print(stack.elementAt(i) + ",");
		}
		System.out.println("");
	}

}
