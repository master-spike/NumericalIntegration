package najeeb.mathematics.terms;

public class LinearTerm extends Term {

	double a;
	double b;

	public LinearTerm(double a, double b) {
		this.a = a;
		this.b = b;
	}

	public double value(double x) {
		return a * x + b;
	}

}
