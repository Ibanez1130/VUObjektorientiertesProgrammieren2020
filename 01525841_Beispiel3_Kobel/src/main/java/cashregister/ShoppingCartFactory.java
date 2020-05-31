/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 30.05.2020
 */
package cashregister;

public class ShoppingCartFactory {
	private static long SHOPPING_CART_ID;

	public ShoppingCartFactory() {
		// TODO Auto-generated constructor stub
	}

	public static IShoppingCart createShoppingCart () {
		ShoppingCartFactory.SHOPPING_CART_ID++;
		return new ShoppingCart(ShoppingCartFactory.SHOPPING_CART_ID);
	}
	
	public static IShoppingCart createShoppingCart (long id) {
		// Not too sure about this implementation. JavaDoc is a little bit weird here.
		if (ShoppingCartFactory.SHOPPING_CART_ID < id) {
			return new ShoppingCart(id + 1);
		} else {
			return new ShoppingCart(id);
		}
	}
}
