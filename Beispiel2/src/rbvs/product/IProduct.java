package rbvs.product;

import rbvs.copy.IDeepCopy;

public interface IProduct extends IDeepCopy {

	public String getName();
	public float getPrice();
}
