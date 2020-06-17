public class CircuitPath {
	private HardwareComponent hwComponent1 = null;
	private HardwareComponent hwComponent2 = null;

	public CircuitPath(HardwareComponent hwComponent1, HardwareComponent hwComponent2) {
		this.hwComponent1 = hwComponent1;
		this.hwComponent2 = hwComponent2;
	}
	
	public HardwareComponent getHWComponent1 () {
		return this.hwComponent1;
	}
	
	public HardwareComponent getHWComponent2 () {
		return this.hwComponent2;
	}
}
