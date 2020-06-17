public class Configurator {
	public static void main(String[] args) {
		PCB platine = new PCB();
		
		Resistor res1 = new Resistor("50 Ohm", 0.01f, 50f);
		Resistor res2 = new Resistor("250 Ohm", 0.015f, 250f);
		Resistor res3 = new Resistor("330 Ohm", 0.012f, 330f);
		Resistor res4 = new Resistor("1 kOhm", 0.013f, 1000f);
		Resistor res5 = new Resistor("10 kOhm", 0.017f, 10000f);
		Resistor res6 = new Resistor("100 kOhm", 0.02f, 100000f);
		
		System.out.println("Resistor 1 | Id:  " + res1.getId() + ", Price: " + res1.getPrice() + ", Value: " + res1.getResistorValue());
		System.out.println("Resistor 2 | Id:  " + res2.getId() + ", Price: " + res2.getPrice() + ", Value: " + res2.getResistorValue());
		System.out.println("Resistor 3 | Id:  " + res3.getId() + ", Price: " + res3.getPrice() + ", Value: " + res3.getResistorValue());
		System.out.println("Resistor 4 | Id:  " + res4.getId() + ", Price: " + res4.getPrice() + ", Value: " + res4.getResistorValue());
		System.out.println("Resistor 5 | Id:  " + res5.getId() + ", Price: " + res5.getPrice() + ", Value: " + res5.getResistorValue());
		System.out.println("Resistor 6 | Id:  " + res6.getId() + ", Price: " + res6.getPrice() + ", Value: " + res6.getResistorValue());
		
		res1.setId("51 Ohm");
		res1.setPrice(0.011f);
		res1.setResistorValue(51f);
		
		System.out.println("Resistor 1 (after change) | Id:  " + res1.getId() + ", Price: " + res1.getPrice() + ", Value: " + res1.getResistorValue());
		
		Capacitor cap1 = new Capacitor("9 nF", 0.01f, 0.000000009f);
		Capacitor cap2 = new Capacitor("12 nF", 0.015f, 0.0000000012f);
		Capacitor cap3 = new Capacitor("15 nF", 0.017f, 0.0000000015f);
		Capacitor cap4 = new Capacitor("3 nF", 0.02f, 0.000000003f);
		Capacitor cap5 = new Capacitor("5 nF", 0.023f, 0.000000005f);
		Capacitor cap6 = new Capacitor("7 nF", 0.022f, 0.000000007f);
		
		System.out.println("Capacitor 1 | Id:  " + cap1.getId() + ", Price: " + cap1.getPrice() + ", Value: " + cap1.getCapacitorValue());
		System.out.println("Capacitor 2 | Id:  " + cap2.getId() + ", Price: " + cap2.getPrice() + ", Value: " + cap2.getCapacitorValue());
		System.out.println("Capacitor 3 | Id:  " + cap3.getId() + ", Price: " + cap3.getPrice() + ", Value: " + cap3.getCapacitorValue());
		System.out.println("Capacitor 4 | Id:  " + cap4.getId() + ", Price: " + cap4.getPrice() + ", Value: " + cap4.getCapacitorValue());
		System.out.println("Capacitor 5 | Id:  " + cap5.getId() + ", Price: " + cap5.getPrice() + ", Value: " + cap5.getCapacitorValue());
		System.out.println("Capacitor 6 | Id:  " + cap6.getId() + ", Price: " + cap6.getPrice() + ", Value: " + cap6.getCapacitorValue());
		
		cap1.setId("9.1 nF");
		cap1.setPrice(0.011f);
		cap1.setCapacitorValue(0.00000000091f);
		
		System.out.println("Capacitor 1 (after change) | Id:  " + cap1.getId() + ", Price: " + cap1.getPrice() + ", Value: " + cap1.getCapacitorValue());
		
		CircuitPath con1 = new CircuitPath(res1, cap3);
		CircuitPath con2 = new CircuitPath(res2, cap4);
		CircuitPath con3 = new CircuitPath(res3, cap5);
		CircuitPath con4 = new CircuitPath(res4, cap6);
		CircuitPath con5 = new CircuitPath(res5, res1);
		CircuitPath con6 = new CircuitPath(res6, res2);
		
		platine.addConnection(con1);
		platine.addConnection(con2);
		platine.addConnection(con3);
		platine.addConnection(con4);
		platine.addConnection(con5);
		platine.addConnection(con6);
		
		platine.placeComponent(res1);
		platine.placeComponent(res2);
		platine.placeComponent(res3);
		platine.placeComponent(res4);
		platine.placeComponent(cap1);
		platine.placeComponent(cap2);
		platine.placeComponent(cap3);
		platine.placeComponent(cap4);
		
		platine.connectComponents(res1, cap1);
		platine.connectComponents(res2, cap2);
		platine.connectComponents(res3, cap3);
		platine.connectComponents(res4, cap4);
		
		platine.showConnectionDetails();
	}
}
