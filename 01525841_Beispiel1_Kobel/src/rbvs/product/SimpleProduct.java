/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 13.03.2020
 */
package rbvs.product;

public class SimpleProduct extends Product {
	
	public SimpleProduct(String name, float price) {
		super(name, price);
	}

	@Override
	public SimpleProduct deepCopy() {
		return new SimpleProduct(this.getName(), this.getPrice());
	}

}
