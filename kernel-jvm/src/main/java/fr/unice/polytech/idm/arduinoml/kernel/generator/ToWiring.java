package fr.unice.polytech.idm.arduinoml.kernel.generator;

import fr.unice.polytech.idm.arduinoml.kernel.BrickApp;
import fr.unice.polytech.idm.arduinoml.kernel.LCDApp;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Joystick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Screen;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor;

public class ToWiring extends Visitor<StringBuffer> {

	public ToWiring() {
		this.result = new StringBuffer();
	}

	protected void wln(String s) {
		result.append(String.format("%s\n", s));
	}

	protected void w(String s) {
		result.append(String.format("%s", s));
	}

	@Override
	public void visit(BrickApp app) {
	}

	@Override
	public void visit(State state) {
	}

	@Override
	public void visit(Transition transition) {
	}

	@Override
	public void visit(Action action) {
	}

	@Override
	public void visit(Actuator actuator) {
	}

	@Override
	public void visit(Sensor sensor) {
	}

	@Override
	public void visit(LCDApp app) {
	}

	@Override
	public void visit(Joystick joystick) {
	}

	@Override
	public void visit(Screen screen) {
	}
}
