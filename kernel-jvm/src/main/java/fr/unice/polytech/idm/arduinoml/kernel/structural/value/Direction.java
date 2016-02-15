package fr.unice.polytech.idm.arduinoml.kernel.structural.value;

import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.IKonamiCode;

public enum Direction implements IKonamiCode {
	LEFT, RIGHT, UP, DOWN, PUSHED, NONE;

	@Deprecated
	@Override
	public void setName(String name) {
	}

	@Override
	public String getName() {
		return this.toString().toLowerCase();
	}
}
