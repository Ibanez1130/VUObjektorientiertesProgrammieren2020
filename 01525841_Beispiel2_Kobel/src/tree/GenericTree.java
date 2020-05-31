/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 02.05.2020
 */
package tree;

import tree.node.ITreeNode;
import util.searchable.*;
import java.util.Collection;
import container.Container;

public class GenericTree<TREETYPE> implements ITree<TREETYPE> {
	private ITreeNode<TREETYPE> root;

	public GenericTree () {
		this.root = null;
	}

	public GenericTree (ITreeNode<TREETYPE> root) {
		this.root = root;
	}
	
	public void setRoot (ITreeNode<TREETYPE> root) {
		this.root = root;
	}
	
	public ITreeNode<TREETYPE> getRoot () {
		return this.root;
	}
	
	public ITreeNode<TREETYPE> findNode (TREETYPE searchValue) {
		if (this.root != null) {
			return this.root.findNodeByValue(searchValue);
		} else {
			return null;
		}
	}
	
	public ITreeNode<TREETYPE> findNode (ITreeNode<TREETYPE> searchNode) {
		if (this.root != null) {
			return this.root.findNodeByNode(searchNode);
		} else {
			return null;
		}
	}
	
	public String generateConsoleView (String spacer) {
		if (this.root != null) {
			return "RootNode representation\n" + this.root.generateConsoleView(spacer, "");
		} else {
			return "RootNode representation\n" + spacer + "- No children available.";
		}
	}
	
	public Collection<ITreeNode<TREETYPE>> searchByFilter (ISearchFilter filter, Object compareObject) {
		if (this.root != null) {
			return this.root.searchByFilter(filter, compareObject);
		} else {
			return new Container<ITreeNode<TREETYPE>>();
		}
	}
	
	public ITree<TREETYPE> deepCopy () {
		if (this.root != null) {
			return new GenericTree<TREETYPE>(this.root.deepCopy());
		} else {
			return new GenericTree<TREETYPE>();
		}
	}
}