package najeeb.mathematics.interfacing;

import najeeb.mathematics.integration.MultiTermSimpsonsRule;
import najeeb.mathematics.integration.MultiTermTrapeziumRule;
import najeeb.mathematics.terms.*;

public class TermListManipulator {

	Term[] terms;
	String[] identifiers;

	public TermListManipulator(int capacity) {
		terms = new Term[capacity];
		identifiers = new String[capacity];
	}
	
	private void delete(String id) {
		for (int i = 0; i < identifiers.length; i++) {
			if (identifiers[i] != null)
				if (identifiers[i].equals(id)) {
					identifiers[i] = null;
					System.out.println("term successfully deleted.");
					return;
				}
		}
		System.out.println("term not found.");
	}
	
	
	// TODO Set up clear method
	// TODO Set up replace method

	// TODO Set up "list" method

	public void command(String command) {
		String[] arguments = command.split(" ");

		if (arguments[0].equals("delete")) {
			System.out.print("Deleting term - ");
			if (arguments.length < 2) {
				System.out.println("false parameters - delete requires a String value for the identifier of the term.");
				return;
			}
			delete(arguments[1]);
			return;
		}

		if (arguments[0].equals("newterm")) {
			System.out.print("Adding new term - ");

			if (arguments.length < 3) {
				System.out.println("not enough arguments");
				return;
			}

			if (arguments[2].equals("LIN")) {
				System.out.print("LinearTerm - ");
				if (arguments.length < 5)
					System.out.println(
							"false parameters - LIN requires two double values for the coefficient and constant");
				else {
					String[] pass_on = new String[arguments.length - 3];
					for (int i = 0; i < arguments.length - 3; i++) {
						pass_on[i] = arguments[3 + i];
					}
					Term T = interpretLIN(pass_on);
					if (T != null)
						addTerm(arguments[1], T);
				}
				return;

			}

			if (arguments[2].equals("IND")) {
				System.out.print("IndexTerm - ");
				if (arguments.length < 5)
					System.out.println(
							"false parameters - IND requires two strings identifying the base and exponent terms");
				else {
					String[] pass_on = new String[arguments.length - 3];
					for (int i = 0; i < arguments.length - 3; i++) {
						pass_on[i] = arguments[3 + i];
					}
					Term T = interpretIND(pass_on);
					if (T != null)
						addTerm(arguments[1], T);
				}
				return;
			}

			if (arguments[2].equals("LOG")) {
				System.out.print("LogarithmTerm - ");
				if (arguments.length < 5)
					System.out.println(
							"false parameters - LOG requires two strings identifying the base and number terms");
				else {
					String[] pass_on = new String[arguments.length - 3];
					for (int i = 0; i < arguments.length - 3; i++) {
						pass_on[i] = arguments[3 + i];
					}
					Term T = interpretLOG(pass_on);
					if (T != null)
						addTerm(arguments[1], T);
				}
				return;
			}

			if (arguments[2].equals("PROD")) {
				System.out.print("ProductTerm - ");
				if (arguments.length < 4)
					System.out.println(
							"false parameters - PROD requires at least one string identifying the terms to be multiplied");
				else {
					String[] pass_on = new String[arguments.length - 3];
					for (int i = 0; i < arguments.length - 3; i++) {
						pass_on[i] = arguments[3 + i];
					}
					Term T = interpretPROD(pass_on);
					if (T != null)
						addTerm(arguments[1], T);
				}
				return;
			}

			if (arguments[2].equals("QUOT")) {
				System.out.print("QuotientTerm - ");
				if (arguments.length < 5)
					System.out.println(
							"false parameters - QUOT requires two strings identifying the numerator and denominator terms");
				else {
					String[] pass_on = new String[arguments.length - 3];
					for (int i = 0; i < arguments.length - 3; i++) {
						pass_on[i] = arguments[3 + i];
					}
					Term T = interpretQUOT(pass_on);
					if (T != null)
						addTerm(arguments[1], T);
				}
				return;
			}

			if (arguments[2].equals("SUM")) {
				System.out.print("SumTerm - ");
				if (arguments.length < 4)
					System.out.println(
							"false parameters - SUM requires at least one string identifying the terms to be summed");
				else {
					String[] pass_on = new String[arguments.length - 3];
					for (int i = 0; i < arguments.length - 3; i++) {
						pass_on[i] = arguments[3 + i];
					}
					Term T = interpretSUM(pass_on);
					if (T != null)
						addTerm(arguments[1], T);
				}
				return;
			}

			System.out.println("\"" + arguments[2] + "\" is not a valid term specifier.");
			return;

		}
		if (arguments[0].equals("integrate")) {
			System.out.print("Integrating - ");
			if (arguments.length < 5) {
				System.out.println(
						"false parameters - integrate requires a method, a list of terms and two double values.");
				return;
			}

			if (arguments[1].equals("simpson")) {
				String[] pass_on = new String[arguments.length - 2];
				for (int i = 2; i < arguments.length; i++) {
					pass_on[i - 2] = arguments[i];
				}
				System.out.println("Result: " + integrateSimpsonsRule(pass_on));
				return;
			}
			if (arguments[1].equals("trapezium")) {
				String[] pass_on = new String[arguments.length - 2];
				for (int i = 2; i < arguments.length; i++) {
					pass_on[i - 2] = arguments[i];
				}
				System.out.println("Result: " + integrateTrapeziumRule(pass_on));
				return;
			}
			System.out.println("Invalid integration method.");

		}
		System.out.println("Unrecognised command.");

	}

	private Term interpretQUOT(String[] params) {
		if (params.length < 2) {
			System.out.println(
					"false parameters - QUOT requires two strings identifying the numerator and denominator terms");
			return null;
		}
		Term t1 = getTerm(params[0]);
		if (t1 != null) {
			Term t2 = getTerm(params[1]);
			if (t2 != null) {
				return new QuotientTerm(t1, t2);
			}
		}
		return null;
	}

	private Term interpretSUM(String[] params) {
		if (params.length < 1) {
			System.out
					.println("false parameters - SUM requires at least one string identifying the terms to be summed");
			return null;
		}
		Term[] terms = new Term[params.length];
		for (int i = 0; i < params.length; i++) {
			terms[i] = getTerm(params[i]);
			if (terms[i] == null)
				return null;
		}
		return new SumTerm(terms);
	}

	private Term interpretPROD(String[] params) {
		if (params.length < 1) {
			System.out.println(
					"false parameters - PROD requires at least one string identifying the terms to be multiplied");
			return null;
		}
		Term[] terms = new Term[params.length];
		for (int i = 0; i < params.length; i++) {
			terms[i] = getTerm(params[i]);
			if (terms[i] == null)
				return null;
		}
		return new ProductTerm(terms);
	}

	private Term interpretLOG(String[] params) {
		if (params.length < 2) {
			System.out.println("false parameters - LOG requires two strings identifying the base and exponent terms");
			return null;
		}
		Term t1 = getTerm(params[0]);
		if (t1 != null) {
			Term t2 = getTerm(params[1]);
			if (t2 != null) {
				return new LogarithmTerm(t1, t2);
			}
		}
		return null;
	}

	private Term interpretLIN(String[] params) {
		if (params.length < 2) {
			System.out.println("false parameters - LIN requires two double values for the coefficient and constant");
			return null;
		}
		try {
			double a = Double.NaN;
			if (!params[0].contains("%")) {
				a = Double.parseDouble(params[0]);
			}
			else if (params[0].equals("%e%")) {
				a = Math.E;
			}
			else if (params[0].equals("%pi%")) {
				a = Math.PI;
			}
			if (a == Double.NaN) {
				System.out.println("invalid constant specified - valid constants are \"%e%\" for Euler's number or \"%pi%\" for pi");
				return null;
			}
			
			double b = Double.NaN;
			if (!params[1].contains("%")) {
				b = Double.parseDouble(params[1]);
			}
			else if (params[1].equals("%e%")) {
				b = Math.E;
			}
			else if (params[1].equals("%pi%")) {
				b = Math.PI;
			}
			if (b == Double.NaN) {
				System.out.println("invalid constant specified - valid constants are \"%e%\" for Euler's number or \"%pi%\" for pi");
				return null;
			}
			return new LinearTerm(a, b);
		} catch (Exception e) {
			System.out.println("false parameters - LIN requires two double values for the coefficient and constant");
			return null;
		}
	}

	private Term interpretIND(String[] params) {
		if (params.length < 2) {
			System.out.println("false parameters - IND requires two strings identifying the base and exponent terms");
			return null;
		}
		Term t1 = getTerm(params[0]);
		if (t1 != null) {
			Term t2 = getTerm(params[1]);
			if (t2 != null) {
				return new IndexTerm(t1, t2);
			}
		}
		return null;

	}

	public void addTerm(String identifier, Term term) {
		for (int i = 0; i < terms.length; i++) {
			if (identifier.equals(identifiers[i])) {
				System.out.println("failed to add term - existent entry entitled \"" + identifier
						+ "\".");

				return;
			}
		}
		for (int i = 0; i < terms.length; i++) {
			if (identifiers[i] == null) {
				terms[i] = term;
				identifiers[i] = identifier;
				System.out.println("successfully added.");
				return;
			}
		}
		System.out.println("No free space in array. Consider erasing an entry of an unused term.");
	}

	public Term getTerm(String identifier) {

		for (int i = 0; i < terms.length; i++) {
			if (identifier.equals(identifiers[i])) {
				return terms[i];
			}
		}
		System.out.println("No terms identified as \"" + identifier + "\". Use a different identifier.");
		return null;
	}

	// Takes arguments from command to integrate using simpson's rule
	public double integrateSimpsonsRule(String[] params) {
		Term[] terms = new Term[params.length - 2];
		double a = 0;
		double b = 0;
		try {
			a = Double.parseDouble(params[0]);
			b = Double.parseDouble(params[1]);
		} catch (Exception e) {
			System.out.println("Invalid input for a and b - Must be double values.");
			return Double.NaN;
		}
		for (int i = 2; i < params.length; i++) {
			terms[i - 2] = getTerm(params[i]);
			if (terms[i - 2] == null) // No error message needed as a null term
										// means that the message would have
										// been called in the "getTerm" method
				return Double.NaN;
		}
		MultiTermSimpsonsRule mttr = new MultiTermSimpsonsRule(terms);
		return mttr.calculate(a, b, 0.0000000000001);
	}

	// Takes arguments from command to integrate using the trapezium rule
	public double integrateTrapeziumRule(String[] params) {
		Term[] terms = new Term[params.length - 2];
		double a = 0;
		double b = 0;
		try {
			a = Double.parseDouble(params[0]);
			b = Double.parseDouble(params[1]);
		} catch (Exception e) {
			System.out.println("Invalid input for a and b - Must be double values.");
			return Double.NaN;
		}
		for (int i = 2; i < params.length; i++) {
			terms[i - 2] = getTerm(params[i]);
			if (terms[i - 2] == null) // No error message needed as a null term
										// means that the message would have
										// been called in the "getTerm" method
				return Double.NaN;
		}
		MultiTermTrapeziumRule mttr = new MultiTermTrapeziumRule(terms);
		return mttr.calculate(a, b, 0.000000000001);
	}

}