package fr.unice.polytech.idm.arduinoml.kernel.structural.sensor;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class DigitalSensor extends Sensor {
	private int pin;

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

}
