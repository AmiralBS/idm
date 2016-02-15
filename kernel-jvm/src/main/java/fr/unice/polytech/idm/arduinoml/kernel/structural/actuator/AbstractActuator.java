package fr.unice.polytech.idm.arduinoml.kernel.structural.actuator;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class AbstractActuator extends Actuator {

	private String action;

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
