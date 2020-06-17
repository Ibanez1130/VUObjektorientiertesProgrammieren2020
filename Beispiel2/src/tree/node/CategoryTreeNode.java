package tree.node;

import java.util.Collection;

public class CategoryTreeNode<NODETYPE, CATEGORY> extends GenericTreeNode<NODETYPE> {
	private CATEGORY category;

	public CategoryTreeNode (CATEGORY category) {
		super((category != null) ? category.toString() : "", null);
		this.category = category;
	}

	public CATEGORY getCategory () {
		return this.category;
	}
	
	public NODETYPE nodeValue () {
		return null;
	}
	
	public String getLabel () {
		// Since in the constructor, we set the label equal to category.toString(), we can return the label as is, in this method.
		return super.getLabel();
	}
	
	public CategoryTreeNode<NODETYPE, CATEGORY> deepCopy () {
		CategoryTreeNode<NODETYPE, CATEGORY> tn = new CategoryTreeNode<NODETYPE, CATEGORY>(this.getCategory());
		Collection<ITreeNode<NODETYPE>> containerElements = tn.getChildren();
		for (ITreeNode<NODETYPE> c:this.getChildren()) {
			containerElements.add(c.deepCopy());
		}
		return tn;
	}
}
