import java.util.Collection;
import java.util.ArrayList;

public class PCB {
	private Collection<HardwareComponent> hwComponents = null;
	private Collection<CircuitPath> connections = null;

	public PCB() {
		this.hwComponents = new ArrayList<HardwareComponent>();
		this.connections = new ArrayList<CircuitPath>();
	}
	
	public void placeComponent(HardwareComponent hw) {
		hwComponents.add(hw);
	}
	
	public boolean connectComponents(HardwareComponent hw1, HardwareComponent hw2) {
		for (HardwareComponent i:this.hwComponents) {
			if (i.equals(hw1)) {
				for (HardwareComponent j:this.hwComponents) {
					if (j.equals(hw2)) {
						return this.connections.add(new CircuitPath(hw1, hw2));
					}
				}
			}
		}
		return false;
	}
	
	public void addConnection(CircuitPath connection) {
		this.hwComponents.add(connection.getHWComponent1());
		this.hwComponents.add(connection.getHWComponent2());
		this.connections.add(connection);
	}
	
	public float calculatePrice () {
		float sum = 0.0f;
		for(HardwareComponent i:this.hwComponents) {
			sum += i.getPrice();
		}
		return sum;
	}
	
	public void showConnectionDetails () {
		for (CircuitPath i:this.connections) {
			System.out.println(i.getHWComponent1().getId() + " <--- connected ---> " + i.getHWComponent2().getId());
		}
		System.out.println("Price (Euro): " + this.calculatePrice());
	}
}
