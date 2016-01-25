package fr.unice.polytech.idm.arduinoml.kernel.generator;

import fr.unice.polytech.idm.arduinoml.kernel.BrickApp;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Brick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class BricksToWiring extends ToWiring {
	
	protected final static String CURRENT_STATE = "current_state";

	public BricksToWiring() {
		super();
	}
	
	@Override
	public void visit(BrickApp app) {
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
			if (condition.getOperator() != null) {
				if (condition.getOperator().equals(Operator.AND))
					w(String.format("&& "));
				if (condition.getOperator().equals(Operator.OR))
					w(String.format("|| "));
			}
			w(String.format("digitalRead(%d) == %s ", condition.getSensor().getPin(), condition.getValue()));
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
