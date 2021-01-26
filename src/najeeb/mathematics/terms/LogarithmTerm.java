package najeeb.mathematics.terms;

public class LogarithmTerm extends Term {

	Term base;
	Term number;
	
	public LogarithmTerm(Term b, Term n) {
		base = b;
		number = n;
	}
	// Uses the logarithm base-change rule
	public double value(double x) {
		return Math.log(number.value(x)) / Math.log(base.value(x));
	}

}
