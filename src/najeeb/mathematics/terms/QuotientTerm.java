package najeeb.mathematics.terms;

public class QuotientTerm extends Term {

	Term numerator;
	Term denominator;
	
	public QuotientTerm(Term num, Term denom) {
		numerator = num;
		denominator = denom;
	}
	
	public double value(double x) {
		return numerator.value(x) / denominator.value(x);
	}

	
	
}
