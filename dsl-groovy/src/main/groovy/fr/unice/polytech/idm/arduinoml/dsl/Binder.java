package fr.unice.polytech.idm.arduinoml.dsl;

import fr.unice.polytech.idm.arduinoml.exception.ElementNotFoundException;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Joystick;

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
}
