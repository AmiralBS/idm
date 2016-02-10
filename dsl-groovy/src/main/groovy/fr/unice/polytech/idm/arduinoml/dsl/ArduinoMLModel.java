package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.unice.polytech.idm.arduinoml.business.Direction;
import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.generator.ToWiring;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.*;
import groovy.lang.Binding;

public class ArduinoMLModel {
	private List<Brick> bricks;
	private List<State> states;
	private State initialState;
	private Transition transitionInProgress;
	private Operator operatorInProgress;

	private static Map<Integer, List<Integer>> bus;
	private static int countStateID = 0;

	private Binding binding;

	public ArduinoMLModel(Binding binding) {
		this.bricks = new ArrayList<Brick>();
		this.states = new ArrayList<State>();
		this.binding = binding;
		this.operatorInProgress = Operator.NONE;

		bus = new HashMap<>();
		bus.put(1, Arrays.asList(3, 4, 5, 6, 7, 8, 9)); // 2 à 8
		bus.put(2, Arrays.asList(10, 11, 12, 13, 14, 15, 16));
		bus.put(3, Arrays.asList(17, 18, 19, 20, 21, 22, 23)); // 12 11 5 4 3 2
	}

	public void createSensor(String name, Integer pinNumber) {
		DigitalSensor sensor = new DigitalSensor();
		sensor.setName(name);
		sensor.setPin(pinNumber);
		if (!bricks.contains(sensor)) {
			this.bricks.add(sensor);
		}
		this.binding.setVariable(name, sensor);
	}

	public void createActuator(String name, Integer pinNumber) {
		DigitalActuator actuator = new DigitalActuator();
		actuator.setName(name);
		actuator.setPin(pinNumber);
		if (!bricks.contains(actuator)) {
			this.bricks.add(actuator);
		}
		this.binding.setVariable(name, actuator);
	}

	public void createState(String name) {
		State state = new State();
		state.setName(name);
		state.setIdent(++countStateID);
		if (!bricks.contains(state)) {
			this.states.add(state);
		}
		this.binding.setVariable(name, state);
	}

	public void createLCD(int n) {
		LCD lcd = new LCD();
		lcd.setName("lcd");
		lcd.setConfig(bus.get(n));
		lcd.setCols(16);
		lcd.setRows(2);
		lcd.setRefresh(500);
		if (!bricks.contains(lcd)) {
			this.bricks.add(lcd);
		}
		this.binding.setVariable(lcd.getName(), lcd);
	}

	public void createButton(int x){
        Button button = new Button();
        button.setName("button");

        DigitalSensor input = new DigitalSensor();
        input.setName("inputButton");
        input.setPin(x);

        if (!bricks.contains(button)) {
            this.bricks.add(button);
        }
        this.binding.setVariable("button", button);
        this.binding.setVariable(button.getName(), button);
	}

	public void createJoystick(int x, int y, int b) {
		Joystick joystick = new Joystick();
		joystick.setName("joystick");

		AnalogSensor horizontal = new AnalogSensor();
		horizontal.setName("joystickX");
		horizontal.setPin(x);

		AnalogSensor vertical = new AnalogSensor();
		vertical.setName("joystickY");
		vertical.setPin(y);

		DigitalSensor button = new DigitalSensor();
		button.setName("joystickB");
		button.setPin(b);

		joystick.setButton(button);
		joystick.setHorizontal(horizontal);
		joystick.setVertical(vertical);

		if (!bricks.contains(joystick)) {
			this.bricks.add(joystick);
		}
		this.binding.setVariable("joystick", joystick);
		this.binding.setVariable(horizontal.getName(), horizontal);
		this.binding.setVariable(vertical.getName(), vertical);
		this.binding.setVariable(button.getName(), button);
	}

	public void bindJoystick(Joystick joystick, Direction direction) {
		createState("neutral");
	}

	public void addActionToLastState(LCD lcd, String message) {
		Action action = new Action();

		LCD lcdCopy = new LCD();
		lcdCopy.setCols(lcd.getCols());
		lcdCopy.setConfig(lcd.getConfig());
		lcdCopy.setName(lcd.getName());
		lcdCopy.setRefresh(lcd.getRefresh());
		lcdCopy.setMessage(new EString(message));
		action.setActuator(lcdCopy);
		this.states.get(this.states.size() - 1).getActions().add(action);
	}

	public void addActionToLastState(Actuator actuator, int value) {
		Action action = new Action();
		action.setActuator(actuator);
		action.setValue(new EInt(value));
		this.states.get(this.states.size() - 1).getActions().add(action);
	}
	
	public void addActionToLastState(Actuator actuator, ESignal value) {
		Action action = new Action();
		action.setActuator(actuator);
		action.setValue(value);
		this.states.get(this.states.size() - 1).getActions().add(action);
	}

	public void setOperator(Operator operator) {
		this.operatorInProgress = operator;
	}

	private void addConditionToLastTransition(Condition condition) {
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

	public void createCondition(Sensor sensor, int value, BinaryOperator op) {
		Condition condition = new Condition();
		condition.setSensor(sensor);
		condition.setValue(new EInt(value));
		condition.setBinaryOperator(op);
		addConditionToLastTransition(condition);
	}
	
	public void createCondition(Sensor sensor, ESignal value, BinaryOperator op) {
		Condition condition = new Condition();
		condition.setSensor(sensor);
		condition.setValue(value);
		condition.setBinaryOperator(op);
		addConditionToLastTransition(condition);
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
