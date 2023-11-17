package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Used as a Newton-Raphson iteration-based fractal viewer.
 * Runs sequentially.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class Newton {
	/**
	 * Starts the program and creates a fractal based on roots which
	 * are given.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner sc = new Scanner(System.in);
		String line = null;
		Complex[] roots = null;
		List<Complex> list = new LinkedList<>();
		int num = 1;

		do {
			try {
				System.out.print("Root " + num + "> ");
				num++;

				line = sc.nextLine();
				line = line.replace(" ", "");
				Complex comp = parse(line);

				list.add(comp);
			} catch (IllegalArgumentException e) {
				if (!line.equals("done"))
					System.out.println("Invalid argument. Try again.");
				num--;
			}
		} while (!line.equals("done"));

		roots = list.toArray(new Complex[0]);
		System.out.println("Image of fractal will appear shortly. Thank you.");

		FractalViewer.show(new MojProducer(roots));
		sc.close();
	}

	/**
	 * Parses given line and creates a complex number.
	 * 
	 * @param line to be parsed
	 * @return a created complex number
	 * @throws IllegalArgumentException if line
	 * 		is in an invalid format
	 */
	private static Complex parse(String line) {
		if (line.length() == 0)
			throw new IllegalArgumentException();

		double real = 0;
		double imag = 0;

		if (line.contains("i")) {
			if (line.lastIndexOf("i") != line.indexOf("i"))
				throw new IllegalArgumentException();

			imag = 1;
			if (line.length() == 1) {
				// imag and real already set, next case is for line.length() > 1
			} else if (line.substring(0, 2).equals("-i") || line.charAt(0) == 'i'
					|| line.substring(0, 2).equals("+i")) {
				line = line.replace("i", "");
				if (line.equals("-")) {
					imag = -1;
				} else if (line.length() > 0) {
					try {
						imag = Double.parseDouble(line);
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException();
					}
				}
			} else {
				String[] parts = line.split("i");
				if (parts.length == 1) {
					String old = parts[0];
					parts = new String[2];
					parts[0] = old;
					parts[1] = "1";
				}

				String sign = String.valueOf(parts[0].charAt(parts[0].length() - 1));
				parts[1] = sign + parts[1];
				parts[0] = parts[0].substring(0, parts[0].length() - 1);

				try {
					real = Double.parseDouble(parts[0]);
					imag = Double.parseDouble(parts[1]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				}
			}
		} else {
			try {
				real = Double.parseDouble(line);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		}

		return new Complex(real, imag);
	}

	/**
	 * Produces data for coloring pixels based on given roots.
	 * 
	 * @author Florijan Rusac
	 * @version 1.0
	 */
	public static class MojProducer implements IFractalProducer {
		/**
		 * Roots of a complex rooted polynomial.
		 */
		Complex[] roots;

		/**
		 * @param roots sets this.roots
		 */
		public MojProducer(Complex[] roots) {
			this.roots = roots;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			int m = 16 * 16 * 16;
			int offset = 0;
			short[] data = new short[width * height];
			ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(Complex.ONE, roots);
			ComplexPolynomial derived = polynomial.toComplexPolynom().derive();
			for (int y = 0; y < height; y++) {
				if (cancel.get())
					break;
				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					double module;
					int iters = 0;
					Complex znold;
					Complex numerator;
					Complex denominator;
					Complex fraction;
					do {
						numerator = polynomial.apply(zn);
						denominator = derived.apply(zn);
						znold = new Complex(zn.getRe(), zn.getIm());
						fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while (iters < m && module > 0.001);
					data[offset] = (short) (polynomial.indexOfClosestRootFor(zn, 0.002) + 1);
					offset++;
				}
			}
			observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
		}
	}

}
