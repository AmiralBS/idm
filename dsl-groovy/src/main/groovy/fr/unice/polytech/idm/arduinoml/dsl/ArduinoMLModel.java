package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.generator.ToWiring;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Brick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.DigitalActuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.DigitalSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor;
import groovy.lang.Binding;

public class ArduinoMLModel {
	private List<Brick> bricks;
	private List<State> states;
	private State initialState;
	private Transition transitionInProgress;
	private Operator operatorInProgress;

	private Binding binding;

	public ArduinoMLModel(Binding binding) {
		this.bricks = new ArrayList<Brick>();
		this.states = new ArrayList<State>();
		this.binding = binding;
		this.operatorInProgress = Operator.NONE;
	}

	public void createSensor(String name, Integer pinNumber) {
		Sensor sensor = new DigitalSensor();
		sensor.setName(name);
		sensor.setPin(pinNumber);
		this.bricks.add(sensor);
		this.binding.setVariable(name, sensor);
	}

	public void createActuator(String name, Integer pinNumber) {
		Actuator actuator = new DigitalActuator();
		actuator.setName(name);
		actuator.setPin(pinNumber);
		this.bricks.add(actuator);
		this.binding.setVariable(name, actuator);
	}

	public void createState(String name) {
		State state = new State();
		state.setName(name);
		this.states.add(state);
		this.binding.setVariable(name, state);
	}
	
	public void createLCD(LCD lcd) {
		this.bricks.add(lcd);
		this.binding.setVariable(lcd.getName(), lcd);
	}

	public void addActionToLastState(Action action) {
		this.states.get(this.states.size() - 1).getActions().add(action);
	}

	public void setOperator(Operator operator) {
		this.operatorInProgress = operator;
	}

	public void addConditionToLastTransition(Condition condition) {
		if (this.operatorInProgress != Operator.NONE) {
			condition.setOperator(operatorInProgress);
		}
		this.transitionInProgress.getConditions().add(condition);
		this.operatorInProgress = Operator.NONE;
	}

	public void createTransition(State from, State to) {
		Transition transition = new Transition();
		transition.setNext(to);
		transition.setConditions(new ArrayList<>());
		from.getTransitions().add(transition);
		transitionInProgress = from.getTransitions().get(from.getTransitions().size() - 1);
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
