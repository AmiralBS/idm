package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.EValue;

public class Action implements Visitable {

	private EValue value;
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

	public EValue getValue() {
		return value;
	}

	public void setValue(EValue value) {
		this.value = value;
	}
}
