package fr.unice.polytech.idm.arduinoml.dsl

import org.codehaus.groovy.control.CompilerConfiguration

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor;

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

		binding.setVariable("high", 1)
		binding.setVariable("low", 0)		
	}
	
	void eval(File scriptFile) {
		Script script = shell.parse(scriptFile)

		binding.setScript(script)
		script.setBinding(binding)

		script.run()
	}
}
