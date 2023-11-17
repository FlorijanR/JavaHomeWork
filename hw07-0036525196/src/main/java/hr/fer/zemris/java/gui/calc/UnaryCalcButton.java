package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class UnaryCalcButton extends JButton {
	private boolean inverted = false;
	private String text1;
	private String text2;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnaryCalcButton(String text1, String text2, CalcModel model, CalcButtonTaskModel task1, CalcButtonTaskModel task2) {
		this.text1 = text1;
		this.text2 = text2;
		
		setOpaque(true);
		setBackground(Color.cyan);
		setText(text1);

		addActionListener(e -> {
			if (model.hasFrozenValue())
				throw new CalculatorInputException();
			
			if (!inverted) {
				task1.doButtonTask(model);
			} else {
				task2.doButtonTask(model);
			}
			repaint();
		});
	}

	public void invert() {
		if (inverted) {
			setText(text1);
			inverted = false;
		} else {
			setText(text2);
			inverted = true;
		}

	}

}
