package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.Map;

import groovy.lang.Binding;
import groovy.lang.Script;

public class ArduinoMLBinding extends Binding {
	// can be useful to return the script in case of syntax trick
	private Script script;
	
	private ArduinoMLModel model;
	
	public ArduinoMLBinding() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
	public ArduinoMLBinding(Map variables) {
		super(variables);
	}
	
	public ArduinoMLBinding(Script script) {
		super();
		this.script = script;
	}
	
	public void setScript(Script script) {
		this.script = script;
	}
	
	public void setGroovuinoMLModel(ArduinoMLModel model) {
		this.model = model;
	}
	
	public Object getVariable(String name) {
		// Easter egg (to show you this trick: seb is now a keyword!)
		if ("seb".equals(name)) {
			// could do something else like: ((App) this.getVariable("app")).action();
			System.out.println("Seb, c'est bien");
			return script;
		}
		return super.getVariable(name);
	}
	
	public void setVariable(String name, Object value) {
		super.setVariable(name, value);
	}
	
	public ArduinoMLModel getGroovuinoMLModel() {
		return this.model;
	}
}
