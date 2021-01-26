package najeeb.mathematics.integration;

import najeeb.mathematics.terms.*;

public class MultiTermSimpsonsRule {
	Term[] T;

	public MultiTermSimpsonsRule(Term[] t) {
		T = t;
	}

	// Uses increasing numbers of trapezii until within margin of error
	public double calculate(double a, double b, double e_margin) {

		if (e_margin < 0) {
			e_margin = -e_margin;
		}

		int n = 2;
		double[] calcs = new double[3];
		calcs[0] = calculateForN(n, a, b);
		System.out.println(calcs[0]);
		n = 4;
		calcs[1] = calculateForN(n, a, b);
		System.out.println(calcs[1]);
		n = 6;
		calcs[2] = calculateForN(n, a, b);
		System.out.println(calcs[2]);
		int i = 0;
		for (i = 0; Math.abs(calcs[2] / calcs[1] - 1) > e_margin | Math.abs(calcs[1] / calcs[0] - 1) > e_margin
				| Math.abs(calcs[0] / calcs[2] - 1) > e_margin; i++) {
			n = n * 2 - 2;
			calcs[i % 3] = calculateForN(n, a, b);
			System.out.println(calcs[i % 3]);
		}
		return calcs[(i + 2) % 3];

	}

	// Method to calculate definite integral of function with n trapezii
	private double calculateForN(int n, double a, double b) {
		// Calculates the width of the trapezium
		double h = (b - a) / n;
		double[] x = new double[n + 1];
		double[] y = new double[n - 1];

		// Prepares all x-values between a and b separated by h
		for (int i = 0; i < n + 1; i++) {
			x[i] = a + i * h;
		}

		// Calculates y-values for the range y(x[1]) to y(x[n-1])
		for (int i = 0; i < n - 1; i++) {
			y[i] = sumOfTermsAt(x[i + 1]);
		}

		// Applies formula for trapezium rule
		int coeff = 4;
		double out = 0;
		out = sumOfTermsAt(x[0]) + sumOfTermsAt(x[n]);
		for (int i = 0; i < n-1; i++) {
			out += coeff*y[i];
			coeff = 6 - coeff;
		}
		return out*h/3;
	}

	// Gets the sum of all the double values in a double array

	private double sumOfTermsAt(double x) {
		double out = 0;
		for (int i = 0; i < T.length; i++) {
			out += T[i].value(x);
		}
		return out;
	}
}
