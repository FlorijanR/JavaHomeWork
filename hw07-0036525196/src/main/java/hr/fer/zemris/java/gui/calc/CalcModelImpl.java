package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel {
	private boolean editable = true;
	private boolean positive = true;
	private String stringNum = "";
	private double doubleNum;
	private String frozen;
	private Double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> list = new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (l == null)
			throw new NullPointerException();

		list.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (l == null)
			throw new NullPointerException();

		list.remove(l);
	}

	@Override
	public String toString() {
		if (hasFrozenValue()) {
			return frozen;
		} else if (stringNum.equals("")) {
			if (positive) {
				return "0";
			} else {
				return "-0";
			}
		} else {
			if (positive) {
				return stringNum;
			} else {
				return "-" + stringNum;
			}
		}
	}

	@Override
	public double getValue() {
		if (!positive) {
			return -1. * doubleNum;
		} else {
			return doubleNum;
		}
	}

	@Override
	public void setValue(double value) {
		if (value < 0) {
			positive = false;
		}
		doubleNum = Math.abs(value);

		if (Double.isNaN(value)) {
			stringNum = "NaN";
		} else if (Double.isInfinite(value)) {
			stringNum = "Infinity";
		} else {
			stringNum = String.valueOf(Math.abs(value));
		}

		notification();

		editable = false;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		doubleNum = 0;
		stringNum = "";
		editable = true;

		notification();
	}

	@Override
	public void clearAll() {
		clearActiveOperand();
		pendingOperation = null;
		clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable())
			throw new CalculatorInputException();

		frozen = null;

		if (positive) {
			positive = false;
		} else {
			positive = true;
		}

		notification();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable() || stringNum.contains(".") || stringNum.equals(""))
			throw new CalculatorInputException();

		stringNum += ".";
		frozen = null;

		notification();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable())
			throw new CalculatorInputException();

		if (digit < 0 || digit > 9)
			throw new IllegalArgumentException();

		if (!(stringNum.equals("0") && digit == 0)) {
			if (stringNum.equals("0")) {
				stringNum = String.valueOf(digit);
			} else {
				stringNum += digit;
			}

			doubleNum = Double.parseDouble(stringNum);

			if (Double.isInfinite(doubleNum)) {
				stringNum = stringNum.substring(0, stringNum.length() - 1);
				throw new CalculatorInputException();
			}

			notification();

			frozen = null;
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		if (activeOperand == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet())
			throw new IllegalStateException();

		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;

	}

	@Override
	public void freezeValue(String value) {
		frozen = value;

		notification();
	}

	@Override
	public boolean hasFrozenValue() {
		if (frozen == null) {
			return false;
		} else {
			return true;
		}
	}

	private void notification() {
		for (var l : list) {
			l.valueChanged(this);
		}
	}

}
