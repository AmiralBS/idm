package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.BrickApp;
import fr.unice.polytech.idm.arduinoml.kernel.LCDApp;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.generator.BricksToWiring;
import fr.unice.polytech.idm.arduinoml.kernel.generator.LCDToWiring;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Brick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Joystick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Screen;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor;
import groovy.lang.Binding;

public class ArduinoMLModel {
	private List<Brick> bricks;
	private List<State> states;
	private State initialState;
	private Transition transitionInProgress;
	private Operator operatorInProgress;
	private Screen screen;
	private Joystick joystick;

	private Binding binding;

	public ArduinoMLModel(Binding binding) {
		this.bricks = new ArrayList<Brick>();
		this.states = new ArrayList<State>();
		this.binding = binding;
		this.operatorInProgress = Operator.NONE;
	}

	public void createSensor(String name, Integer pinNumber) {
		Sensor sensor = new Sensor();
		sensor.setName(name);
		sensor.setPin(pinNumber);
		this.bricks.add(sensor);
		this.binding.setVariable(name, sensor);
		// System.out.println("> sensor " + name + " on pin " + pinNumber);
	}

	public void createActuator(String name, Integer pinNumber) {
		Actuator actuator = new Actuator();
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
		from.setTransition(transition);
		transitionInProgress = from.getTransition();
	}

	public void createScreen(String name) {
		this.screen = new Screen();
		this.screen.setName(name);
	}

	public void setScreenPins(int... pins) {
		for (int pin : pins) {
			this.screen.getPins().add(pin);
		}
	}

	public void setScreenWidth(int width) {
		this.screen.setWidth(width);
	}

	public void setScreenHeight(int height) {
		this.screen.setHeigth(height);
	}

	public void setScreenRefresh(int refresh) {
		this.screen.setRefresh(refresh);
	}

	public void setInitialState(State state) {
		this.initialState = state;
	}

	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		Visitor codeGenerator;
		if (!bricks.isEmpty()) {
			BrickApp app = new BrickApp();
			app.setName(appName);
			app.setBricks(this.bricks);
			app.setStates(this.states);
			app.setInitial(this.initialState);
			codeGenerator = new BricksToWiring();
			app.accept(codeGenerator);
		} else {
			LCDApp app = new LCDApp();
			app.setName(appName);
			app.setJoystick(this.joystick);
			app.setScreen(this.screen);
			codeGenerator = new LCDToWiring();
			app.accept(codeGenerator);
		}

		return codeGenerator.getResult();
	}
}
