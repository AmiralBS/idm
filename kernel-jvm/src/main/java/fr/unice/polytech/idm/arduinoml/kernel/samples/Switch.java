package fr.unice.polytech.idm.arduinoml.kernel.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.idm.arduinoml.kernel.generator.ToWiring;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.DigitalActuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.DigitalSensor;

public class Switch {

	public static void main(String[] args) {

		// Declaring elementary bricks
		DigitalSensor button = new DigitalSensor();
		button.setName("button");
		button.setPin(9);

		DigitalActuator led = new DigitalActuator();
		led.setName("LED");
		led.setPin(12);

		// Declaring states
		State on = new State();
		on.setName("on");

		State off = new State();
		off.setName("off");

		// Creating actions
		Action switchTheLightOn = new Action();
		switchTheLightOn.setActuator(led);
		switchTheLightOn.setValue(1);

		Action switchTheLightOff = new Action();
		switchTheLightOff.setActuator(led);
		switchTheLightOff.setValue(0);

		// Binding actions to states
		on.setActions(Arrays.asList(switchTheLightOn));
		off.setActions(Arrays.asList(switchTheLightOff));
		
		Condition buttonHigh = new Condition();
		buttonHigh.setSensor(button);
		buttonHigh.setBinaryOperator(BinaryOperator.EQ);
		buttonHigh.setValue(1);
		
		List<Condition> conditions = new ArrayList<>();
		conditions.add(buttonHigh);

		// Creating transitions
		Transition on2off = new Transition();
		on2off.setNext(off);
		on2off.setConditions(conditions);

		Transition off2on = new Transition();
		off2on.setNext(on);
		off2on.setConditions(conditions);

		// Binding transitions to states
		on.setTransitions(new ArrayList<Transition>() {{ add(on2off); add(on2off); add(on2off); }});
		off.setTransitions(new ArrayList<Transition>() {{ add(off2on); add(on2off); add(on2off); }});

		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Switch!");
		theSwitch.setBricks(Arrays.asList(button, led ));
		theSwitch.setStates(Arrays.asList(on, off));
		theSwitch.setInitial(off);

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}
