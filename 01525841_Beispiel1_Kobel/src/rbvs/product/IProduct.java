/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 13.03.2020
 */
package rbvs.product;

import ict.basics.IDeepCopy;

public interface IProduct extends IDeepCopy {
	String getName();
	
	float getPrice();
}
