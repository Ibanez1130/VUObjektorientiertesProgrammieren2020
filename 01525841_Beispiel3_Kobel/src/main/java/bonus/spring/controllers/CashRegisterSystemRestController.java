package bonus.spring.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bonus.spring.dto.CashRegisterInfo;
import bonus.spring.dto.CashRegisters;
import bonus.spring.dto.ShoppingCartInfo;

@RestController
public class CashRegisterSystemRestController {

	private static final String APPLICATION_JSON_VALUE = "application/json";
	
	@RequestMapping(method = RequestMethod.GET, value = "/cashregisters", produces = APPLICATION_JSON_VALUE)
	public CashRegisters cashRegisters() {
		// TODO: add your code here
		System.out.println("### WE ARE IN THE API! ###");
		CashRegisters cr = new CashRegisters();
		cr.addCashRegisterID(Long.valueOf(1));
		return cr;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cashregister/{id}", produces = APPLICATION_JSON_VALUE)
	public CashRegisterInfo cashRegister(@PathVariable Long id) {
		// TODO: add your code here
		System.out.println("We are in /cashregister/{id} and the ID is " + id.toString());
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cashregister/{cid}/shoppingcart/{sid}", produces = APPLICATION_JSON_VALUE)
	public ShoppingCartInfo shoppingcarts(@PathVariable Long cid, @PathVariable Long sid) {
		// TODO: add your code here
		return null;
	}
	
	// TODO: add REST-endpoints for:
	//	- ordering products for a specific shoppingcart at a specific cashregister
	//	- for paying shoppingcarts (if elements are present)
	//	- for retrieving paid shoppingcarts
	//	- for retrieving the product sortiment
	
	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {

		response.sendError(HttpStatus.BAD_REQUEST.value());

	}
}