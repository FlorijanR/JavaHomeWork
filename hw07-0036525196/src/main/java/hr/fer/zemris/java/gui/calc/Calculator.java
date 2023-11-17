package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

public class Calculator extends JFrame {
	private static final long serialVersionUID = 1L;
	private CalcModelImpl model = new CalcModelImpl();
	private Container cp = getContentPane();
	private Stack<Double> stack = new Stack<>();

	public Calculator() {
		setTitle("");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(600, 600);
		initGUI();
		// pack();
	}

	private void initGUI() {
		cp.setLayout(new CalcLayout(5));

		CalcButtonTask digitButtons = (model, text) -> {
			model.insertDigit(Integer.parseInt(text));
			model.freezeValue(null);
		};
		CalcButton b0 = new CalcButton("0", model, digitButtons);
		cp.add(b0, "5,3");
		CalcButton b1 = new CalcButton("1", model, digitButtons);
		cp.add(b1, "4,3");
		CalcButton b2 = new CalcButton("2", model, digitButtons);
		cp.add(b2, "4,4");
		CalcButton b3 = new CalcButton("3", model, digitButtons);
		cp.add(b3, "4,5");
		CalcButton b4 = new CalcButton("4", model, digitButtons);
		cp.add(b4, "3,3");
		CalcButton b5 = new CalcButton("5", model, digitButtons);
		cp.add(b5, "3,4");
		CalcButton b6 = new CalcButton("6", model, digitButtons);
		cp.add(b6, "3,5");
		CalcButton b7 = new CalcButton("7", model, digitButtons);
		cp.add(b7, "2,3");
		CalcButton b8 = new CalcButton("8", model, digitButtons);
		cp.add(b8, "2,4");
		CalcButton b9 = new CalcButton("9", model, digitButtons);
		cp.add(b9, "2,5");

		JLabel screen = new JLabel("", SwingConstants.RIGHT);
		screen.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		screen.setOpaque(true);
		screen.setBackground(Color.yellow);
		screen.setFont(screen.getFont().deriveFont(30f));
		cp.add(screen, "1,1");

		model.addCalcValueListener(m -> {
			screen.setText(m.toString());
		});

		CalcButton posNeg = new CalcButton("+/-", model, (model) -> model.swapSign());
		cp.add(posNeg, "5,4");

		CalcButton dot = new CalcButton(".", model, (model) -> model.insertDecimalPoint());
		cp.add(dot, "5,5");

		CalcButton div = new CalcButton("/", model, (model) -> {
			checkFrozen();
			updateResult();
			model.setPendingBinaryOperation((x, y) -> x / y);
			model.clear();
		});
		cp.add(div, "2,6");
		CalcButton mul = new CalcButton("*", model, (model) -> {
			checkFrozen();
			updateResult();
			model.setPendingBinaryOperation((x, y) -> x * y);
			model.clear();
		});
		cp.add(mul, "3,6");
		CalcButton sub = new CalcButton("-", model, (model) -> {
			checkFrozen();
			updateResult();
			model.setPendingBinaryOperation((x, y) -> x - y);
			model.clear();
		});
		cp.add(sub, "4,6");
		CalcButton sum = new CalcButton("+", model, (model) -> {
			checkFrozen();
			updateResult();
			model.setPendingBinaryOperation((x, y) -> x + y);
			model.clear();
		});
		cp.add(sum, "5,6");

		CalcButton calc = new CalcButton("=", model, (model) -> {
			double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());

			// model.setValue(result);
			// model.clearActiveOperand();
			// model.setPendingBinaryOperation(null);

			// model.freezeValue(String.valueOf(result));

			model.clearAll();
			model.setValue(result);
		});
		cp.add(calc, "1,6");

		CalcButton clr = new CalcButton("clr", model, (model) -> {
			model.clear();
		});
		cp.add(clr, "1,7");

		CalcButton res = new CalcButton("res", model, (model) -> {
			model.clearAll();
			model.freezeValue(null);
			if (model.toString().contains("-"))
				model.swapSign();
		});
		cp.add(res, "2,7");

		CalcCheckBox inv = new CalcCheckBox("Inv");
		cp.add(inv, "5,7");

		UnaryCalcButton ub31 = new UnaryCalcButton("log", "10^x", model, m -> m.setValue(Math.log10(m.getValue())),
				m -> m.setValue(Math.pow(10, m.getValue())));
		cp.add(ub31, "3,1");
		inv.addListener(ub31);
		UnaryCalcButton ub41 = new UnaryCalcButton("ln", "e^x", model, m -> m.setValue(Math.log(m.getValue())),
				m -> m.setValue(Math.pow(Math.E, m.getValue())));
		cp.add(ub41, "4,1");
		inv.addListener(ub41);
		UnaryCalcButton ub51 = new UnaryCalcButton("x^n", "x^(1/n)", model,
				m -> m.setValue(Math.pow(m.getValue(), m.getActiveOperand())),
				m -> m.setValue(Math.pow(m.getValue(), m.getActiveOperand())));
		cp.add(ub51, "5,1");
		inv.addListener(ub51);
		UnaryCalcButton ub22 = new UnaryCalcButton("sin", "arcsin", model, m -> m.setValue(Math.sin(m.getValue())),
				m -> m.setValue(Math.asin(m.getValue())));
		cp.add(ub22, "2,2");
		inv.addListener(ub22);
		UnaryCalcButton ub32 = new UnaryCalcButton("cos", "arccos", model, m -> m.setValue(Math.cos(m.getValue())),
				m -> m.setValue(Math.acos(m.getValue())));
		cp.add(ub32, "3,2");
		inv.addListener(ub32);
		UnaryCalcButton ub42 = new UnaryCalcButton("tan", "arctan", model, m -> m.setValue(Math.tan(m.getValue())),
				m -> m.setValue(Math.atan(m.getValue())));
		cp.add(ub42, "4,2");
		inv.addListener(ub42);
		UnaryCalcButton ub52 = new UnaryCalcButton("ctg", "arcctg", model,
				m -> m.setValue(Math.cos(m.getValue()) / Math.sin(m.getValue())),
				m -> m.setValue(Math.PI / 2 - Math.atan(m.getValue())));
		cp.add(ub52, "5,2");
		inv.addListener(ub52);
		CalcButton ub21 = new CalcButton("1/x", model, (m) -> {
			checkFrozen();
			m.setValue(1. / m.getValue());
		});
		cp.add(ub21, "2,1");
		
		CalcButton push = new CalcButton("push", model, (m) -> {
			stack.push(m.getValue());
		});
		cp.add(push, "3,7");
		CalcButton pop = new CalcButton("pop", model, (m) -> {
			if (!stack.empty()) {
				m.setValue(stack.pop());
			}
		});
		cp.add(pop, "4,7");
	}

	private void updateResult() {
		double result;
		if (model.getPendingBinaryOperation() != null) {
			result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
		} else {
			result = model.getValue();
		}
		model.freezeValue(String.valueOf(result));
		model.setActiveOperand(result);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

	public void checkFrozen() {
		if (model.hasFrozenValue())
			throw new CalculatorInputException();
	}

}
