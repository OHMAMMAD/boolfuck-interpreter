
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class bfi {


	static String getFile(String fileName) {
		String out = "";
		try {
			File file = new File(fileName);
			Scanner fileReader = new Scanner(file);


			while (fileReader.hasNextLine()) {
				out += fileReader.nextLine();
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found.");
			e.printStackTrace();
  		}
		return out;
	}

	static int findNextBracket(String code, int bracketLoc) {
		if (code.charAt(bracketLoc) != '[') {System.out.println("ERROR: NOT STARTING WITH BRACKET"); return 0;}

		int i = bracketLoc + 1;
		int bracketsOpened = 0;
		while (true) {
			if (code.charAt(i) == '[') {bracketsOpened++;}
			if (code.charAt(i) == ']') {
				if (bracketsOpened == 0) {return i;}
				else {bracketsOpened--;}
			}

			i++;
			if (i == code.length()) {break;}
		}
		System.out.println("ERROR: NO MATCHING BRACKET FOR OPENED BRACKET IN LOC " + bracketLoc);
		return 0;
	}

	static void printArr(boolean[] arr) {

		String out = "";
		for (int i=0; i<arr.length; i++) {
			out += (arr[i]) ? 1 : 0;
			if (i != arr.length-1) {out += ", ";}
		}
		System.out.print(out);
	}

	public static void main(String[] args) {
		String code = getFile(args[0]);
		System.out.println(code);

		int arrLen = 16;
		boolean[] arr = new boolean[arrLen];
		int pointer = 0;

		char[] validCommands = {'+', '>', '<', '[', ']', '.', ','};
		int validCommandsCounter = 0;
		int index = 0;
		List<Integer> stack = new ArrayList<Integer>();
		while (index < code.length()) {
			char cm = code.charAt(index);

			if      (cm == '+') {arr[pointer] = !(arr[pointer]);}
			else if (cm == '>')
			{
				pointer++;
				if (pointer >= arrLen) {
					System.out.println("you cant wrap around the array"); break;}
			}
			else if (cm == '<')
			{
				pointer--;
				if (pointer < 0) {
					System.out.println("you cant wrap around the array"); break;}
			}
			else if (cm == '[')
			{
				if (arr[pointer]) {stack.add(index);}
				else {index = findNextBracket(code, index);}
			}
			else if (cm == ']')
			{
				int tempIndex = index;
				index = stack.get(stack.size()-1);

				if (!(arr[pointer])) { //if we are ending the loop
					stack.remove(stack.size()-1);
					index = tempIndex;
				}
			}

			for (int i=0; i<validCommands.length; i++)
			{
				if (cm == validCommands[i])
				{
					validCommandsCounter++;
				}
			}

			index++;
			printArr(arr);
			System.out.println("      pointer: " + pointer + "   command: " + cm);
		}
		System.out.println("number commands: " + index);
		System.out.println("number of valid commands: " + validCommandsCounter);
	}

}
