package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

public class CalcCheckBox extends JCheckBox {
	List<UnaryCalcButton> list = new ArrayList<>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CalcCheckBox(String text) {
		setOpaque(true);
		setBackground(Color.cyan);
		setText(text);
		setFont(getFont().deriveFont(30f));

		addActionListener(e -> {
			for (var button : list) {
				button.invert();
			}
		});
	}
	
	public void addListener(UnaryCalcButton button) {
		list.add(button);
	}

}
