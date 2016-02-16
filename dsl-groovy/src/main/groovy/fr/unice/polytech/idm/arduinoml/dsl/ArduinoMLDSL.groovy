package fr.unice.polytech.idm.arduinoml.dsl

import org.codehaus.groovy.control.CompilerConfiguration

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.ESignal;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Direction;

class ArduinoMLDSL {
	private GroovyShell shell
	private CompilerConfiguration configuration
	private ArduinoMLBinding binding
	private ArduinoMLBasescript basescript

	ArduinoMLDSL() {
		binding = new ArduinoMLBinding()
		binding.setGroovuinoMLModel(new ArduinoMLModel(binding));
		configuration = new CompilerConfiguration()
		configuration.setScriptBaseClass("fr.unice.polytech.idm.arduinoml.dsl.ArduinoMLBasescript")
		shell = new GroovyShell(configuration)

		binding.setVariable("high", ESignal.HIGH)
		binding.setVariable("low", ESignal.LOW)
		binding.setVariable("right", Direction.RIGHT)
		binding.setVariable("left", Direction.LEFT)
		binding.setVariable("up", Direction.UP)
		binding.setVariable("down", Direction.DOWN)
		binding.setVariable("pushed", Direction.PUSHED)
	}

	void eval(File scriptFile) {
		Script script = shell.parse(scriptFile)

		binding.setScript(script)
		script.setBinding(binding)

		script.run()
	}
}
