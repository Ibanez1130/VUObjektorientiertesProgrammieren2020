/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 31.05.2020
 */
package cashregister.ui;

import java.util.Collection;

import tree.ITree;
import rbvs.product.IProduct;
import rbvs.product.IShoppingCartElement;
import cashregister.IShoppingCart;
import rbvs.record.IInvoice;
import rbvs.record.PaymentTransaction;

public class CashRegisterConsoleUI implements ICashRegisterUI {

	public CashRegisterConsoleUI() {
		// TODO Auto-generated constructor stub
	}
	
	public void displayProducts (ITree<IProduct> products) {
		if (products == null) return;
		System.out.println("\n!------------Products------------!");
		System.out.println(products.generateConsoleView("\t"));
	}
	
	public void displayShoppingCart (IShoppingCart shoppingCart) {
		if (shoppingCart == null) return;
		System.out.println("\n!------------ShoppingCart------------!");
		System.out.println("ID: " + shoppingCart.getShoppingCartID());
		System.out.println("Elements: " + shoppingCart.currentElements().toString());
		System.out.println("Total price: " + shoppingCart.getTotalPriceOfElements());
	}
	
	public void displayShoppingCarts (Collection<IShoppingCart> shoppingCarts) {
		if (shoppingCarts == null) return;
		System.out.println("\n!------------ShoppingCarts------------!");
		for (IShoppingCart sc:shoppingCarts) {
			this.displayShoppingCart(sc);
		}
	}
	
	public void displayRecords (Collection<IInvoice> records) {
		if (records == null) return;
		System.out.println("\n!------------Records------------!");
		for (IInvoice r:records) {
			System.out.println("!------------Record------------!");
			this.displayTransaction(r.getPaymentTransaction());
			System.out.println("Invoice Products");
			for (IShoppingCartElement p:r.getInvoiceProducts()) {
				System.out.println("    !--------Product--------!");
				System.out.println("    Name: " + p.getName());
				System.out.println("    Price: " + p.getPrice());
			}
		}
	}
	
	public void displayTransaction (PaymentTransaction transaction) {
		System.out.println("\n!------------PaymentTransaction------------!");
		System.out.println("ID: " + transaction.getTransactionId());
		System.out.println("Paid prive: " + transaction.getPaidPrice());
		System.out.println("Provider name: " + transaction.getPaymentProviderName());
		System.out.println("Timestamp: " + transaction.getTimestamp());
	}
	
	public void pushUpdateInformation (ITree<IProduct> products, Collection<IShoppingCart> carts, Collection<IInvoice> records) {
		// Any update notification is discarded means this method does nothing I guess?
		return;
	}
}
