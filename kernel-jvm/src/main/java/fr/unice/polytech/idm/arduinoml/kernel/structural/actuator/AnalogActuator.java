package fr.unice.polytech.idm.arduinoml.kernel.structural.actuator;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class AnalogActuator extends Actuator {

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
