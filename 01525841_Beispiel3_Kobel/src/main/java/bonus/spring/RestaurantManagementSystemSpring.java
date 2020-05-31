package bonus.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import cashregister.CashRegisterFactory;
import cashregister.ICashRegister;
import cashregister.ui.CashRegisterConsoleUI;
import cashregister.ui.CashRegisterGUI;
import cashregister.ui.ICashRegisterUI;
import managementserver.IManagementServer;
import managementserver.ManagementServer;
import managementtools.ManagementServerViewer;
import rbvs.product.CompositeProduct;
import rbvs.product.ProductCategory;
import rbvs.product.SimpleProduct;
import warehouse.IWarehouse;
import warehouse.IWarehouseListener;
import warehouse.Warehouse;
import warehouse.ui.WarehouseManager;

@SpringBootApplication
public class RestaurantManagementSystemSpring {

	public RestaurantManagementSystemSpring() {
		this.run();
	}

	public void run() {
		// TODO: Place your code from RestaurantManagementSystem here.
		// this method starts the application as normal. No special attention is needed
		// for the REST-Controllers in package bonus.spring.controllers
		
		System.out.println("Yes, we are in here!");

		// Create ManagementServer and Warehouse
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		IWarehouse warehouse = Warehouse.GET_INSTANCE();
		
		// TODO: register mngServer as listener at the warehouse
		((Warehouse) warehouse).registerListener((IWarehouseListener) mngServer);

		// TODO: add Products to warehouse
		SimpleProduct apfel = new SimpleProduct("Apfel", 1.2f, ProductCategory.FOOD);
		SimpleProduct birne = new SimpleProduct("Birne", 1.5f, ProductCategory.FOOD);
		SimpleProduct banane = new SimpleProduct("Banane", 2.5f, ProductCategory.FOOD);
		SimpleProduct pfirsich = new SimpleProduct("Pfirsich", 2.0f, ProductCategory.FOOD);
		SimpleProduct wasser = new SimpleProduct("Wasser", 0.1f, ProductCategory.BEVERAGE);
		SimpleProduct verpackung = new SimpleProduct("Verpackung", 0.2f, ProductCategory.DEFAULT);

		CompositeProduct smoothie = new CompositeProduct("Smoothie", 15, ProductCategory.BEVERAGE);
		smoothie.addProduct(apfel);
		smoothie.addProduct(birne);
		smoothie.addProduct(banane);
		smoothie.addProduct(wasser);
		smoothie.addProduct(verpackung);

		CompositeProduct obstsalat = new CompositeProduct("Obstsalat", 10, ProductCategory.FOOD);
		obstsalat.addProduct(apfel);
		obstsalat.addProduct(birne);
		obstsalat.addProduct(banane);
		obstsalat.addProduct(pfirsich);
		obstsalat.addProduct(verpackung);
		
		CompositeProduct apfelsaft = new CompositeProduct("Apfelsaft", 10, ProductCategory.BEVERAGE);
		apfelsaft.addProduct(apfel);
		apfelsaft.addProduct(birne);
		apfelsaft.addProduct(wasser);
		
		warehouse.addProduct(apfel);
		warehouse.addProduct(birne);
		warehouse.addProduct(banane);
		warehouse.addProduct(pfirsich);
		warehouse.addProduct(wasser);
		warehouse.addProduct(verpackung);

		warehouse.addProduct(smoothie);
		warehouse.addProduct(obstsalat);
		warehouse.addProduct(apfelsaft);
		
		// TODO: create CashRegister and register it as an observer at the mngServer
		ICashRegister cr = CashRegisterFactory.createCashRegister();
		mngServer.addCashRegister(cr);

		// TODO: Create GUI for CashRegister
		ICashRegisterUI ui = new CashRegisterConsoleUI();
		ICashRegisterUI gui = new CashRegisterGUI(cr);

		new WarehouseManager(warehouse);
		new ManagementServerViewer(ManagementServer.GET_INSTANCE());

		// TODO: register CashRegisterGUI as an UI at the previously created cashRegister
		cr.registerCashRegisterUI(ui);
		cr.registerCashRegisterUI(gui);
	}

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(RestaurantManagementSystemSpring.class);
		builder.headless(false);
		builder.run(args);
	}
}
