package cashregister;

import java.util.Collection;
import java.util.Iterator;

import rbvs.product.IShoppingCartElement;
import rbvs.record.IInvoice;
import rbvs.record.Invoice;
import rbvs.record.PaymentTransaction;
import paymentprovider.IPayment;
import managementserver.ISubjectManagementServer;
import rbvs.product.IProduct;
import rbvs.product.Product;
import util.searchable.ProductNameFilter;
import tree.ITree;
import tree.node.ITreeNode;
import util.Tuple;
import cashregister.ui.ICashRegisterUI;
import container.Container;
import tree.GenericTree;

public class CashRegister implements IObserver, ICashRegister {
	private long id;
	private Collection<IInvoice> records;
	private Collection<ICashRegisterUI> uis;
	private ITree<IProduct> products;
	private Collection<Tuple<ISubjectManagementServer,Boolean>> subjects;
	private Collection<IShoppingCart> shoppingCarts;
	
	public CashRegister (long id) {
		this.id = id;
		this.records = new Container<IInvoice>();
		this.uis = new Container<ICashRegisterUI>();
		this.products = new GenericTree<IProduct>();
		this.subjects = new Container<Tuple<ISubjectManagementServer,Boolean>>();
		this.shoppingCarts = new Container<IShoppingCart>();
	}
	
	public long getID () {
		return this.id;
	}
	
	public Long addShoppingCart () {
		IShoppingCart sc = ShoppingCartFactory.createShoppingCart();
		this.shoppingCarts.add(sc);
		return sc.getShoppingCartID();
	}
	
	public Collection<IShoppingCart> getShoppingCarts () {
		return this.shoppingCarts;
	}
	
	protected IShoppingCart findShoppingCartById (Long id) {
		for (IShoppingCart sc:this.shoppingCarts) {
			if (sc.getShoppingCartID() == id) return sc;
		}
		return null;
	}
	
	public boolean addProductToShoppingCart (long id, IShoppingCartElement element) {
		if (element == null) return false;
		IShoppingCart sc = this.findShoppingCartById(id);
		if (sc == null) return false;
		sc.addElement(element.deepCopy());
		return true;
	}
	
	public IInvoice payShoppingCart (long id, IPayment provider) throws ShoppingCartNotFoundException {
		if (provider == null) return null;
		IShoppingCart sc = this.findShoppingCartById(id);
		if (sc == null) throw new ShoppingCartNotFoundException(this.id, id);
		PaymentTransaction t = provider.pay(sc.getTotalPriceOfElements());
		IInvoice i = new Invoice();
		
		Collection<IShoppingCartElement> c = new Container<IShoppingCartElement>();
		// removeCopy is a second Collection, which stores the elements from the currentElements of the ShoppingCart. 
		// Since we run into problems if we remove items from a collection, we currently iterate over,
		// this is a little dirty hack to make it work anyways.
		Collection<IShoppingCartElement> removeCopy = new Container<IShoppingCartElement>();
		for (IShoppingCartElement e:sc.currentElements()) {
			c.add(e.deepCopy());
			removeCopy.add(e);
		}
		for (IShoppingCartElement sce:removeCopy) {
			sc.removeElement(sce);
		}
		i.setInvoiceProducts(c);
		i.addPaymentTransaction(t);
		this.records.add(i);
		return i;
	}
	
	public void registerCashRegisterUI (ICashRegisterUI ui) {
		if (ui == null) return;
		this.uis.add(ui);
	}
	
	public void unregisterCashRegisterUI (ICashRegisterUI ui) {
		if (ui == null || !this.uis.contains(ui)) return;
		this.uis.remove(ui);
	}
	
	public void displayProducts () {
		this.uis
			.stream()
			.forEach(u -> u.displayProducts(this.products));
	}
	
	public void displayShoppingCarts () {
		this.uis
			.stream()
			.forEach(u -> u.displayShoppingCarts(this.shoppingCarts));
	}
	
	@Override
	public void displayShoppingCart(long id) {
		IShoppingCart sc = this.findShoppingCartById(id);
		if (sc == null) return;
		this.uis
			.stream()
			.forEach(u -> u.displayShoppingCart(sc));
	}
	
	public void displayRecords () {
		this.uis
			.stream()
			.forEach(u -> u.displayRecords(this.records));
	}
	
	public void notifyChange (ISubjectManagementServer subject) {
		if (subject == null) return;
		Tuple<ISubjectManagementServer,Boolean> s = null;
		for (Tuple<ISubjectManagementServer,Boolean> e:this.subjects) {
			if (e.getValueA().equals(subject)) s = e;
		}
		if (s == null) return;
		if (s.getValueB().booleanValue()) {
			this.products = subject.getChanges();
		}
	}
	
	public IShoppingCartElement selectProduct (String product) {
		if (product == null || product.length() == 0) return null;
		Collection<ITreeNode<IProduct>> c = this.products.searchByFilter(new ProductNameFilter(), product);
		Iterator<ITreeNode<IProduct>> i = c.iterator();
		if (i.hasNext()) {
			return (Product) i.next().nodeValue();
		}
		return null;
	}
	
	public IShoppingCartElement selectProduct (Product product) {
		if (product == null) return null;
		ITreeNode<IProduct> p = this.products.findNode(product);
		if (p == null) return null;
		return (IShoppingCartElement) p.nodeValue();
	}
	
	public void activateNotifications (ISubjectManagementServer subject) {
		if (subject == null) return;
		Tuple<ISubjectManagementServer,Boolean> s = null;
		for (Tuple<ISubjectManagementServer,Boolean> e:this.subjects) {
			if (e.getValueA().equals(subject)) s = e;
		}
		if (s == null) {
			this.subjects.add(new Tuple<ISubjectManagementServer,Boolean>(subject, true));
		} else {
			s.setValueB(true);
		}
	}
	
	public void deactivateNotifications (ISubjectManagementServer subject) {
		if (subject == null) return;
		Tuple<ISubjectManagementServer,Boolean> s = null;
		for (Tuple<ISubjectManagementServer,Boolean> e:this.subjects) {
			if (e.getValueA().equals(subject)) s = e;
		}
		if (s == null) return;
		s.setValueB(false);
	}
	
	public String toString () {
		return "CashRegister [ id = " + this.id + ", records = " + this.records.size() + ", uis = " + this.uis.size() + ", products = " + this.products.generateConsoleView("\t") + ", subjects = " + this.subjects.size() + ", shoppingCarts = " + this.shoppingCarts.size() + " ]";
	}
	
	public Collection<IInvoice> getRecords () {
		return this.records;
	}
}
