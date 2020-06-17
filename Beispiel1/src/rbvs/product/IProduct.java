package rbvs.product;

import ict.basics.IDeepCopy;

public interface IProduct extends IDeepCopy {
	String getName();
	
	float getPrice();
}
