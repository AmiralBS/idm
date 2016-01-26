package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator;

public class Action implements Visitable {

	private int value;
	private Actuator actuator;

	public Actuator getActuator() {
		return actuator;
	}

	public void setActuator(Actuator actuator) {
		this.actuator = actuator;
	}

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
