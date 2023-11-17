package hr.fer.zemris.math;

/**
 * Models a complex polynomial in a root form.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ComplexRootedPolynomial {
	/**
	 * Constant of a complex rooted polynomial.
	 */
	Complex constant;
	/**
	 * Roots of a complex polynomial.
	 */
	Complex[] roots;

	/**
	 * @param constant sets this.constant
	 * @param roots sets this.roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * @param z where to compute the polynomial
	 * @return polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		Complex applied = new Complex(constant.getRe(), constant.getIm());
		
		for (int i = 0, len = roots.length; i < len; i++) {
			applied = applied.multiply(z.sub(roots[i]));
		}

		return applied;
	}

	/**
	 * @return this representation converted to ComplexPolynomial type
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(constant);

		for (int i = 0, len = roots.length; i < len; i++) {
			Complex[] arr = {roots[i].multiply(Complex.ONE_NEG), Complex.ONE};
			result = result.multiply(new ComplexPolynomial(arr));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("(").append(constant).append(")");

		for (int i = 0, len = roots.length; i < len; i++) {
			sb.append("*(z-(").append(roots[i]).append("))");
		}

		return sb.toString();
	}

	/** 
	 * Finds index of closest root for given complex number z that is within
	 * threshold; if there is no such root, returns -1,
	 * first root has index 0, second index 1, etc.
	 * 
	 * @param z to search a closest root for
	 * @param threshold, they have to be closer than
	 * @return index of a closest root if it is within threshold, otherwise -1
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		double minDistance = z.sub(roots[0]).module();
		int index = 0;

		for (int i = 1, len = roots.length; i < len; i++) {
			double current = z.sub(roots[i]).module();
			if (current < minDistance) {
				minDistance = current;
				index = i;
			}
		}

		if (minDistance < threshold) {
			return index;
		} else {
			return -1;
		}
	}

}
