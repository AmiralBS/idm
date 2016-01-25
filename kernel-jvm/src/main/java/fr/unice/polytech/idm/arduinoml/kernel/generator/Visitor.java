package fr.unice.polytech.idm.arduinoml.kernel.generator;

import java.util.HashMap;
import java.util.Map;

import fr.unice.polytech.idm.arduinoml.kernel.BrickApp;
import fr.unice.polytech.idm.arduinoml.kernel.LCDApp;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Joystick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Screen;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor;

public abstract class Visitor<T> {

	public abstract void visit(BrickApp app);

	public abstract void visit(State state);

	public abstract void visit(Transition transition);

	public abstract void visit(Action action);

	public abstract void visit(Actuator actuator);

	public abstract void visit(Sensor sensor);
	
	public abstract void visit(LCDApp app);
	
	public abstract void visit(Joystick joystick);
	
	public abstract void visit(Screen screen);
	

	/***********************
	 ** Helper mechanisms **
	 ***********************/

	protected Map<String, Object> context = new HashMap<>();

	protected T result;

	public T getResult() {
		return result;
	}

}
