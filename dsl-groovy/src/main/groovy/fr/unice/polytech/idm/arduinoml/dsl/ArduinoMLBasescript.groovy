package fr.unice.polytech.idm.arduinoml.dsl

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator
import fr.unice.polytech.idm.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor


abstract class ArduinoMLBasescript extends Script {
	LCD lcd

	// input "name" on n
	def input(String name) {
		[on: { n -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createSensor(name, n) }]
	}

	// output "name" on n
	def output(String name) {
		[on: { n -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createActuator(name, n) }, off: { n -> println("Et les tirages du loto sont " + n)}]
	}

	def lcd(String name) {
		lcd = new LCD();
		lcd.setName(name);
		return this
	}

	def config(int... values) {
		for(int value : values)
			this.lcd.config.add(value)
		return this
	}

	def dim(int cols, int rows) {
		this.lcd.cols = cols
		this.lcd.rows = rows
		return this
	}

	def col(int n) {
		this.lcd.cols = n
		return this
	}

	def row(int n) {
		this.lcd.row = n
		return this
	}

	def refresh(int n) {
		this.lcd.refresh = n
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createLCD(lcd)
	}

	def _(LCD lcd) {
		[display: { message ->
				Action action = new Action()
				lcd.message = message
				action.setActuator(lcd)
				((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().addActionToLastState(action)
			}]
	}

	// state "name" means actuator becomes signal [and actuator becomes signal]*n
	def state(String name) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name)
		[means: {
			}]
	}

	def _(Actuator actuator) {
		[value: { signal ->
				Action action = new Action()
				action.setActuator(actuator)
				action.setValue(signal)
				((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().addActionToLastState(action)
			}]
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

	def _(Sensor sensor) {
		[eq: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.EQ)},
		ne: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.NE)},
		lt: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.LT)},
		gt: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.GT)},
		le: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.LE)},
		ge: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.GE)}]
	}

	// export name
	def export(String name) {
		println(((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
	}
}
