package fr.unice.polytech.idm.arduinoml.kernel.structural;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class Joystick extends Sensor {

	private AnalogSensor horizontal;
	private AnalogSensor vertical;
	private DigitalSensor button;

	public AnalogSensor getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(AnalogSensor horizontal) {
		this.horizontal = horizontal;
	}

	public AnalogSensor getVertical() {
		return vertical;
	}

	public void setVertical(AnalogSensor vertical) {
		this.vertical = vertical;
	}

	public DigitalSensor getButton() {
		return button;
	}

	public void setButton(DigitalSensor button) {
		this.button = button;
	}

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

}
