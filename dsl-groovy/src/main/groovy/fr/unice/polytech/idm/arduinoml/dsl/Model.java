package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.exception.ElementNotFoundException;
import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
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
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.DigitalSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Sensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EInt;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.ESignal;
import groovy.lang.Binding;

public class Model implements ConfigName {
	private List<Brick> bricks;
	private List<State> states;
	private State initialState;

	private Binding binding;

	public Model(Binding binding) {
		this.bricks = new ArrayList<Brick>();
		this.states = new ArrayList<State>();
		this.binding = binding;

		this.binding.setVariable(CURRENT_STATE_ID, 0);
		this.binding.setVariable(CURRENT_OPERATOR, Operator.NONE);
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

	public void createState(String name) {
		State state = new State();
		state.setName(name);

		int id = (int) this.binding.getVariable(CURRENT_STATE_ID);
		state.setIdent(++id);
		this.binding.setVariable(CURRENT_STATE_ID, id);

		this.states.add(state);
		this.binding.setVariable(name, state);
		this.binding.setVariable(CURRENT_STATE, state);
	}

	public void createAction(Actuator actuator, ESignal signal) throws ElementNotFoundException {
		State currentState = (State) this.binding.getVariable(CURRENT_STATE);

		if (currentState == null)
			throw new ElementNotFoundException();

		Action action = new Action();
		action.setActuator(actuator);
		action.setValue(signal);

		currentState.getActions().add(action);
	}

	public void createTransition(State from, State to) {
		Transition transition = new Transition();
		transition.setNext(to);
		from.getTransitions().add(transition);

		this.binding.setVariable(CURRENT_TRANSITION, transition);
	}

	public void createCondition(Sensor sensor, BinaryOperator binaryOperator, ESignal signal)
			throws ElementNotFoundException {
		createCondition(sensor, binaryOperator, (signal.equals(ESignal.HIGH) ? 1 : 0));
	}

	public void createCondition(Sensor sensor, BinaryOperator binaryOperator, int value)
			throws ElementNotFoundException {
		Transition currentTransition = (Transition) this.binding.getVariable(CURRENT_TRANSITION);

		if (currentTransition == null)
			throw new ElementNotFoundException();

		Condition condition = new Condition();
		condition.setSensor(sensor);
		condition.setBinaryOperator(binaryOperator);
		condition.setValue(new EInt(value));
		
		condition.setOperator((Operator) this.binding.getVariable(CURRENT_OPERATOR));
		this.binding.setVariable(CURRENT_OPERATOR, Operator.NONE);

		currentTransition.getConditions().add(condition);
	}

	public void setInitialState(State state) {
		this.initialState = state;
	}

	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		App app = new App();
		app.setName(appName);
		app.setBricks(this.bricks);
		app.setStates(this.states);
		app.setInitial(this.initialState);
		Visitor codeGenerator = new ToWiring();
		app.accept(codeGenerator);

		return codeGenerator.getResult();
	}
}
