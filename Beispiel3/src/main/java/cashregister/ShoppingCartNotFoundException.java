/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 30.05.2020
 */
package cashregister;

public class ShoppingCartNotFoundException extends Exception {
	private static final long serialVersionUID = 8L;

	public ShoppingCartNotFoundException (Long cashregisterid, Long shoppingcartid) {
		super("The shopping cart with id " + shoppingcartid + " was not found in cashregister " + cashregisterid);
	}
}
