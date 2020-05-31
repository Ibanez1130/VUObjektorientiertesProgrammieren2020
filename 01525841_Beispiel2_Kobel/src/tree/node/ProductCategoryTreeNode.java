/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 03.05.2020
 */
package tree.node;

import util.ProductCategory;

public class ProductCategoryTreeNode<NODETYPE> extends CategoryTreeNode<NODETYPE, ProductCategory> {

	public ProductCategoryTreeNode(ProductCategory category) {
		super(category);
	}

	public String getLabel () {
		return super.getLabel();
	}
}
