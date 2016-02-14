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
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.DigitalActuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.AnalogSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.DigitalSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Joystick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EInt;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EString;

public class Switch {

	public static void main(String[] args) {

		// Declaring elementary bricks
		DigitalSensor button = new DigitalSensor();
		button.setName("button");
		button.setPin(9);

		DigitalActuator led = new DigitalActuator();
		led.setName("LED");
		led.setPin(12);
		
		LCD lcd = new LCD();
		lcd.setName("lcd");
		lcd.setCols(16);
		lcd.setRows(2);
		List<Integer> config = new ArrayList<>();
		config.add(11);
		config.add(12);
		config.add(13);
		lcd.setConfig(config);
		
		LCD lcd2 = new LCD();
		lcd2.setName("lcd2");
		lcd2.setCols(16);
		lcd2.setRows(2);
		lcd2.setConfig(config);
		
		Joystick joy = new Joystick();
		joy.setName("joy");
		DigitalSensor buttonJoy = new DigitalSensor();
		buttonJoy.setName("buttonJoy");
		buttonJoy.setPin(15);
		joy.setButton(buttonJoy);
		AnalogSensor vertical = new AnalogSensor();
		vertical.setName("vertJoy");
		vertical.setPin(16);
		joy.setVertical(vertical);
		AnalogSensor horizontal = new AnalogSensor();
		horizontal.setName("horJoy");
		horizontal.setPin(17);
		joy.setHorizontal(horizontal);

		// Declaring states
		State on = new State();
		on.setName("on");

		State off = new State();
		off.setName("off");

		// Creating actions
		Action switchTheLightOn = new Action();
		switchTheLightOn.setActuator(lcd);
		switchTheLightOn.setValue(new EString("hello coucou les copains"));

		Action switchTheLightOff = new Action();
		switchTheLightOff.setActuator(lcd2);
		switchTheLightOff.setValue(new EString("hello mais tu n'es pas mon copain oO"));

		// Binding actions to states
		on.setActions(Arrays.asList(switchTheLightOn));
		off.setActions(Arrays.asList(switchTheLightOff));
		
		Condition buttonHigh = new Condition();
		buttonHigh.setSensor(joy.getHorizontal());
		buttonHigh.setBinaryOperator(BinaryOperator.LT);
		buttonHigh.setValue(new EInt(200));
		
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
		theSwitch.setBricks(Arrays.asList(button, led, lcd, lcd2, joy ));
		theSwitch.setStates(Arrays.asList(on, off));
		theSwitch.setInitial(off);

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}
