/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 02.05.2020
 */
package util;

public enum ProductCategory {
	FOOD("FOOD"),
	BEVERAGE("BEVERAGE"),
	DEFAULT("DEFAULT");
	
	private final String label;
	
	private ProductCategory (String label) {
		this.label = label;
	}
	
	public String getLabel () {
		return this.label;
	}
}
