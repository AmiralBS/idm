package fr.unice.polytech.idm.arduinoml.dsl

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.Actuator
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.LCD
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Joystick
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Sensor
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.Direction


abstract class Basescript extends Script {
	Model model() {
		return ((DSLBinding)this.getBinding()).getModel()
	}

	/*
	 * 				********************
	 * 				* Brick Management *
	 * 				********************
	 */

	// input "name" on pin
	def input(String name) {
		[on: { pin -> model().createSensor(name, pin) }]
	}

	// output "name" on pin
	def output(String name) {
		[on: { pin -> model().createActuator(name, pin) }]
	}
	
	// lcd "name" bus busPins
	def lcd(String name) {
		[bus: { busPins -> model().createLCD(name, busPins) }]
	}
	
	// joystick on X,Y,B
	def joystick(String name) {
		[on: { x, y, b -> model().createJoystick(name, x, y, b) }]
	}

	/*
	 * 				********************
	 * 				* State Management *
	 * 				********************
	 */

	// state "name" means
	def state(String name) {
		model().createState(name)
		[means: {}]
	}

	// _ actuator value signal
	def _(Actuator actuator) {
		[becomes: { signal ->
				model().createAction(actuator, signal)
			}]
	}
	
	// _ lcd display "message"
	def _(LCD lcd) {
		[display: { message ->
				model().createAction(lcd, message)
			}]
	}

	// initial state
	def initial(State state) {
		model().setInitialState(state)
	}

	/*
	 * 				*************************
	 * 				* Transition Management *
	 * 				*************************
	 */

	// from state1 to state2 when
	def from(State state1) {
		[to: { state2 ->
				model().createTransition(state1,state2)
				[when: {
					}]
			}]
	}

	// _ sensor eq|ne|lt|gt|le|ge value
	def _(Sensor sensor) {
		[eq: {value -> model().createCondition(sensor, BinaryOperator.EQ, value)},
			ne: {value -> model().createCondition(sensor, BinaryOperator.NE, value)},
			lt: {value -> model().createCondition(sensor, BinaryOperator.LT, value)},
			gt: {value -> model().createCondition(sensor, BinaryOperator.GT, value)},
			le: {value -> model().createCondition(sensor, BinaryOperator.LE, value)},
			ge: {value -> model().createCondition(sensor, BinaryOperator.GE, value)}]
	}
	
	/*
	 * 				**********************
	 * 				* Builder Management *
	 * 				**********************
	 */
	
	def bind(Joystick joystick) {
		[to: { lcd -> model().bind(joystick, lcd) }];
	}

	/*
	 * 				*********************
	 * 				* Export Management *
	 * 				*********************
	 */

	// export name
	def export(String name) {
		println(model().generateCode(name).toString())
	}
}
