package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;

/**
 * Class used for modeling complex numbers.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class Complex {
	/**
	 * Real part of a complex number.
	 */
	private double re;
	/**
	 * Imaginary part of a complex number.
	 */
	private double im;

	/**
	 * Returns a Complex number with real part set to 0
	 * and imaginary part set to 0.
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Returns a Complex number with real part set to 1
	 * and imaginary part set to 0.
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Returns a Complex number with real part set to -1
	 * and imaginary part set to 0.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Returns a Complex number with real part set to 0
	 * and imaginary part set to 1.
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Returns a Complex number with real part set to 0
	 * and imaginary part set to -1.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default constructor.
	 */
	public Complex() {
	}

	/**
	 * Constructor that sets the parts of a complex
	 * number.
	 * 
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * @return the real part
	 */
	public double getRe() {
		return re;
	}

	/**
	 * @return the imaginary part
	 */
	public double getIm() {
		return im;
	}

	/**
	 * @return module of a complex number
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * @param c to multiply this number with
	 * @return this*c
	 */
	public Complex multiply(Complex c) {
		return new Complex(re * c.getRe() - im * c.getIm(), re * c.getIm() + im * c.getRe());
	}

	/**
	 * @param c to divide this number with
	 * @return this/c
	 */
	public Complex divide(Complex c) {
		double real = (module() / c.module()) * (Math.cos(calculatePhi(re, im) - calculatePhi(c.getRe(), c.getIm())));
		double imag = (module() / c.module()) * (Math.sin(calculatePhi(re, im) - calculatePhi(c.getRe(), c.getIm())));

		return new Complex(real, imag);
	}

	/**
	 * @param c to add this number to
	 * @return this+c
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.getRe(), im + c.getIm());
	}

	/**
	 * @param c to subtract this number to
	 * @return this-c
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.getRe(), im - c.getIm());
	}

	/**
	 * @return -1*this
	 */
	public Complex negate() {
		return new Complex((-1) * re, (-1) * im);
	}

	/**
	 * @param n exponent
	 * @return this^n, n is non-negative integer
	 */
	public Complex power(int n) {
		return new Complex(Math.pow(module(), n) * Math.cos(n * calculatePhi(re, im)),
				Math.pow(module(), n) * Math.sin(n * calculatePhi(re, im)));
	}

	/**
	 * @param re real part
	 * @param im imaginary part
	 * @return phi of a complex number
	 */
	private double calculatePhi(double re, double im) {
		return Math.atan2(im, re);
	}

	/**
	 * @param n determines which root
	 * @return n-th root of this, n is positive integer
	 */
	public List<Complex> root(int n) {
		List<Complex> list = new LinkedList<>();

		for (int k = 0; k < n; k++) {
			list.add(new Complex(Math.pow(module(), 1.0 / n) * Math.cos((calculatePhi(re, im) + 2 * k * Math.PI) / n),
					Math.pow(module(), 1.0 / n) * Math.sin((calculatePhi(re, im) + 2 * k * Math.PI) / n)));
		}

		return list;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(re);
		if (im < 0) {
			sb.append("-");
		} else {
			sb.append("+");
		}
		sb.append("i");
		sb.append(Math.abs(im));

		return sb.toString();
	}

}
