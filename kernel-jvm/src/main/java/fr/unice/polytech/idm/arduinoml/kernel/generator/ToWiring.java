package fr.unice.polytech.idm.arduinoml.kernel.generator;

import java.util.StringJoiner;

import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Joystick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Brick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.DigitalActuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.DigitalSensor;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {
	private static final String BRICKS_MODE = "brick_mode";
	private static final int SETUP = 1;
	private static final int LOOP = 2;
	private static final int STATE = 3;

	private static final String LCD = "lcd";
	private static final String ACTION = "action";

	public ToWiring() {
		this.result = new StringBuffer();
	}

	private void wln(String s) {
		result.append(String.format("%s\n", s));
	}

	private void w(String s) {
		result.append(String.format("%s", s));
	}

	@Override
	public void visit(App app) {
		wln("// Wiring code generated from an ArduinoML model");
		wln(String.format("// Application name: %s\n", app.getName()));

		wln("#include <LiquidCrystal.h>");
		wln("int joyX, joyY, button;");
		wln("LiquidCrystal* lcd;");
		
		wln("void setup(){");
		context.put(BRICKS_MODE, SETUP);
		for (Brick brick : app.getBricks()) {
			brick.accept(this);
		}
		wln("}\n");

		wln("long time = 0; long debounce = 200;\n");

		if (context.containsKey(LCD)) {
			wln("void write(String input){");
			wln("  lcd->clear();");
			wln("  lcd->setCursor(0,0);");
			wln("  lcd->print(input);");
			wln("  delay(" + ((LCD) context.get(LCD)).getRefresh() + ");");
			wln("}");
		}

		context.put(BRICKS_MODE, STATE);
		for (State state : app.getStates()) {
			state.accept(this);
		}

		context.put(BRICKS_MODE, LOOP);
		wln("void loop() {");
		wln(String.format("  state_%s();", app.getInitial().getName()));
		wln("}");
	}

	@Override
	public void visit(State state) {
		wln(String.format("void state_%s() {", state.getName()));

		for (Action action : state.getActions()) {
			context.put(ACTION, action);
			action.accept(this);
		}

		wln("  boolean guard = millis() - time > debounce;");

		for (int i = 0; i < state.getTransitions().size(); i++) {
			if (i == 0) {
				w("  if");
				state.getTransitions().get(i).accept(this);
			} else {
				w(" else if");
				state.getTransitions().get(i).accept(this);
			}
		}
		wln(" else {");
		wln(String.format("    state_%s();", state.getName()));
		wln("  }");
		wln("}\n");

	}

	@Override
	public void visit(Action action) {
		action.getActuator().accept(this);
	}

	@Override
	public void visit(Transition transition) {
		w("(");
		for (Condition condition : transition.getConditions()) {
			condition.accept(this);
		}
		wln("&& guard)");
		wln("    time = millis();");
		wln(String.format("    state_%s();", transition.getNext().getName()));
		w("  }");
	}

	@Override
	public void visit(Condition condition) {
		if (condition.getOperator() != null) {
			if (condition.getOperator().equals(Operator.AND))
				w(String.format("&& "));
			if (condition.getOperator().equals(Operator.OR))
				w(String.format("|| "));
		}
		condition.getSensor().accept(this);
		w(String.format(" %s %s ", condition.getBinaryOperator().toString(), condition.getValue()));

	}

	@Override
	public void visit(DigitalActuator digitalActuator) {
		switch ((Integer) context.get(BRICKS_MODE)) {
		case SETUP:
			wln(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]", digitalActuator.getPin(),
					digitalActuator.getName()));
			break;
		case LOOP:
			w(String.format("digitalRead(%d)", digitalActuator.getPin()));
			break;
		case STATE:
			wln(String.format("  digitalWrite(%d,%s);", digitalActuator.getPin(),
					((Action) context.get(ACTION)).getValue()));
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(DigitalSensor digitalSensor) {
		switch ((Integer) context.get(BRICKS_MODE)) {
		case SETUP:
			wln(String.format("  pinMode(%d, INPUT); // %s [Actuator]", digitalSensor.getPin(),
					digitalSensor.getName()));
			break;
		case LOOP:
			w(String.format("digitalRead(%d)", digitalSensor.getPin()));
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(LCD lcd) {
		context.put(LCD, lcd);

		switch ((Integer) context.get(BRICKS_MODE)) {
		case SETUP:
			StringJoiner joiner = new StringJoiner(",", "(", ");");
			for (int c : lcd.getConfig())
				joiner.add(String.valueOf(c));
			wln("  lcd = new LiquidCrystal" + joiner.toString());

			joiner = new StringJoiner(",", "(", ");");
			joiner.add(String.valueOf(lcd.getCols()));
			joiner.add(String.valueOf(lcd.getRows()));
			wln("  lcd->begin" + joiner.toString());
			break;
		case LOOP:
			break;
		case STATE:
			wln("  write(\"" + lcd.getMessage() + "\");\n");
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(Joystick joystick) {
		// TODO Auto-generated method stub

	}

}
