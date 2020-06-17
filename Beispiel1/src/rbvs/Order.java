/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 13.03.2020
 */
package rbvs;

import ict.basics.IDeepCopy;
import rbvs.product.IProduct;
import rbvs.record.Record;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class Order extends Record implements IDeepCopy {
	private OrderState currentState;
	private List<IProduct> products = new Vector<IProduct>();
	private Table table;

	public Order(long identifier, Table table, List<IProduct> products) {
		super(identifier);
		this.currentState = OrderState.OPEN;
		this.table = table;
		products
			.stream()
			.forEach(p -> this.products.add(p));
	}
	
	public List<IProduct> getProducts() {
		List<IProduct> products = new Vector<IProduct>();
		this.products
			.stream()
			.forEach(p -> products.add((IProduct) p.deepCopy()));
		return products;
	}
	
	public boolean setState(OrderState newStatus) {
		if (this.currentState != OrderState.OPEN) return false;
		this.currentState = newStatus;
		return true;
	}
	
	public OrderState getState() {
		return this.currentState;
	}
	
	public boolean isCancelled() {
		if (this.currentState == OrderState.CANCELLED) return true;
		return false;
	}
	
	public boolean isPaid() {
		if (this.currentState == OrderState.PAID) return true;
		return false;
	}
	
	public Table getTable() {
		return this.table;
	}
	
	@Override
	public Order deepCopy() {
		Order o = new Order(this.getIdentifier(), this.getTable(), this.getProducts());
		o.setState(this.getState());
		return o;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Order)) return false;
		Order o = (Order) obj;
		
		if (o.getIdentifier() != this.getIdentifier()) return false;
		if (o.getState() != this.getState()) return false;
		if (o.getTable() != this.getTable()) return false;
		
		List<IProduct> l1 = this.getProducts()
				.stream()
				.filter(p -> !(o.getProducts().contains(p)))
				.collect(Collectors.toList());
		
		List<IProduct> l2 = o.getProducts()
				.stream()
				.filter(p -> !(this.getProducts().contains(p)))
				.collect(Collectors.toList());
		
		if (l1.size() > 0 || l2.size() > 0) return false;
		return true;
	}
}
