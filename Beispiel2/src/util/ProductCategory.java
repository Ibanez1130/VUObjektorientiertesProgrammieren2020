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
