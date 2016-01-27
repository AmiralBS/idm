package fr.unice.polytech.idm.arduinoml.dsl

import fr.unice.polytech.idm.arduinoml.business.Direction;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State
import fr.unice.polytech.idm.arduinoml.kernel.samples.Switch;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator
import fr.unice.polytech.idm.arduinoml.kernel.structural.Joystick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor


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

	def joystick(String name) {
		[on: { x, y, b -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createJoystick(name, x, y, b) }]
	}

	def joystick(Direction direction) {
		if(! neutral_added) {
			state "neutral" means
			_ screen display "waiting input"
			neutral_added = true;
		}
		switch(direction) {
			case left :
				state "left" means

				from left to neutral when
				_ joyX lt 700

				from neutral to left when
				_ joyX gt 700
				break;
			case right :
				state "right" means

				from right to neutral when
				_ joyX gt 200

				from neutral to right when
				_ joyX lt 200
				break;
			case up :
				state "up" means

				from up to neutral when
				_ joyY lt 700

				from neutral to up when
				_ joyY lt 200
				break;
			case down :
				state "down" means

				from down to neutral when
				_ joyY gt 200

				from neutral to down when
				_ joyY gt 700
				break;
			case pushed :
				state "pushed" means

				from pushed to neutral when
				_ joyB eq 0

				from neutral to pushed when
				_ joyB ne 0
				break;
		}
		[means: {
			}]
	}

	// state "name" means actuator becomes signal [and actuator becomes signal]*n
	def state(String name) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name)
		[means: {
			}]
	}

	def lcd(String name) {
		[on_bus: { n -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createLCD(name, n) }]
	}

	def _(LCD lcd) {
		[display: { message ->
				((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().addActionToLastState(lcd, message)
			}]
	}

	def _(Actuator actuator) {
		[value: { signal ->
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
