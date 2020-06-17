package bonus.spring.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bonus.spring.dto.CashRegisterInfo;
import bonus.spring.dto.CashRegisters;
import bonus.spring.dto.ShoppingCartInfo;
import bonus.spring.dto.Records;

import managementserver.IManagementServer;
import managementserver.ManagementServer;
import cashregister.CashRegister;
import cashregister.ICashRegister;
import cashregister.NotRegisteredException;
import cashregister.IShoppingCart;
import cashregister.ShoppingCartNotFoundException;
import tree.ITree;
import rbvs.product.IProduct;
import rbvs.record.IInvoice;
import paymentprovider.*;

@RestController
public class CashRegisterSystemRestController {
	private static final String APPLICATION_JSON_VALUE = "application/json";

	@RequestMapping(method = RequestMethod.GET, value = "/cashregisters", produces = APPLICATION_JSON_VALUE)
	public CashRegisters cashRegisters () {
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		CashRegisters crs = new CashRegisters();
		for (ICashRegister cr:mngServer.retrieveRegisteredCashRegisters()) {
			crs.addCashRegisterID(cr.getID());
		}
		return crs;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cashregister/{id}", produces = APPLICATION_JSON_VALUE)
	public CashRegisterInfo cashRegister (@PathVariable Long id) {
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		CashRegisterInfo cri = new CashRegisterInfo();
		try {
			ICashRegister cr = mngServer.retrieveRegisteredCashRegister(id);
			cri.setId(cr.getID());
			String scString = cr.getShoppingCarts().stream().map(s -> s.toString()).collect(Collectors.joining(", "));
			cri.setInfo("Shopping Carts [ " + scString + " ]");
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
		return cri;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cashregister/{cid}/shoppingcart/{sid}", produces = APPLICATION_JSON_VALUE)
	public ShoppingCartInfo shoppingCarts (@PathVariable Long cid, @PathVariable Long sid) {
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		ShoppingCartInfo sci = new ShoppingCartInfo();
		try {
			ICashRegister cr = mngServer.retrieveRegisteredCashRegister(cid);
			Collection<IShoppingCart> scs = cr.getShoppingCarts();
			for (IShoppingCart sc:scs) {
				if (sc.getShoppingCartID() == sid) {
					sci.setShoppingCartElements(sc.currentElements());
				}
			}
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
		return sci;
	}

	// TODO: add REST-endpoints for:
	//	- ordering products for a specific shoppingcart at a specific cashregister
	//	- for paying shoppingcarts (if elements are present)
	//	- for retrieving paid shoppingcarts
	//	- for retrieving the product sortiment

	@RequestMapping(method = RequestMethod.POST, value = "/order/cashregister/{cid}/shoppingcart/{sid}", produces = APPLICATION_JSON_VALUE)
	public Boolean orderProductForShoppingCart (@PathVariable Long cid, @PathVariable Long sid, @RequestBody String payload) {
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		Boolean success = false;
		try {
			ICashRegister cr = mngServer.retrieveRegisteredCashRegister(cid);
			success = cr.addProductToShoppingCart(sid, cr.selectProduct(payload));
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
		return success;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/productassortment", produces = APPLICATION_JSON_VALUE)
	public String getProductAssortment () {
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		ITree<IProduct> t = mngServer.retrieveProductSortiment();
		return t.generateConsoleView("\t");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/pay/cashregister/{cid}/shoppingcart/{sid}/{cardtype}", produces = APPLICATION_JSON_VALUE)
	public IInvoice payShoppingCart (@PathVariable Long cid, @PathVariable Long sid, @PathVariable String cardtype, @RequestBody String payload) {
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		IInvoice i = null;
		IPayment pp = (cardtype.equalsIgnoreCase("creditcard")) ? new CreditCardProvider(payload) : new BankCardProvider(payload);
		try {
			ICashRegister cr = mngServer.retrieveRegisteredCashRegister(cid);
			i = cr.payShoppingCart(sid, pp);
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		} catch (ShoppingCartNotFoundException sce) {
			sce.printStackTrace();
		}
		return i;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/paidshoppingcarts", produces = APPLICATION_JSON_VALUE)
	public Records paidShoppingCarts () {
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		Records r = new Records();
		Collection<ICashRegister> crs = mngServer.retrieveRegisteredCashRegisters();
		for (ICashRegister cr:crs) {
			for (IInvoice i:((CashRegister) cr).getRecords()) {
				r.addRecord(i);
			}
		}
		return r;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/paidshoppingcarts/cashregister/{cid}", produces = APPLICATION_JSON_VALUE)
	public Records paidShoppingCarts (@PathVariable Long cid) {
		// In this method we just return the records of a cash register. Since a record does not include the id of the shopping card, only its elements
		// and a shopping cart has no flat which states if it was paid for or not, there is no sufficient way to check if a shopping cart has been paid.
		// But since the records hold the same information as the shopping carts (except the id) there is no real difference between those two, tbh.
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		Records r = new Records();
		try {
			ICashRegister cr = mngServer.retrieveRegisteredCashRegister(cid);
			r.setRecords(((CashRegister) cr).getRecords());
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}