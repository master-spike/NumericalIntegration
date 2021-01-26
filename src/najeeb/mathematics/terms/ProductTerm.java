package najeeb.mathematics.terms;

public class ProductTerm extends Term {

	Term[] terms;

	public ProductTerm(Term[] t) {
		terms = t;
	}

	public double value(double x) {

		double p = 1;

		// Multiplies p by each term in the array to get the product
		for (int i = 0; i < terms.length; i++) {
			p *= terms[i].value(x);
		}

		return p;

	}

}
