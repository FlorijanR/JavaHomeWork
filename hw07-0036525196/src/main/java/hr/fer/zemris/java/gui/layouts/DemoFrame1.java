package hr.fer.zemris.java.gui.layouts;

import java.awt.Container;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class DemoFrame1 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//setSize(500, 500);
		initGUI();
		pack();
	}

	private void initGUI() {
		Container cp = getContentPane();

		
		cp.setLayout(new CalcLayout(3));
		
//		cp.add(l("x"), new RCPosition(1,1));
//		cp.add(l("y"), new RCPosition(2,3));
//		cp.add(l("z"), new RCPosition(2,7));
//		cp.add(l("w"), new RCPosition(4,2));
//		cp.add(l("a"), new RCPosition(4,5));
//		cp.add(l("b"), new RCPosition(4,7));
		
//		cp.add(l("x"), "1,1");
//		cp.add(l("y"), "2,3");
//		cp.add(l("z"), "2,7");
//		cp.add(l("w"), "4,2");
//		cp.add(l("a"), "4,5");
//		cp.add(l("b"), "4,7");
		
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
	}

	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame1().setVisible(true);
		});
	}
}