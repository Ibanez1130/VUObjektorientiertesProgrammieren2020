public class Capacitor extends HardwareComponent {
	private float capacitorValue = 0.0f;

	public Capacitor(String id, float price) {
		super(id, price);
	}
	
	public Capacitor(String id, float price, float capacitorValue) {
		super(id, price);
		this.capacitorValue = capacitorValue;
	}
	
	public float getCapacitorValue () {
		return this.capacitorValue;
	}
	
	public void setCapacitorValue (float new_capacitorValue) {
		this.capacitorValue = new_capacitorValue;
	}
}
