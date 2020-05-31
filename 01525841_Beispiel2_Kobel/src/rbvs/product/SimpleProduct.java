/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 01.05.2020
 */
package rbvs.product;

public class SimpleProduct extends Product {

	public SimpleProduct(String name) {
		super(name);
	}
	
	public SimpleProduct(String name, float price) {
		super(name, price);
	}
	
	public void getInfo () {
		System.out.println("SIMPLE PRODUCT!");
	}
	
	public void test () {
		System.out.println("SIMPLE PRODUCT!");
	}

	@Override
	public SimpleProduct deepCopy() {
		return new SimpleProduct(this.getName(), this.getPrice());
	}

}
