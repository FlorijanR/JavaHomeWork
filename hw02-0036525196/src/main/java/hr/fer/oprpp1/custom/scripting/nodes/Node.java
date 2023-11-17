package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base class for Nodes. Supports adding child 
 * nodes, getting number of child nodes and 
 * getting child nodes by index.
 * 
 * Used to build a document model from parsed text.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class Node {
	/**
	 * Collection in which child nodes are stored.
	 */
	private ArrayIndexedCollection col;

	/**
	 * Adds passed node as a child to this one.
	 * 
	 * @param child to be added
	 */
	public void addChildNode(Node child) {
		if (col == null)
			col = new ArrayIndexedCollection();

		col.add(child);
	}

	/**
	 * @return number of children of this node
	 */
	public int numberOfChildren() {
		if (col == null)
			return 0;
		return col.size();
	}

	/**
	 * Returnes node at passed index.
	 * 
	 * @param index of node to return
	 * @return node at passed index
	 * @throws IndexOutOfBoundsException 
	 * 		if invalid index is given
	 */
	public Node getChild(int index) {
		if (col == null || index < 0 || index > col.size() - 1)
			throw new IndexOutOfBoundsException();
		
		return (Node) col.get(index);
	}

}
