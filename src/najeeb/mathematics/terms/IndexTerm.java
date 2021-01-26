package najeeb.mathematics.terms;



public class IndexTerm extends Term {

	Term exponent;
	Term base;
	
	public IndexTerm(Term bs, Term ex) {
		exponent = ex;
		base = bs;
	}
	
	public double value(double x) {
		// Returns x^exponent
		return Math.pow(base.value(x), exponent.value(x));
	}

}