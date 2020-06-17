/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 04.04.2020
 */

package rbvs.restaurantchain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import rbvs.product.IProduct;
import rbvs.Restaurant;
import rbvs.DuplicateProductException;
import java.util.stream.Collectors;

public class RestaurantChain {
	private String name = "";
	private List<IProduct> productAssortment;
	private List<Restaurant> restaurants;
	
	public RestaurantChain(String name) {
		this.name = (name != null) ? name : "";
		this.productAssortment = new Vector<IProduct>();
		this.restaurants = new Vector<Restaurant>();
	}

	public String getName () {
		return this.name;
	}
	
	public boolean containsRestaurant(String name) {
		return this.restaurants
				.stream()
				.map(r -> r.getName())
				.collect(Collectors.toList())
				.contains(name);
	}
	
	public Restaurant findRestaurant (String name) {
		for (Restaurant r:this.restaurants) {
			if (r.getName().equals(name)) return r;
		}
		return null;
	}
	
	public void addRestaurant (Restaurant r) {
		r.setProductAssortment(this.productAssortment);
		this.restaurants.add(r);
	}
	
	public boolean containsProduct(IProduct compareProduct) {
		return this.productAssortment.contains(compareProduct);
	}

	public boolean addProduct(IProduct product) throws DuplicateProductException {
		if (product == null) return false;
		if (this.containsProduct(product)) throw new DuplicateProductException(product);
		return this.productAssortment.add((IProduct) product.deepCopy());
	}

	public boolean addProduct(Collection<IProduct> products) throws DuplicateProductException {
		List<Boolean> l = new Vector<Boolean>();
		for (IProduct p:products) {
			try {
				l.add(this.addProduct(p));
			} catch (DuplicateProductException e) {
				throw e;
			}
		}
		if (l.contains(false)) return false;
		return true;
	}
	
	public void initiateRestaurantChain (File file) throws FileNotFoundException {
		try {
		    Scanner sc = new Scanner(file);
		    while (sc.hasNextLine()) {
		    	String data = sc.nextLine();
		    	String restaurantname = data.split(";")[0];
		    	String tablename = data.split(";")[1];
		    	String tableseatcount = data.split(";")[2];
		    	if (!this.containsRestaurant(restaurantname)) {
		    		this.addRestaurant(new Restaurant(restaurantname));
		    	}
		    	if (this.findRestaurant(restaurantname) != null) {
		    		this.findRestaurant(restaurantname).createTable(tablename);
		    		this.findRestaurant(restaurantname).getSpecificTable(tablename).setSeatCount(Integer.parseInt(tableseatcount));
		    	}
		    }
		    sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString () {
		String restaurants = this.restaurants
				.stream()
				.map(r -> r.toString())
				.collect(Collectors.joining(", "));
		
		String products = this.productAssortment
				.stream()
				.map(r -> r.toString())
				.collect(Collectors.joining(", "));
		
		return "RestaurantChain [ name = " + this.getName() + ", restaurants = [ " + restaurants + " ], productAssortment = " + products + "]";
	}
}
