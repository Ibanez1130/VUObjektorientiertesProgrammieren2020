import tree.*;
import tree.node.*;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import rbvs.product.IProduct;
import rbvs.product.Product;
import rbvs.product.SimpleProduct;
import rbvs.product.CompositeProduct;
import network.*;

public class Application {

	public static void main (String[] args) {
		try {
			new Thread(new Server("Main Server", 8001, 8002)).start();
			new Thread(new Client("Main Client", 8001)).start();
			new Thread(new Server("Backup Server", 8002)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(Application.generateGenericTree());
		System.out.println(Application.generateProductTree());
	}

	public static String generateGenericTree() {
		/*
		 * DISCLAIMER:
		 * This is by no means a political statement.
		 * But since the requirement is to create a hierarchy tree, the government was a welcoming example. 
		 */
		GenericTreeNode<String> root = new GenericTreeNode<String>("Bundeskanzler", "Sebastian Kurz");
		GenericTree<String> t = new GenericTree<String>(root);

		GenericTreeNode<String> ministerium1 = new GenericTreeNode<String>("Ministerium", "Kunst, Kultur, �ffentlichen Dienst und Sport");
		GenericTreeNode<String> ministerium1minister = new GenericTreeNode<String>("Minister", "Werner Kogler");
		GenericTreeNode<String> ministerium1mitarbeiter1 = new GenericTreeNode<String>("Staatssekret�rin", "Ulrike Lunacek");
		GenericTreeNode<String> ministerium1mitarbeiter2 = new GenericTreeNode<String>("Generalsekret�rin", "Wildfellner Eva");
		GenericTreeNode<String> ministerium1mitarbeiter3 = new GenericTreeNode<String>("Leitung", "Brosz Dieter");
		

		GenericTreeNode<String> ministerium2 = new GenericTreeNode<String>("Ministerium", "Soziales, Gesundheit, Pflege und Konsumentenschutz");
		GenericTreeNode<String> ministerium2minister = new GenericTreeNode<String>("Minister", "Rudolf Anschober");
		GenericTreeNode<String> ministerium2mitarbeiter1 = new GenericTreeNode<String>("Leitung", "Lichtenecker Ruperta");
		GenericTreeNode<String> ministerium2mitarbeiter2 = new GenericTreeNode<String>("Pressesprecherin", "Draxl Margit");
		GenericTreeNode<String> ministerium2mitarbeiter3 = new GenericTreeNode<String>("Mitarbeiterin", "H�ckel-Schinkinger Katharina");
		
		GenericTreeNode<String> ministerium3 = new GenericTreeNode<String>("Ministerium", "Finanzen");
		GenericTreeNode<String> ministerium3minister = new GenericTreeNode<String>("Minister", "Gernot Bl�mel");
		GenericTreeNode<String> ministerium3mitarbeiter1 = new GenericTreeNode<String>("Leitung", "Niedrist Clemens-Wolfgang");
		GenericTreeNode<String> ministerium3mitarbeiter2 = new GenericTreeNode<String>("Stellvertretung", "M�ller-Guttenbrunn Iris");
		GenericTreeNode<String> ministerium3mitarbeiter3 = new GenericTreeNode<String>("Mitarbeiterin", "Glaser-Steiner Maria");
		
		GenericTreeNode<String> ministerium4 = new GenericTreeNode<String>("Ministerium", "Bildung, Wissenschaft und Forschung");
		GenericTreeNode<String> ministerium4minister = new GenericTreeNode<String>("Minister", "Heinz Fa�mann");
		GenericTreeNode<String> ministerium4mitarbeiter1 = new GenericTreeNode<String>("Generalsekret�r", "Martin Netzer");
		GenericTreeNode<String> ministerium4mitarbeiter2 = new GenericTreeNode<String>("Ombudsstelle f�r Studierende", "Josef Leidenfrost");
		GenericTreeNode<String> ministerium4mitarbeiter3 = new GenericTreeNode<String>("Ombudsstelle f�r Schulen", "Kurt Nekula");
		
		Collection<ITreeNode<String>> tmp = root.getChildren();
		
		tmp.add(ministerium1);
		tmp.add(ministerium2);
		tmp.add(ministerium3);
		tmp.add(ministerium4);
		
		tmp = ministerium1.getChildren();
		tmp.add(ministerium1minister);
		tmp.add(ministerium1mitarbeiter1);
		tmp.add(ministerium1mitarbeiter2);
		tmp.add(ministerium1mitarbeiter3);
		
		tmp = ministerium2.getChildren();
		tmp.add(ministerium2minister);
		tmp.add(ministerium2mitarbeiter1);
		tmp.add(ministerium2mitarbeiter2);
		tmp.add(ministerium2mitarbeiter3);
		
		tmp = ministerium3.getChildren();
		tmp.add(ministerium3minister);
		tmp.add(ministerium3mitarbeiter1);
		tmp.add(ministerium3mitarbeiter2);
		tmp.add(ministerium3mitarbeiter3);
		
		tmp = ministerium4.getChildren();
		tmp.add(ministerium4minister);
		tmp.add(ministerium4mitarbeiter1);
		tmp.add(ministerium4mitarbeiter2);
		tmp.add(ministerium4mitarbeiter3);

		/* System.out.println("findNode \"Sebastian Kurz\". Result: " + t.findNode("Sebastian Kurz").toString());
		System.out.println("findNode \"Niedrist Clemens-Wolfgang\". Result: " + t.findNode("Niedrist Clemens-Wolfgang").toString());
		System.out.println("findNode \"ministerium4mitarbeiter2\". Result: " + t.findNode(ministerium4mitarbeiter2).toString());
		System.out.println("searchByFilter \"ministerium4mitarbeiter2\". Result: " + t.searchByFilter(new TextSearchIgnoreCaseFilter(), ministerium4mitarbeiter2).toString());
		System.out.println("searchByFilter \"new GenericTreeNode<String>(\"BlaBla\", \"\"))\". Result: " + t.searchByFilter(new TextSearchIgnoreCaseFilter(), new GenericTreeNode<String>("BlaBla", ""))); */

		return t.generateConsoleView("\t");
	}
	
	public static String generateProductTree () {
		Collection<Product> compositeProducts = Application.generateCompositeProducts();
		ProductTreeNode root = new ProductTreeNode((IProduct) new CompositeProduct("Salate", 0.5f, compositeProducts));
		ProductTree t = new ProductTree(root);

		return t.generateConsoleView("\t");
	}
	
	private static List<Product> generateCompositeProducts() {
		List<Product> l = new Vector<Product>();
		
		Product p0 = new SimpleProduct("Vogerlsalat", 1);
		Product p1 = new SimpleProduct("Salz", 0.01f);
		Product p2 = new SimpleProduct("�l", 0.03f);
		Product p3 = new SimpleProduct("Essig", 0.05f);
		Product p4 = new SimpleProduct("Gurke", 1);
		Product p5 = new SimpleProduct("Tomate", 1);
		Product p6 = new SimpleProduct("Pinienkerne", 2);
		Product p7 = new SimpleProduct("Champignions", 3);
		Product p8 = new SimpleProduct("Mozzarella", 1.5f);
		
		Product cp1 = new CompositeProduct("Salat-Dressing", 0);
		Product cp2 = new CompositeProduct("Standard Salat", 15);
		Product cp3 = new CompositeProduct("Tomate-Mozzarella", 10);
		Product cp4 = new CompositeProduct("Champignon Salat", 5);
		Product cp5 = new CompositeProduct("Gebratene Pinienkerne", 10);
		
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
		
		((CompositeProduct) cp5).addProduct((Product) cp1);
		((CompositeProduct) cp5).addProduct((Product) p6);
		((CompositeProduct) cp5).addProduct((Product) p2);
		((CompositeProduct) cp5).addProduct((Product) p1);
		((CompositeProduct) cp5).addProduct((Product) p0);
		
		l.add(cp2);
		l.add(cp3);
		l.add(cp4);
		l.add(cp5);
		
		return l;
	}
}
