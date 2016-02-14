package fr.unice.polytech.idm.arduinoml.dsl

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State
import fr.unice.polytech.idm.arduinoml.kernel.structural.actuator.Actuator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Sensor;


abstract class Basescript extends Script {
	Model model() {
		return ((DSLBinding)this.getBinding()).getModel()
	}

	/*
	 * 				********************
	 * 				* Brick Management *
	 * 				********************
	 */

	// input "name" on n
	def input(String name) {
		[on: { n -> model().createSensor(name, n) }]
	}

	// output "name" on n
	def output(String name) {
		[on: { n -> model().createActuator(name, n) }]
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

	def _(Sensor sensor) {
		[eq: {value -> model().createCondition(sensor, BinaryOperator.EQ, value)},
			ne: {value -> model().createCondition(sensor, BinaryOperator.NE, value)},
			lt: {value -> model().createCondition(sensor, BinaryOperator.LT, value)},
			gt: {value -> model().createCondition(sensor, BinaryOperator.GT, value)},
			le: {value -> model().createCondition(sensor, BinaryOperator.LE, value)},
			ge: {value -> model().createCondition(sensor, BinaryOperator.GE, value)}]
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
