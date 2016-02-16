package fr.unice.polytech.idm.arduinoml.dsl

import org.codehaus.groovy.control.CompilerConfiguration

import fr.unice.polytech.idm.arduinoml.kernel.structural.value.Direction;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.ESignal;

class DSL {
	private GroovyShell shell
	private CompilerConfiguration configuration
	private DSLBinding binding
	private Basescript basescript
	
	DSL() {
		binding = new DSLBinding()
		binding.setModel(new Model(binding));
		configuration = new CompilerConfiguration()
		configuration.setScriptBaseClass("fr.unice.polytech.idm.arduinoml.dsl.Basescript")
		shell = new GroovyShell(configuration)
		
		binding.setVariable("high", ESignal.HIGH)
		binding.setVariable("low", ESignal.LOW)
		
		binding.setVariable("R", Direction.RIGHT)
		binding.setVariable("L", Direction.LEFT)
		binding.setVariable("U", Direction.UP)
		binding.setVariable("D", Direction.DOWN)
	}
	
	void eval(File scriptFile) {
		Script script = shell.parse(scriptFile)
		
		binding.setScript(script)
		script.setBinding(binding)
		
		script.run()
	}
}
