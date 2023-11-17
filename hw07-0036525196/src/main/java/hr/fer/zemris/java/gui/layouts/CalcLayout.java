package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

public class CalcLayout implements LayoutManager2 {
	private int space;
	private Map<Component, RCPosition> components = new HashMap<>();

	public CalcLayout(int space) {
		super();
		this.space = space;
	}

	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if (components.containsKey(comp)) {
			components.remove(comp);
		}
	}

	private interface SizeGetter {
		Dimension getSize(Component comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return layoutSize(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layoutSize(parent, Component::getMinimumSize);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return layoutSize(target, Component::getMaximumSize);
	}

	private Dimension layoutSize(Container parent, SizeGetter getter) {
		Dimension dim = new Dimension(0, 0);

		for (var oneComp : components.keySet()) {
			Dimension cdim = getter.getSize(oneComp);
			if (cdim != null) {
				if (components.get(oneComp).getColumn() == 1 && components.get(oneComp).getRow() == 1) {
					dim.width = Math.max(dim.width, (int) Math.ceil(((cdim.width - 4 * space) / 5.)));
					dim.height = Math.max(dim.height, cdim.height);
				} else {
					dim.width = Math.max(dim.width, cdim.width);
					dim.height = Math.max(dim.height, cdim.height);
				}
			}
		}
		dim.width = 7 * dim.width + 6 * space;
		dim.height = 5 * dim.height + 4 * space;

		Insets parentInsets = parent.getInsets();
		dim.width += parentInsets.left + parentInsets.right;
		dim.height += parentInsets.top + parentInsets.bottom;

		return dim;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets parentInsets = parent.getInsets();
		int w = parent.getWidth();
		int h = parent.getHeight();
		w = Math.max(0, w - 6 * space - parentInsets.left - parentInsets.right);
		h = Math.max(0, h - 4 * space - parentInsets.top - parentInsets.bottom);

		Map<Integer, Integer> col = new HashMap<>();
		Map<Integer, Integer> row = new HashMap<>();

		for (int i = 1; i < 8; i++) {
			col.put(i, (int) Math.ceil(w / 7.));
		}
		for (int i = 1; i < 6; i++) {
			row.put(i, (int) Math.ceil(h / 5.));
		}

		int greaterW = (int) Math.ceil(w / 7.);
		int numTooBigC = greaterW - w;
		int greaterH = (int) Math.ceil(h / 5.);
		int numTooBigR = greaterH - h;

		if (numTooBigC == 1) {
			col.put(4, (int) (w / 7.));
		} else if (numTooBigC == 2) {
			col.put(2, (int) (w / 7.));
			col.put(6, (int) (w / 7.));
		} else if (numTooBigC == 3) {
			col.put(2, (int) (w / 7.));
			col.put(4, (int) (w / 7.));
			col.put(6, (int) (w / 7.));
		} else if (numTooBigC == 4) {
			col.put(1, (int) (w / 7.));
			col.put(3, (int) (w / 7.));
			col.put(5, (int) (w / 7.));
			col.put(7, (int) (w / 7.));
		} else if (numTooBigC == 5) {
			col.put(1, (int) (w / 7.));
			col.put(3, (int) (w / 7.));
			col.put(4, (int) (w / 7.));
			col.put(5, (int) (w / 7.));
			col.put(7, (int) (w / 7.));
		} else if (numTooBigC == 6) {
			col.put(1, (int) (w / 7.));
			col.put(2, (int) (w / 7.));
			col.put(3, (int) (w / 7.));
			col.put(5, (int) (w / 7.));
			col.put(6, (int) (w / 7.));
			col.put(7, (int) (w / 7.));
		}

		if (numTooBigR == 1) {
			row.put(3, (int) (h / 5.));
		} else if (numTooBigR == 2) {
			row.put(2, (int) (h / 5.));
			row.put(4, (int) (h / 5.));
		} else if (numTooBigR == 3) {
			row.put(1, (int) (h / 5.));
			row.put(3, (int) (h / 5.));
			row.put(5, (int) (h / 5.));
		} else if (numTooBigR == 4) {
			row.put(1, (int) (h / 5.));
			row.put(2, (int) (h / 5.));
			row.put(4, (int) (h / 5.));
			row.put(5, (int) (h / 5.));
		}

		for (var oneComp : components.keySet()) {
			if (components.get(oneComp).getColumn() == 1 && components.get(oneComp).getRow() == 1) {
				int cwidth = 4 * space;
				for (int i = 1; i < 6; i++) {
					cwidth += col.get(i);
				}
				oneComp.setBounds(parentInsets.left, parentInsets.top, cwidth, row.get(1));
			} else {
				int left = parentInsets.left + space * (components.get(oneComp).getColumn() - 1);
				int top = parentInsets.top + space * (components.get(oneComp).getRow() - 1);

				for (int i = 1; i < components.get(oneComp).getColumn(); i++) {
					left += col.get(i);
				}
				for (int i = 1; i < components.get(oneComp).getRow(); i++) {
					top += row.get(i);
				}

				oneComp.setBounds(left, top, col.get(components.get(oneComp).getColumn()),
						row.get(components.get(oneComp).getRow()));
			}

		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null) {
			throw new NullPointerException();
		}

		if (constraints instanceof String || constraints instanceof RCPosition) {
			RCPosition p;
			if (constraints instanceof String) {
				try {
					p = RCPosition.parse((String) constraints);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				}
			} else {
				p = (RCPosition) constraints;
			}

			if (p.getRow() < 1 || p.getRow() > 5 || p.getColumn() < 1 || p.getColumn() > 7) {
				throw new CalcLayoutException();
			}
			if (p.getRow() == 1 && (p.getColumn() > 1 && p.getColumn() < 6)) {
				throw new CalcLayoutException();
			}
			for (var pos : components.values()) {
				if (pos.getRow() == p.getRow() && pos.getColumn() == p.getColumn()) {
					throw new CalcLayoutException();
				}
			}
			for (var oneComp : components.keySet()) {
				if (oneComp.equals(comp)) {
					throw new CalcLayoutException();
				}
			}
			
			components.put(comp, p);
		} else {
			throw new IllegalArgumentException();
		}

	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub

	}

}
