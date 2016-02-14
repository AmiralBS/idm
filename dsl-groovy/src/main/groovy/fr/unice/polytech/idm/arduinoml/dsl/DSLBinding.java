package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.Map;

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import groovy.lang.Binding;
import groovy.lang.Script;

public class DSLBinding extends Binding implements ConfigName {
	// can be useful to return the script in case of syntax trick
	private Script script;
	
	private Model model;
	
	public DSLBinding() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
	public DSLBinding(Map variables) {
		super(variables);
	}
	
	public DSLBinding(Script script) {
		super();
		this.script = script;
	}
	
	public void setScript(Script script) {
		this.script = script;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public Object getVariable(String name) {
		if ("and".equals(name)) {
			setVariable(CURRENT_OPERATOR, Operator.AND);
			return script;
		}
		if ("or".equals(name)) {
			setVariable(CURRENT_OPERATOR, Operator.OR);
			return script;
		}
		return super.getVariable(name);
	}
	
	public void setVariable(String name, Object value) {
		super.setVariable(name, value);
	}
	
	public Model getModel() {
		return this.model;
	}
}
