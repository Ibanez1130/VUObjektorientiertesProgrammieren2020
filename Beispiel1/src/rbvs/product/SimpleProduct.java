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
