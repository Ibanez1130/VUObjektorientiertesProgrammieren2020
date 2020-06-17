package rbvs;

import rbvs.product.*;

import java.util.List;
import java.util.Vector;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Scanner;
import rbvs.restaurantchain.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Restaurant {
	private String name;
	private List<Order> orderHistory;
	private List<IProduct> productAssortment;
	private List<Table> tables;
	private long uniqueOrderIdentifier = 0;

	public Restaurant(String name) {
		this.name = name;
		this.orderHistory = new Vector<Order>();
		this.productAssortment = new Vector<IProduct>();
		this.tables = new Vector<Table>();
	}
	
	public String getName() {
		return this.name;
	}
	
	// TODO: REMOVE IF THIS DOES NOT WORK!
	public void setProductAssortment (List<IProduct> products) {
		this.productAssortment = products;
	}
	
	public boolean createTable(String tableIdentifier) {
		List<String> l = this.getTableIdentifiers();
		if (l.contains(tableIdentifier)) return false;

		Table t = new Table(tableIdentifier);
		return this.tables.add(t);
	}
	
	public List<String> getTableIdentifiers() {
		return this.tables
				.stream()
				.map(t -> t.getTableIdentifier())
				.collect(Collectors.toList());
	}
	
	public Table getSpecificTable(String identifier) {
		for (Table t:this.tables) {
			if (t.getTableIdentifier().equals(identifier)) return t;
		}
		return null;
	}
	
	public boolean containsProduct(IProduct compareProduct) {
		return this.productAssortment.contains(compareProduct);
	}
	
	public IProduct findProduct(String productName) {
		for (IProduct p:this.productAssortment) {
			if (p.getName().equals(productName)) return p;
		}
		return null;
	}
	
	public IProduct findProduct(IProduct compareProduct) {
		for (IProduct p:this.productAssortment) {
			if (p.equals(compareProduct)) return p;
		}
		return null;
	}
	
	private long generateUniqueIdentifier() {
		return this.uniqueOrderIdentifier + 1;
	}
	
	public static List<IProduct> generateSimpleProducts() {
		List<IProduct> l = new Vector<IProduct>();
		IProduct p0 = (IProduct) new SimpleProduct("Vogerlsalat", 1);
		IProduct p1 = (IProduct) new SimpleProduct("Salz", 0.01f);
		IProduct p2 = (IProduct) new SimpleProduct("�l", 0.03f);
		IProduct p3 = (IProduct) new SimpleProduct("Essig", 0.05f);
		IProduct p4 = (IProduct) new SimpleProduct("Gurke", 1);
		IProduct p5 = (IProduct) new SimpleProduct("Tomate", 1);
		IProduct p6 = (IProduct) new SimpleProduct("Pinienkerne", 2);
		IProduct p7 = (IProduct) new SimpleProduct("Champignions", 3);
		IProduct p8 = (IProduct) new SimpleProduct("Mozzarella", 1.5f);
		
		l.add(p0);
		l.add(p1);
		l.add(p2);
		l.add(p3);
		l.add(p4);
		l.add(p5);
		l.add(p6);
		l.add(p7);
		l.add(p8);
		
		return l;
	}
	
	public static List<IProduct> generateCompositeProducts() {
		List<IProduct> l = new Vector<IProduct>();
		
		IProduct p0 = (IProduct) new SimpleProduct("Vogerlsalat", 1);
		IProduct p1 = (IProduct) new SimpleProduct("Salz", 0.01f);
		IProduct p2 = (IProduct) new SimpleProduct("�l", 0.03f);
		IProduct p3 = (IProduct) new SimpleProduct("Essig", 0.05f);
		IProduct p4 = (IProduct) new SimpleProduct("Gurke", 1);
		IProduct p5 = (IProduct) new SimpleProduct("Tomate", 1);
		IProduct p6 = (IProduct) new SimpleProduct("Pinienkerne", 2);
		IProduct p7 = (IProduct) new SimpleProduct("Champignions", 3);
		IProduct p8 = (IProduct) new SimpleProduct("Mozzarella", 1.5f);
		
		IProduct cp1 = (IProduct) new CompositeProduct("Salat-Dressing", 0);
		IProduct cp2 = (IProduct) new CompositeProduct("Standard Salat", 15);
		IProduct cp3 = (IProduct) new CompositeProduct("Tomate-Mozzarella", 10);
		IProduct cp4 = (IProduct) new CompositeProduct("Champignon Salat", 5);
		
		((CompositeProduct) cp1).addProduct((Product) p1);
		((CompositeProduct) cp1).addProduct((Product) p2);
		((CompositeProduct) cp1).addProduct((Product) p3);
		
		((CompositeProduct) cp2).addProduct((Product) cp1);
		((CompositeProduct) cp2).addProduct((Product) p0);
		((CompositeProduct) cp2).addProduct((Product) p4);
		((CompositeProduct) cp2).addProduct((Product) p5);
		
		((CompositeProduct) cp3).addProduct((Product) cp1);
		((CompositeProduct) cp3).addProduct((Product) p5);
		((CompositeProduct) cp3).addProduct((Product) p8);
		
		((CompositeProduct) cp4).addProduct((Product) cp1);
		((CompositeProduct) cp4).addProduct((Product) p0);
		((CompositeProduct) cp4).addProduct((Product) p4);
		((CompositeProduct) cp4).addProduct((Product) p5);
		((CompositeProduct) cp4).addProduct((Product) p6);
		((CompositeProduct) cp4).addProduct((Product) p7);
		
		l.add(cp2);
		l.add(cp3);
		l.add(cp4);
		
		return l;
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
	
	public List<IProduct> getProducts () {
		List<IProduct> products = this.productAssortment
				.stream()
				.map(p -> (IProduct) p.deepCopy())
				.collect(Collectors.toList());
		return products;
	}
	
	public boolean orderProductForTable(Table table, IProduct product) {
		if (table == null || product == null) return false;
		if (this.getSpecificTable(table.getTableIdentifier()) == null) return false;
		if (!(this.containsProduct(product))) return false;
		
		IProduct p = this.findProduct(product);
		List<IProduct> l = new Vector<IProduct>();
		l.add(p);
		
		Order o = new Order(this.generateUniqueIdentifier(), this.getSpecificTable(table.getTableIdentifier()), l);
		
		return this.orderHistory.add(o);
	}
	
	public boolean orderProductForTable(Table table, IProduct product, int count) {
		if (table == null || product == null || count < 1) return false;
		if (this.getSpecificTable(table.getTableIdentifier()) == null) return false;
		if (!(this.containsProduct(product))) return false;

		IProduct p = this.findProduct(product);
		List<IProduct> l = new Vector<IProduct>();
		
		for (int i = 0; i < count; i++) {
			l.add(p);
		}

		Order o = new Order(this.generateUniqueIdentifier(), this.getSpecificTable(table.getTableIdentifier()), l);

		return this.orderHistory.add(o);
	}
	
	public String toString() {
		String orders = this.orderHistory
				.stream()
				.map(o -> o.toString())
				.collect(Collectors.joining(", "));

		String products = this.productAssortment
				.stream()
				.map(p -> p.toString())
				.collect(Collectors.joining(", "));

		String tables = this.tables
				.stream()
				.map(t -> t.toString())
				.collect(Collectors.joining(", "));

		return "Restaurant [ name = " + this.getName() + ", orderHistory = [ " + orders + " ], productAssortment = [ " + products + " ], tables = [ " + tables + " ], uniqueOrderIdentifier = " + this.uniqueOrderIdentifier + " ]";
	}
	
	public static void main(String[] args) {
		Restaurant r = new Restaurant("Home Kitchen");
		
		r.createTable("Tisch 1");
		r.createTable("Tisch 2");
		r.createTable("Tisch 3");
		
		List<IProduct> simpleProducts = Restaurant.generateSimpleProducts();
		List<IProduct> compositeProducts = Restaurant.generateCompositeProducts();
		List<IProduct> extendedProducts = new Vector<IProduct>();

		IProduct ep1 = (IProduct) new ExtendedProduct("Gr�n-Tee", 2);
		IProduct ep2 = (IProduct) new ExtendedProduct("Fr�chte-Tee", 1.5f);
		IProduct ep3 = (IProduct) new ExtendedProduct("Schwarz-Tee", 3);
		IProduct ep4 = (IProduct) new ExtendedProduct("Kr�uter-Tee", 2);
		IProduct ep5 = (IProduct) new ExtendedProduct("Ingwer-Zitronen-Tee", 5);

		extendedProducts.add(ep1);
		extendedProducts.add(ep2);
		extendedProducts.add(ep3);
		extendedProducts.add(ep4);
		extendedProducts.add(ep5);

		try {
			r.addProduct(simpleProducts);
			r.addProduct(compositeProducts);
			r.addProduct(extendedProducts);
		} catch (DuplicateProductException e) {
			e.printStackTrace();
		}

		// This should fail
		/* try {
			r.addProduct(ep1);
		} catch (DuplicateProductException e) {
			e.printStackTrace();
		} */
		
		IProduct tea = (IProduct) new CompositeProduct("Tee", 10);
		IProduct ingredients = (IProduct) new CompositeProduct("Zutaten", 5);
		IProduct salads = (IProduct) new CompositeProduct("Salate", 20);
		IProduct others = (IProduct) new CompositeProduct("Sonstiges", 20);

		((CompositeProduct) tea).addProduct((Product) ep1);
		((CompositeProduct) tea).addProduct((Product) ep2);
		((CompositeProduct) tea).addProduct((Product) ep3);
		((CompositeProduct) tea).addProduct((Product) ep4);
		((CompositeProduct) tea).addProduct((Product) ep5);
		
		((CompositeProduct) ingredients).addProduct((Product) r.findProduct("Vogerlsalat"));
		((CompositeProduct) ingredients).addProduct((Product) r.findProduct("Gurke"));
		((CompositeProduct) ingredients).addProduct((Product) r.findProduct("Tomate"));
		((CompositeProduct) ingredients).addProduct((Product) r.findProduct("Pinienkerne"));
		((CompositeProduct) ingredients).addProduct((Product) r.findProduct("Champignions"));

		((CompositeProduct) salads).addProduct((Product) r.findProduct("Standard Salat"));
		((CompositeProduct) salads).addProduct((Product) r.findProduct("Champignon Salat"));

		((CompositeProduct) others).addProduct((Product) r.findProduct("Tomate-Mozzarella"));

		r.orderProductForTable(r.getSpecificTable("Tisch 1"), r.findProduct("Standard Salat"), 2);
		r.orderProductForTable(r.getSpecificTable("Tisch 1"), r.findProduct("Fr�chte-Tee"));
		r.orderProductForTable(r.getSpecificTable("Tisch 1"), r.findProduct("Ingwer-Zitronen-Tee"));

		r.orderProductForTable(r.getSpecificTable("Tisch 2"), r.findProduct("Champignon Salat"), 3);
		r.orderProductForTable(r.getSpecificTable("Tisch 2"), r.findProduct("Tomate-Mozzarella"));
		r.orderProductForTable(r.getSpecificTable("Tisch 2"), r.findProduct("Schwarz-Tee"), 2);
		r.orderProductForTable(r.getSpecificTable("Tisch 2"), r.findProduct("Gr�n-Tee"), 2);

		r.orderProductForTable(r.getSpecificTable("Tisch 3"), r.findProduct("Champignon Salat"));
		r.orderProductForTable(r.getSpecificTable("Tisch 3"), r.findProduct("Standard Salat"));
		r.orderProductForTable(r.getSpecificTable("Tisch 3"), r.findProduct("Schwarz-Tee"), 2);
		r.orderProductForTable(r.getSpecificTable("Tisch 3"), r.findProduct("Kr�uter-Tee"), 2);

		System.out.println(r.toString());
		
		// Input menu
		int input = 1;
		Scanner scanner = new Scanner(System.in);
		
		while (input != 0) {
			System.out.println("If you want to exit, enter \"0\".");
			System.out.println("If you want to search for a product, enter \"1\".");
			System.out.println("If you want to add a product, enter \"2\".");
			input = scanner.nextInt();
			scanner.nextLine();
			switch (input) {
				case 1:
					System.out.println("Please enter the name of the product, you are looking for:");
					String productname = scanner.nextLine();
					IProduct product = r.findProduct(productname);
					if (product != null) {
						System.out.println("Your product has been found sucessfully: " + product.toString());
					} else {
						System.out.println("We could not find the product you are looking for.");
					}
					break;
				case 2:
					System.out.println("Please enter the name of the product, you want to add:");
					String new_productname = scanner.nextLine();
					IProduct new_product = new SimpleProduct(new_productname);
					try {
						r.addProduct(new_product);
						System.out.println("Please enter the price of the new product:");
						float new_productprice = scanner.nextFloat();
						try {
							((Product) r.findProduct(new_productname)).setPrice(new_productprice);
							System.out.println("The product has been added successfully! " + ((Product) r.findProduct(new_productname)).toString());
						} catch (IllegalArgumentException iae) {
							iae.printStackTrace();
							System.out.println("The price entered is not valid!");
						}
					} catch (DuplicateProductException dpe) {
						dpe.printStackTrace();
						System.out.println("A product with that name already exists!");
					}
					break;
			}
		}
		scanner.close();
		System.out.println("Thanks for your visit! Hope to see you again soon!");
		System.out.println();

		// BONUSAUFGABE ANFANG
		System.out.println("BONUSAUFGABE!");
		
		RestaurantChain rc = new RestaurantChain("The Chain");
		File file = new File("C:\\---\\src\\initFile.txt");

		try {
			rc.initiateRestaurantChain(file);
			System.out.println(rc.toString());
			try {
				rc.findRestaurant("Hauptbahnhof").addProduct((IProduct) new SimpleProduct("Sahneschnitte", 5.50f));
				rc.findRestaurant("Hauptbahnhof").addProduct((IProduct) salads);
				rc.findRestaurant("Hauptbahnhof").addProduct((IProduct) ep5);
			} catch (DuplicateProductException dpe) {
				dpe.printStackTrace();
			}
			System.out.println(rc.findRestaurant("Mariahilfer Strasse").findProduct("Sahneschnitte"));
			System.out.println(rc.findRestaurant("Palais Luxus").findProduct("Salate"));
			System.out.println(rc.findRestaurant("Palais Luxus").findProduct("Ingwer-Zitronen-Tee"));
			rc.findRestaurant("Palais Luxus").orderProductForTable(rc.findRestaurant("Palais Luxus").getSpecificTable("Lounge Mozart"), rc.findRestaurant("Palais Luxus").findProduct("Ingwer-Zitronen-Tee"), 3);
			System.out.println(rc.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// BONUSAUFGABE ENDE
	}
}
