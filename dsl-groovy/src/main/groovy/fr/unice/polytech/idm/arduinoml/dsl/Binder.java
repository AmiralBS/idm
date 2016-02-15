package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.exception.ElementNotFoundException;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.IKonamiCode;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Joystick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.KonamiSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.Direction;

public class Binder {
	private Model model;

	public Binder(Model model) {
		this.model = model;
	}

	public void bind(Joystick joystick, LCD lcd) throws ElementNotFoundException {
		/*
		 * States
		 */
		State neutral = model.createState("neutral");
		model.createAction(lcd, "waiting input");

		State left = model.createState("left");
		model.createAction(lcd, "left");

		State right = model.createState("right");
		model.createAction(lcd, "right");

		State up = model.createState("up");
		model.createAction(lcd, "up");

		State down = model.createState("down");
		model.createAction(lcd, "down");

		State pushed = model.createState("pushed");
		model.createAction(lcd, "pushed");

		/*
		 * Transitions
		 */
		model.createTransition(left, neutral);
		model.createCondition(joystick.getHorizontal(), BinaryOperator.LT, 700);
		model.createTransition(neutral, left);
		model.createCondition(joystick.getHorizontal(), BinaryOperator.GT, 700);

		model.createTransition(right, neutral);
		model.createCondition(joystick.getHorizontal(), BinaryOperator.GT, 200);
		model.createTransition(neutral, right);
		model.createCondition(joystick.getHorizontal(), BinaryOperator.LT, 200);

		model.createTransition(up, neutral);
		model.createCondition(joystick.getVertical(), BinaryOperator.GT, 200);
		model.createTransition(neutral, up);
		model.createCondition(joystick.getVertical(), BinaryOperator.LT, 200);

		model.createTransition(down, neutral);
		model.createCondition(joystick.getVertical(), BinaryOperator.LT, 700);
		model.createTransition(neutral, down);
		model.createCondition(joystick.getVertical(), BinaryOperator.GT, 700);

		model.createTransition(pushed, neutral);
		model.createCondition(joystick.getButton(), BinaryOperator.EQ, 0);
		model.createTransition(neutral, pushed);
		model.createCondition(joystick.getButton(), BinaryOperator.NE, 0);

		model.setInitialState(neutral);
	}

	public void bind(Joystick joystick, LCD lcd, List<IKonamiCode> codes) throws ElementNotFoundException {
		List<State> states = new ArrayList<>();
		State current;
		String nameState;
		int i = 0;

		for (IKonamiCode code : codes) {
			nameState = "step_" + (++i);
			current = model.createState(nameState);
			model.createAction(lcd, nameState);
			states.add(current);
		}

		State fail = model.createState("fail");
		model.createAction(lcd, "fail");

		State valid = model.createState("valid");
		model.createAction(lcd, "valid");
		
		State success = model.createState("success");
		model.createAction(lcd, "success");
		
		Direction direction;
		List<IKonamiCode> codesCopy;
		for (i = 0; i < states.size() - 1; i++) {
			if (codes.get(i) instanceof Direction) {
				direction = (Direction) codes.get(i);

				model.createTransition(states.get(i), states.get(i + 1));
				next(direction, joystick);
				
				model.createTransition(states.get(i), fail);
				fail(direction, joystick);
			} else if (codes.get(i) instanceof KonamiSensor) {
				// TODO
			}
		}
		
		if (codes.get(codes.size() - 1) instanceof Direction) {
			direction = (Direction) codes.get(codes.size() - 1);
			
			model.createTransition(states.get(states.size() - 1), valid);
			next(direction, joystick);
						
			model.createTransition(states.get(states.size() - 1), fail);
			fail(direction, joystick);
		} else if (codes.get(codes.size() - 1) instanceof KonamiSensor) {
			// TODO
		}	
		
		model.createTransition(valid, success);
		next(Direction.PUSHED, joystick);
		
		model.createTransition(valid, fail);
		fail(Direction.PUSHED, joystick);
		
		model.setInitialState(states.get(0));
	}

	private void next(Direction direction, Joystick joystick) throws ElementNotFoundException {
		switch (direction) {
		case LEFT:
			model.createCondition(joystick.getHorizontal(), BinaryOperator.GT, 700);
			break;
		case RIGHT:
			model.createCondition(joystick.getHorizontal(), BinaryOperator.LT, 200);
			break;
		case UP:
			model.createCondition(joystick.getVertical(), BinaryOperator.LT, 200);
			break;
		case DOWN:
			model.createCondition(joystick.getVertical(), BinaryOperator.GT, 700);
			break;
		case PUSHED:
			model.createCondition(joystick.getButton(), BinaryOperator.NE, 0);
			break;
		}
	}
	
	private void fail(Direction direction, Joystick joystick) throws ElementNotFoundException {
		switch (direction) {
		case LEFT:
			model.createCondition(joystick.getHorizontal(), BinaryOperator.LT, 200, Operator.OR); // R
			model.createCondition(joystick.getVertical(), BinaryOperator.LT, 200, Operator.OR); // U
			model.createCondition(joystick.getButton(), BinaryOperator.NE, 0, Operator.OR); // P
			model.createCondition(joystick.getVertical(), BinaryOperator.GT, 700); // D
			break;
		case RIGHT:
			model.createCondition(joystick.getHorizontal(), BinaryOperator.GT, 700, Operator.OR); // L
			model.createCondition(joystick.getVertical(), BinaryOperator.LT, 200, Operator.OR); // U
			model.createCondition(joystick.getButton(), BinaryOperator.NE, 0, Operator.OR); // P
			model.createCondition(joystick.getVertical(), BinaryOperator.GT, 700); // D
			break;
		case UP:
			model.createCondition(joystick.getHorizontal(), BinaryOperator.GT, 700, Operator.OR); // L
			model.createCondition(joystick.getHorizontal(), BinaryOperator.LT, 200, Operator.OR); // R
			model.createCondition(joystick.getButton(), BinaryOperator.NE, 0, Operator.OR); // P
			model.createCondition(joystick.getVertical(), BinaryOperator.GT, 700); // D
			break;
		case DOWN:
			model.createCondition(joystick.getHorizontal(), BinaryOperator.GT, 700, Operator.OR); // L
			model.createCondition(joystick.getHorizontal(), BinaryOperator.LT, 200, Operator.OR); // R
			model.createCondition(joystick.getVertical(), BinaryOperator.LT, 200, Operator.OR); // U
			model.createCondition(joystick.getButton(), BinaryOperator.NE, 0); // P
			break;
		case PUSHED:
			model.createCondition(joystick.getHorizontal(), BinaryOperator.GT, 700, Operator.OR); // L
			model.createCondition(joystick.getHorizontal(), BinaryOperator.LT, 200, Operator.OR); // R
			model.createCondition(joystick.getVertical(), BinaryOperator.LT, 200, Operator.OR); // U
			model.createCondition(joystick.getVertical(), BinaryOperator.GT, 700); // D
			break;
		}
	}
}
