package fr.unice.polytech.idm.arduinoml.dsl

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Action
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor


abstract class ArduinoMLBasescript extends Script {
	// input "name" on n
	def input(String name) {
		[on: { n -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createSensor(name, n) }]
	}

	// output "name" on n
	def output(String name) {
		[on: { n -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createActuator(name, n) }]
	}

	// state "name" means actuator becomes signal [and actuator becomes signal]*n
	def state(String name) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name)
		[means: {
			}]
	}
	
	def screen(String name) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createScreen(name)
	}
	
	def pins(List pins) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().setScreenPins(pins)
	}
	
	def width(int n) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().setScreenWidth(n)
	}
	
	def height(int n) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().setScreenHeight(n)
	}
	
	def refresh(int n) {
		((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().setScreenRefresh(n)
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
		[becomes: {signal ->
				Condition condition = new Condition()
				condition.setSensor(sensor)
				condition.setValue(signal)
				((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().addConditionToLastTransition(condition)
			}]
	}

	// export name
	def export(String name) {
		println(((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
	}
}
