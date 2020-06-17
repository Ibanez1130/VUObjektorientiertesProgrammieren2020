package tree;

import rbvs.product.IProduct;
import tree.node.ITreeNode;
import tree.node.ProductTreeNode;

public class ProductTree extends GenericTree<IProduct> {

	public ProductTree () {
		super();
	}
	
	public ProductTree (IProduct product) {
		super(new ProductTreeNode(product));
	}
	
	public ProductTree (ITreeNode<IProduct> root) {
		super(root);
	}
}
