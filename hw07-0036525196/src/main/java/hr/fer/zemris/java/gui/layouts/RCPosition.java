package hr.fer.zemris.java.gui.layouts;

public class RCPosition {
	private int row;
	private int column;
	
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public static RCPosition parse(String text) {
		if (text != null) {
			String[] nums = text.split(",");
			int r = Integer.parseInt(nums[0].trim());
			int c = Integer.parseInt(nums[1].trim());

			return new RCPosition(r, c);
		}
		return null;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

}
