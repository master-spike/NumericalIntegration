package najeeb.mathematics.terms;

public class SumTerm extends Term{
	
	Term[] T;

	public SumTerm(Term[] t) {
		T = t;
	}
	
	public double value(double x) {
		double out = 0;
		for (int i = 0; i < T.length; i++) {
			out += T[i].value(x);
		}
		return out;
	}

}
