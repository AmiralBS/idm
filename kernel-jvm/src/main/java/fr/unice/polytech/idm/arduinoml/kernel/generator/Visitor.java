package fr.unice.polytech.idm.arduinoml.kernel.generator;

import java.util.HashMap;
import java.util.Map;

import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Attribute;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.AnalogActuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.DigitalActuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.AnalogSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.DigitalSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Joystick;

public abstract class Visitor<T> {

	public abstract void visit(App app);

	public abstract void visit(State state);

	public abstract void visit(Transition transition);

	public abstract void visit(Action action);

	public abstract void visit(DigitalActuator digitalActuator);

	public abstract void visit(AnalogActuator analogActuator);

	public abstract void visit(LCD lcd);

	public abstract void visit(DigitalSensor digitalSensor);
	
	public abstract void visit(AnalogSensor analogSensor);
	
	public abstract void visit(Attribute attribute);

	public abstract void visit(Joystick joystick);

	public abstract void visit(Condition condition);
	
	/***********************
	 ** Helper mechanisms **
	 ***********************/

	protected Map<String, Object> context = new HashMap<>();

	protected T result;

	public T getResult() {
		return result;
	}



}
