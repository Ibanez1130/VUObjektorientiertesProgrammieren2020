package cashregister;

public class CashRegisterFactory {
	private static long CASH_REGISTER_ID = 0;

	public CashRegisterFactory() {
		// TODO Auto-generated constructor stub
	}

	public static ICashRegister createCashRegister () {
		CashRegisterFactory.CASH_REGISTER_ID++;
		return new CashRegister(CashRegisterFactory.CASH_REGISTER_ID);
	}
}
