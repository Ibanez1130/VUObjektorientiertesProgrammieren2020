public class Resistor extends HardwareComponent {
	private float resistorValue = 0.0f;

	public Resistor(String id, float price) {
		super(id, price);
	}

	public Resistor(String id, float price, float resistorValue) {
		super(id, price);
		this.resistorValue = resistorValue;
	}
	
	public float getResistorValue () {
		return this.resistorValue;
	}
	
	public void setResistorValue (float new_resistorValue) {
		this.resistorValue = new_resistorValue;
	}
}
