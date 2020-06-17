/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 03.05.2020
 */
package tree.node;

import java.util.Collection;
import rbvs.product.IProduct;
import rbvs.product.CompositeProduct;

public class ProductTreeNode extends GenericTreeNode<IProduct> {

	public ProductTreeNode (String label, IProduct value) {
		super(label, value);
		this.initialize(value);
	}
	
	public ProductTreeNode (IProduct value) {
		super((value != null) ? value.getName() : "", value);
		this.initialize(value);
	}
	
	private ProductTreeNode (CompositeProduct value) {
		super((value != null) ? value.getName() : "", value);
	}

	private void initialize (IProduct item) {
		if (item == null) return;
		if (item instanceof CompositeProduct) {
			Collection<ITreeNode<IProduct>> containerProducts = this.getChildren();
			for (IProduct p:((CompositeProduct) item).getProducts()) {
				containerProducts.add(new ProductTreeNode(p)); // No recursiveness needed here -> results in an error.
			}
		}
	}
	
	public ProductTreeNode deepCopy () {
		ProductTreeNode tn = new ProductTreeNode(super.getLabel(), (super.nodeValue() != null) ? (IProduct) super.nodeValue().deepCopy() : null);
		Collection<ITreeNode<IProduct>> containerProducts = tn.getChildren();
		for (ITreeNode<IProduct> c:this.getChildren()) {
			containerProducts.add(c.deepCopy());
		}
		return tn;
	}
}
