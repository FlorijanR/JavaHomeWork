package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	void testPreferredLayoutSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	void testPreferredLayoutSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	void testAddLayoutComponentComponentObject() {
		JPanel p = new JPanel(new CalcLayout());
		JLabel l = new JLabel("");

		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(0, 1)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(6, 1)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 0)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 8)));
	}

	@Test
	void testAddLayoutComponentComponentObjectInvalidPosition() {
		JPanel p = new JPanel(new CalcLayout());
		JLabel l = new JLabel("");

		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 3)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 4)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 5)));
	}

	@Test
	void testAddLayoutComponentComponentObjectSameLimitation() {
		JPanel p = new JPanel(new CalcLayout());
		JLabel l1 = new JLabel("");
		JLabel l2 = new JLabel("");

		p.add(l1, new RCPosition(1, 1));

		assertThrows(CalcLayoutException.class, () -> p.add(l2, new RCPosition(1, 1)));
	}

}
