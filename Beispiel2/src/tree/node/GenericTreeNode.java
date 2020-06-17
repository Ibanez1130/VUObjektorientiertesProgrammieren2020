/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 03.05.2020
 */
package tree.node;

import java.util.Collection;
import java.util.stream.Collectors;
import container.Container;
import util.searchable.ISearchFilter;

public class GenericTreeNode<NODETYPE> implements ITreeNode<NODETYPE> {
	private NODETYPE nodeValue;
	private String label;
	private Container<ITreeNode<NODETYPE>> children;

	public GenericTreeNode(String label, NODETYPE value) {
		this.label = label;
		this.nodeValue = value;
		this.children = new Container<ITreeNode<NODETYPE>>();
	}

	public Collection<ITreeNode<NODETYPE>> getChildren () {
		return this.children;
	}
	
	public boolean isLeaf () {
		return this.children.size() == 0;
	}
	
	public NODETYPE nodeValue () {
		return this.nodeValue;
	}
	
	public String getLabel () {
		return this.label;
	}
	
	public ITreeNode<NODETYPE> findNodeByValue (NODETYPE searchValue) {
		if (searchValue == null) return null;
		if (this.nodeValue == null) return null;
		if (this.nodeValue.equals(searchValue)) return this;
		for (ITreeNode<NODETYPE> tn:this.children) {
			if (tn.findNodeByValue(searchValue) != null) return tn.findNodeByValue(searchValue);
		}
		return null;
	}
	
	public ITreeNode<NODETYPE> findNodeByNode (ITreeNode<NODETYPE> searchNode) {
		if (searchNode == null) return null;
		if (this.equals(searchNode)) return this;
		for (ITreeNode<NODETYPE> tn:this.children) {
			if (tn.findNodeByNode(searchNode) != null) return tn.findNodeByNode(searchNode);
		}
		return null;
	}
	
	public boolean checkNodeByValue (NODETYPE value) {
		if (value == null) return false;
		return this.nodeValue.equals(value);
	}
	
	public String generateConsoleView (String spacer, String preamble) {
		String s = spacer + preamble + ((this.isLeaf()) ? "- " : "+ " ) + this.toString() + "\n";
		if (!this.isLeaf()) {
			for (ITreeNode<NODETYPE> tn:this.children) {
				s += tn.generateConsoleView(spacer + spacer, preamble);
			}
		}
		return s;
	}
	
	@Override
	public String toString () {
		String children = this.children
				.stream()
				.map(c -> c.toString())
				.collect(Collectors.joining(", "));
		return "GenericTreeNode [ nodeValue = " + this.nodeValue + ", label = " + this.getLabel() + ", children = [ " + children + " ] ]";
	}
	
	public GenericTreeNode<NODETYPE> deepCopy () {
		GenericTreeNode<NODETYPE> gtn = new GenericTreeNode<NODETYPE>(this.getLabel(), this.nodeValue);
		// Since there is currently no method to set the children of a GenericTreeNode, we have to do this via the refernces.
		Collection<ITreeNode<NODETYPE>> children = gtn.getChildren();
		for (ITreeNode<NODETYPE> tn:this.children) {
			children.add(tn.deepCopy());
		}
		return gtn;
	}

	public Collection<ITreeNode<NODETYPE>> searchByFilter (ISearchFilter filter, Object compareObject) {
		if (filter == null || compareObject == null) return null;
		if (!(compareObject instanceof ITreeNode<?>)) return null;
		Container<ITreeNode<NODETYPE>> hits = new Container<ITreeNode<NODETYPE>>();
		if (filter.searchFilterFunction(this, compareObject)) hits.add(this);
		if (this.children == null) return hits;
		for (ITreeNode<NODETYPE> tn:this.children) {
			hits.addAll(((GenericTreeNode<NODETYPE>) tn).searchByFilter(filter, compareObject));
		}
		hits
			.stream()
			.filter(h -> h != null);
		return hits;
	}
}
