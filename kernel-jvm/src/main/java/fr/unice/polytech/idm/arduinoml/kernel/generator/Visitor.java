package fr.unice.polytech.idm.arduinoml.kernel.generator;

import java.util.HashMap;
import java.util.Map;

import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.idm.arduinoml.kernel.structural.*;

public abstract class Visitor<T> {

	public abstract void visit(App app);

	public abstract void visit(State state);

	public abstract void visit(Transition transition);

	public abstract void visit(Action action);

	public abstract void visit(DigitalActuator digitalActuator);

	public abstract void visit(LCD lcd);

	public abstract void visit(DigitalSensor digitalSensor);
	
	public abstract void visit(AnalogSensor analogSensor);

	public abstract void visit(Joystick joystick);

	public abstract void visit(Condition condition);

	public abstract void visit(Button button);

	/***********************
	 ** Helper mechanisms **
	 ***********************/

	protected Map<String, Object> context = new HashMap<>();

	protected T result;

	public T getResult() {
		return result;
	}

}
