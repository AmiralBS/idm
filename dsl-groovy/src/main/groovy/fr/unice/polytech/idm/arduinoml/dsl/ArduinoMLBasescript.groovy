package fr.unice.polytech.idm.arduinoml.dsl

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Konami
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator
import fr.unice.polytech.idm.arduinoml.kernel.structural.DigitalSensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Direction
import fr.unice.polytech.idm.arduinoml.kernel.structural.IKonamiCode
import fr.unice.polytech.idm.arduinoml.kernel.structural.Joystick
import fr.unice.polytech.idm.arduinoml.kernel.structural.KonamiSensor
import fr.unice.polytech.idm.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor
import fr.unice.polytech.idm.arduinoml.business.DirectionMap;


abstract class ArduinoMLBasescript extends Script {
	boolean neutral_added = false;

	// input "name" on n
	def input(String name) {
		[on: { n -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createSensor(name, n) }]
	}

	// output "name" on n || output "name off n
	def output(String name) {
		[on: { n -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createActuator(name, n) }]
	}

	def konami(Joystick joy, DigitalSensor ... digitalSensors) {
		if(digitalSensors == null ) {
			((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createKonami(joy, new ArrayList())
		} else {
			((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createKonami(joy, new ArrayList(Arrays.asList(digitalSensors)))
		}
	}

	def konami(Joystick joy) {
		konami(joy, null)
	}

	def code(IKonamiCode ... konamSensors) {
		//TODO use groovy to define the different states
		IKonamiCode inputTransition = null;
		int i = 1;
		for(IKonamiCode s : konamSensors){
			if(s instanceof Direction) {
				Direction dir = (Direction) s;
				state dir.toString() + "_" + (i++) means
				if(inputTransition != null) {
					if(inputTransition instanceof Direction) {
						String name = ((Direction) inputTransition).toString()
						_ lcd display name.toLowerCase()//""+DirectionMap.getActions((Direction) inputTransition)
					}
				}
			} else if (s instanceof KonamiSensor) {
				KonamiSensor konSensor = (KonamiSensor) s;
			}
			inputTransition = s;
			//			((Konami)this.binding.getVariable("konami"))
		}
	}

	// Pour gérer plusieurs code Konami
	//def code(String codeName, KonamiSensor ... ksensors) {
	//}

	def joystick(int x, int y, int b) {
		((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createJoystick(x, y, b)
		init_joystick()
	}

	def init_joystick() {
		joystick left
		joystick right
		joystick up
		joystick down
		joystick pushed
		initial neutralState
	}

	def joystick(Direction direction) {
		if(! neutral_added) {
			state "neutralState" means
			_ lcd display "waiting input"
			neutral_added = true;
		}
		switch(direction) {
			case left :
				state "leftState" means
				_ lcd display "left"

				from leftState to neutralState when
				_ joystickX lt 700

				from neutralState to leftState when
				_ joystickX gt 700
				break;
			case right :
				state "rightState" means
				_ lcd display "right"

				from rightState to neutralState when
				_ joystickX gt 200

				from neutralState to rightState when
				_ joystickX lt 200
				break;
			case up :
				state "upState" means
				_ lcd display "up"

				from upState to neutralState when
				_ joystickY gt 200

				from neutralState to upState when
				_ joystickY lt 200
				break;
			case down :
				state "downState" means
				_ lcd display "down"

				from downState to neutralState when
				_ joystickY lt 700

				from neutralState to downState when
				_ joystickY gt 700
				break;
			case pushed :
				state "pushedState" means
				_ lcd display "pushed"

				from pushedState to neutralState when
				_ joystickB eq 0

				from neutralState to pushedState when
				_ joystickB ne 0
				break;
		}
	}

	// state "name" means actuator becomes signal [and actuator becomes signal]*n
	def state(String name) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name)
		[means: {
			}]
	}

	def lcd(n) {
		((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createLCD(n)
	}

	def _(LCD lcd) {
		[display: { message ->
				((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().addActionToLastState(lcd, message)
			}]
	}

	def _(Actuator actuator) {
		[becomes: { signal ->
				((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().addActionToLastState(actuator, signal)
			}]
	}

	def _(Sensor sensor) {
		[eq: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.EQ)},
			ne: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.NE)},
			lt: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.LT)},
			gt: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.GT)},
			le: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.LE)},
			ge: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.GE)}]
	}

	// initial state
	def initial(State state) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().setInitialState(state)
	}

	// from state1 to state2 when sensor becomes signal
	def from(State state1) {
		[to: { state2 ->
				((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1,state2)
				[when: {
					}]}]
	}

	// export name
	def export(String name) {
		println(((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
	}
}
