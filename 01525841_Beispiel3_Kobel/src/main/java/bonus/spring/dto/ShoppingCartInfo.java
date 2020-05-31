package bonus.spring.dto;

import java.util.ArrayList;
import java.util.Collection;

import rbvs.product.IShoppingCartElement;

public class ShoppingCartInfo {
	
	private Collection<IShoppingCartElement> shoppingCartElements = new ArrayList<>();

	public Collection<IShoppingCartElement> getShoppingCartElements() {
		return shoppingCartElements;
	}

	public void setShoppingCartElements(Collection<IShoppingCartElement> shoppingCartElements) {
		this.shoppingCartElements = shoppingCartElements;
	}
	
	public void addProduct(IShoppingCartElement shoppingCartElement) {
		this.shoppingCartElements.add(shoppingCartElement);
	}
	
	// TODO: add additional attributes (if necessary)

}
