package rbvs.product;

import java.util.Collection;
import java.util.Vector;
import java.util.stream.Collectors;

public class CompositeProduct extends Product {
	private Collection<Product> containedProducts;
	private float discount;
	
	public CompositeProduct(String name, float discountPercentage) {
		super(name);
		this.discount = (discountPercentage < 0) ? 0 : (discountPercentage > 100) ? 100 : discountPercentage;
		this.containedProducts = new Vector<Product>();
	}

	public CompositeProduct(String name, float discountPercentage, Collection<Product> products) {
		super(name);
		this.discount = (discountPercentage < 0) ? 0 : (discountPercentage > 100) ? 100 : discountPercentage;
		this.containedProducts = new Vector<Product>();
		if (products != null) {
			products
				.stream()
				.forEach(p -> this.containedProducts.add(p.deepCopy()));
		}
	}

	public void addProduct(Product product) {
		this.containedProducts.add(product.deepCopy());
	}
	
	public boolean removeProduct(Product product) {
		return this.containedProducts.remove(product);
	}

	public Collection<Product> getProducts() {
		return this.containedProducts;
	}
	
	public float getPrice() {
		float sum = 0.0f;
		for (Product p:this.containedProducts) {
			sum += p.getPrice();
		}
		return (sum * (100 - this.discount) / 100);
	}
	
	public String toString() {
		String products = this.containedProducts
				.stream()
				.map(p -> p.toString())
				.collect(Collectors.joining(", "));
		return "CompositeProduct [ name = " + super.getName() + ", price = " + this.getPrice() + ", discount = " + this.discount + ", contained Products = [ "+ products + " ] ]";
	}
	
	@Override
	public CompositeProduct deepCopy() {
		return new CompositeProduct(this.getName(), this.discount, this.containedProducts);
	}
}
