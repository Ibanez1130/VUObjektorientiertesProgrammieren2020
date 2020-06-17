/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 01.06.2020
 */
package bonus.spring.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class CashRegisters {

	private Collection<Long> cashRegisterIDs = new ArrayList<>();

	public Collection<Long> getCashRegisterIDs() {
		return cashRegisterIDs;
	}

	public void setCashRegisterIDs(Collection<Long> cashRegisterIDs) {
		this.cashRegisterIDs = cashRegisterIDs;
	}
	
	public void addCashRegisterID(Long id) {
		this.cashRegisterIDs.add(id);
	}
	
	public String toString () {
		return "[" + this.cashRegisterIDs.stream().map(e -> e.toString()).collect(Collectors.joining(",")) + "]";
	}
	
	// TODO: add additional attributes (if necessary)
}
