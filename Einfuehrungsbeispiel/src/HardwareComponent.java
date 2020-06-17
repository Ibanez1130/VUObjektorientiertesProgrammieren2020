public abstract class HardwareComponent {
	private String id = null;
	private float price = 0.0f;

	public HardwareComponent(String id, float price) {
		this.id = id;
		this.price = price;
	}

	public String getId () {
		return this.id;
	}
	
	public void setId (String new_id) {
		this.id = new_id;
	}
	
	public float getPrice () {
		return this.price;
	}

	public void setPrice (float new_price) {
		this.price = new_price;
	}
}
