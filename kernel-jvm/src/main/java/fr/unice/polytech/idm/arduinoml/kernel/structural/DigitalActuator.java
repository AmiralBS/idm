package fr.unice.polytech.idm.arduinoml.kernel.structural;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class DigitalActuator extends Actuator {

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

}
