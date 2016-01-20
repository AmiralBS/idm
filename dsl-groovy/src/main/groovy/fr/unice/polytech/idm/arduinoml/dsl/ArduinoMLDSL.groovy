package fr.unice.polytech.idm.arduinoml.dsl

import org.codehaus.groovy.control.CompilerConfiguration

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.structural.SIGNAL

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
		
		binding.setVariable("high", SIGNAL.HIGH)
		binding.setVariable("low", SIGNAL.LOW)
		binding.setVariable("and", Operator.AND)
		binding.setVariable("or", Operator.OR)
		binding.setVariable("none", Operator.NONE)
	}
	
	void eval(File scriptFile) {
		Script script = shell.parse(scriptFile)
		
		binding.setScript(script)
		script.setBinding(binding)
		
		script.run()
	}
}
