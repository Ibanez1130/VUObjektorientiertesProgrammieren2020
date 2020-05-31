/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 13.03.2020
 */
package rbvs;

import rbvs.product.IProduct;

public class DuplicateProductException extends Exception {
	private static final long serialVersionUID = 1L;
	private IProduct product;

	public DuplicateProductException(IProduct product) {
		this.product = product;
	}

	public String getMessage() {
		return "Product " + this.product.getName() + " already exists!";
	}
}
