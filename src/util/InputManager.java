package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class InputManager {

	private static Scanner scanner = new Scanner(System.in);

	public static String getScannerNextLine() {
		return scanner.nextLine();
	}
	
	public static void closeScanner() {
		scanner.close();
	}
	
	public static String inputString(String name) {
		String input = null;

		System.out.print(name + " (chaîne de caractère) : ");
		input = getScannerNextLine();

		return input;
	}
	
	public static String inputChoices(String question, String... choices) {
		String input = null;
		boolean valid = false;
		
		while (!valid) {
			System.out.print(question + " (" + String.join("/", choices)  + ") : ");
			
			input = getScannerNextLine();
			
			if (Arrays.asList(choices).contains(input)) {
				valid = true;
			}
			else {
				System.out.println("Un choix valide est attendu");
			}
		}
		
		return input;
	}
	
	public static int inputUnsignedInt(String name) {
		String input = null;
		int value = 0;
		boolean valid = false;
		
		while (!valid) {
			System.out.print(name + " (entier positif) : ");
			
			input = getScannerNextLine();
			
			try {
				value = Integer.parseUnsignedInt(input);
				valid = true;
			}
			catch (NumberFormatException exception) {
				System.out.println("Un entier positif est attendu");
			}
		}
		
		return value;
	}
	
	public static float inputUnsignedFloat(String name) {
		String input = null;
		float value = 0;
		boolean valid = false;
		
		while (!valid) {
			System.out.print(name + " (nombre flottant positif) : ");
			
			input = getScannerNextLine();
			
			try {
				value = Float.parseFloat(input);
				
				if (value >= 0) {
					valid = true;
				}
				else {
					throw new NumberFormatException("Unsigned float expected");
				}
			}
			catch (NumberFormatException exception) {
				System.out.println("Un nombre flottant positif est attendu");
			}
		}
		
		return value;
	}
	
	public static float inputFloatInInterval(String name, float min, float max) {
		String input = null;
		float value = 0;
		boolean valid = false;
		
		while (!valid) {
			System.out.print(name + " (nombre flottant entre " + min + " et " + max + ") : ");
			
			input = getScannerNextLine();
			
			try {
				value = Float.parseFloat(input);
				
				if (value >= min && value <= max) {
					valid = true;
				}
				else {
					throw new NumberFormatException("Unsigned float expected");
				}
			}
			catch (NumberFormatException exception) {
				System.out.println("Un nombre flottant entre " + min + " et " +  max + " est attendu");
			}
		}
		
		return value;
	}
	
	public static Date inputDateTime(String name) {
		SimpleDateFormat formatterDate = DateFormatters.getDateTimeFormatter();
		String input = null;
		Date value = null;
		boolean valid = false;
		
		while (!valid) {
			System.out.print(name + " (date au format \"" + DateFormatters.dateTimeFormat + "\") : ");
			
			input = getScannerNextLine();
			
			try {
				value = formatterDate.parse(input);
				valid = true;
			}
			catch (NumberFormatException | ParseException exception) {
				System.out.println("Une date au bon format est attendue");
			}
		}
		
		return value;
	}
	
	public static Date inputDate(String name) {
		SimpleDateFormat formatterDate = DateFormatters.getDateFormatter();
		String input = null;
		Date value = null;
		boolean valid = false;
		
		while (!valid) {
			System.out.print(name + " (date au format \"" + DateFormatters.dateFormat + "\") : ");
			
			input = getScannerNextLine();
			
			try {
				value = formatterDate.parse(input);
				valid = true;
			}
			catch (NumberFormatException | ParseException exception) {
				System.out.println("Une date au bon format est attendue");
			}
		}
		
		return value;
	}
	
	public static Date inputTime(String name) {
		SimpleDateFormat formatterDate = DateFormatters.getTimeFormatter();
		String input = null;
		Date value = null;
		boolean valid = false;
		
		while (!valid) {
			System.out.print(name + " (heure au format \"" + DateFormatters.timeFormat + "\") : ");
			
			input = getScannerNextLine();
			
			try {
				value = formatterDate.parse(input);
				valid = true;
			}
			catch (NumberFormatException | ParseException exception) {
				System.out.println("Une heure au bon format est attendue");
			}
		}
		
		return value;
	}
}
