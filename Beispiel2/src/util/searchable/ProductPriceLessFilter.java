/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 02.05.2020
 */
package util.searchable;

import rbvs.product.IProduct;

public class ProductPriceLessFilter implements ISearchFilter {

	public boolean searchFilterFunction (Object originalObject, Object compareObject) {
		if (originalObject == null || compareObject == null) return false;
		if (!(originalObject instanceof IProduct) || !(compareObject instanceof IProduct)) return false;
		IProduct originalProduct = (IProduct) originalObject;
		IProduct compareProduct = (IProduct) compareObject;
		return  originalProduct.getPrice() < compareProduct.getPrice();
	}
}
