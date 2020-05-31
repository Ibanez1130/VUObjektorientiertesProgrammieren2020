/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 30.05.2020
 */
package cashregister;

public class CashRegisterFactory {
	private static long CASH_REGISTER_ID;

	public CashRegisterFactory() {
		// TODO Auto-generated constructor stub
	}

	public static ICashRegister createCashRegister () {
		CashRegisterFactory.CASH_REGISTER_ID++;
		return new CashRegister(CashRegisterFactory.CASH_REGISTER_ID);
	}
}
