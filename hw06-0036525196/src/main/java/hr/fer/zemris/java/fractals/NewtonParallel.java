package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Used as a Newton-Raphson iteration-based fractal viewer.
 * Runs with multiple threads.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class NewtonParallel {
	/**
	 * Calculates a part of data used to color a fractal.
	 * 
	 * @author Florijan Rusac
	 * @version 1.0
	 */
	public static class PosaoIzracuna implements Runnable {
		/**
		 * Polynomial for which data is calculated.
		 */
		ComplexRootedPolynomial polynomial;
		/**
		 * Minimum of real axis.
		 */
		double reMin;
		/**
		 * Maximum of real axis.
		 */
		double reMax;
		/**
		 * Minimum of imaginary axis.
		 */
		double imMin;
		/**
		 * Maximum of imaginary axis.
		 */
		double imMax;
		/**
		 * Screen width.
		 */
		int width;
		/**
		 * Screen height.
		 */
		int height;
		/**
		 * Minimum of imaginary axis for this job.
		 */
		int yMin;
		/**
		 * Maximum of imaginary axis for this job.
		 */
		int yMax;
		/**
		 * Maximum number of iterations.
		 */
		int m;
		/**
		 * Data for creating a fractal.
		 */
		short[] data;
		/**
		 * If true stops calculating a fractal when size of viewer changes.
		 */
		AtomicBoolean cancel;
		/**
		 * Used for poisoning threads.
		 */
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

		/**
		 * Simple constructor.
		 */
		private PosaoIzracuna() {
		}

		/**
		 * Sets all member variables.
		 * 
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param width
		 * @param height
		 * @param yMin
		 * @param yMax
		 * @param m
		 * @param data
		 * @param cancel
		 * @param polynomial
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial polynomial) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.polynomial = polynomial;
		}

		@Override
		public void run() {
			ComplexPolynomial derived = polynomial.toComplexPolynom().derive();
			int offset = width * yMin;
			for (int y = yMin; y <= yMax; y++) {
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
		}
	}

	/**
	 * Starts the program and creates a fractal based on roots which
	 * are given.
	 * 
	 * @param args used for setting the number of threads and jobs
	 */
	public static void main(String[] args) {
		int workerNumber = Runtime.getRuntime().availableProcessors();
		int trackNumber = 4 * workerNumber;

		if (args.length == 1) {
			if (args[0].contains("--workers=")) {
				args[0] = args[0].substring(10);
				try {
					workerNumber = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					System.out.println("Invalid argument passed to program.");
				}
			} else if (args[0].contains("-w ")) {
				args[0] = args[0].substring(3);
				try {
					workerNumber = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					System.out.println("Invalid argument passed to program.");
				}
			} else if (args[0].contains("--tracks=")) {
				args[0] = args[0].substring(9);
				try {
					int t = Integer.parseInt(args[0]);
					if (t > 0) {
						trackNumber = t;
					} else {
						System.out.println("Track number must be greater than 0.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid argument passed to program.");
				}
			} else if (args[0].contains("-t ")) {
				args[0] = args[0].substring(3);
				try {
					int t = Integer.parseInt(args[0]);
					if (t > 0) {
						trackNumber = t;
					} else {
						System.out.println("Track number must be greater than 0.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid argument passed to program.");
				}
			} else {
				System.out.println("Invalid argument passed to program.");
			}
		} else if (args.length == 2) {
			boolean tracksSet = false;
			boolean workersSet = false;

			for (int i = 0; i < 2; i++) {
				if (args[i].contains("--workers=")) {
					args[i] = args[i].substring(10);
					try {
						if (workersSet == true) {
							workerNumber = Runtime.getRuntime().availableProcessors();
							throw new IllegalArgumentException();
						}
						workersSet = true;
						workerNumber = Integer.parseInt(args[i]);
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid argument passed to program.");
					}
				} else if (args[i].contains("-w ")) {
					args[i] = args[i].substring(3);
					try {
						if (workersSet == true) {
							workerNumber = Runtime.getRuntime().availableProcessors();
							throw new IllegalArgumentException();
						}
						workersSet = true;
						workerNumber = Integer.parseInt(args[i]);
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid argument passed to program.");
					}
				} else if (args[i].contains("--tracks=")) {
					args[i] = args[i].substring(9);
					try {
						if (tracksSet == true) {
							trackNumber = 4 * workerNumber;
							throw new IllegalArgumentException();
						}
						tracksSet = true;
						int t = Integer.parseInt(args[i]);
						if (t > 0) {
							trackNumber = t;
						} else {
							System.out.println("Track number must be greater than 0.");
						}
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid argument passed to program.");
					}
				} else if (args[i].contains("-t ")) {
					args[i] = args[i].substring(3);
					try {
						if (tracksSet == true) {
							trackNumber = 4 * workerNumber;
							throw new IllegalArgumentException();
						}
						tracksSet = true;
						int t = Integer.parseInt(args[i]);
						if (t > 0) {
							trackNumber = t;
						} else {
							System.out.println("Track number must be greater than 0.");
						}
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid argument passed to program.");
					}
				} else {
					System.out.println("Invalid argument passed to program.");
				}
			}
		} else {
			System.out.println("Invalid arguments passed to program.");
		}

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

		FractalViewer.show(new MojProducer(roots, trackNumber, workerNumber));
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
		 * Number of jobs.
		 */
		int trackNumber;
		/**
		 * Number of threads.
		 */
		int workerNumber;

		/**
		 * Constructors that sets member variables.
		 * 
		 * @param roots of a complex rooted polynomial
		 * @param trackNumber number of jobs
		 * @param workerNumber number of threads
		 */
		public MojProducer(Complex[] roots, int trackNumber, int workerNumber) {
			this.roots = roots;
			this.trackNumber = trackNumber;
			this.workerNumber = workerNumber;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			if (trackNumber > height) {
				trackNumber = height;
			}

			System.out.println(
					"Effective number of threads is " + workerNumber + ", number of tracks is " + trackNumber + ".");

			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			int yByTrack = height / trackNumber;
			ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(Complex.ONE, roots);

			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] workers = new Thread[workerNumber];
			for (int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if (p == PosaoIzracuna.NO_JOB)
									break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}

			for (int i = 0; i < workers.length; i++) {
				workers[i].start();
			}

			for (int i = 0; i < trackNumber; i++) {
				int yMin = i * yByTrack;
				int yMax = (i + 1) * yByTrack - 1;
				if (i == trackNumber - 1) {
					yMax = height - 1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
						cancel, polynomial);
				while (true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for (int i = 0; i < workers.length; i++) {
				while (true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			for (int i = 0; i < workers.length; i++) {
				while (true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
		}
	}

}
