package fr.unice.polytech.idm.arduinoml.dsl

import org.codehaus.groovy.control.CompilerConfiguration

class ArduinoMLDSL {
	private GroovyShell shell
	private CompilerConfiguration configuration
	private ArduinoMLBinding binding
	private ArduinoMLBasescript basescript
	
	ArduinoMLDSL() {
		binding = new ArduinoMLBinding()
		binding.setGroovuinoMLModel(new ArduinoMLModel(binding));
		configuration = new CompilerConfiguration()
		configuration.setScriptBaseClass("main.groovy.groovuinoml.dsl.GroovuinoMLBasescript")
		shell = new GroovyShell(configuration)
		
		binding.setVariable("high", SIGNAL.HIGH)
		binding.setVariable("low", SIGNAL.LOW)
	}
	
	void eval(File scriptFile) {
		Script script = shell.parse(scriptFile)
		
		binding.setScript(script)
		script.setBinding(binding)
		
		script.run()
	}
}
