package postfixCalculator;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public static int evaluatePostfix(String userInput) {
		Stack<Integer> expStack = new Stack<>();
		String[] expTokens = userInput.split(" ");
		try {
			for (String token : expTokens) {
				if (isNumeric(token)) {
					expStack.push(Integer.parseInt(token));
				} else {
					int b = expStack.pop();
					int a = expStack.pop();
					int result = calculate(token, a, b);
					expStack.push(result);
				}
			}
			if (expStack.size() != 1) {
				throw new IllegalStateException();
			}
		} catch (ArithmeticException ae) {
			System.out.println(ae);
			return Integer.MIN_VALUE;
		} catch (Exception e) {
			System.out.println("Error: Invalid postfix expression.");
			return Integer.MIN_VALUE;
		}
		return expStack.pop();
	}

	private static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static int calculate(String operator, int a, int b) {
		return switch (operator) {
		case "+" -> a + b;
		case "-" -> a - b;
		case "*" -> a * b;
		case "/" -> a / b;
		case "%" -> a % b;
		default -> throw new IllegalArgumentException("Error: Invalid postfix expression");
		};
	}

	public static void readFromFile() {
		String filePath = "postfixExpressions.txt";

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			int lineNum = 1;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				System.out.println("Line " + lineNum + ": " + line);
				int result = evaluatePostfix(line);
				if (result != Integer.MIN_VALUE) {
					System.out.println("Result: " + result);
				}
				System.out.println();
				lineNum++;
			}
		} catch (IOException e) {
			System.out.println("Error Reading File " + filePath + ": " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		boolean runProgram = true;
		Scanner scnr = new Scanner(System.in);

		System.out.println("Please Enter Your Postfix Expression");
		System.out.println("Type Read To Evaluate Expressions Written In The postfixExpressiosn.txt File");
		System.out.println("To Exit The Program Type X");
		while (runProgram) {
			System.out.println("Expression or Command: ");
			String userInput = scnr.nextLine().trim();

			if (userInput.equalsIgnoreCase("x")) {
				runProgram = false;
			} else if (userInput.equalsIgnoreCase("read")) {
				readFromFile();
			} else {
				int result = evaluatePostfix(userInput);
				if (result != Integer.MIN_VALUE) {
					System.out.println("Result: " + result);	
				}
			}
		}
		
		System.out.println("Goodbye");
		scnr.close();
	}
}
