package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.unice.polytech.idm.arduinoml.exception.ElementNotFoundException;
import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Attribute;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.generator.ToWiring;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Brick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.Actuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.DigitalActuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.AnalogSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.DigitalSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.IKonamiCode;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Joystick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Sensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EInt;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.ESignal;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EString;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EValue;
import groovy.lang.Binding;

public class Model implements BindName {
	private List<Brick> bricks;
	private List<State> states;
	private List<Attribute> attributes;
	private State initialState;

	private Binding binding;
	private Binder binder;

	public Model(Binding binding) {
		this.bricks = new ArrayList<Brick>();
		this.states = new ArrayList<State>();
		this.attributes = new ArrayList<Attribute>();
		this.binding = binding;
		this.binder = new Binder(this);

		this.binding.setVariable(CURRENT_STATE_ID, 0);
		this.binding.setVariable(CURRENT_OPERATOR, Operator.NONE);

		Map<Integer, List<Integer>> busPins = new HashMap<>();
		busPins.put(1, Arrays.asList(3, 4, 5, 6, 7, 8, 9));
		busPins.put(2, Arrays.asList(10, 11, 12, 13, 14, 15, 16));
		busPins.put(3, Arrays.asList(17, 18, 19, 20, 21, 22, 23));
		this.binding.setVariable(BUS_PINS, busPins);
	}

	public void createSensor(String name, Integer pinNumber) {
		DigitalSensor sensor = new DigitalSensor();
		sensor.setName(name);
		sensor.setPin(pinNumber);

		this.bricks.add(sensor);
		this.binding.setVariable(name, sensor);
	}

	public void createActuator(String name, Integer pinNumber) {
		DigitalActuator actuator = new DigitalActuator();
		actuator.setName(name);
		actuator.setPin(pinNumber);

		this.bricks.add(actuator);
		this.binding.setVariable(name, actuator);
	}

	@SuppressWarnings("unchecked")
	public void createLCD(String name, Integer bus) throws ElementNotFoundException {
		LCD lcd = new LCD();
		lcd.setName(name);

		Map<Integer, List<Integer>> busPins = (Map<Integer, List<Integer>>) this.binding.getVariable(BUS_PINS);
		if (!busPins.containsKey(bus))
			throw new ElementNotFoundException("bus " + bus + " not defined");
		lcd.setConfig(busPins.get(bus));

		lcd.setCols(16);
		lcd.setRows(2);
		lcd.setRefresh(250);

		this.bricks.add(lcd);
		this.binding.setVariable(name, lcd);
	}

	public void createJoystick(String name, Integer x, Integer y, Integer b) {
		Joystick joystick = new Joystick();
		joystick.setName(name);

		AnalogSensor horizontal = new AnalogSensor();
		horizontal.setName(name + "X");
		horizontal.setPin(x);

		AnalogSensor vertical = new AnalogSensor();
		vertical.setName(name + "Y");
		vertical.setPin(y);

		DigitalSensor button = new DigitalSensor();
		button.setName(name + "B");
		button.setPin(b);

		joystick.setButton(button);
		joystick.setHorizontal(horizontal);
		joystick.setVertical(vertical);

		this.bricks.add(joystick);
		this.binding.setVariable(name, joystick);
		this.binding.setVariable(horizontal.getName(), horizontal);
		this.binding.setVariable(vertical.getName(), vertical);
		this.binding.setVariable(button.getName(), button);
	}

	public State createState(String name) {
		if (this.binding.getVariables().containsKey(name))
			return (State) this.binding.getVariable(name);

		State state = new State();
		state.setName(name);

		int id = (int) this.binding.getVariable(CURRENT_STATE_ID);
		state.setIdent(++id);
		this.binding.setVariable(CURRENT_STATE_ID, id);

		this.states.add(state);
		this.binding.setVariable(name, state);
		this.binding.setVariable(CURRENT_STATE, state);

		return state;
	}

	public Action createAction(Attribute attribute) throws ElementNotFoundException {
		State currentState = (State) this.binding.getVariable(CURRENT_STATE);

		if (currentState == null)
			throw new ElementNotFoundException();

		Action action = new Action();
		action.setActionable(attribute);

		currentState.getActions().add(action);
		
		return action;
	}

	public void createAction(Actuator actuator, EValue value) throws ElementNotFoundException {
		State currentState = (State) this.binding.getVariable(CURRENT_STATE);

		if (currentState == null)
			throw new ElementNotFoundException();

		Action action = new Action();
		action.setActionable(actuator);
		action.setValue(value);

		currentState.getActions().add(action);
	}

	public void createAction(LCD lcd, String message) throws ElementNotFoundException {
		createAction(lcd, new EString(message));
	}

	public void createTransition(State from, State to) {
		Transition transition = new Transition();
		transition.setNext(to);
		from.getTransitions().add(transition);

		this.binding.setVariable(CURRENT_TRANSITION, transition);
	}

	public void createTransition(State from, State to, List<Action> actions) {
		Transition transition = new Transition();
		transition.setNext(to);
		transition.setActions(actions);
		from.getTransitions().add(transition);

		this.binding.setVariable(CURRENT_TRANSITION, transition);
	}

	public void createCondition(Sensor sensor, BinaryOperator binaryOperator, ESignal signal)
			throws ElementNotFoundException {
		createCondition(sensor, binaryOperator, (signal.equals(ESignal.HIGH) ? 1 : 0));
	}

	public void createCondition(Sensor sensor, BinaryOperator binaryOperator, int value, Operator operator)
			throws ElementNotFoundException {
		createCondition(sensor, binaryOperator, value);
		this.binding.setVariable(CURRENT_OPERATOR, operator);
	}

	public void createCondition(Sensor sensor, BinaryOperator binaryOperator, ESignal signal, Operator operator)
			throws ElementNotFoundException {
		createCondition(sensor, binaryOperator, signal);
		this.binding.setVariable(CURRENT_OPERATOR, operator);
	}

	public void createCondition(Attribute attribute, BinaryOperator binaryOperator, int value)
			throws ElementNotFoundException {
		Transition currentTransition = (Transition) this.binding.getVariable(CURRENT_TRANSITION);

		if (currentTransition == null)
			throw new ElementNotFoundException();

		Condition condition = new Condition();
		condition.setConditionable(attribute);
		condition.setBinaryOperator(binaryOperator);
		condition.setValue(new EInt(value));

		condition.setOperator((Operator) this.binding.getVariable(CURRENT_OPERATOR));
		this.binding.setVariable(CURRENT_OPERATOR, Operator.NONE);

		currentTransition.getConditions().add(condition);
	}

	public void createCondition(Sensor sensor, BinaryOperator binaryOperator, int value)
			throws ElementNotFoundException {
		Transition currentTransition = (Transition) this.binding.getVariable(CURRENT_TRANSITION);

		if (currentTransition == null)
			throw new ElementNotFoundException();

		Condition condition = new Condition();
		condition.setConditionable(sensor);
		condition.setBinaryOperator(binaryOperator);
		condition.setValue(new EInt(value));

		condition.setOperator((Operator) this.binding.getVariable(CURRENT_OPERATOR));
		this.binding.setVariable(CURRENT_OPERATOR, Operator.NONE);

		currentTransition.getConditions().add(condition);
	}

	public void bind(Joystick joystick, LCD lcd) throws ElementNotFoundException {
		this.binder.bind(joystick, lcd);
	}

	public void bind(Joystick joystick, LCD lcd, List<IKonamiCode> codes) throws ElementNotFoundException {
		this.binder.bind(joystick, lcd, codes);
	}

	public void setOperator(Operator operator) {
		this.binding.setVariable(CURRENT_OPERATOR, operator);
	}

	public void setInitialState(State state) {
		this.initialState = state;
	}

	public Attribute createAttribute(String type, String name, String initial) {
		Attribute counter = new Attribute();
		counter.setType(type);
		counter.setName(name);
		counter.setInitial(initial);

		this.attributes.add(counter);

		return counter;
	}

	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		App app = new App();
		app.setName(appName);
		app.setBricks(this.bricks);
		app.setStates(this.states);
		app.setAttributes(attributes);
		app.setInitial(this.initialState);
		Visitor codeGenerator = new ToWiring();
		app.accept(codeGenerator);

		return codeGenerator.getResult();
	}

}
