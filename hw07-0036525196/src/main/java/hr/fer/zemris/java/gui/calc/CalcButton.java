package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

public class CalcButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalcButton(String text, CalcModel model, CalcButtonTask task) {
		setup(text);

		addActionListener(e -> {
			task.doButtonTask(model, text);
		});
	}

	public CalcButton(String text, CalcModel model, CalcButtonTaskModel task) {
		setup(text);

		addActionListener(e -> {
			task.doButtonTask(model);
		});
	}

	private void setup(String text) {
		setOpaque(true);
		setBackground(Color.cyan);
		setText(text);
		setFont(getFont().deriveFont(18f));
	}

}
