/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 01.05.2020
 */
package rbvs.product;

import rbvs.copy.IDeepCopy;

public interface IProduct extends IDeepCopy {

	public String getName();
	public float getPrice();
}
