package fr.unice.polytech.idm.arduinoml.kernel.generator;

import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.idm.arduinoml.kernel.structural.*;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {

	private final static String CURRENT_STATE = "current_state";

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

		wln("void setup(){");
		for (Brick brick : app.getBricks()) {
			brick.accept(this);
		}
		wln("}\n");

		wln("long time = 0; long debounce = 200;\n");

		for (State state : app.getStates()) {
			state.accept(this);
		}

		wln("void loop() {");
		wln(String.format("  state_%s();", app.getInitial().getName()));
		wln("}");
	}

	@Override
	public void visit(Actuator actuator) {
		wln(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]", actuator.getPin(), actuator.getName()));
	}

	@Override
	public void visit(Sensor sensor) {
		wln(String.format("  pinMode(%d, INPUT);  // %s [Sensor]", sensor.getPin(), sensor.getName()));
	}

	@Override
	public void visit(State state) {
		wln(String.format("void state_%s() {", state.getName()));
		for (Action action : state.getActions()) {
			action.accept(this);
		}
		wln("  boolean guard = millis() - time > debounce;");
		context.put(CURRENT_STATE, state);
		state.getTransition().accept(this);
		wln("}\n");

	}

	@Override
	public void visit(Transition transition) {
		w(String.format("  if("));
		for (Condition condition : transition.getConditions()) {
			w(String.format("digitalRead(%d) == %s ", condition.getSensor().getPin(), condition.getValue()));
			if (condition.getOperator() != null) {
				if (condition.getOperator().equals(Operator.AND))
					w(String.format("&& "));
				if (condition.getOperator().equals(Operator.OR))
					w(String.format("|| "));
			}
		}
		wln(String.format("&& guard) {"));
		wln("    time = millis();");
		wln(String.format("    state_%s();", transition.getNext().getName()));
		wln("  } else {");
		wln(String.format("    state_%s();", ((State) context.get(CURRENT_STATE)).getName()));
		wln("  }");
	}

	@Override
	public void visit(Action action) {
		wln(String.format("  digitalWrite(%d,%s);", action.getActuator().getPin(), action.getValue()));
	}

}
