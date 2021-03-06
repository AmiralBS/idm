package fr.unice.polytech.idm.arduinoml.kernel.generator;

import java.util.StringJoiner;

import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Attribute;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Brick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.AnalogActuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.DigitalActuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.AnalogSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.DigitalSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Joystick;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {
	private static final String BRICKS_MODE = "brick_mode";
	private static final int GLOBAL = 0;
	private static final int SETUP = 1;
	private static final int STATE = 2;
	private static final int CONDITION = 3;
	private static final int LOOP = 4;

	private static final String CURRENT_LCD = "lcd";
	private static final String CURRENT_ACTION = "action";
	private static final String LIQUID_CRYSTAL_IMPORTED = "liquid_crystal_imported";

	public ToWiring() {
		this.result = new StringBuffer();
	}

	private void wln(String s) {
		result.append(String.format("%s\n", s));
	}

	private void wln() {
		wln("");
	}

	private void w(String s) {
		result.append(String.format("%s", s));
	}

	@Override
	public void visit(App app) {
		context.put(LIQUID_CRYSTAL_IMPORTED, false);

		context.put(BRICKS_MODE, GLOBAL);
		wln("// Wiring code generated from an ArduinoML model");
		wln(String.format("// Application name: %s\n", app.getName()));

		for (Brick brick : app.getBricks()) {
			brick.accept(this);
		}

		for (Attribute attribute : app.getAttributes()) {
			attribute.accept(this);
		}

		wln();
		wln("void setup(){");
		context.put(BRICKS_MODE, SETUP);
		for (Brick brick : app.getBricks()) {
			brick.accept(this);
		}
		wln("}\n");

		wln("long time = 0; long debounce = 200;\n");

		context.put(BRICKS_MODE, STATE);
		for (State state : app.getStates()) {
			state.accept(this);
		}

		context.put(BRICKS_MODE, LOOP);
		if (app.getInitial() != null)
			wln(String.format("int state = %d;", app.getInitial().getIdent()));
		wln("void loop() {");

		if (!app.getStates().isEmpty()) {
			wln("  switch(state) {");
			for (State state : app.getStates()) {
				state.accept(this);
			}
			wln("    default:");
			wln("      break;");
			wln("  }");
		}
		wln("}");
	}

	@Override
	public void visit(State state) {
		switch ((Integer) context.get(BRICKS_MODE)) {
		case STATE:
			wln(String.format("int state_%s() {", state.getName()));

			for (Action action : state.getActions()) {
				context.put(CURRENT_ACTION, action);
				action.accept(this);
			}

			if (state.getTransitions().isEmpty()) {
				wln(String.format("  return %d; // to %s();", state.getIdent(), state.getName()));
			} else {
				wln();
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
				wln(String.format("    return %d; // to %s();", state.getIdent(), state.getName()));
				wln("  }");
			}
			wln("}\n");
			break;
		case LOOP:
			wln(String.format("    case %d:", state.getIdent()));
			wln(String.format("      state = state_%s();", state.getName()));
			wln("      break;");
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(Action action) {
		action.getActionable().accept(this);
	}

	@Override
	public void visit(Transition transition) {
		w("(");
		for (Condition condition : transition.getConditions()) {
			condition.accept(this);
		}
		wln("&& guard) {");
		wln("    time = millis();");

		for (Action action : transition.getActions()) {
			action.accept(this);
		}
		wln(String.format("    return %d; // to %s();", transition.getNext().getIdent(),
				transition.getNext().getName()));
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
		context.put(BRICKS_MODE, CONDITION);
		condition.getConditionable().accept(this);
		context.put(BRICKS_MODE, STATE);
		w(String.format(" %s %s ", condition.getBinaryOperator().toString(), condition.getValue()));

	}

	@Override
	public void visit(DigitalActuator digitalActuator) {
		switch ((Integer) context.get(BRICKS_MODE)) {
		case SETUP:
			wln(String.format("  pinMode(%d, OUTPUT); // %s [DigitalActuator]", digitalActuator.getPin(),
					digitalActuator.getName()));
			break;
		case LOOP:
			w(String.format("digitalRead(%d)", digitalActuator.getPin()));
			break;
		case STATE:
			wln(String.format("  digitalWrite(%d,%s);", digitalActuator.getPin(),
					((Action) context.get(CURRENT_ACTION)).getValue()));
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(AnalogActuator analogActuator) {
		switch ((Integer) context.get(BRICKS_MODE)) {
		case SETUP:
			wln(String.format("  pinMode(A%d, OUTPUT); // %s [AnalogActuator]", analogActuator.getPin(),
					analogActuator.getName()));
			break;
		case LOOP:
			w(String.format("analogRead(%d)", analogActuator.getPin()));
			break;
		case STATE:
			wln(String.format("  analogWrite(%d,%s);", analogActuator.getPin(),
					((Action) context.get(CURRENT_ACTION)).getValue()));
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(DigitalSensor digitalSensor) {
		switch ((Integer) context.get(BRICKS_MODE)) {
		case SETUP:
			wln(String.format("  pinMode(%d, INPUT); // %s [DigitalSensor]", digitalSensor.getPin(),
					digitalSensor.getName()));
			break;
		case CONDITION:
		case LOOP:
			w(String.format("digitalRead(%d)", digitalSensor.getPin()));
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(AnalogSensor analogSensor) {
		switch ((Integer) context.get(BRICKS_MODE)) {
		case SETUP:
			wln(String.format("  pinMode(A%d, INPUT); // %s [AnalogSensor]", analogSensor.getPin(),
					analogSensor.getName()));
			break;
		case CONDITION:
			w(String.format("analogRead(%d)", analogSensor.getPin()));
			break;
		case STATE:
			wln(String.format("  analogWrite(%d,%s);", analogSensor.getPin(),
					((Action) context.get(CURRENT_ACTION)).getValue()));
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(LCD lcd) {
		context.put(CURRENT_LCD, lcd);

		switch ((Integer) context.get(BRICKS_MODE)) {
		case GLOBAL:
			if (!(Boolean) context.get(LIQUID_CRYSTAL_IMPORTED)) {
				wln("#include <LiquidCrystal.h>");
				wln();
				wln("void write(LiquidCrystal &lcd, int input, int refresh){");
				wln("  write(lcd, input + \"\", refresh);");
				wln("}");
				wln();
				wln("void write(LiquidCrystal &lcd, String input, int refresh){");
				wln("  lcd.clear();");
				wln("  lcd.setCursor(0,0);");
				wln("  lcd.print(input);");
				wln("  delay(refresh);");
				wln("}");
				wln();
				context.put(LIQUID_CRYSTAL_IMPORTED, true);
			}
			StringJoiner joiner = new StringJoiner(",", "(", ");");
			for (int c : lcd.getConfig())
				joiner.add(String.valueOf(c));
			wln("LiquidCrystal " + lcd.getName() + joiner.toString());
			break;
		case SETUP:
			joiner = new StringJoiner(",", "(", ");");
			joiner.add(String.valueOf(lcd.getCols()));
			joiner.add(String.valueOf(lcd.getRows()));
			wln("  " + lcd.getName() + ".begin" + joiner.toString());
			break;
		case STATE:
			wln("  write(" + lcd.getName() + ", " + ((Action) context.get(CURRENT_ACTION)).getValue() + ", "
					+ lcd.getRefresh() + ");");
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(Joystick joystick) {
		joystick.getHorizontal().accept(this);
		joystick.getVertical().accept(this);
		joystick.getButton().accept(this);
	}

	@Override
	public void visit(Attribute attribute) {
		switch ((Integer) context.get(BRICKS_MODE)) {
		case GLOBAL:
			w(String.format("%s %s", attribute.getType(), attribute.getName()));
			if (attribute.getInitial() != null)
				wln(String.format(" = %s;", attribute.getInitial()));
			else
				wln(";");
			break;
		case CONDITION:
			w(String.format("%s", attribute.getName()));
			break;
		case STATE:
			wln(String.format("    %s %s;", attribute.getName(), attribute.getAction()));
			break;
		default:
			break;
		}
	}
}
