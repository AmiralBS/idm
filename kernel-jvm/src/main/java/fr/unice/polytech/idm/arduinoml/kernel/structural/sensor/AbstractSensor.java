package fr.unice.polytech.idm.arduinoml.kernel.structural.sensor;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class AbstractSensor extends Sensor {
	private String type;
	private String initial;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

}
