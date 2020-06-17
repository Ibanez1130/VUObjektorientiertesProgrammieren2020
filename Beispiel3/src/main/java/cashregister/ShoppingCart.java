/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 30.05.2020
 */
package cashregister;

import java.util.Collection;
import java.util.Vector;
import java.util.stream.Collectors;

import rbvs.product.IShoppingCartElement;

public class ShoppingCart implements IShoppingCart {
	private Long id;
	private Collection<IShoppingCartElement> shoppingCartElements;

	public ShoppingCart (long id) {
		this.id = id;
		this.shoppingCartElements = new Vector<IShoppingCartElement>();
	}
	
	public Long getShoppingCartID () {
		return this.id;
	}

	public Collection<IShoppingCartElement> currentElements () {
		return this.shoppingCartElements;
	}
	
	public void addElement (IShoppingCartElement shoppingCartElement) {
		if (shoppingCartElement == null) return; 
		this.shoppingCartElements.add(shoppingCartElement);
	}
	
	public int getNumberOfElements () {
		return this.shoppingCartElements.size();
	}
	
	public float getTotalPriceOfElements () {
		float sum = 0.0f;
		for (IShoppingCartElement e:this.shoppingCartElements) {
			sum += e.getPrice();
		}
		return sum;
	}
	
	public boolean removeElement (IShoppingCartElement element) {
		if (element == null) return false;
		if (this.shoppingCartElements.contains(element)) {
			return this.shoppingCartElements.remove(element);
		}
		return false;
	}
	
	public String toString () {
		String elements = this.shoppingCartElements
				.stream()
				.map(e -> e.getName())
				.collect(Collectors.joining(", "));
		return "ShoppingCart [ id = " + this.id + ", shoppingCartElements = [ " + elements + " ] ]";
	}
}
