package fr.unice.polytech.idm.arduinoml.business

import fr.unice.polytech.idm.arduinoml.dsl.ArduinoMLBasescript
import fr.unice.polytech.idm.arduinoml.dsl.ArduinoMLBinding
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Condition

class SensorManagement {
	ArduinoMLBasescript script
	Condition condition

	SensorManagement(ArduinoMLBasescript script, Condition condition) {
		this.script = script
		this.condition = condition
	}

	def eq(int n) {
		condition.value = n
		condition.binaryOperator = BinaryOperator.EQ
		((ArduinoMLBinding) script.getBinding()).getGroovuinoMLModel().addConditionToLastTransition(condition)
		return script
	}

	def ne(int n) {
		condition.value = n
		condition.binaryOperator = BinaryOperator.NE
		((ArduinoMLBinding) script.getBinding()).getGroovuinoMLModel().addConditionToLastTransition(condition)
		return script
	}

	def lt(int n) {
		condition.value = n
		condition.binaryOperator = BinaryOperator.LT
		((ArduinoMLBinding) script.getBinding()).getGroovuinoMLModel().addConditionToLastTransition(condition)
		return script
	}

	def gt(int n) {
		condition.value = n
		condition.binaryOperator = BinaryOperator.GT
		((ArduinoMLBinding) script.getBinding()).getGroovuinoMLModel().addConditionToLastTransition(condition)
		return script
	}

	def le(int n) {
		condition.value = n
		condition.binaryOperator = BinaryOperator.LE
		((ArduinoMLBinding) script.getBinding()).getGroovuinoMLModel().addConditionToLastTransition(condition)
		return script
	}

	def ge(int n) {
		condition.value = n
		condition.binaryOperator = BinaryOperator.GE
		((ArduinoMLBinding) script.getBinding()).getGroovuinoMLModel().addConditionToLastTransition(condition)
		return script
	}
}
