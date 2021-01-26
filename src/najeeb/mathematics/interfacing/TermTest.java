package najeeb.mathematics.interfacing;

import java.util.Scanner;

public class TermTest {

	public static void main(String[] args) {
		
		System.out.println("Enter term list capacity:");
		

		Scanner sc = new Scanner(System.in);
		int cap = sc.nextInt();
		sc.nextLine();
		TermListManipulator tlist = new TermListManipulator(cap);
		String input = "";
		input = sc.nextLine();
		while (!input.equals("end")) {
			tlist.command(input);
			input = sc.nextLine();
		}
		sc.close();
		
	}

}
