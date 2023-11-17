package hr.fer.zemris.math;

/**
 * Models a complex polynomial.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ComplexPolynomial {
	/**
	 * Factors of a polynomial.
	 */
	private Complex[] factors;

	/**
	 * @param factors initializes this.factors
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * @return order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 */
	public short order() {
		for (int i = factors.length - 1; i >= 0; i--) {
			if (factors[i].getIm() != 0 || factors[i].getRe() != 0) {
				return (short) i;
			}
		}
		return 0;
	}

	/**
	 * @param p polynomial for multiplying with
	 * @return a new polynomial this*p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] result = new Complex[factors.length + p.factors.length - 1];

		for (int i = 0, len = result.length; i < len; i++) {
			result[i] = Complex.ZERO;
		}

		for (int i = 0, len = factors.length; i < len; i++) {
			for (int j = 0, len2 = p.factors.length; j < len2; j++) {
				result[i + j] = result[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(result);
	}

	/**
	 * @return first derivative of this polynomial; for example, for
	 * 		(7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length - 1];

		for (int i = 0, len = newFactors.length; i < len; i++) {
			newFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * @param z where to compute the polynomial
	 * @return polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex(factors[0].getRe(), factors[0].getIm());

		for (int i = 1, len = factors.length; i < len; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = factors.length - 1; i >= 1; i--) {
			sb.append("(").append(factors[i]).append(")*z^").append(i).append("+");
		}

		sb.append("(").append(factors[0]).append(")");

		return sb.toString();
	}

}
