package fr.unice.polytech.idm.arduinoml.kernel.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.App;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.idm.arduinoml.kernel.generator.ToWiring;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.*;

public class Switch2Buttons {

	public static void main(String[] args) {

		// Declaring elementary bricks
		Sensor button1 = new Sensor();
		button1.setName("button1");
		button1.setPin(9);
		
		Sensor button2 = new Sensor();
		button2.setName("button2");
		button2.setPin(10);

		Actuator led = new Actuator();
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
		switchTheLightOn.setValue(SIGNAL.HIGH);

		Action switchTheLightOff = new Action();
		switchTheLightOff.setActuator(led);
		switchTheLightOff.setValue(SIGNAL.LOW);

		// Binding actions to states
		on.setActions(Arrays.asList(switchTheLightOn));
		off.setActions(Arrays.asList(switchTheLightOff));
		
		Condition button1High = new Condition();
		button1High.setSensor(button1);
		button1High.setValue(SIGNAL.HIGH);
		button1High.setOperator(Operator.AND);
		
		Condition button2High = new Condition();
		button2High.setSensor(button2);
		button2High.setValue(SIGNAL.HIGH);
		
		List<Condition> conditionsOff2On = new ArrayList<>();
		conditionsOff2On.add(button1High);
		conditionsOff2On.add(button2High);
		
		Condition button1Low = new Condition();
		button1Low.setSensor(button1);
		button1Low.setValue(SIGNAL.LOW);
		button1Low.setOperator(Operator.OR);
		
		Condition button2Low = new Condition();
		button2Low.setSensor(button2);
		button2Low.setValue(SIGNAL.LOW);
		
		List<Condition> conditionsOn2Off = new ArrayList<>();
		conditionsOn2Off.add(button1Low);
		conditionsOn2Off.add(button2Low);

		// Creating transitions
		Transition on2off = new Transition();
		on2off.setNext(off);
		on2off.setConditions(conditionsOn2Off);

		Transition off2on = new Transition();
		off2on.setNext(on);
		off2on.setConditions(conditionsOff2On);

		// Binding transitions to states
		on.setTransition(on2off);
		off.setTransition(off2on);

		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Switch!");
		theSwitch.setBricks(Arrays.asList(button1, led ));
		theSwitch.setStates(Arrays.asList(on, off));
		theSwitch.setInitial(off);

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}
