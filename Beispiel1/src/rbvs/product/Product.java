package rbvs.product;

public abstract class Product implements IProduct {
	private String name;
	private float price;

	public Product(String name) {
		this.initialize(name, 0.0f);
	}

	public Product(String name, float price) {
		this.initialize(name, price);
	}
	
	private void initialize (String name, float price) {
		this.name = (name != null) ? name : "";
		this.price = price;
	}
	
	public String getName () {
		return this.name;
	}
	
	public float getPrice () {
		return this.price;
	}
	
	public void setName (String new_name) {
		this.name = (new_name != null) ? new_name : "";
	}
	
	public void setPrice (float new_price) throws IllegalArgumentException {
		if (new_price < 0) {
			throw new IllegalArgumentException("The price of a product cannot be negative! new_price: " + new_price);
		}
		this.price = new_price;
	}
	
	@Override
	public String toString () {
		return "Product [ name = " + this.getName() + ", price = " + this.getPrice() + " ]";
	}
	
	public final boolean equals (Object obj) {
		if (!(obj instanceof Product)) return false;
		Product p = (Product) obj;
		return p.getName().equals(this.getName());
	}
	
	@Override
	public abstract Product deepCopy();
}
