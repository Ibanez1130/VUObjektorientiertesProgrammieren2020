/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 30.05.2020
 */
package managementserver;

import java.util.Collection;

import warehouse.IWarehouseListener;
import container.Container;
import cashregister.ICashRegister;
import cashregister.IObserver;
import cashregister.NotRegisteredException;
import tree.ITree;
import tree.node.ITreeNode;
import tree.node.ProductCategoryTreeNode;
import tree.GenericTree;
import rbvs.product.IProduct;
import rbvs.product.ProductCategory;
import rbvs.product.CompositeProduct;
import tree.node.CategoryTreeNode;
import tree.node.ProductTreeNode;
import util.searchable.ProductNameFilter;
import util.searchable.ProductCategoryNodeFilter;

public class ManagementServer implements IManagementServer, IWarehouseListener, ISubjectManagementServer {
	private Collection<ICashRegister> cashRegisters;
	private Collection<IObserver> observer;
	private ITree<IProduct> productAssortment;
	private static ManagementServer INSTANCE;

	private ManagementServer() {
		this.initialize();
	}
	
	private void initialize () {
		if (ManagementServer.INSTANCE == null) {
			this.cashRegisters = new Container<ICashRegister>();
			this.observer = new Container<IObserver>();
			this.productAssortment = new GenericTree<IProduct>();
			this.productAssortment.setRoot(new CategoryTreeNode<IProduct,String>("Products"));
			ITreeNode<IProduct> r = this.productAssortment.getRoot();
			r.getChildren().add(new ProductCategoryTreeNode<IProduct>(ProductCategory.FOOD));
			r.getChildren().add(new ProductCategoryTreeNode<IProduct>(ProductCategory.BEVERAGE));
			r.getChildren().add(new ProductCategoryTreeNode<IProduct>(ProductCategory.DEFAULT));
			ManagementServer.INSTANCE = this;
		}
	}

	public static IManagementServer GET_INSTANCE () {
		if (ManagementServer.INSTANCE == null) return new ManagementServer();
		return ManagementServer.INSTANCE;
	}
	
	public void addCashRegister (ICashRegister cashRegister) {
		if (cashRegister == null) return;
		this.cashRegisters.add(cashRegister);
		if (cashRegister instanceof IObserver) {
			((IObserver) cashRegister).activateNotifications(this);
			((IObserver) cashRegister).notifyChange(this);
			if (!(this.observer.contains((IObserver) cashRegister))) this.observer.add((IObserver) cashRegister);
		}
	}
	
	public void unregisterCashRegister (ICashRegister cashRegister) throws NotRegisteredException  {
		if (cashRegister == null) return;
		if (!(this.cashRegisters.contains(cashRegister))) throw new NotRegisteredException("The CashRegister with id " + cashRegister.getID() + " is not registered in the Management Server.");
		this.cashRegisters.remove(cashRegister);
		if (cashRegister instanceof IObserver && this.observer.contains((IObserver) cashRegister)) {
			((IObserver) cashRegister).deactivateNotifications(this);
			this.observer.remove((IObserver) cashRegister);
		}
	}
	
	public void notifyChange (IProduct object) {
		if (object == null) return;
		// Find the tree node, thats node value is equal to the provided product.
		ITreeNode<IProduct> tn = this.productAssortment.findNode(object);
		if (tn == null) return;
		// Get the product, from the productAssortment, that is equal to object.
		IProduct p = tn.nodeValue();
		if (object.getCategory() != p.getCategory()) {
			// A difference in the category has been made!
			// Remove from the ProductCategoryTreeNode of the old Category.
			Collection<ITreeNode<IProduct>> op = this.productAssortment.searchByFilter(new ProductCategoryNodeFilter(), p.getCategory());
			// It is possible that the category is not food, beverage or default.
			// In this case we need a fallback to default, which is done here.
			// The same is done for adding a product.
			if (!(op.isEmpty())) {
				op.iterator().next().removeNodeByValue(object);
			} else {
				this.productAssortment.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.DEFAULT)
					.iterator()
					.next()
					.removeNodeByValue(object);
			}
			// Add to the ProductCategoryTreeNode of the new category.
			Collection<ITreeNode<IProduct>> np = this.productAssortment.searchByFilter(new ProductCategoryNodeFilter(), object.getCategory());
			if (!(np.isEmpty())) {
				np.iterator().next().getChildren().add(new ProductTreeNode(object));
			} else {
				this.productAssortment.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.DEFAULT)
					.iterator()
					.next()
					.getChildren()
					.add(new ProductTreeNode(object));
			}
		}
		// Now lets update the matching nodes and its children.
		Collection<ITreeNode<IProduct>> c = this.productAssortment.getRoot().searchByFilter(new ProductNameFilter(), object.getName());
		for (ITreeNode<IProduct> e:c) {
			// Update each product that equals the product provided to this method.
			e.nodeValue().update(object);
			if (object instanceof CompositeProduct) {
				// Check if new childs has been added to the product.
				((CompositeProduct) object).getProducts().stream().forEach(child -> {
					Collection<ITreeNode<IProduct>> hits = e.searchByFilter(new ProductNameFilter(),  child);
					if (hits.size() == 0) {
						// We got a new child!
						e.getChildren().add(new ProductTreeNode(child));
					}
					if (!((CompositeProduct) e.nodeValue()).getProducts().contains(child)) {
						((CompositeProduct) e.nodeValue()).getProducts().add(child);
					}
				});
			}
		}
		// As a last step we need to propagate the changes to the observer.
		this.propagateProducts();
	}
	
	public void productAdded (IProduct product) {
		if (product == null) return;
		// The following is basically the same routine we do in notifyChanges.
		Collection<ITreeNode<IProduct>> p = this.productAssortment.searchByFilter(new ProductCategoryNodeFilter(), product.getCategory());
		if (!(p.isEmpty())) {
			p.iterator().next().getChildren().add(new ProductTreeNode(product));
		} else {
			this.productAssortment.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.DEFAULT)
				.iterator()
				.next()
				.getChildren()
				.add(new ProductTreeNode(product));
		}
		this.propagateProducts();
	}
	
	public void productRemoved(IProduct product) {
		if (product == null) return;
		this.productAssortment.removeNode(product);
		// Since it is possible that another product with the same properties
		// is included in the productAssortment, we need to remove them all.
		if (this.productAssortment.findNode(product) != null) {
			this.productRemoved(product);
		}
		this.propagateProducts();
	}
	
	public ITree<IProduct> getChanges () {
		return this.productAssortment.deepCopy();
	}
	
	public boolean register (IObserver obs) {
		if (obs == null) return false;
		obs.activateNotifications(this);
		obs.notifyChange(this);
		return (this.observer.contains(obs)) ? true : this.observer.add(obs);
	}
	
	public boolean unregister (IObserver obs) {
		if (obs == null) return false;
		obs.deactivateNotifications(this);
		return this.observer.remove(obs);
	}
	
	public void propagateProducts () {
		this.observer
			.stream()
			.forEach(o -> o.notifyChange(this));
	}
	
	public ITree<IProduct> retrieveProductSortiment () {
		// The JavaDoc is a little bit mysterious here.
		return this.productAssortment.deepCopy();
	}
	
	public Collection<ICashRegister> retrieveRegisteredCashRegisters() {
		return this.cashRegisters;
	}
	
	public ICashRegister retrieveRegisteredCashRegister (Long cashRegisterId) throws NotRegisteredException {
		for (ICashRegister cr:this.cashRegisters) {
			if (cr.getID() == cashRegisterId) return cr;
		}
		throw new NotRegisteredException("The CashRegister with id " + cashRegisterId + " is not registered in the Management Server.");
	}
}
