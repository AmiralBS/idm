package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.Map;

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
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
		if ("and".equals(name)) {
			model.setOperator(Operator.AND);
			return script;
		}
		if ("or".equals(name)) {
			model.setOperator(Operator.OR);
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
